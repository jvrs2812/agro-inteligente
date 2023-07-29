package com.agro.inteligente.Email.Repository;

import jakarta.persistence.Temporal;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface IEmailRepository extends JpaRepository<EmailModelRepository, UUID> {
    @Query(value = "select * from email_send where send = false", nativeQuery = true)
    @Transactional
    List<EmailModelRepository> getAllEmailsNotSend();

    @Modifying(clearAutomatically = true)
    @Transactional
    @Query(value = "update email_send set send = true where id = :id", nativeQuery = true)
    void emailSend(@Param("id") UUID id);
}
