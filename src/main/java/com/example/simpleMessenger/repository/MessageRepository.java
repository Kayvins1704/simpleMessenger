package com.example.simpleMessenger.repository;

import com.example.simpleMessenger.dtos.ChatHistory;
import com.example.simpleMessenger.dtos.UnreadMessages;
import com.example.simpleMessenger.entity.Message;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.BulkOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.*;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class MessageRepository {
    private final MongoTemplate mongoTemplate;

    public List<UnreadMessages> getUnreadMessages(String username) {
        SortOperation sortOperation = Aggregation.sort(Sort.Direction.ASC, "_id");

        MatchOperation matchOperation = Aggregation.match(
                new Criteria().andOperator(
                        Criteria.where("toUser").is(username),
                        Criteria.where("isRead").is(Boolean.FALSE)
                )
        );

        GroupOperation groupOperation = Aggregation.group("fromUser")
                .first("fromUser").as("fromUser")
                .push("text").as("texts")
                .push("_id").as("messageIds");

        Aggregation aggregation = Aggregation.newAggregation(sortOperation, matchOperation, groupOperation);

        AggregationResults<UnreadMessages> result
                = mongoTemplate.aggregate(aggregation, Message.class, UnreadMessages.class);

        List<UnreadMessages> unreadMessagesList = result.getMappedResults();

        List<String> messageIdList = new ArrayList<>();
        unreadMessagesList.forEach(unreadMessage -> messageIdList.addAll(unreadMessage.getMessageIds()));

        if(!messageIdList.isEmpty()) {
            BulkOperations bulkOps = mongoTemplate.bulkOps(BulkOperations.BulkMode.UNORDERED, Message.class, "message");
            for(String messageId : messageIdList) {
                Query query = new Query();
                query.addCriteria(Criteria.where("_id").is(messageId));
                Update update = new Update();
                update.set("isRead", Boolean.TRUE);
                bulkOps.upsert(query, update);
            }
            bulkOps.execute();
        }

        return unreadMessagesList;
    }

    public List<ChatHistory> getChatHistory(String fromUser, String toUser) {
        Criteria criteria = new Criteria().andOperator(
                Criteria.where("fromUser").in(List.of(fromUser, toUser)),
                Criteria.where("toUser").in(List.of(fromUser, toUser))
        );

        Query query = new Query().addCriteria(criteria)
                        .with(Sort.by(Sort.Direction.ASC, "_id"));
        query.fields()
                .include("fromUser")
                .include("text");
        List<Message> messages = mongoTemplate.find(query, Message.class);
        return messages.stream()
                .map(message -> new ChatHistory(message.getFromUser(), message.getText()))
                .collect(Collectors.toList());
    }

    public void postMessage(Message message) {
        mongoTemplate.save(message, "message");
    }
}
