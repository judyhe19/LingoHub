package com.personalProject.languageLearningApp.dao;

import com.personalProject.languageLearningApp.domain.Account;
import com.personalProject.languageLearningApp.domain.ExerciseType;
import com.personalProject.languageLearningApp.domain.Topic;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface TopicDao {
    int insert(Topic topic);
    void updateTopicNameById(Topic topic);

    @Select("select * from grammar_topics where name = #{name} AND creator_account_id = #{accountId};")
    Topic findByName(@Param("name") String name, @Param("accountId") Integer accountId);

    @Select("select * from grammar_topics where creator_account_id = #{accountId};")
    Topic[] getAllTopics(@Param("accountId") Integer accountId);
}
