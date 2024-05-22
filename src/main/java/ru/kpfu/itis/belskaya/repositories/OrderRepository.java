package ru.kpfu.itis.belskaya.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.kpfu.itis.belskaya.models.Order;
import ru.kpfu.itis.belskaya.models.User;

import java.util.List;
import java.util.Optional;

/**
 * @author Elizaveta Belskaya
 */
@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query(nativeQuery = true, value = "SELECT * FROM ie_order ORDER BY id OFFSET :startIndex LIMIT :pageSize")
    Optional<List<Order>> getOrdersByPageNumber(@Param("startIndex") int startIndex, @Param("pageSize") int pageSize);


    @Query("SELECT o from Order o where o.author = :author and o.state = 'ACTUAL'")
    Optional<List<Order>> findAllByAuthor(@Param("author") User author);

    Optional<Order> findById(Long id);

    @Query("SELECT o FROM Order o " +
                    "INNER JOIN Account author on o.author.account = author " +
                    "INNER JOIN User t ON t.id = :recipientId " +
                    "INNER JOIN Account recipientAccount ON t.account = recipientAccount " +
                    "AND o.recipient IS NULL AND NOT t.id IN (select candidates.id from o.candidates candidates) " +
                    "AND o.minRating <= t.rating " +
                    "AND o.author.id <> :recipientId " +
                    "AND (o.recipientGender = t.gender OR o.recipientGender = 'BOTH') " +
                    "AND o.skill in (select s.title from t.skillList s) AND o.state = 'ACTUAL' " +
            "ORDER BY o.creationDate"
    )
    Optional<List<Order>> findSuitableOrderForRecipient(@Param("recipientId") Long recipientId);


    @Query(nativeQuery = true, value = "WITH t AS (SELECT * FROM ie_recipient WHERE id = :recipientId LIMIT 1)," +
            " skill_ids AS (SELECT skill_id FROM recipient_skill WHERE recipient_id = :recipientId) " +
            "SELECT * FROM ie_order WHERE (recipient_id IS NULL AND min_rating <= (SELECT rating FROM t) " +
            "AND author_id <> :recipientId AND skill IN (SELECT title FROM skill WHERE id IN (SELECT * FROM skill_ids)) " +
            "AND ((SELECT gender FROM t) = recipient_gender OR recipient_gender = 'BOTH') " +
            "AND id NOT IN (SELECT order_id FROM order_recipient WHERE recipient_id = :recipientId) AND state = 'ACTUAL')" +
            " ORDER BY created_at")
    Optional<List<Order>> findSuitableOrderForRecipientAlternative(@Param("recipientId") Long recipientId);



    Optional<List<Order>> findOrdersByRecipient(User user);


    @Query("SELECT o FROM Order o WHERE o.recipient is null and o.author.id = :authorId and o.state = 'ACTUAL'")
    List<Order> findOrdersByAuthorWithoutRecipient(@Param("authorId") Long authorId);


    @Query("SELECT DISTINCT t FROM User t INNER JOIN Order o ON o.recipient = t WHERE o.author.id = :authorId")
    List<User> findRecipientsOfAuthor(@Param("authorId") Long authorId);


    @Transactional
    @Modifying
    @Query("UPDATE Order o SET o.recipient = null WHERE o.author.id = :authorId AND o.recipient.id = :rejectId")
    void rejectRecipientFromAuthorOrders(@Param("authorId") Long authorId, @Param("rejectId") Long rejectId);



}
