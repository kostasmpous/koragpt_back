package com.koragpt.koragpt.repositories;

import com.koragpt.koragpt.models.Contact;
import com.koragpt.koragpt.models.Message;
import com.koragpt.koragpt.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ContactRepository extends JpaRepository<Contact,Long> {
    List<Contact> findAllByUser(User user);

    List<Contact> findAllByUserAndType(User user, String type);
}
