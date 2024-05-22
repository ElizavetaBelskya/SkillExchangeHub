package ru.kpfu.itis.belskaya.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.kpfu.itis.belskaya.models.User;

import java.util.List;
import java.util.Optional;

/**
 * @author Elizaveta Belskaya
 */

@Repository
public interface RecipientRepository extends JpaRepository<User, Long> {

    Optional<List<User>> findUserByEmailOrPhone(String email, String phone);


}
