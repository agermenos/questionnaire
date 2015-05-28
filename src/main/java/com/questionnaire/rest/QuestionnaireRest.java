package com.questionnaire.rest;

import com.google.gson.Gson;
import com.questionnaire.jpa.QuestionEntity;
import com.questionnaire.jpa.QuestionnaireEntity;
import com.questionnaire.services.QuestionnaireService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Alejandro on 2/12/2015.
 */

@Controller
@RequestMapping("/questionnaire")
public class QuestionnaireRest {
    @Autowired
    QuestionnaireService questionnaireServices;
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
        QuestionnaireEntity questionnaire = gson.fromJson(jsonQuestionnaire, QuestionnaireEntity.class);
        questionnaireServices.storeQuestionnaire(questionnaire);
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
