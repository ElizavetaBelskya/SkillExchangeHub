package ru.kpfu.itis.belskaya.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.kpfu.itis.belskaya.models.Review;
import ru.kpfu.itis.belskaya.models.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

    @Query("SELECT AVG(r.rating) FROM Review r WHERE r.recipient.id = :recipientId")
    Float changeRating(@Param("recipientId") Long recipientId);

    @Query("SELECT r from Review r WHERE r.recipient = :recipient and r.author = :author")
    Optional<Review> findByRecipientAndAuthor(@Param("recipient") User user, @Param("author") User author);

    Optional<List<Review>> findAllByRecipient(User user);

}
