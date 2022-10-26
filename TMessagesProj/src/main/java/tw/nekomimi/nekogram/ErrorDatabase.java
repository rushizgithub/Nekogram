package tw.nekomimi.nekogram;

import android.widget.Toast;

import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.ApplicationLoader;
import org.telegram.tgnet.TLObject;

import java.util.Arrays;
import java.util.HashSet;

public class ErrorDatabase {
    private static final HashSet<String> userOnly = new HashSet<>(Arrays.asList("account.acceptAuthorization",
            "account.cancelPasswordEmail",
            "account.changeAuthorizationSettings",
            "account.changePhone",
            "account.checkUsername",
            "account.clearRecentEmojiStatuses",
            "account.confirmPasswordEmail",
            "account.confirmPhone",
            "account.createTheme",
            "account.declinePasswordReset",
            "account.deleteAccount",
            "account.deleteSecureValue",
            "account.finishTakeoutSession",
            "account.getAccountTTL",
            "account.getAllSecureValues",
            "account.getAuthorizationForm",
            "account.getAuthorizations",
            "account.getAutoDownloadSettings",
            "account.getChatThemes",
            "account.getContactSignUpNotification",
            "account.getContentSettings",
            "account.getDefaultEmojiStatuses",
            "account.getGlobalPrivacySettings",
            "account.getMultiWallPapers",
            "account.getNotifyExceptions",
            "account.getNotifySettings",
            "account.getPassword",
            "account.getPasswordSettings",
            "account.getPrivacy",
            "account.getRecentEmojiStatuses",
            "account.getSavedRingtones",
            "account.getSecureValue",
            "account.getTheme",
            "account.getThemes",
            "account.getTmpPassword",
            "account.getWallPaper",
            "account.getWallPapers",
            "account.getWebAuthorizations",
            "account.initTakeoutSession",
            "account.installTheme",
            "account.installWallPaper",
            "account.registerDevice",
            "account.reportPeer",
            "account.reportProfilePhoto",
            "account.resendPasswordEmail",
            "account.resetAuthorization",
            "account.resetNotifySettings",
            "account.resetPassword",
            "account.resetWallPapers",
            "account.resetWebAuthorization",
            "account.resetWebAuthorizations",
            "account.saveAutoDownloadSettings",
            "account.saveRingtone",
            "account.saveSecureValue",
            "account.saveTheme",
            "account.saveWallPaper",
            "account.sendChangePhoneCode",
            "account.sendConfirmPhoneCode",
            "account.sendVerifyEmailCode",
            "account.sendVerifyPhoneCode",
            "account.setAccountTTL",
            "account.setAuthorizationTTL",
            "account.setContactSignUpNotification",
            "account.setContentSettings",
            "account.setGlobalPrivacySettings",
            "account.setPrivacy",
            "account.unregisterDevice",
            "account.updateDeviceLocked",
            "account.updateEmojiStatus",
            "account.updateNotifySettings",
            "account.updatePasswordSettings",
            "account.updateProfile",
            "account.updateStatus",
            "account.updateTheme",
            "account.updateUsername",
            "account.uploadRingtone",
            "account.uploadTheme",
            "account.uploadWallPaper",
            "account.verifyEmail",
            "account.verifyPhone",
            "auth.acceptLoginToken",
            "auth.cancelCode",
            "auth.checkPassword",
            "auth.checkRecoveryPassword",
            "auth.exportLoginToken",
            "auth.importLoginToken",
            "auth.recoverPassword",
            "auth.requestPasswordRecovery",
            "auth.resendCode",
            "auth.resetAuthorizations",
            "auth.sendCode",
            "auth.signIn",
            "auth.signUp",
            "channels.checkUsername",
            "channels.convertToGigagroup",
            "channels.createChannel",
            "channels.deleteChannel",
            "channels.deleteHistory",
            "channels.deleteParticipantHistory",
            "channels.deleteUserHistory",
            "channels.editCreator",
            "channels.editLocation",
            "channels.exportMessageLink",
            "channels.getAdminedPublicChannels",
            "channels.getAdminLog",
            "channels.getGroupsForDiscussion",
            "channels.getInactiveChannels",
            "channels.getLeftChannels",
            "channels.getSendAs",
            "channels.getSponsoredMessages",
            "channels.inviteToChannel",
            "channels.joinChannel",
            "channels.readHistory",
            "channels.readMessageContents",
            "channels.reportSpam",
            "channels.setDiscussionGroup",
            "channels.toggleJoinRequest",
            "channels.toggleJoinToSend",
            "channels.togglePreHistoryHidden",
            "channels.toggleSignatures",
            "channels.toggleSlowMode",
            "channels.updateUsername",
            "channels.viewSponsoredMessage",
            "contacts.acceptContact",
            "contacts.addContact",
            "contacts.block",
            "contacts.blockFromReplies",
            "contacts.deleteByPhones",
            "contacts.deleteContacts",
            "contacts.getBlocked",
            "contacts.getContactIDs",
            "contacts.getContacts",
            "contacts.getLocated",
            "contacts.getSaved",
            "contacts.getStatuses",
            "contacts.getTopPeers",
            "contacts.importContacts",
            "contacts.resetSaved",
            "contacts.resetTopPeerRating",
            "contacts.resolvePhone",
            "contacts.search",
            "contacts.toggleTopPeers",
            "contacts.unblock",
            "folders.deleteFolder",
            "folders.editPeerFolders",
            "help.acceptTermsOfService",
            "help.dismissSuggestion",
            "help.editUserInfo",
            "help.getAppChangelog",
            "help.getAppConfig",
            "help.getAppUpdate",
            "help.getCountriesList",
            "help.getDeepLinkInfo",
            "help.getInviteText",
            "help.getNearestDc",
            "help.getPassportConfig",
            "help.getPremiumPromo",
            "help.getPromoData",
            "help.getProxyData",
            "help.getRecentMeUrls",
            "help.getSupport",
            "help.getSupportName",
            "help.getTermsOfServiceUpdate",
            "help.getUserInfo",
            "help.hidePromoData",
            "help.saveAppLog",
            "langpack.getDifference",
            "langpack.getLangPack",
            "langpack.getLanguage",
            "langpack.getLanguages",
            "langpack.getStrings",
            "messages.acceptEncryption",
            "messages.acceptUrlAuth",
            "messages.addChatUser",
            "messages.checkChatInvite",
            "messages.checkHistoryImport",
            "messages.checkHistoryImportPeer",
            "messages.clearAllDrafts",
            "messages.clearRecentReactions",
            "messages.clearRecentStickers",
            "messages.createChat",
            "messages.deleteChat",
            "messages.deleteExportedChatInvite",
            "messages.deleteHistory",
            "messages.deletePhoneCallHistory",
            "messages.deleteRevokedExportedChatInvites",
            "messages.deleteScheduledMessages",
            "messages.discardEncryption",
            "messages.editChatAdmin",
            "messages.faveSticker",
            "messages.getAdminsWithInvites",
            "messages.getAllChats",
            "messages.getAllDrafts",
            "messages.getAllStickers",
            "messages.getArchivedStickers",
            "messages.getAttachedStickers",
            "messages.getAttachMenuBot",
            "messages.getAttachMenuBots",
            "messages.getAvailableReactions",
            "messages.getBotCallbackAnswer",
            "messages.getChatInviteImporters",
            "messages.getCommonChats",
            "messages.getDhConfig",
            "messages.getDialogFilters",
            "messages.getDialogs",
            "messages.getDialogUnreadMarks",
            "messages.getDiscussionMessage",
            "messages.getEmojiKeywords",
            "messages.getEmojiKeywordsDifference",
            "messages.getEmojiKeywordsLanguages",
            "messages.getEmojiStickers",
            "messages.getEmojiURL",
            "messages.getExportedChatInvite",
            "messages.getExportedChatInvites",
            "messages.getExtendedMedia",
            "messages.getFavedStickers",
            "messages.getFeaturedEmojiStickers",
            "messages.getFeaturedStickers",
            "messages.getHistory",
            "messages.getInlineBotResults",
            "messages.getMaskStickers",
            "messages.getMessageEditData",
            "messages.getMessageReactionsList",
            "messages.getMessageReadParticipants",
            "messages.getMessagesReactions",
            "messages.getMessagesViews",
            "messages.getOldFeaturedStickers",
            "messages.getOnlines",
            "messages.getPeerDialogs",
            "messages.getPeerSettings",
            "messages.getPinnedDialogs",
            "messages.getPollResults",
            "messages.getPollVotes",
            "messages.getRecentLocations",
            "messages.getRecentReactions",
            "messages.getRecentStickers",
            "messages.getReplies",
            "messages.getSavedGifs",
            "messages.getScheduledHistory",
            "messages.getScheduledMessages",
            "messages.getSearchCounters",
            "messages.getSearchResultsCalendar",
            "messages.getSearchResultsPositions",
            "messages.getSplitRanges",
            "messages.getStatsURL",
            "messages.getStickers",
            "messages.getSuggestedDialogFilters",
            "messages.getTopReactions",
            "messages.getUnreadMentions",
            "messages.getUnreadReactions",
            "messages.getWebPage",
            "messages.getWebPagePreview",
            "messages.hideAllChatJoinRequests",
            "messages.hidePeerSettingsBar",
            "messages.importChatInvite",
            "messages.initHistoryImport",
            "messages.installStickerSet",
            "messages.markDialogUnread",
            "messages.migrateChat",
            "messages.prolongWebView",
            "messages.rateTranscribedAudio",
            "messages.readDiscussion",
            "messages.readEncryptedHistory",
            "messages.readFeaturedStickers",
            "messages.readHistory",
            "messages.readMentions",
            "messages.readMessageContents",
            "messages.readReactions",
            "messages.receivedMessages",
            "messages.receivedQueue",
            "messages.reorderPinnedDialogs",
            "messages.reorderStickerSets",
            "messages.report",
            "messages.reportEncryptedSpam",
            "messages.reportReaction",
            "messages.reportSpam",
            "messages.requestEncryption",
            "messages.requestSimpleWebView",
            "messages.requestUrlAuth",
            "messages.requestWebView",
            "messages.saveDefaultSendAs",
            "messages.saveDraft",
            "messages.saveGif",
            "messages.saveRecentSticker",
            "messages.search",
            "messages.searchGifs",
            "messages.searchGlobal",
            "messages.searchSentMedia",
            "messages.searchStickerSets",
            "messages.sendEncrypted",
            "messages.sendEncryptedFile",
            "messages.sendEncryptedService",
            "messages.sendInlineBotResult",
            "messages.sendReaction",
            "messages.sendScheduledMessages",
            "messages.sendScreenshotNotification",
            "messages.sendVote",
            "messages.sendWebViewData",
            "messages.setChatAvailableReactions",
            "messages.setChatTheme",
            "messages.setDefaultReaction",
            "messages.setEncryptedTyping",
            "messages.setHistoryTTL",
            "messages.startBot",
            "messages.startHistoryImport",
            "messages.toggleBotInAttachMenu",
            "messages.toggleDialogPin",
            "messages.toggleNoForwards",
            "messages.toggleStickerSets",
            "messages.transcribeAudio",
            "messages.translateText",
            "messages.uninstallStickerSet",
            "messages.updateDialogFilter",
            "messages.updateDialogFiltersOrder",
            "messages.uploadEncryptedFile",
            "messages.uploadImportedMedia",
            "msg_container",
            "msgs_ack",
            "payments.assignAppStoreTransaction",
            "payments.assignPlayMarketTransaction",
            "payments.canPurchasePremium",
            "payments.clearSavedInfo",
            "payments.getBankCardData",
            "payments.getPaymentForm",
            "payments.getPaymentReceipt",
            "payments.getSavedInfo",
            "payments.requestRecurringPayment",
            "payments.sendPaymentForm",
            "payments.validateRequestedInfo",
            "phone.acceptCall",
            "phone.checkGroupCall",
            "phone.confirmCall",
            "phone.createGroupCall",
            "phone.discardCall",
            "phone.discardGroupCall",
            "phone.editGroupCallParticipant",
            "phone.editGroupCallTitle",
            "phone.exportGroupCallInvite",
            "phone.getCallConfig",
            "phone.getGroupCall",
            "phone.getGroupCallJoinAs",
            "phone.getGroupCallStreamChannels",
            "phone.getGroupCallStreamRtmpUrl",
            "phone.getGroupParticipants",
            "phone.inviteToGroupCall",
            "phone.joinGroupCall",
            "phone.joinGroupCallPresentation",
            "phone.leaveGroupCall",
            "phone.leaveGroupCallPresentation",
            "phone.receivedCall",
            "phone.requestCall",
            "phone.saveCallDebug",
            "phone.saveCallLog",
            "phone.saveDefaultGroupCallJoinAs",
            "phone.sendSignalingData",
            "phone.setCallRating",
            "phone.startScheduledGroupCall",
            "phone.toggleGroupCallRecord",
            "phone.toggleGroupCallSettings",
            "phone.toggleGroupCallStartSubscription",
            "photos.deletePhotos",
            "photos.updateProfilePhoto",
            "photos.uploadProfilePhoto",
            "stats.getBroadcastStats",
            "stats.getMegagroupStats",
            "stats.getMessagePublicForwards",
            "stats.getMessageStats",
            "stats.loadAsyncGraph",
            "stickers.checkShortName",
            "stickers.suggestShortName",
            "upload.getCdnFile",
            "upload.getWebFile"));

    public static String getMethodName(TLObject method) {
        var name = method.toString();
        var start = name.indexOf("$") + 4;
        var end = name.indexOf("@");
        return name.substring(start, end).replace("_", ".");
    }

    public static boolean isUserOnlyMethod(TLObject method) {
        return userOnly.contains(getMethodName(method));
    }

    public static void showErrorToast(TLObject method, String text) {
        if (text.equals("FILE_REFERENCE_EXPIRED")) {
            return;
        }
        AndroidUtilities.runOnUIThread(() -> Toast.makeText(ApplicationLoader.applicationContext, getMethodName(method) + ": " + text, Toast.LENGTH_SHORT).show());
    }
}
