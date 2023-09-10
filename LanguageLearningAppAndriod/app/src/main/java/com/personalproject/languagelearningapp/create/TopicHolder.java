package com.personalproject.languagelearningapp.create;

import android.content.SharedPreferences;
import com.personalproject.languagelearningapp.common.HttpUtils;

import java.util.ArrayList;

public class TopicHolder {

    public static final String accountFileSharePreferenceKey = "local.account.stored.info";
    public static final String topicSharePreferenceKey = "local.account.stored.info.topics";
    public static final String topicIdSharePreferenceKey = "local.account.stored.info.topicIds";

    public static ArrayList<Integer> topicIds = new ArrayList<>();
    public static ArrayList<Topic> topics = new ArrayList<>();

    public static void onEditTopics(Topic topic, SharedPreferences sharedPreferences) {
        topicIds.add(topic.getId());
        addTopic(topic);
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.putString(topicSharePreferenceKey, HttpUtils.gson.toJson(topics));
        edit.putString(topicIdSharePreferenceKey, HttpUtils.gson.toJson(topicIds));
        edit.apply();
    }

    public static ArrayList<Topic> getTopics() {
        return topics;
    }

    public static void addTopic(Topic topic) {
        topics.add(topic);
    }
}
