package com.personalProject.languageLearningApp.service;

import com.personalProject.languageLearningApp.domain.Account;
import com.personalProject.languageLearningApp.enums.UserFailureCode;
import com.personalProject.languageLearningApp.exception.AuthException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.UUID;

@Service
public class AuthService {
    @Autowired
    private AccountService accountService;

    public Boolean hasValidCredential(String username, String token) {
        Account accountByUsernameAndToken = accountService.getAccountByUsernameAndToken(username, token);
        Timestamp currentTime = new Timestamp(System.currentTimeMillis());
        return accountByUsernameAndToken != null && currentTime.before(accountByUsernameAndToken.getTokenExpire());
    }

    public String login(Integer userId, String username, String email, String password) throws AuthException {
        Account accountByUserId = accountService.getAccountByUserId(userId);
        if (accountByUserId.getUsername().equalsIgnoreCase(username) &&
                accountByUserId.getPassword().equalsIgnoreCase(password) &&
                accountByUserId.getEmail().equalsIgnoreCase(email)) {

            String token = UUID.randomUUID().toString();
            Timestamp expire = Timestamp.valueOf(
                    new Timestamp(System.currentTimeMillis())
                    .toLocalDateTime()
                    .plusHours(24L));

            accountByUserId.setTokenExpire(expire);
            accountByUserId.setToken(token);
            accountService.updateLoginInfo(accountByUserId);
            return token;
        } else {
            throw new AuthException(UserFailureCode.AUTH_FAILURE.getCode(), UserFailureCode.AUTH_FAILURE.getMessage());
        }
    }

    public void setExpire(Account account) {
        Timestamp expire = Timestamp.valueOf(
                new Timestamp(System.currentTimeMillis()).toLocalDateTime());
        account.setTokenExpire(expire);
        accountService.updateLoginInfo(account);
    }
}
