package ru.kpfu.itis.belskaya.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.kpfu.itis.belskaya.models.Skill;

import java.util.List;

/**
 * @author Elizaveta Belskaya
 */
@Repository
public interface SkillRepository extends JpaRepository<Skill, Long> {

    @Query("SELECT s.title FROM Skill s")
    List<String> findAllTitles();

}
