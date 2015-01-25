package com.questionnaire.dao;

import com.questionnaire.jpa.QuestionEntity;
import com.questionnaire.jpa.QuestionnaireEntity;
import com.questionnaire.jpa.UserEntity;
import org.hibernate.SQLQuery;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by Alejandro on 1/24/2015.
 */
@Repository("userQuestionsDao")
public class UserQuestionsDao {
    @Autowired
    private SessionFactory sessionFactory;

    @Transactional
    public void add(QuestionEntity question){
        sessionFactory.getCurrentSession().save(question);
    }

    @Transactional
    public void update(QuestionEntity question){
        sessionFactory.getCurrentSession().update(question);
    }

    @Transactional
    public void delete(QuestionEntity question){
        sessionFactory.getCurrentSession().delete(question);
    }

    @Transactional
    public QuestionEntity findById(int questionId){
        return (QuestionEntity) sessionFactory.getCurrentSession().get(QuestionEntity.class, questionId);
    }

    @Transactional
    public List<UserEntity> findUsersByQuestionnaire(int questionnaireId){
        SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery("" +
                "SELECT * from user WHERE user.id=(SELECT user_id FROM user_questions WHERE user_questions.questionnaire_id= " + questionnaireId + ")");
        query.addEntity(UserEntity.class);
        return query.list();
    }

    @Transactional
    public List<QuestionnaireEntity> findQuestionnairesByUser(int userId){
        SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery("" +
                "SELECT * from questionnaire WHERE questionnaire.id=(SELECT questionnaire_id FROM user_questions WHERE user_questions.user_id= " + userId + ")");
        query.addEntity(QuestionnaireEntity.class);
        return query.list();
    }
}
