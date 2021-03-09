package com.ofbusiness.chatlogs.resources;

import com.ofbusiness.chatlogs.models.LogMessageDetailsRequest;
import com.ofbusiness.chatlogs.service.ChatLogsService;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.Objects;
import java.util.UUID;

/**
 * A resource containing specific endpoints to access the service.
 *
 * @author vbishnoi
 * @since 8 March 2021
 */
@Path("/v1/users/{userId}")
@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_FORM_URLENCODED})
@Produces(MediaType.APPLICATION_JSON)
public class ChatLogsResource {

    private final ChatLogsService chatLogsService;

    public ChatLogsResource(ChatLogsService chatLogsService) {this.chatLogsService = chatLogsService;}

    /**
     * logMessage
     *
     * @param userId {@link UUID}
     *
     * @return {@link Response}
     */
    @POST
    @Path("/message")
    public Response logMessage(@PathParam("userId") UUID userId,
                               @Valid @NotNull LogMessageDetailsRequest logMessageDetailsRequest)
    throws Exception {
        chatLogsService.logMessage(userId, logMessageDetailsRequest);
        return Response.status(Response.Status.CREATED).build();
    }

    /**
     * getChatLogs
     *
     * @param userId          {@link UUID}
     * @param offSetMessageId {@link UUID}
     * @param limit           {@link Integer}
     *
     * @return {@link Response}
     */
    @GET
    @Path("/messages")
    public Response getChatLogs(@PathParam("userId") UUID userId,
                                @QueryParam("offSetMessageId") UUID offSetMessageId,
                                @DefaultValue("10") @QueryParam("limit") int limit) throws IOException {
        Object userMessageDetailsList = chatLogsService.getChatLogs(userId, offSetMessageId, limit);
        if (Objects.nonNull(userMessageDetailsList)) {
            return Response.status(Response.Status.OK).entity(userMessageDetailsList).build();
        } else {
            return Response.status(Response.Status.NO_CONTENT).build();
        }
    }

    /**
     * deleteMessageLog
     *
     * @param userId    {@link UUID}
     * @param messageId {@link UUID}
     *
     * @return {@link Response}
     */
    @DELETE
    @Path("/messages/{messageId:(/messageId/[^/]+?)?}")
    public Response deleteMessageLog(@PathParam("userId") UUID userId,
                                     @PathParam("messageId") UUID messageId) {
        boolean isDeleted = chatLogsService.deleteChatLogs(userId, messageId);
        return isDeleted
               ? Response.status(Response.Status.OK).build()
               : Response.status(Response.Status.NO_CONTENT).build();
    }
}
