package com.example.simpleMessenger.repository;

import com.example.simpleMessenger.dtos.request.GroupCreationRequest;
import com.example.simpleMessenger.entity.Group;
import com.example.simpleMessenger.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class GroupRepository {
    private final MongoTemplate mongoTemplate;
    private final UserRepository userRepository;

    public Optional<Group> findByGroupName(String groupName) {
        Query query = new Query();
        query.addCriteria(Criteria.where("groupName").is(groupName));
        Group group = mongoTemplate.findOne(query, Group.class);
        return Optional.ofNullable(group);
    }

    public void createGroup(GroupCreationRequest groupCreationRequest) {
        List<User> usernameList = userRepository.fetchAllUsers(groupCreationRequest.getUsernameList());
        Group group = Group.builder()
                .groupName(groupCreationRequest.getGroupName())
                .userList(usernameList)
                .build();
        mongoTemplate.save(group, "group");
    }
}
