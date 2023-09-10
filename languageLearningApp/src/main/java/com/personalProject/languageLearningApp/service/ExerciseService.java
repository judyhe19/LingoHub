package com.personalProject.languageLearningApp.service;

import com.personalProject.languageLearningApp.dao.GrammarExerciseDao;
import com.personalProject.languageLearningApp.dao.RelationDao;
import com.personalProject.languageLearningApp.domain.Account;
import com.personalProject.languageLearningApp.domain.Exercise;
import com.personalProject.languageLearningApp.domain.ExerciseType;
import com.personalProject.languageLearningApp.domain.Relation;
import com.personalProject.languageLearningApp.domain.Topic;
import com.personalProject.languageLearningApp.exception.ExerciseException;
import com.personalProject.languageLearningApp.pojo.ExerciseContainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;

@Service
public class ExerciseService {
    @Autowired
    private GrammarExerciseDao grammarExerciseDao;
    @Autowired
    private TopicService topicService;
    @Autowired
    private TypeService typeService;
    @Autowired
    private RelationDao relationDao;


    public void createExercise(ExerciseContainer exerciseContainer) {
        exerciseContainer.getTopic().setCreateTime(new Timestamp(System.currentTimeMillis()));
        exerciseContainer.getExerciseType().setCreateTime(new Timestamp(System.currentTimeMillis()));

        Topic topic = topicService.getTopic(exerciseContainer.getTopic().getName(), exerciseContainer.getCreator().getId());
        if (topic == null) {
            topicService.createTopic(exerciseContainer.getTopic(), exerciseContainer.getCreator().getId());
        } else {
            exerciseContainer.setTopic(topic);
        }

        ExerciseType exerciseType = typeService.getType(exerciseContainer.getExerciseType().getName(), exerciseContainer.getCreator().getId());
        if (exerciseType == null) {
            typeService.createExerciseType(exerciseContainer.getExerciseType(), exerciseContainer.getCreator().getId());
        } else {
            exerciseContainer.setExerciseType(exerciseType);
        }

        exerciseContainer.getExercise().setCreateTime(new Timestamp(System.currentTimeMillis()));
        exerciseContainer.getExercise().setCreatorAccountId(exerciseContainer.getCreator().getId());
        grammarExerciseDao.createExercise(exerciseContainer.getExercise());

        Relation topicRelation = relationDao.find("EXERCISE", exerciseContainer.getExercise().getId(), "TOPIC", exerciseContainer.getTopic().getId());
        if (topicRelation == null) {
            topicRelation = new Relation();
            topicRelation.setFromResource("EXERCISE");
            topicRelation.setFromResourceId(exerciseContainer.getExercise().getId());
            topicRelation.setToResource("TOPIC");
            topicRelation.setToResourceId(exerciseContainer.getTopic().getId());
            relationDao.insert(topicRelation);
        }

        Relation typeRelation = relationDao.find("EXERCISE", exerciseContainer.getExercise().getId(), "EXERCISE_TYPE", exerciseContainer.getExerciseType().getId());
        if (typeRelation == null) {
            typeRelation = new Relation();
            typeRelation.setFromResource("EXERCISE");
            typeRelation.setFromResourceId(exerciseContainer.getExercise().getId());
            typeRelation.setToResource("EXERCISE_TYPE");
            typeRelation.setToResourceId(exerciseContainer.getExerciseType().getId());
            relationDao.insert(typeRelation);
        }
    }

    public Exercise getExerciseById(int id) {
        return grammarExerciseDao.findExerciseById(id);
    }
    public ArrayList<Exercise> getExercisesIdsByRelation(Integer topicId, Integer typeId) {
        ArrayList<Exercise> exercises = new ArrayList<>();
        Integer[] exerciseIds = relationDao.findExerciseIdsByTopicAndType(topicId, typeId, "EXERCISE");
        for (int i = 0; i < exerciseIds.length; i++) {
            exercises.add(getExerciseById(exerciseIds[i]));
        }
        return exercises;
    }


//    public void createExercise(Account account, String topic, String type, Exercise exercise) throws ExerciseException {
//        int topicId = grammarExerciseDao.findTopicByName(topic);
//        int typeId = grammarExerciseDao.findTypeByName(type);
//
//        if (topicId == 0) {
//            grammarExerciseDao.createTopic(topic);
//            topicId = grammarExerciseDao.findTopicByName(topic);
//        }
//
//        if (typeId == 0) {
//           grammarExerciseDao.createType(type);
//           typeId = grammarExerciseDao.findTypeByName(type);
//        }
//
//        grammarExerciseDao.createExercise(exercise);
//        int exerciseId = grammarExerciseDao.findExerciseByQuestion(exercise.getQuestion()).getId();
//        grammarExerciseDao.relateAccountTOExercise(account.getId(), exerciseId);
//        grammarExerciseDao.relateAccountTOTopic(account.getId(), topicId);
//        grammarExerciseDao.relateExerciseTOType(exerciseId, typeId);
//        grammarExerciseDao.relateTypeTOTopic(typeId, topicId);
//
//        // accId  exId
//        // accId   topicId
//        // typeId topicId
//        // exId   typeId
//
//
//
//    }
//
//    public void createTopic(String name) {grammarExerciseDao.createTopic(name);}
//    public void createType(String name) {grammarExerciseDao.createType(name);}

//    public void updateExercise(GrammarExercise exercise, String topic, String type) {
//        grammarExerciseDao.updateExercise(exercise);
//
//
//        // only type changed, check if type/topic already exists & whether the relationship already exists
//        int typeId = grammarExerciseDao.findTypeByName(type);
//        int topicId = grammarExerciseDao.findTopicByName(topic);
//
//        if (typeId == 0) {
//            grammarExerciseDao.createType(type);
//            typeId = grammarExerciseDao.findTypeByName(type);
//        }
//        grammarExerciseDao.relateTypeTOTopic(typeId, topicId);
//
//        // only topic change
//
//        // BOTH type and topic have changed
//
//        // check if type has changed
//        // put exercise under new type
//        // check if topic has changed
//        // put exercise type under new topic
//
//
//        if (topicId == 0) {
//            grammarExerciseDao.createTopic(topic);
//            topicId = grammarExerciseDao.findTopicByName(topic);
//        }
//
//
//
//        if (typeId == 0) {
//            grammarExerciseDao.createType(type);
//            typeId = grammarExerciseDao.findTypeByName(type);
//        }
//
//        if (typeId != 0) {grammarExerciseDao.relateExerciseTOType(exercise.getId(), typeId);};
//        if (topicId != 0) {grammarExerciseDao.relateTypeTOTopic(typeId, topicId);};
//    }


}
