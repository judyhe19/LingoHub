package com.personalProject.languageLearningApp.controller;
import com.personalProject.languageLearningApp.common.ResultResponse;
import com.personalProject.languageLearningApp.domain.Account;
import com.personalProject.languageLearningApp.exception.UserException;
import com.personalProject.languageLearningApp.service.AccountService;
import com.personalProject.languageLearningApp.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import static com.personalProject.languageLearningApp.enums.UserFailureCode.NO_VALID_ACCOUNT;

@RestController
@RequestMapping("/account")
public class AccountController {
    @Autowired
    private AuthService authService;
    @Autowired
    private AccountService accountService;

    @PostMapping("/register")
    public ResultResponse<Account> register(@RequestBody Account account) {
        ResultResponse<Account> resultResponse = new ResultResponse<>();
        try {
            accountService.createAccount(account);
            resultResponse.setData(account);
        } catch (UserException e) {
            resultResponse.setCode(e.getCode());
            resultResponse.setMessage(e.getMessage());
        }
        return resultResponse;
    }

    @GetMapping("/info/id/{userId}")
    public ResultResponse<Account> getById(@PathVariable("userId") Integer userId) {
        ResultResponse<Account> resultResponse = new ResultResponse<>();
        resultResponse.setData(accountService.getAccountByUserId(userId));
        return resultResponse;
    }

    @GetMapping("/info/email/{email}")
    public ResultResponse<Account> getByEmail(@PathVariable("email") String email) {
        ResultResponse<Account> resultResponse = new ResultResponse<>();
        resultResponse.setData(accountService.getAccountByEmail(email));
        return resultResponse;
    }

    @GetMapping("/info/username/{username}")
    public ResultResponse<Account> getByUsername(@PathVariable("username") String username) {
        ResultResponse<Account> resultResponse = new ResultResponse<>();
        resultResponse.setData(accountService.getAccountByUsername(username));
        return resultResponse;
    }

    @PostMapping("/resetPassword/{newPassword}")
    public ResultResponse<Boolean> resetPassword(@RequestBody Account account, @PathVariable("newPassword") String newPassword) {
        ResultResponse<Boolean> resultResponse = new ResultResponse<>();
        if (authService.hasValidCredential(account.getUsername(), account.getToken())) {
            account.setPassword(newPassword);
            accountService.updatePassword(account);
            // set tokenExpire
            authService.setExpire(account);
            resultResponse.setData(true);
        }
        else {
            resultResponse.setCode(NO_VALID_ACCOUNT.getCode());
            resultResponse.setMessage(NO_VALID_ACCOUNT.getMessage());
            resultResponse.setData(false);
        }
        return resultResponse;
    }

    @PostMapping("/updateLanguage/{language}")
    public ResultResponse<Boolean> updateLanguage(@RequestBody Account account, @PathVariable("language") String language) {
        ResultResponse<Boolean> resultResponse = new ResultResponse<>();
        if (authService.hasValidCredential(account.getUsername(), account.getToken())) {
            account.setLanguage(language);
            accountService.updateLanguage(account);
            resultResponse.setData(true);
        }
        else {
            resultResponse.setCode(NO_VALID_ACCOUNT.getCode());
            resultResponse.setMessage(NO_VALID_ACCOUNT.getMessage());
            resultResponse.setData(false);
        }
        return resultResponse;
    }

    @PostMapping("/deleteAccount")
    public ResultResponse<Boolean> deleteAccount(@RequestBody Account account)  {
        ResultResponse<Boolean> resultResponse = new ResultResponse<>();
        if (authService.hasValidCredential(account.getUsername(), account.getToken())) {
            accountService.deleteAccount(account);
            resultResponse.setData(true);
        } else {
            resultResponse.setCode(NO_VALID_ACCOUNT.getCode());
            resultResponse.setMessage(NO_VALID_ACCOUNT.getMessage());
            resultResponse.setData(false);
        }
        return resultResponse;
    }



}
