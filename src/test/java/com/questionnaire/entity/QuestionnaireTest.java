package com.questionnaire.entity;


import com.questionnaire.AbstractTest;
import com.questionnaire.dao.UserDao;
import com.questionnaire.jpa.QuestionEntity;
import com.questionnaire.jpa.QuestionnaireEntity;
import com.questionnaire.jpa.UserEntity;
import com.questionnaire.services.QuestionnaireServices;
import com.questionnaire.status.QuestionStatus;

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
        question.setParentQuestion(parent);
        question.setType(status.toString());
        return question;
    }
    public void createQuestionnaireTest(QuestionnaireServices questionnaireServices, UserDao userDao) {
        QuestionnaireEntity questionnaire = new QuestionnaireEntity();
        questionnaire.setName("Basic Questionnaire");

        questionnaire.addQuestion(createQuestion("How many fingers are in one hand?",null, QuestionStatus.TEXT));
        QuestionEntity outerQuestion = createQuestion("What's the capital of Mexico?", null, QuestionStatus.MULTIPLE_CHOICE);
        questionnaire.addQuestion(outerQuestion);
        questionnaire.addQuestion(createQuestion("Estroncia", outerQuestion, QuestionStatus.CHOICE));
        questionnaire.addQuestion(createQuestion("Santa Lucia", outerQuestion, QuestionStatus.CHOICE));
        questionnaire.addQuestion(createQuestion("Guadalajara", outerQuestion, QuestionStatus.CHOICE));
        questionnaire.addQuestion(createQuestion("Mexico City", outerQuestion, QuestionStatus.CHOICE));
        UserEntity user = userDao.findByEmail("agermenos@gmail.com");
        questionnaireServices.storeQuestionnaire(questionnaire, user.getId());
    }

    public static void main(String args[]){
        QuestionnaireTest questionnaireTest=new QuestionnaireTest("spring/config/beanlocations.xml");
        QuestionnaireServices questionnaireServices = (QuestionnaireServices)questionnaireTest.getContext().getBean("questionnaireServices");
        UserDao userDao = (UserDao)questionnaireTest.getContext().getBean("userDao");

        questionnaireTest.createQuestionnaireTest(questionnaireServices, userDao);
    }
}
