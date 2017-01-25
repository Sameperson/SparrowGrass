package com.sameperson;

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
    public List<Message> getMessages(@QueryParam("year") int year,
                                     @DefaultValue("-1") @QueryParam("start") int start,
                                     @DefaultValue("-1") @QueryParam("size") int size) {
        if (year > 0) {
            return messageService.getAllMessagesForTheYear(year);
        }
        if (start >= 0 && size >= 0) {
            return messageService.getAllMessagesPaginated(start, size);
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

}
