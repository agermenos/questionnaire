package com.questionnaire.rest;

import com.google.gson.Gson;
import com.questionnaire.jpa.QuestionnaireEntity;
import com.questionnaire.services.QuestionnaireServices;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by Alejandro on 2/12/2015.
 */

@Controller
@RequestMapping("/questionnaire")
public class QuestionnaireRest {
    @Autowired
    QuestionnaireServices questionnaireServices;
    Logger log = LoggerFactory.getLogger(QuestionnaireRest.class);
    Gson gson;

    @RequestMapping(value="/{id}", method = RequestMethod.GET)
    public @ResponseBody
    String getQuestionnaire(@PathVariable int id, Model model) {
        QuestionnaireEntity questionnaire = questionnaireServices.getQuestionnaireById(id);
        log.info("questionnaire: " + questionnaire.toString());
        return gson.toJson(questionnaire);
    }

    @RequestMapping(value="/{id}", method = RequestMethod.GET)
    public @ResponseBody
    String addQuestions(@PathVariable int id, Model model) {
        QuestionnaireEntity questionnaire = questionnaireServices.getQuestionnaireById(id);
      //  questionnaire.setQuestions(questions);
      //  questionnaireServices.storeQuestionnaire(questionnaire);
        return null;
    }
}
