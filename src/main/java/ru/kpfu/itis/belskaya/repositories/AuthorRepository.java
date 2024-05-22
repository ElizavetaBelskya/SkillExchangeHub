package ru.kpfu.itis.belskaya.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.kpfu.itis.belskaya.models.User;

import java.util.List;


/**
 * @author Elizaveta Belskaya
 */
@Repository
public interface AuthorRepository extends JpaRepository<User, Long> {

    @Query(nativeQuery = true, value = "SELECT COALESCE(count(o.author_id), 0) from  ie_order o " +
            " where o.skill = :skill and o.recipient_id = :id")
    int findBuddyCountBySkill(@Param("id") Long id, @Param("skill") String skill);

    @Query("SELECT count(u) from User u inner join Order o on o.author = u and o.recipient = :recipient")
    int getAuthorsCountByRecipient(@Param("recipient") User user);


}
