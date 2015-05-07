package com.questionnaire.services;

import com.questionnaire.dao.QuestionDao;
import com.questionnaire.dao.QuestionnaireDao;
import com.questionnaire.dao.UserDao;
import com.questionnaire.dao.UserQuestionsDao;
import com.questionnaire.jpa.QuestionEntity;
import com.questionnaire.jpa.QuestionnaireEntity;
import com.questionnaire.jpa.UserEntity;
import com.questionnaire.status.QuestionnaireStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

/**
 * Created by Alejandro on 1/24/2015.
 */
@Service("questionnaireServices")
public class QuestionnaireServices {
    @Autowired
    QuestionnaireDao questionnaireDao;
    @Autowired
    QuestionDao questionDao;
    @Autowired
    UserQuestionsDao userQuestionsDao;
    @Autowired
    UserDao userDao;

    @Transactional
    public QuestionnaireEntity getQuestionnaireById(int questionnaireId){
        QuestionnaireEntity questionnaire = questionnaireDao.findById(questionnaireId);
        return questionnaire;
    }

    @Transactional
    public List<QuestionnaireEntity> getQuestionsByUserId(int userId){
        return questionnaireDao.findByUser(userId);
    }

    @Transactional
    public void publishQuestionnaire(int questionnaireId){
        QuestionnaireEntity questionnaire = questionnaireDao.findById(questionnaireId);
        if (questionnaire.getQuestions()!=null){
            questionnaire.setStatus(QuestionnaireStatus.PUBLISHED.toString());
        }
    }

    @Transactional
    public void saveQuestion(QuestionEntity question){
        if (question.getId()==null || question.getId()==0) {
            questionDao.add(question);
        }
        else {
            questionDao.update(question);
        }
    }

    @Transactional
    public void storeQuestionnaire(QuestionnaireEntity questionnaire, int userId){
        questionnaire.setUserId(userId);
        if (questionnaire.getId()==0) {
            questionnaire.setCreated(new Date().toString());
            questionnaire.setModified(new Date().toString());
            questionnaire.setStatus(QuestionnaireStatus.CREATED.toString());
            questionnaireDao.add(questionnaire);
        }
        else {
            questionnaire.setModified(new Date().toString());
            questionnaireDao.update(questionnaire);
        }


    }

    @Transactional
    public List<QuestionEntity> getQuestions(int questionnaireId) {
        return questionnaireDao.findQuestionsByQuestionnaire(questionnaireId);
    }
}
