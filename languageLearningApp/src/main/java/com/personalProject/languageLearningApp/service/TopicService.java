package com.personalProject.languageLearningApp.service;

import com.personalProject.languageLearningApp.dao.TopicDao;
import com.personalProject.languageLearningApp.domain.Topic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TopicService {

    @Autowired
    private TopicDao topicDao;

    public void createTopic(Topic topic, Integer creatorId) {
        Topic byName = getTopic(topic.getName(), creatorId);
        if (byName == null) {
            topicDao.insert(topic);
        } else {
            topic.setId(byName.getId());
        }
    }

    public Topic getTopic(String name, Integer creatorId) {
        return topicDao.findByName(name, creatorId);
    }
    public Topic[] getAllTopics(Integer accountId) {return  topicDao.getAllTopics(accountId);}
}
