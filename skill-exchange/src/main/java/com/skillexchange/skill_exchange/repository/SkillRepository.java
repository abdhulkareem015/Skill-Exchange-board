package com.skillexchange.skill_exchange.repository;

import com.skillexchange.skill_exchange.model.Skill;
import com.skillexchange.skill_exchange.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SkillRepository extends JpaRepository<Skill, Long> {
    List<Skill> findByUser(User user);
}
