package com.savesmart.model;

import jakarta.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "accounts", uniqueConstraints = @UniqueConstraint(columnNames = "email"))
public class Account {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 80)
    private String fullName;

    @Column(nullable = false, unique = true, length = 120)
    private String email;

    @Column(nullable = false)
    private String passwordHash;

    @Column(nullable = false, updatable = false)
    private Instant createdAt = Instant.now();

    protected Account() {}

    public Account(String fullName, String email, String passwordHash) {
        this.fullName = fullName;
        this.email = email.toLowerCase().trim();
        this.passwordHash = passwordHash;
    }

    public Long getId() { return id; }
    public String getFullName() { return fullName; }
    public String getEmail() { return email; }
    public String getPasswordHash() { return passwordHash; }
    public Instant getCreatedAt() { return createdAt; }
}
