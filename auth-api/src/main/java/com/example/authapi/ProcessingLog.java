package com.example.authapi;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;

    @Entity
    @Table(name = "processing_log")
    @Getter @Setter
    public class ProcessingLog {
        @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @ManyToOne(optional = false)              // связь с пользователем
        @JoinColumn(name = "user_id")
        private User user;

        @Column(name = "input_text", nullable = false)
        private String inputText;

        @Column(name = "output_text", nullable = false)
        private String outputText;

        @CreationTimestamp
        @Column(name = "created_at", nullable = false, updatable = false)
        private Instant createdAt;
}
