package com.ofbusiness.chatlogs.couchbase;

import java.util.UUID;

/**
 * ChatLogsKeyProvider
 * <p>
 * custom key provider for creating couch document key using user id, message id.
 *
 * @author vbishnoi
 */
public class ChatLogsKeyProvider {


    private ChatLogsKeyProvider() {
    }

    public static String getChatLogsKeyCouchbaseKey(UUID userId, UUID messageId) {
        /*
         * Generates a Chat Logs key prefixed by {environment name}::{applicationName}::{typeName}
         */
        String documentPrefix = "dev::chat-logs-service::messageDetails::";
        return documentPrefix.concat(String.valueOf(userId)) + "::" + messageId;
    }
}

