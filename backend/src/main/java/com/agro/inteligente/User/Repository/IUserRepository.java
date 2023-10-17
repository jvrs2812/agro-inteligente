package com.agro.inteligente.User.Repository;

import com.agro.inteligente.User.Repository.Models.UserModelRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface IUserRepository extends JpaRepository<UserModelRepository, UUID> {

    @Query(value = "select * from user_table where email = :email", nativeQuery = true)
    Optional<UserModelRepository> findByEmail(String email);

    @Query(value = "select count(*) > 0 from user_table where cpf = :cpf", nativeQuery = true)
    boolean existByCpf(String cpf);

    @Modifying(clearAutomatically = true)
    @Transactional
    @Query(value = "update user_table set password = :password where email = :email", nativeQuery = true)
    void updatePasswordWithId(@Param("email") String email, @Param("password") String newPassword);
}
