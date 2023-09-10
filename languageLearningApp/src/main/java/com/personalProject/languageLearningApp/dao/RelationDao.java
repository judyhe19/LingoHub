package com.personalProject.languageLearningApp.dao;

import com.personalProject.languageLearningApp.domain.Relation;
import com.personalProject.languageLearningApp.domain.Topic;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface RelationDao {
    int insert(Relation relation);

    @Select("select * from relations where from_resource_type = #{fromType} and from_resource_id = #{fromId} and to_resource_type = #{toType} and to_resource_id = #{toId};")
    Relation find(@Param("fromType") String fromType, @Param("fromId") Integer fromId, @Param("toType") String toType, @Param("toId")Integer toId);

    @Select("select from_resource_id from relations where to_resource_id = #{toTopicId} and from_resource_type = #{fromType} or to_resource_id = #{toTypeId} and from_resource_type = #{fromType};")
    Integer[] findExerciseIdsByTopicAndType(@Param("toTopicId") Integer toTopicId, @Param("toTypeId") Integer toTypeId, @Param("fromType") String fromType);
}
