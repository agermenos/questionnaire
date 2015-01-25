package com.questionnaire.dao;

import com.questionnaire.jpa.QuestionEntity;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

/**
 * Created by agermenos on 1/23/15.
 */
@Repository("questionDao")
public class QuestionDao {
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
}
