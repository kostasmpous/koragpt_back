package com.koragpt.koragpt.repositories;

import com.koragpt.koragpt.models.Chat;
import com.koragpt.koragpt.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ChatRepository extends JpaRepository<Chat,Long> {
    List<Chat> findByUserOrderByUpdatedAtDesc(User user); // get user’s chats, newest first

    Optional<Chat> findById(Long Id);

}
