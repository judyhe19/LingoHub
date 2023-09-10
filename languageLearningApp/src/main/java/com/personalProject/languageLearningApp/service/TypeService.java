package com.personalProject.languageLearningApp.service;

import com.personalProject.languageLearningApp.dao.ExerciseTypeDao;
import com.personalProject.languageLearningApp.domain.ExerciseType;
import com.personalProject.languageLearningApp.domain.Topic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TypeService {

    @Autowired
    ExerciseTypeDao exerciseTypeDao;

    public void createExerciseType(ExerciseType exerciseType, Integer accountId)  {
        ExerciseType byName = getType(exerciseType.getName(), accountId);
        if (byName == null) {
            exerciseTypeDao.insert(exerciseType);
        } else {
            exerciseType.setId(byName.getId());
        }
    }

    public ExerciseType getType(String name, Integer accountId) {
        return exerciseTypeDao.findByName(name, accountId);
    }
    public ExerciseType[] getAllTypes(Integer accountId) {return  exerciseTypeDao.getAllTypes(accountId);}

}
