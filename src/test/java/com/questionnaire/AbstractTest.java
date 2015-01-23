package com.questionnaire;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by Alejandro on 8/17/2014.
 */
public class AbstractTest {
    private Logger log = LoggerFactory.getLogger(this.getClass());
    private ApplicationContext appContext;

    public AbstractTest(){

    }

    public AbstractTest(String context){
        appContext = new ClassPathXmlApplicationContext(context);
    }

    public Logger getLog() {
        return log;
    }

    public void setLog(Logger log) {
        this.log = log;
    }

    public ApplicationContext getContext() {
        return appContext;
    }

    public void setContext(ApplicationContext appContext) {
        this.appContext = appContext;
    }

    public ApplicationContext getAppContext() {return this.appContext;}
}
