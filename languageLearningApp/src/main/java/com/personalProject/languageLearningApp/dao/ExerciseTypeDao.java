package com.personalProject.languageLearningApp.dao;

import com.personalProject.languageLearningApp.domain.ExerciseType;
import com.personalProject.languageLearningApp.domain.Topic;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface ExerciseTypeDao {
    int insert(ExerciseType exerciseType);

    @Select("select * from grammar_exercise_types where name = #{name} AND creator_account_id = #{accountId};")
    ExerciseType findByName(@Param("name") String name, @Param("accountId") Integer accountId);
    @Select("select * from grammar_exercise_types where creator_account_id = #{accountId};")
    ExerciseType[] getAllTypes(@Param("accountId") Integer accountId);

    void updateTypeNameById(ExerciseType exerciseType);
}
