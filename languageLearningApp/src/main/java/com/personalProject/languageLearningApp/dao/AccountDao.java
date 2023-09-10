package com.personalProject.languageLearningApp.dao;

import com.personalProject.languageLearningApp.domain.Account;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface AccountDao {
    int insert(Account account);

    // Mybatis mapping - XML approach
    Account findById(@Param("id") Integer id);

    // Mybatis mapping - Annotations approach
    @Select("select * from account_info where email = #{email};")
    Account findByEmail(@Param("email") String email);

    @Select("select * from account_info where username = #{username};")
    Account findByUsername(@Param("username") String username);


    // Mybatis mapping - XML approach
    Account findByCred(@Param("username") String username, @Param("token") String token);

    void updateLoginInfo(Account account);
    void updatePassword(Account account);
    void updateLanguage(Account account);
    void deleteAccount(Account account);
}
