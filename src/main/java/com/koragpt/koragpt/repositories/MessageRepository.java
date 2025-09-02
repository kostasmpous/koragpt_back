package com.koragpt.koragpt.repositories;

import com.koragpt.koragpt.models.Chat;
import com.koragpt.koragpt.models.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message,Long> {

    List<Message> findByChatOrderByCreatedAtAsc(Chat chat); // get messages in a chat, oldest first

    List<Message> findTop50ByChatOrderByCreatedAtDesc(Chat chat); // get recent messages
}
