package com.example.simpleMessenger.repository;

import com.example.simpleMessenger.entity.Token;
import com.example.simpleMessenger.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserRepository {
    private final MongoTemplate mongoTemplate;
    private final TokenRepository tokenRepository;

    public Optional<User> findByUsername(String username) {
        Query query = new Query();
        query.addCriteria(Criteria.where("username").is(username));
        User user = mongoTemplate.findOne(query, User.class);
        return Optional.ofNullable(user);
    }

    public User createUser(User user) {
        return mongoTemplate.save(user, "user");
    }

    public List<User> fetchAllUsers() {
        return mongoTemplate.findAll(User.class);
    }

    public void saveUserToken(User user, String jwtToken) {
        Token token = Token.builder()
                .accessToken(jwtToken)
                .expired(Boolean.FALSE)
                .revoked(Boolean.FALSE)
                .build();
        tokenRepository.save(token);
        if(user.getTokenList() == null) user.setTokenList(new ArrayList<>());
        user.getTokenList().add(token);
        mongoTemplate.save(user, "user");
    }

    public void revokeAllUserTokens(User user) {
        if(user.getTokenList() != null && !user.getTokenList().isEmpty())
            tokenRepository.revokeAllUserTokens(user.getTokenList());
    }

    public long getCount(List<String> username) {
        Query query = new Query();
        query.addCriteria(Criteria.where("username").in(username));
        return mongoTemplate.count(query, User.class);
    }

    public List<User> fetchAllUsers(List<String> usernameList) {
        Query query = new Query();
        query.addCriteria(Criteria.where("username").in(usernameList));
        return mongoTemplate.find(query, User.class);
    }
}
