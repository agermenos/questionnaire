package com.questionnaire.services;

import com.questionnaire.dao.AnswerDao;
import com.questionnaire.dao.UserDao;
import com.questionnaire.jpa.AnswerEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by Alejandro on 1/25/2015.
 */
@Service("answerServices")
public class AnswerServices {
    @Autowired
    AnswerDao answerDao;
    @Autowired
    UserDao userDao;

    @Transactional
    public void storeAnswers(List<AnswerEntity> answers, int userId){
        for (AnswerEntity answer:answers){
            answer.setUser(userDao.findById(userId));
            answerDao.add(answer);
        }
    }
}
