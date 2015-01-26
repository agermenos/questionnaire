package com.questionnaire.services;

import com.questionnaire.dao.UserDao;
import com.questionnaire.jpa.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 * Created by agermenos on 1/26/15.
 */
@Service("userServices")
public class UserServices {
    @Autowired
    UserDao userDao;

    @Transactional
    public void addUser(UserEntity user){
        userDao.add(user);
    }

    @Transactional
    public void updateUser(UserEntity user){
        userDao.update(user);
    }
}
