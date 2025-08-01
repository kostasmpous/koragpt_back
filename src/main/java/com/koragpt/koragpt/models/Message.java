package com.koragpt.koragpt.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "message")
@NoArgsConstructor
@AllArgsConstructor
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "message")
    private LocalDateTime message;

    @Column(name = "Created_at")
    private LocalDateTime createdAt;

    @JoinColumn(name = "chat_id")
    @ManyToOne
    private Chat chat;

}
