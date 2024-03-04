package com.example.simpleMessenger.repository;

import com.example.simpleMessenger.entity.Message;
import com.example.simpleMessenger.entity.Token;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.BulkOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class TokenRepository {
    private final MongoTemplate mongoTemplate;

    public Optional<Token> findByToken(String token) {
        Query query = new Query(Criteria.where("accessToken").is(token));
        return Optional.ofNullable(mongoTemplate.findOne(query, Token.class));
    }

    public void save(Token token) {
        mongoTemplate.save(token, "token");
    }

    public void revokeAllUserTokens(List<Token> tokenList) {
        for(Token token : tokenList) {
            token.setRevoked(Boolean.TRUE);
            token.setExpired(Boolean.TRUE);
            save(token);
        }
    }
}
