package com.sameperson.resources;

import com.sameperson.beans.MessageFilterBean;
import com.sameperson.model.Message;
import com.sameperson.service.MessageService;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.util.List;

@Path("messages")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class MessageResource {

    private MessageService messageService = new MessageService();

    @GET
    public List<Message> getMessages(@BeanParam MessageFilterBean messageFilterBean) {
        if (messageFilterBean.getYear() > 0) {
            return messageService.getAllMessagesForTheYear(messageFilterBean.getYear());
        }
        if (messageFilterBean.getStart() >= 0 && messageFilterBean.getSize() >= 0) {
            return messageService.getAllMessagesPaginated(messageFilterBean.getStart(), messageFilterBean.getSize());
        }
        return messageService.getAllMessages();
    }

    @GET
    @Path("/{messageId}")
    public Message getMessage(@PathParam("messageId") long messageId, @Context UriInfo uriInfo) {
        Message message = messageService.getMessage(messageId);
        message.addLink(getUriForSelf(uriInfo, message), "self");
        message.addLink(getUriForProfile(uriInfo, message), "profile");
        message.addLink(getUriForComments(uriInfo, message), "comments");
        return message;
    }

    private String getUriForComments(UriInfo uriInfo, Message message) {
        return uriInfo.getBaseUriBuilder()
                .path(MessageResource.class)
                .path(MessageResource.class, "getCommentResource")
                .path(CommentResource.class)
                .resolveTemplate("messageId", message.getId())
                .build()
                .toString();
    }

    private String getUriForProfile(UriInfo uriInfo, Message message) {
        return uriInfo.getBaseUriBuilder()
                .path(ProfileResource.class)
                .path(message.getAuthor())
                .build()
                .toString();
    }

    private String getUriForSelf(@Context UriInfo uriInfo, Message message) {
        return uriInfo.getBaseUriBuilder()
                    .path(MessageResource.class)
                    .path(Long.toString(message.getId()))
                    .build()
                    .toString();
    }

    @POST
    public Response addMessage(Message message, @Context UriInfo uriInfo) {
        String newId = String.valueOf(message.getId());
        Message newMessage = messageService.addMessage(message);
        URI uri = uriInfo.getAbsolutePathBuilder().path(newId).build();
        return Response.created(uri)
                .entity(newMessage)
                .build();
    }

    @PUT
    @Path("/{messageId}")
    public Message updateMessage(@PathParam("messageId") long messageId, Message message) {
        message.setId(messageId);
        return messageService.updateMessage(message);
    }

    @DELETE
    @Path("/{messageId}")
    public void deleteMessage(@PathParam("messageId") long messageId) {
        messageService.removeMessage(messageId);
    }

    @Path("/{messageId}/comments")
    public CommentResource getCommentResource() {
        CommentResource commentResource = new CommentResource();
        return commentResource;
    }
}
