package com.personalProject.languageLearningApp.controller;

import com.personalProject.languageLearningApp.common.ResultResponse;
import com.personalProject.languageLearningApp.domain.Account;
import com.personalProject.languageLearningApp.domain.Exercise;
import com.personalProject.languageLearningApp.domain.ExerciseType;
import com.personalProject.languageLearningApp.domain.Topic;
import com.personalProject.languageLearningApp.exception.ExerciseContainerException;
import com.personalProject.languageLearningApp.exception.ExerciseException;
import com.personalProject.languageLearningApp.pojo.ExerciseContainer;
import com.personalProject.languageLearningApp.service.*;
import com.sun.org.apache.xpath.internal.operations.Bool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import static com.personalProject.languageLearningApp.enums.ExerciseFailureCode.CREATION_FAILURE;
import static com.personalProject.languageLearningApp.enums.UserFailureCode.NO_VALID_ACCOUNT;

@RestController
@RequestMapping("/exercise")
public class ExerciseController {
    @Autowired
    private AuthService authService;
    @Autowired
    private AccountService accountService;
    @Autowired
    private ExerciseService grammarService;
    @Autowired
    private TopicService topicService;
    @Autowired
    private TypeService typeService;

    @PostMapping("/add/{topic}/{type}")
    public ResultResponse<ExerciseContainer> add(@RequestBody ExerciseContainer exerciseContainer,
                                        @PathVariable("topic") String topic,
                                        @PathVariable("type") String type) {
        ResultResponse<ExerciseContainer> resultResponse = new ResultResponse<>();
        Account account = exerciseContainer.getCreator();

        if (authService.hasValidCredential(account.getUsername(), account.getToken())) {
            Topic t = new Topic();
            ExerciseType et = new ExerciseType();
            et.setName(type);
            et.setCreatorAccountId(account.getId());
            t.setName(topic);
            t.setCreatorAccountId(account.getId());
            exerciseContainer.setExerciseType(et);
            exerciseContainer.setTopic(t);
            grammarService.createExercise(exerciseContainer);
            resultResponse.setData(exerciseContainer);
        }
        else {
            resultResponse.setCode(NO_VALID_ACCOUNT.getCode());
            resultResponse.setMessage(NO_VALID_ACCOUNT.getMessage());
            resultResponse.setData(null);
        }
        return resultResponse;
    }

    @GetMapping("/info/{exerciseId}")
    public ResultResponse<Exercise> getById(@PathVariable("exerciseId") Integer exerciseId) {
        ResultResponse<Exercise> resultResponse = new ResultResponse<>();
        resultResponse.setData(grammarService.getExerciseById(exerciseId));
        return resultResponse;
    }

    @GetMapping("/info/allTopics/{accountId}")
    public ResultResponse<Topic[]> getAllTopics(@PathVariable("accountId") Integer accountId) {
        ResultResponse<Topic[]> resultResponse = new ResultResponse<>();
        resultResponse.setData(topicService.getAllTopics(accountId));
        return resultResponse;
    }

    @GetMapping("/info/allTypes/{accountId}")
    public ResultResponse<ExerciseType[]> getAllTypes(@PathVariable("accountId") Integer accountId) {
        ResultResponse<ExerciseType[]> resultResponse = new ResultResponse<>();
        resultResponse.setData(typeService.getAllTypes(accountId));
        return resultResponse;
    }

    @GetMapping("/findExercises/{topicId}/{typeId}")
    public ResultResponse<ArrayList<Exercise>> getAllExercises(@PathVariable("topicId") Integer topicId, @PathVariable("typeId") Integer typeId ) {
        ResultResponse<ArrayList<Exercise>> resultResponse = new ResultResponse<>();
        resultResponse.setData(grammarService.getExercisesIdsByRelation(topicId, typeId));
        return resultResponse;
    }

//    @PostMapping("/updateExercise")
//    public ResultResponse<Exercise> getById(@RequestBody Exercise exercise) {
//        ResultResponse<Boolean> resultResponse = new ResultResponse<>();
//
//        if (authService.hasValidCredential(account.getUsername(), account.getToken())) {
//            account.setLanguage(language);
//            accountService.updateLanguage(account);
//            resultResponse.setData(true);
//        }
//        else {
//            resultResponse.setCode(NO_VALID_ACCOUNT.getCode());
//            resultResponse.setMessage(NO_VALID_ACCOUNT.getMessage());
//            resultResponse.setData(false);
//        }
//        return resultResponse;
//    }
}
