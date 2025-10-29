package com.skillexchange.skill_exchange.service;

import com.skillexchange.skill_exchange.model.Skill;
import com.skillexchange.skill_exchange.model.User;
import com.skillexchange.skill_exchange.repository.SkillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SkillService {

    @Autowired
    private SkillRepository skillRepository;

    public Skill addSkill(Skill skill) {
        return skillRepository.save(skill);
    }

    public List<Skill> getSkillsByUser(User user) {
        return skillRepository.findByUser(user);
    }

    public List<Skill> getAllSkills() {
        return skillRepository.findAll();
    }
}
