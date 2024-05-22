package ru.kpfu.itis.belskaya.services;


import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import ru.kpfu.itis.belskaya.models.Account;
import ru.kpfu.itis.belskaya.models.User;
import ru.kpfu.itis.belskaya.repositories.AccountRepository;
import ru.kpfu.itis.belskaya.repositories.RecipientRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AccountService implements UserDetailsService {

    private final AccountRepository accountRepository;
    private final RecipientRepository recipientRepository;
    private final PasswordEncoder passwordEncoder;

    public Optional<List<User>> findAccount(String email, String phone) {
        return recipientRepository.findUserByEmailOrPhone(email, phone);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<Account> user = accountRepository.findByEmail(email);
        if (user.isPresent()) {
            return user.get();
        } else {
            throw new UsernameNotFoundException("User with such email doesn't exist");
        }
    }

    public void deleteAccount(Account account) {
        account.setState(Account.State.DELETED);
        accountRepository.save(account);
    }

    public boolean registerUser(Account account, User user) throws Exception {
        if (findAccount(user.getEmail(), user.getPhone()).isPresent()) {
            throw new Exception("User already registered");
        } else {
            account.setPasswordHash(passwordEncoder.encode(account.getPassword()));
            Account addedAccount = accountRepository.save(account);
            user.setAccount(addedAccount);
            User createdUser = recipientRepository.save(user);
            return createdUser.getId() != null;
        }
    }



}
