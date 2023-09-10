package com.personalproject.languagelearningapp.create;

import java.sql.Timestamp;

public class ExerciseType {
    private Integer id;
    private String name;
    private Integer creatorAccountId;
    private String createTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCreatorAccountId() {
        return creatorAccountId;
    }

    public void setCreatorAccountId(Integer creatorAccountId) {
        this.creatorAccountId = creatorAccountId;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }


}
