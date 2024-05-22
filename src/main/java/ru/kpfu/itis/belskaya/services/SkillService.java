package ru.kpfu.itis.belskaya.services;


import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.kpfu.itis.belskaya.models.Skill;
import ru.kpfu.itis.belskaya.repositories.SkillRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SkillService {

    private final SkillRepository skillRepository;

    public List<String> findAllTitles() {
        return skillRepository.findAllTitles();
    }


    public List<Skill> findAll() {
        return skillRepository.findAll();
    }


}

