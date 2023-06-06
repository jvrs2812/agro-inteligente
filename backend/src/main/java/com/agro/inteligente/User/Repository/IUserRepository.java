package com.agro.inteligente.User.Repository;

import com.agro.inteligente.User.Repository.Models.UserModelRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface IUserRepository extends JpaRepository<UserModelRepository, UUID> {

    Optional<UserModelRepository> findByEmail(String email);

    @Query(value = "select count(*) > 0 from user_table where cpf = :cpf", nativeQuery = true)
    boolean existByCpf(String cpf);
}
