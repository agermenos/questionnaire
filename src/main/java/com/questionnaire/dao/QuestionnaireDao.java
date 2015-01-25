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

    @Transactional
    public void add(QuestionnaireEntity questionnaire){
        sessionFactory.getCurrentSession().save(questionnaire);
    }

    @Transactional
    public void update(QuestionnaireEntity questionnaire){
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
        Criteria criteria = sessionFactory.
                getCurrentSession().
                createCriteria(UserEntity.class);
        criteria.add(Restrictions.like("user", "%" + userId + "%"));
        return criteria.list();
    }

    @Transactional
    public List<QuestionEntity> findQuestionsByQuestionnaire (int questionnaireId){
        SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery("" +
                "SELECT * FROM question WHERE questionnaire_id=" + questionnaireId);
        query.addEntity(QuestionEntity.class);
        return query.list();
    }
}
