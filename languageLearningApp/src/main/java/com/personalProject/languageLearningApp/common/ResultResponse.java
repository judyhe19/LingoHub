package com.personalProject.languageLearningApp.common;

import lombok.Data;

@Data
public class ResultResponse<T> {
    int code = 0;
    String message = "success";
    T data;
}