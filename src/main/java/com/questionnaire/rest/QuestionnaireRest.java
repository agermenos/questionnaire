package com.questionnaire.rest;

import com.google.gson.Gson;
import com.questionnaire.jpa.QuestionEntity;
import com.questionnaire.jpa.QuestionnaireEntity;
import com.questionnaire.services.QuestionnaireServices;
import jdk.nashorn.internal.ir.RuntimeNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by Alejandro on 2/12/2015.
 */

@Controller
@RequestMapping("/questionnaire")
public class QuestionnaireRest {
    @Autowired
    QuestionnaireServices questionnaireServices;
    Logger log = LoggerFactory.getLogger(QuestionnaireRest.class);
    Gson gson=new Gson();

    /**
     @RequestMapping(value="/{id}", method = RequestMethod.GET)
     public @ResponseBody
     String getQuestionnaire(@PathVariable int id, Model model) {
     QuestionnaireEntity questionnaire = questionnaireServices.getQuestionnaireById(id);
     log.info("questionnaire: " + questionnaire.toString());
     return gson.toJson(questionnaire);
     }
     */

    @RequestMapping(value="/{id}", method = RequestMethod.PUT)
    public @ResponseBody
    String updateQuestion(@PathVariable int id, @RequestBody String jsonQuestionnaire) {
        QuestionEntity questions[] = gson.fromJson(jsonQuestionnaire, QuestionEntity[].class);
        for (QuestionEntity question:questions){
            if (question.getId()==null || question.getId()==1) {
                questionnaireServices.saveQuestion(question);
            }
        }
        //QuestionnaireEntity questionnaire = questionnaireServices.getQuestionnaireById(id);
        //questionnaire.setQuestions(questions);
        //questionnaireServices.storeQuestionnaire(questionnaire);
        return null;
    }

    @RequestMapping(value="/{id}", method = RequestMethod.POST)
    public @ResponseBody
    String addQuestionnaire(@PathVariable int id, Model model) {
        QuestionnaireEntity questionnaire = questionnaireServices.getQuestionnaireById(id);
        //  questionnaire.setQuestions(questions);
        //  questionnaireServices.storeQuestionnaire(questionnaire);
        return null;
    }

    @RequestMapping(value="/{userId}", method = RequestMethod.GET)
    public @ResponseBody
    String getQuestionnaires(@PathVariable int userId, Model model){
        return gson.toJson(questionnaireServices.getQuestionsByUserId(userId));
    }
}
