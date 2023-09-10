package com.personalProject.languageLearningApp.service;

import com.personalProject.languageLearningApp.dao.AccountDao;
import com.personalProject.languageLearningApp.domain.Account;
import com.personalProject.languageLearningApp.enums.UserFailureCode;
import com.personalProject.languageLearningApp.exception.UserException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.UUID;

@Service
public class AccountService {
    @Autowired
    private AccountDao accountDao;

    public void createAccount(Account account) throws UserException {
        Account byEmail = accountDao.findByEmail(account.getEmail());
        if (byEmail != null) {
            throw new UserException(UserFailureCode.ACCOUNT_EXISTS.getCode(), UserFailureCode.ACCOUNT_EXISTS.getMessage());
        }

        if (account.getToken() == null) {
            account.setToken(UUID.randomUUID().toString());
        }
        accountDao.insert(account);
    }

    public Account getAccountByUserId(Integer userId) {
        return accountDao.findById(userId);
    }
    public Account getAccountByEmail(String email) {
        return accountDao.findByEmail(email);
    }
    public Account getAccountByUsername(String username) {
        return accountDao.findByUsername(username);
    }



    public Account getAccountByUsernameAndToken(String username, String token) {
        return accountDao.findByCred(username, token);
    }

    public Account getAccountByEmailAndToken(String email) {
        return accountDao.findByEmail(email);
    }

    public void updateLoginInfo(Account account) {
        accountDao.updateLoginInfo(account);
    }
    public void updatePassword(Account account) {
        accountDao.updatePassword(account);
    }
    public void updateLanguage(Account account) {
        accountDao.updateLanguage(account);
    }
    public void deleteAccount(Account account) {
        accountDao.deleteAccount(account);
    }
}

