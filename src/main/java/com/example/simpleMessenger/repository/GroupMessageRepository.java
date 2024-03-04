package com.example.simpleMessenger.repository;

import com.example.simpleMessenger.dtos.ChatHistory;
import com.example.simpleMessenger.entity.Group;
import com.example.simpleMessenger.entity.GroupMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class GroupMessageRepository {
    private final MongoTemplate mongoTemplate;
    private final GroupRepository groupRepository;

    public List<ChatHistory> getChatHistory(String groupName) {
        Criteria criteria = Criteria.where("groupName").is(groupName);
        Query query = new Query().addCriteria(criteria);
        Group group = mongoTemplate.findOne(query, Group.class);
        if(group.getGroupMessageList() == null) return new ArrayList<>();
        return group.getGroupMessageList().stream()
                .map(message -> new ChatHistory(message.getFromUser(), message.getText()))
                .collect(Collectors.toList());
    }

    public void postMessage(GroupMessage groupMessage, String groupName) {
        groupMessage = mongoTemplate.save(groupMessage, "group_message");
        var group = groupRepository.findByGroupName(groupName).orElseThrow();
        if(group.getGroupMessageList() == null) group.setGroupMessageList(new ArrayList<>());
        group.getGroupMessageList().add(groupMessage);
        mongoTemplate.save(group, "group");
    }
}
