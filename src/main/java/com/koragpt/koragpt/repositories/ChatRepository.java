package com.koragpt.koragpt.repositories;

import com.koragpt.koragpt.models.Chat;
import com.koragpt.koragpt.models.Message;
import com.koragpt.koragpt.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatRepository extends JpaRepository<Chat,Long> {
    List<Chat> findByUserOrderByUpdatedAtDesc(User user); // get user’s chats, newest first

}
