package com.agro.inteligente.User.Repository;

import com.agro.inteligente.User.Repository.Models.UserRecoveryPasswordModel;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface IUserRecoveryPasswordRepository extends JpaRepository<UserRecoveryPasswordModel, UUID> {

    @Modifying(clearAutomatically = true)
    @Transactional
    @Query(value = "update recovery_password set email_send = true where id= :id", nativeQuery = true)
    void updateEmailSend(@Param("id") UUID id);

    @Modifying(clearAutomatically = true)
    @Transactional
    @Query(value = "update recovery_password set reset_password = true where id= :id", nativeQuery = true)
    void recoverySucess(@Param("id") UUID id);
}
