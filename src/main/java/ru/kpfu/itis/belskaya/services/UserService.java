package ru.kpfu.itis.belskaya.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.kpfu.itis.belskaya.models.User;
import ru.kpfu.itis.belskaya.repositories.AccountRepository;
import ru.kpfu.itis.belskaya.repositories.RecipientRepository;

/**
 * @author Elizaveta Belskaya
 */

@Service
public class UserService {


    @Autowired
    private RecipientRepository recipientRepository;



    public User findByAccountId(Long id) {
        User account = recipientRepository.getOne(id);
        return account;
    }
}
