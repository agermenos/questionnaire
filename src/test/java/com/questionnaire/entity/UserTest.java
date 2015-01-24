package com.questionnaire.entity;

import com.questionnaire.AbstractTest;
import com.questionnaire.dao.UserDao;
import com.questionnaire.jpa.UserEntity;
import com.questionnaire.status.UserStatus;
import com.questionnaire.util.AESTransformer;

import java.text.ParseException;
import java.util.List;

/**
 * Created by Alejandro on 8/17/2014.
 */
public class UserTest extends AbstractTest {
    public UserTest(String context){
        super(context);
    }

    public void findByLastName(UserDao userDao) {
        List<UserEntity> users = userDao.findByLastName("Germenos");
        if (users!=null) {
            for (UserEntity u:users){
                getLog().info("User: " + u);
            }
        }
    }

    public void findByName(UserDao userDao) {
        List<UserEntity> users = userDao.findByName("Alejandro");
        if (users!=null) {
            for (UserEntity u:users){
                getLog().info("User: " + u);
            }
        }
    }

    public void testInserts(UserDao userDao) throws ParseException {
        UserEntity user = new UserEntity();
        user.setEmail("agermenos@gmail.com");
        user.setName("Alejandro");
        user.setLastName("Germenos");
        user.setPassword("password");
        user.setUsername("agermenos");
        user.setStatus(UserStatus.ACTIVE.toString());
        userDao.add(user);
    }

    public void retrieveUser(UserDao userDao) throws Exception {
        UserEntity user=userDao.findByEmail("agermenos@gmail.com");
        getLog().info("USER: " + user.toString());
        getLog().info("Decrypted password: " + AESTransformer.decrypt(user.getPassword()));

    }

    public void updateUser(UserDao userDao){
        try {
            UserEntity user = userDao.findByEmail("agermenos@gmail.com");
            user.setPassword(AESTransformer.encrypt(user.getPassword()));
            userDao.update(user);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void main(String args[]){
        UserTest userTest=new UserTest("spring/config/beanlocations.xml");
        UserDao userDao = (UserDao)userTest.getContext().getBean("userDao");
        try {
            userTest.testInserts(userDao);
            userTest.updateUser(userDao);
            userTest.retrieveUser(userDao);
            userTest.findByName(userDao);
            userTest.findByLastName(userDao);
        }
        catch (Exception e){
            userTest.getLog().error(e.toString());
        }
    }


}
