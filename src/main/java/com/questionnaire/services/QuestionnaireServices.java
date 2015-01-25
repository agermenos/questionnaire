package com.questionnaire.services;

import com.questionnaire.dao.QuestionDao;
import com.questionnaire.dao.QuestionnaireDao;
import com.questionnaire.dao.UserQuestionsDao;
import com.questionnaire.jpa.QuestionEntity;
import com.questionnaire.jpa.QuestionnaireEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
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

    @Transactional
    public QuestionnaireEntity getQuestionnaireById(int questionnaireId){
        QuestionnaireEntity questionnaire = questionnaireDao.findById(questionnaireId);
        List<QuestionEntity> questions = questionnaireDao.findQuestionsByQuestionnaire(questionnaireId);
        questionnaire.setQuestions(questions);
        return questionnaire;
    }

    @Transactional
    public void storeQuestionnaire(QuestionnaireEntity questionnaire){
        questionnaireDao.add(questionnaire);
        if (questionnaire.getQuestions()!=null ){
            for (QuestionEntity question:questionnaire.getQuestions()){
                questionDao.add(question);
            }
        }
    }
}
