package com.koragpt.koragpt.repositories;

import com.koragpt.koragpt.models.Chat;
import com.koragpt.koragpt.models.ModelsAI;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ModelsAIRepository extends JpaRepository<ModelsAI,Long> {
    List<ModelsAI> findAllByCompany(String company);
}
