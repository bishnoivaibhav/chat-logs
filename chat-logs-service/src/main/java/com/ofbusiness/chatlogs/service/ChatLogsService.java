package com.ofbusiness.chatlogs.service;

import com.fasterxml.uuid.Generators;
import com.ofbusiness.chatlogs.dao.ChatLogsDao;
import com.ofbusiness.chatlogs.models.ImmutableLoggedMessageResponse;
import com.ofbusiness.chatlogs.models.LogMessageDetailsRequest;
import com.ofbusiness.chatlogs.models.LoggedMessageResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

/**
 * ChatLogsService
 *
 * @author vbishnoi
 * @since 8 March 2021
 */
public class ChatLogsService {

    private final ChatLogsDao chatLogsDao;
    private static final Logger LOG = LoggerFactory.getLogger(ChatLogsService.class);

    public ChatLogsService(ChatLogsDao chatLogsDao) {this.chatLogsDao = chatLogsDao;}

    /**
     * logMessage
     * <p>
     * method for creating a log message id and passing that onto data source layer {@link ChatLogsDao} if message gets
     * logged it return message id {@link UUID}. a time based message id {@link UUID} would be generated in order to
     * maintain time reference between different log requests.
     *
     * @param userId                   {@link UUID} user id for which we are logging a message.
     * @param logMessageDetailsRequest {@link LogMessageDetailsRequest} message details to be logged.
     *
     * @return {@link UUID} message id corresponding to log.
     *
     * @throws Exception ex
     */
    public LoggedMessageResponse logMessage(UUID userId, LogMessageDetailsRequest logMessageDetailsRequest)
    throws Exception {
        UUID messageId = Generators.timeBasedGenerator().generate();
        try {
            chatLogsDao.logMessage(userId, messageId, logMessageDetailsRequest);
            return ImmutableLoggedMessageResponse
                           .builder()
                           .message(logMessageDetailsRequest.getMessage())
                           .messageId(messageId)
                           .isSender(logMessageDetailsRequest.getIsSender())
                           .timestamp(logMessageDetailsRequest.getTimestamp())
                           .build();
        } catch (Exception exception) {
            LOG.error(" Failure while creating log", exception);
            throw exception;
        }
    }

    /**
     * getChatLogs
     * <p>
     * method to fetch logs corresponding to a user with given constrains, if message id is passed it'll use it as
     * offset and will fetch a limit number of messages provided by user{@link Integer}.
     *
     * @param userId          {@link UUID} user id for fetching user logs.
     * @param offSetMessageId {@link UUID} message id to be used as off set while fetching.
     * @param limit           {@link Integer} batch size for message.
     *
     * @return {@link List} list of message log responses {@link LoggedMessageResponse}  which contains message related
     *         information.
     *
     * @throws IOException ex
     */
    public List<LogMessageDetailsRequest> getChatLogs(UUID userId, UUID offSetMessageId, int limit)
    throws IOException {
        return chatLogsDao.getChatLogs(userId, offSetMessageId, limit);
    }

    /**
     * deleteChatLogs
     * <p>
     * method for removing message logs either on bases of message id if provided else all the user logs will be
     * removed.
     *
     * @param userId    {@link UUID} user id referred for removing logs
     * @param messageId {@link UUID} message id .
     */
    public boolean deleteChatLogs(UUID userId, UUID messageId) {
        try {
            chatLogsDao.deleteChatLogs(userId, messageId);
            return true;
        } catch (Exception exception) {
            LOG.error("Failure while deletion ", exception);
            return false;
        }
    }
}
