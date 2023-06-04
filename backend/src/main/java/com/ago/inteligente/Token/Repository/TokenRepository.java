package com.ago.inteligente.Token.Repository;

import com.ago.inteligente.Token.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface TokenRepository extends JpaRepository<Token, UUID> {
    @Query(value = "select t.* from token t inner join user_table u on (t.user_id = u.id) where u.id = :id and (t.expired = false or t.revoked = false)", nativeQuery = true)
    List<Token> findAllValidTokenByUser(UUID id);

    Optional<Token> findByToken(String token);
}
