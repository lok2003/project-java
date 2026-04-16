package com.example.webapp.controller;

import com.example.webapp.model.Message;
import com.example.webapp.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Web Controller — handles both HTML page routes and REST API endpoints.
 */
@Controller
@RequestMapping("/")
public class WebController {

    @Autowired
    private MessageService messageService;

    // -------------------------------------------------------
    // HTML page endpoints (Thymeleaf views)
    // -------------------------------------------------------

    /** Home page */
    @GetMapping
    public String home(Model model) {
        model.addAttribute("messages", messageService.getAllMessages());
        return "index";
    }

    // -------------------------------------------------------
    // REST API endpoints (JSON responses)
    // -------------------------------------------------------

    /** GET all messages */
    @GetMapping("/api/messages")
    @ResponseBody
    public ResponseEntity<List<Message>> getMessages() {
        return ResponseEntity.ok(messageService.getAllMessages());
    }

    /** GET a single message by id */
    @GetMapping("/api/messages/{id}")
    @ResponseBody
    public ResponseEntity<Message> getMessage(@PathVariable Long id) {
        return messageService.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /** POST — create a new message */
    @PostMapping("/api/messages")
    @ResponseBody
    public ResponseEntity<Message> createMessage(@RequestBody Message message) {
        Message saved = messageService.save(message);
        return ResponseEntity.ok(saved);
    }

    /** DELETE — remove a message */
    @DeleteMapping("/api/messages/{id}")
    @ResponseBody
    public ResponseEntity<Void> deleteMessage(@PathVariable Long id) {
        messageService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
