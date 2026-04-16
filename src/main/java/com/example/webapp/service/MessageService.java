package com.example.webapp.service;

import com.example.webapp.model.Message;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Service layer — business logic for Messages.
 * Uses an in-memory list (swap for a JPA repository when ready).
 */
@Service
public class MessageService {

    private final List<Message> store = new ArrayList<>();
    private final AtomicLong idSequence = new AtomicLong(1);

    public MessageService() {
        // Seed data
        store.add(new Message(idSequence.getAndIncrement(), "Hello, World!", "System"));
        store.add(new Message(idSequence.getAndIncrement(), "Spring Boot is running!", "System"));
    }

    public List<Message> getAllMessages() {
        return List.copyOf(store);
    }

    public Optional<Message> getById(Long id) {
        return store.stream().filter(m -> m.getId().equals(id)).findFirst();
    }

    public Message save(Message message) {
        message.setId(idSequence.getAndIncrement());
        store.add(message);
        return message;
    }

    public void delete(Long id) {
        store.removeIf(m -> m.getId().equals(id));
    }
}
