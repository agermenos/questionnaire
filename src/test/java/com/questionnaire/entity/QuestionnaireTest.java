package com.questionnaire.entity;

import com.questionnaire.AbstractTest;
import com.questionnaire.dao.QuestionnaireDao;
import com.questionnaire.dao.UserDao;
import com.questionnaire.jpa.QuestionEntity;
import com.questionnaire.jpa.QuestionnaireEntity;
import com.questionnaire.jpa.UserEntity;
import com.questionnaire.services.QuestionnaireService;
import com.questionnaire.status.QuestionStatus;
import com.questionnaire.status.QuestionnaireStatus;

import java.util.Date;

/**
 * Created by agermenos on 1/28/15.
 */
public class QuestionnaireTest extends AbstractTest {
    public QuestionnaireTest(String context){
        super(context);
    }

    private QuestionEntity createQuestion(String strQuestion, QuestionEntity parent, QuestionStatus status){
        QuestionEntity question = new QuestionEntity();
        question.setQuestion(strQuestion);
        question.setParent(parent==null?null:parent.getId());
        question.setType(status.toString());
        return question;
    }
    public void createQuestionnaireTest(QuestionnaireDao questionnaireDao, UserDao userDao) {
        QuestionnaireEntity questionnaire = new QuestionnaireEntity();
        questionnaire.setName("Basic Questionnaire");
        questionnaire.setCreated(new Date().toString());
        questionnaire.setModified(new Date().toString());
        questionnaire.setStatus(QuestionnaireStatus.CREATED.toString());

        questionnaire.addQuestion(createQuestion("How many fingers are in one hand?",null, QuestionStatus.TEXT));
        QuestionEntity outerQuestion = createQuestion("What's the capital of Mexico?", null, QuestionStatus.MULTIPLE_CHOICE);
        questionnaire.addQuestion(outerQuestion);
        questionnaire.addQuestion(createQuestion("Estroncia", outerQuestion, QuestionStatus.SINGLE_CHOICE));
        questionnaire.addQuestion(createQuestion("Santa Lucia", outerQuestion, QuestionStatus.SINGLE_CHOICE));
        questionnaire.addQuestion(createQuestion("Guadalajara", outerQuestion, QuestionStatus.SINGLE_CHOICE));
        questionnaire.addQuestion(createQuestion("Mexico City", outerQuestion, QuestionStatus.SINGLE_CHOICE));
        UserEntity user = userDao.findByEmail("agermenos@gmail.com");
        questionnaire.setUserId(user.getId());
        questionnaireDao.add(questionnaire);
    }

    public void updateQuestionnaireTest(QuestionnaireService questionnaireService, UserDao userDao) {
        UserEntity user = userDao.findByEmail("agermenos@gmail.com");
        QuestionnaireEntity questionnaireEntity = questionnaireService.getQuestionsByUserId(user.getId()).get(0);
        QuestionEntity outerQuestion =  createQuestion("Cuantas habas hay en un habal?", null, QuestionStatus.SINGLE_CHOICE);
        questionnaireEntity.addQuestion(outerQuestion);
        outerQuestion.addAnswer(createQuestion("Una haba", outerQuestion, QuestionStatus.SINGLE_CHOICE));
        outerQuestion.addAnswer(createQuestion("Muchas habas", outerQuestion, QuestionStatus.SINGLE_CHOICE));
        outerQuestion.addAnswer(createQuestion("Los habales no existen", outerQuestion, QuestionStatus.SINGLE_CHOICE));
        questionnaireService.storeQuestionnaire(questionnaireEntity, user.getId());
    }

    public void getQuestionnaire(QuestionnaireDao questionnaireDao, UserDao userDao){
        UserEntity user = userDao.findByEmail("agermenos@gmail.com");
        //QuestionnaireEntity questionnaire = questionnaireDao.findById(1500);
       QuestionnaireEntity questionnaireEntity = questionnaireDao.findByUser(user.getId()).get(0);
        getLog().info("Questionnaire: " + questionnaireEntity.toString());

    }

    public static void main(String args[]){
        QuestionnaireTest questionnaireTest=new QuestionnaireTest("spring/config/beanlocations.xml");
        QuestionnaireDao questionnaireDao = (QuestionnaireDao)questionnaireTest.getContext().getBean("questionnaireDao");
        QuestionnaireService questionnaireService = (QuestionnaireService) questionnaireTest.getContext().getBean("questionnaireService");
        UserDao userDao = (UserDao)questionnaireTest.getContext().getBean("userDao");

        //questionnaireTest.createQuestionnaireTest(questionnaireDao, userDao);
        questionnaireTest.getQuestionnaire(questionnaireDao, userDao);
        //questionnaireTest.updateQuestionnaireTest(questionnaireService, userDao);
        //questionnaireTest.getQuestionnaire(questionnaireDao);
    }
}
