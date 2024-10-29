package com.killiann.oneshot.repositories;

import com.killiann.oneshot.entities.Message;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MessageRepository extends MongoRepository<Message, String> {
    void deleteByRoomId(String roomId);
}
