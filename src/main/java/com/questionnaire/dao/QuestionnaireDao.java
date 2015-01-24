package com.questionnaire.dao;

import com.questionnaire.jpa.QuestionnaireEntity;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.transaction.Transactional;

/**
 * Created by Alejandro on 1/24/2015.
 */
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
}
