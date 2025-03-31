package com.scm.entities;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@Entity
public class User {
    @Id
    private String userId;

    @Column(nullable = false)
    private String userName;

    @Column(unique = true, nullable = false)
    private String email;
    @Column(nullable = false)
    private String password;

    private String profilePic;
    @Column(length = 10)
    private String phoneNo;
    @Column(length = 100)
    private String about;

    private boolean phoneVerified = false;
    private boolean emailVerified = false;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Contact> contacts = new ArrayList<>();

    @PrePersist
    public void generateId() {
        if (this.userId == null || this.userId.isEmpty()) {
            this.userId = UUID.randomUUID().toString();
        }
    }
}
