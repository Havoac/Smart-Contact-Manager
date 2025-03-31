package com.scm.entities;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@Entity
public class Contact {
    @Id
    private int contactId;

    @Column(nullable = false)
    private String contactName;
    private String email;
    @Column(nullable = false)
    private String phoneNo;
    private boolean isFavourite = false;
    private String address;

    @ManyToOne
    private User user;

    @OneToMany(mappedBy = "contact", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SocialAccounts> socialAccounts = new ArrayList<>();
}
