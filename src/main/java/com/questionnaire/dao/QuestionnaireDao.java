package com.questionnaire.dao;

import com.questionnaire.jpa.QuestionEntity;
import com.questionnaire.jpa.QuestionnaireEntity;
import com.questionnaire.jpa.UserEntity;
import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by Alejandro on 1/24/2015.
 */
@Repository("questionnaireDao")
public class QuestionnaireDao {
    @Autowired
    private SessionFactory sessionFactory;
    @Autowired
    private QuestionDao questionDao;

    @Transactional
    public void add(QuestionnaireEntity questionnaire){
        sessionFactory.getCurrentSession().save(questionnaire);
        for (QuestionEntity question:questionnaire.getQuestions()){
            sessionFactory.getCurrentSession().save(question);
        }
    }

    @Transactional
    public void update(QuestionnaireEntity questionnaire){
        for (QuestionEntity question:questionnaire.getQuestions()){
            for (QuestionEntity answer: question.getAnswers()){
                if (answer.getQuestionnaireId()!=null) {
                    answer.setQuestionnaireId(null);
                }
            }
            if (question.getId()==null) {
                questionDao.add(question);
            }
            else {
                questionDao.update(question);
            }
        }
        sessionFactory.getCurrentSession().update(questionnaire);
    }

    @Transactional
    public void delete(QuestionnaireEntity questionnaire){
        sessionFactory.getCurrentSession().delete(questionnaire);
    }

    @Transactional
    public QuestionnaireEntity findById(int questionnaireId){
        return (QuestionnaireEntity) sessionFactory.getCurrentSession().get(QuestionnaireEntity.class, questionnaireId);
    }

    @Transactional
    public List<QuestionnaireEntity> findByUser(int userId){
        SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery("" +
                "SELECT * FROM questionnaire WHERE user_id=" + userId);
        query.addEntity(QuestionnaireEntity.class);
        return query.list();
    }

    @Transactional
    public List<QuestionEntity> findQuestionsByQuestionnaire (int questionnaireId){
        SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery("" +
                "SELECT * FROM question WHERE questionnaire_id=" + questionnaireId +
                " AND parent_question is NULL");

        query.addEntity(QuestionEntity.class);
        return query.list();
    }
}
