package com.questionnaire.dao;

import com.questionnaire.jpa.UserEntity;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by agermenos on 1/22/15.
 */
@Repository("userDao")
public class UserDao {
    @Autowired
    private SessionFactory sessionFactory;

    @Transactional
    public void add(UserEntity user){
        sessionFactory.getCurrentSession().save(user);
    }

    @Transactional
    public void update(UserEntity user){
        sessionFactory.getCurrentSession().update(user);
    }

    @Transactional
    public void delete(UserEntity user){
        sessionFactory.getCurrentSession().delete(user);
    }

    @Transactional
    public UserEntity findById(int userId){
        return (UserEntity) sessionFactory.getCurrentSession().get(UserEntity.class, userId);
    }

    @Transactional
    public UserEntity findByEmail(String email) {
        Criteria criteria = sessionFactory.
                getCurrentSession().
                createCriteria(UserEntity.class);
        criteria.add(Restrictions.like("email", "%" + email + "%"));
        return (UserEntity) criteria.list().get(0);
    }

    @Transactional
    public List<UserEntity> findByName(String name) {
        Criteria criteria = sessionFactory.
                getCurrentSession().
                createCriteria(UserEntity.class);
        criteria.add(Restrictions.like("name", "%" + name + "%"));
        return criteria.list();
    }

    @Transactional
    public List<UserEntity> findByLastName(String lastName) {
        Criteria criteria = sessionFactory.
                getCurrentSession().
                createCriteria(UserEntity.class);
        criteria.add(Restrictions.like("lastName", "%" + lastName + "%"));
        return criteria.list();
    }

    @Transactional
    public List<UserEntity> getAll(){
        Criteria criteria = sessionFactory.
                getCurrentSession().
                createCriteria(UserEntity.class);
        return criteria.list();
    }
}
