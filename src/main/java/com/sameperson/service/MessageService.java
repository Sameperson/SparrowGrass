package com.sameperson.service;

import com.sameperson.dao.MockDao;
import com.sameperson.exception.DataNotFoundException;
import com.sameperson.model.Message;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

public class MessageService {

    private Map<Long, Message> messages = MockDao.getMessages();

    public MessageService() {
        //Mock
        messages.put(1L, new Message(1, "Hello world!", "dmitry"));
        messages.put(2L, new Message(2, "TestMessage", "TestName"));
    }

    public List<Message> getAllMessages() {
        return new ArrayList<>(messages.values());
    }

    public List<Message> getAllMessagesForTheYear(int year) {
        List<Message> messagesForYear = new ArrayList<>();
        Calendar cal = Calendar.getInstance();
        for (Message message : messages.values()) {
            cal.setTime(message.getCreated());
            if (cal.get(Calendar.YEAR) == year) {
                messagesForYear.add(message);
            }
        }
        return messagesForYear;
    }

    public List<Message> getAllMessagesPaginated(int start, int size) {
        ArrayList<Message> list = new ArrayList<>(messages.values());
        if (start + size > list.size()) {
            return new ArrayList<>();
        }
        return list.subList(start, start + size);
    }

    public Message getMessage(long id) {
        Message message = messages.get(id);
        if (message == null) {
            throw new DataNotFoundException("Message with id " + id + " not found" );
        }
        return message;
    }

    public Message addMessage(Message message) {
        message.setId(messages.size() + 1);
        messages.put(message.getId(), message);
        return message;
    }

    public Message updateMessage(Message message) {
        if (message.getId() <= 0) {
            return null;
        }
        messages.put(message.getId(), message);
        return message;
    }

    public Message removeMessage(long id) {
        return messages.remove(id);
    }

}
