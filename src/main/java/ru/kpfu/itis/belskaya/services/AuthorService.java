package ru.kpfu.itis.belskaya.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.kpfu.itis.belskaya.models.User;
import ru.kpfu.itis.belskaya.repositories.AuthorRepository;

@Service
public class AuthorService {

    @Autowired
    AuthorRepository authorRepository;

    public int getAuthorsCountByRecipient(User user) {
        return authorRepository.getAuthorsCountByRecipient(user);
    }


}
