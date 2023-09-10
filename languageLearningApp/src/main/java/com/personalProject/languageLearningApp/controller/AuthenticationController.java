package com.personalProject.languageLearningApp.controller;

import com.personalProject.languageLearningApp.common.ResultResponse;
import com.personalProject.languageLearningApp.domain.Account;
import com.personalProject.languageLearningApp.enums.UserFailureCode;
import com.personalProject.languageLearningApp.exception.AuthException;
import com.personalProject.languageLearningApp.service.AccountService;
import com.personalProject.languageLearningApp.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    @Autowired
    private AuthService authService;
    @Autowired
    private AccountService accountService;

    @PostMapping("/doAuth")
    public ResultResponse<Boolean> auth(@RequestBody Account account) {
        ResultResponse<Boolean> resultResponse = new ResultResponse<>();
        resultResponse.setData(authService.hasValidCredential(account.getUsername(), account.getToken()));
        return resultResponse;
    }

    @PostMapping("/logout")
    public ResultResponse<Boolean> logout(@RequestBody Account account) {
        ResultResponse<Boolean> resultResponse = new ResultResponse<>();

        if (authService.hasValidCredential(account.getUsername(), account.getToken())) {
            authService.setExpire(account);
            resultResponse.setData(true);
        }
        else  {
            resultResponse.setCode(UserFailureCode.NO_VALID_ACCOUNT.getCode());
            resultResponse.setMessage(UserFailureCode.NO_VALID_ACCOUNT.getMessage());
            resultResponse.setData(false);
        }
        return resultResponse;
    }

    @PostMapping("/login")
    public ResultResponse<String> login(@RequestBody Account account) {
        ResultResponse<String> resultResponse = new ResultResponse<>();
        try {
            if (account.getId() == null) {
                resultResponse.setCode(UserFailureCode.NO_VALID_ACCOUNT.getCode());
                resultResponse.setMessage(UserFailureCode.NO_VALID_ACCOUNT.getMessage());
            }
            else {
                resultResponse.setData(authService.login(account.getId(), account.getUsername(), account.getEmail(), account.getPassword()));
            }
        } catch (AuthException e) {
            resultResponse.setCode(e.getCode());
            resultResponse.setMessage(e.getMessage());
        }
        return resultResponse;
    }
}
