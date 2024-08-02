package com.smart.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.smart.entities.Contact;

public interface ContactRepository extends JpaRepository<Contact, Integer> {
    @Query("from Contact as c where c.user.id =:userId")
    // currentPage-page
    // contact Per page 5
    // pageable parameter is compulsory since return type is for Pageable query
    public Page<Contact> findContactsByUser(@Param("userId") int userId, Pageable pageable);
}
