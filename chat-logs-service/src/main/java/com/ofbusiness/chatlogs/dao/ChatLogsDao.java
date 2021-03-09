package com.ofbusiness.chatlogs.dao;

import com.couchbase.client.core.deps.com.fasterxml.jackson.core.JsonProcessingException;
import com.couchbase.client.core.deps.com.fasterxml.jackson.databind.ObjectMapper;
import com.ofbusiness.chatlogs.couchbase.ChatLogsKeyProvider;
import com.ofbusiness.chatlogs.exceptions.CouchbaseException;
import com.ofbusiness.chatlogs.models.LogMessageDetailsRequest;
import com.ofbusiness.chatlogs.util.CouchBaseCloud;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * ChatLogsDao class for Querying chat log data source.
 *
 * @author vbishnoi
 * @since 8 March 2021
 */
public class ChatLogsDao {

    private static final Logger LOG = LoggerFactory.getLogger(ChatLogsDao.class);
    private final ObjectMapper objectMapper = new ObjectMapper();

    public ChatLogsDao() {
    }

    /**
     * getChatLogs method to query on cb and fetching list {@link List} of message logs {@link
     * LogMessageDetailsRequest}
     */
    public List<LogMessageDetailsRequest> getChatLogs(UUID userId, UUID offSetMessageId, int limit) throws IOException {
        Objects.requireNonNull(userId);
        if (Objects.isNull(offSetMessageId)) {
            offSetMessageId = UUID.fromString("00000000-0000-0000-0000-000000000000");
        }
        String selectQuery = String.format("SELECT messageDetails FROM couchbasecloudbucket AS b USE INDEX " +
                                                   "(`chat_logs_idx`)\n" +
                                                   "WHERE split(meta().`id`, \"::\")[0] = 'dev' AND\n" +
                                                   "     split(meta(b).`id`, \"::\")[1] = 'chat-logs-service' AND \n" +
                                                   "     split(meta(b).`id`, \"::\")[2] = 'messageDetails' AND\n" +
                                                   "     split(meta().`id`, \"::\")[3] = " +
                                                   "'%s' AND\n" +
                                                   "     split(meta().`id`, \"::\")[4] > " +
                                                   "'%s' \n" +
                                                   "     LIMIT %s\n" +
                                                   "     ", userId, offSetMessageId, limit);
        try {
            return CouchBaseCloud.getCouchBaseCluster()
                                 .query(selectQuery).rowsAs(LogMessageDetailsRequest.class);
        } catch (Exception couchbaseException) {
            LOG.error("Failure while fetching chat logs/ Selecting", couchbaseException);
            throw couchbaseException;
        }
    }

    public void logMessage(UUID userId, UUID messageId, LogMessageDetailsRequest logMessageDetailsRequest)
    throws JsonProcessingException {

        String documentKey = ChatLogsKeyProvider.getChatLogsKeyCouchbaseKey(userId, messageId);
        try {
            CouchBaseCloud.getCouchBaseCluster().query(
                    String.format(" INSERT INTO $bucketName (KEY, VALUE) " +
                                          "   VALUES (%s, %s)",
                            documentKey,
                            objectMapper.writeValueAsString(logMessageDetailsRequest)));
        } catch (CouchbaseException couchbaseException) {
            LOG.error("Failure while creating chat logs/ Insertion", couchbaseException);
            throw couchbaseException;
        }
    }

    /**
     * deleteChatLogs
     * <p>
     * method for removing all the user logs from cb using the n1ql query. soft delete takes place with expiration time
     * of 1 sec.
     */
    public void deleteChatLogs(UUID userId, UUID messageId) {
        Objects.requireNonNull(userId);
        String touchQuery = String.format("UPSERT INTO default (KEY key, VALUE doc, OPTIONS {\"expiration\": 1}) " +
                                                  "SELECT messageDetails FROM couchbasecloudbucket AS b USE INDEX " +
                                                  "(`chat_logs_idx`)\n" +
                                                  "WHERE split(meta().`id`, \"::\")[0] = 'dev' AND\n" +
                                                  "     split(meta(b).`id`, \"::\")[1] = 'chat-logs-service' AND \n" +
                                                  "     split(meta(b).`id`, \"::\")[2] = 'messageDetails' AND\n" +
                                                  "     split(meta().`id`, \"::\")[3] = '%s'  ", userId);
        if (Objects.nonNull(messageId)) {
            touchQuery = touchQuery.concat(String.format("AND      split(meta().`id`, \"::\")[3] = '%s' ", messageId));
        }
        try {
            CouchBaseCloud.getCouchBaseCluster().query(touchQuery);
        } catch (CouchbaseException couchbaseException) {
            LOG.error("Failure while removing chat logs in bulk/ deletion", couchbaseException);
            throw couchbaseException;
        }
    }

}
