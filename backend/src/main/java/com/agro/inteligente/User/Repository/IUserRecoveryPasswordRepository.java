package com.agro.inteligente.User.Repository;

import com.agro.inteligente.User.Repository.Models.UserRecoveryPasswordModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface IUserRecoveryPasswordRepository extends JpaRepository<UserRecoveryPasswordModel, UUID> {
}
