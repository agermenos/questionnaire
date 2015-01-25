package com.questionnaire.dao;

import com.questionnaire.jpa.AnswerEntity;
import org.hibernate.SQLQuery;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by agermenos on 1/23/15.
 */
@Repository("answerDao")
public class AnswerDao {
    @Autowired
    private SessionFactory sessionFactory;

    @Transactional
    public void add(AnswerEntity answer){
        sessionFactory.getCurrentSession().save(answer);
    }

    @Transactional
    public void update(AnswerEntity answer){
        sessionFactory.getCurrentSession().update(answer);
    }

    @Transactional
    public void delete(AnswerEntity answer){
        sessionFactory.getCurrentSession().delete(answer);
    }

    @Transactional
    public AnswerEntity findById(int answerId){
        return (AnswerEntity) sessionFactory.getCurrentSession().get(AnswerEntity.class, answerId);
    }

    @Transactional
    List<AnswerEntity> findByQuestionnaireAndUser(int questionnaireId, int userId){
        SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery("" +
                "SELECT * from answer inner join question on answer.questionId = question.id " +
                "inner join questionnaire on questionnaire.id = " + questionnaireId  + " and anwer.userId = " + userId);
        query.addEntity(AnswerEntity.class);
        return query.list();
    }


}
