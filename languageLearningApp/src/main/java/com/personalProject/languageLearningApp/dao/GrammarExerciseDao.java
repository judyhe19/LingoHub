package com.personalProject.languageLearningApp.dao;

import com.personalProject.languageLearningApp.domain.Exercise;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface GrammarExerciseDao {

    void createExercise(Exercise exercise);
//    void createTopic(String name);
//    void createType(String name);
    void updateExercise(Exercise exercise);
    Exercise findExerciseByQuestion(@Param("question") String question);
    Exercise findExerciseById(@Param("id") int id);
//    @Select("select id from grammar_exercise_type where name = #{name};")
//    int findTypeByName(@Param("name") String name);

//    @Select("select id from grammar_topics where name = #{name};")
//    int findTopicByName(@Param("name") String name);

//    void relateAccountTOExercise(int accountId, int exerciseId);
//    void relateAccountTOTopic(int accountId, int topicId);
//    void relateExerciseTOType(int exerciseId, int typeId);
//    void relateTypeTOTopic(int typeId, int topicId);
}
