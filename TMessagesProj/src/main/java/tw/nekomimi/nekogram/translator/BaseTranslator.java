package tw.nekomimi.nekogram.translator;

import android.text.TextUtils;
import android.util.Pair;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.collection.LruCache;
import androidx.core.content.ContextCompat;

import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;

import org.telegram.messenger.ApplicationLoader;
import org.telegram.messenger.FileLog;
import org.telegram.messenger.LocaleController;
import org.telegram.messenger.R;
import org.telegram.tgnet.TLRPC;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.Callable;

import okhttp3.Cache;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import tw.nekomimi.nekogram.NekoConfig;

abstract public class BaseTranslator {

    private final LruCache<Pair<Object, String>, Result> cache = new LruCache<>(200);

    abstract protected Result translate(String query, String fl, String tl) throws Exception;

    abstract public List<String> getTargetLanguages();

    public String convertLanguageCode(String language, String country) {
        return language;
    }

    public String convertLanguageCode(String code, boolean reverse) {
        return code;
    }

    void startTask(Object query, String fromLang, String toLang, Translator.TranslateCallBack translateCallBack) {
        var result = cache.get(Pair.create(query, toLang));
        if (result != null) {
            translateCallBack.onSuccess(result.translation, result.sourceLanguage == null ? fromLang : convertLanguageCode(result.sourceLanguage, true), toLang);
        } else {
            TranslateTask translateTask = new TranslateTask(query, fromLang, toLang, translateCallBack);
            ListenableFuture<Object> future = Translator.getExecutorService().submit(translateTask);
            Futures.addCallback(future, new FutureCallback<>() {
                @Override
                public void onSuccess(Object result) {
                    translateTask.onPostExecute(result);
                }

                @Override
                public void onFailure(@NonNull Throwable t) {
                    translateTask.onPostExecute(t);
                }
            }, ContextCompat.getMainExecutor(ApplicationLoader.applicationContext));
        }
    }

    protected String URLEncode(String s) throws UnsupportedEncodingException {
        //noinspection CharsetObjectCanBeUsed
        return URLEncoder.encode(s, "UTF-8");
    }

    public boolean supportLanguage(String language) {
        return getTargetLanguages().contains(language);
    }

    public String getCurrentAppLanguage() {
        String toLang;
        Locale locale = LocaleController.getInstance().getCurrentLocale();
        toLang = convertLanguageCode(locale.getLanguage(), locale.getCountry());
        if (!supportLanguage(toLang)) {
            toLang = convertLanguageCode(LocaleController.getString("LanguageCode", R.string.LanguageCode), null);
        }
        return toLang;
    }

    public String getTargetLanguage(String language) {
        String toLang;
        if (language.equals("app")) {
            toLang = getCurrentAppLanguage();
        } else {
            toLang = language;
        }
        return toLang;
    }

    public String getCurrentTargetLanguage() {
        return getTargetLanguage(NekoConfig.translationTarget);
    }

    private class TranslateTask implements Callable<Object> {
        private final Translator.TranslateCallBack translateCallBack;
        private final Object query;
        private final String fl;
        private final String tl;

        public TranslateTask(Object query, String fl, String tl, Translator.TranslateCallBack translateCallBack) {
            this.query = query;
            this.fl = fl;
            this.tl = tl;
            this.translateCallBack = translateCallBack;
        }

        protected void onPostExecute(Object result) {
            if (result == null) {
                translateCallBack.onError(null);
            } else if (result instanceof Exception) {
                translateCallBack.onError((Exception) result);
            } else {
                Result translationResult = (Result) result;
                translateCallBack.onSuccess(translationResult.translation, translationResult.sourceLanguage == null ? fl : convertLanguageCode(translationResult.sourceLanguage, true), tl);
                cache.put(Pair.create(query, tl), translationResult);
            }
        }

        @Override
        public Object call() throws Exception {
            try {
                var from = convertLanguageCode(TextUtils.isEmpty(fl) || "und".equals(fl) ? "auto" : fl, false);
                var to = convertLanguageCode(tl, false);
                if (query instanceof String) {
                    return translate((String) query, from, to);
                } else if (query instanceof TLRPC.Poll) {
                    TLRPC.TL_poll poll = new TLRPC.TL_poll();
                    TLRPC.TL_poll original = (TLRPC.TL_poll) query;
                    var question = translate(original.question, from, to);
                    if (NekoConfig.showOriginal) {
                        poll.question = original.question +
                                "\n" +
                                "--------" +
                                "\n" + question.translation;
                    } else {
                        poll.question = (String) question.translation;
                    }
                    for (int i = 0; i < original.answers.size(); i++) {
                        TLRPC.TL_pollAnswer answer = new TLRPC.TL_pollAnswer();
                        if (NekoConfig.showOriginal) {
                            answer.text = original.answers.get(i).text + " | " + translate(original.answers.get(i).text, from, to).translation;
                        } else {
                            answer.text = (String) translate(original.answers.get(i).text, from, to).translation;
                        }
                        answer.option = original.answers.get(i).option;
                        poll.answers.add(answer);
                    }
                    poll.close_date = original.close_date;
                    poll.close_period = original.close_period;
                    poll.closed = original.closed;
                    poll.flags = original.flags;
                    poll.id = original.id;
                    poll.multiple_choice = original.multiple_choice;
                    poll.public_voters = original.public_voters;
                    poll.quiz = original.quiz;
                    return new Result(poll, question.sourceLanguage);
                } else {
                    throw new UnsupportedOperationException("Unsupported translation query");
                }
            } catch (Throwable e) {
                e.printStackTrace();
                FileLog.e(e);
                return e;
            }
        }
    }

    public static class Http {
        private static OkHttpClient okHttpClient;
        private static Cache okHttpCache;

        public static Cache getOkHttpCache() {
            if (okHttpCache != null) return okHttpCache;
            long size50MiB = 50 * 1024 * 1024;
            okHttpCache = new Cache(new File(ApplicationLoader.applicationContext.getCacheDir(), "http_cache"), size50MiB);
            return okHttpCache;
        }

        private final Request.Builder builder;

        private Http(String url) {
            if (okHttpClient == null) {
                var builder = new OkHttpClient.Builder()
                        .cache(getOkHttpCache());
                okHttpClient = builder.build();
            }
            builder = new Request.Builder()
                    .url(url);
        }

        public static Http url(String url) {
            return new Http(url);
        }

        public Http header(String key, String value) {
            builder.header(key, value);
            return this;
        }

        public Http data(String data) {
            return data(data, "application/x-www-form-urlencoded");
        }

        public Http data(String data, String mediaType) {
            builder.post(RequestBody.create(data, MediaType.get(mediaType)));
            return this;
        }

        public String request() throws IOException {
            try (var response = okHttpClient.newCall(builder.build()).execute()) {
                if (response.code() == 429) {
                    throw new BaseTranslator.Http429Exception();
                }
                var body = response.body();
                if (body == null) {
                    return null;
                }
                return body.string();
            }
        }
    }

    public static class Http429Exception extends IOException {

    }

    public static class Result {
        public Object translation;
        @Nullable
        public String sourceLanguage;

        public Result(Object translation, @Nullable String sourceLanguage) {
            this.translation = translation;
            this.sourceLanguage = sourceLanguage;
        }
    }
}
