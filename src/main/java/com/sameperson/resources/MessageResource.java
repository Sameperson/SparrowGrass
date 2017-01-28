package com.sameperson.resources;

import com.sameperson.beans.MessageFilterBean;
import com.sameperson.model.Message;
import com.sameperson.service.MessageService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
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
    public Message getMessage(@PathParam("messageId") long messageId) {
        return messageService.getMessage(messageId);
    }

    @POST
    public Message addMessage(Message message) {
        return messageService.addMessage(message);
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