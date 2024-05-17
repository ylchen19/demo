package com.example.demo.repository;

import com.example.demo.entity.TokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface TokenRepository extends JpaRepository<TokenEntity, Long> {

    @Query("""
    select t from TokenEntity t inner join User u on t.user.id = u.id
    where u.id = :userId and (t.expired = false or t.revoked = false)
""")
    List<TokenEntity> findAllValidTokensByUser(Long userId);
    Optional<TokenEntity> findByToken(String token);
}
