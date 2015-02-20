package com.questionnaire.rest;

import com.google.gson.Gson;
import com.questionnaire.jpa.QuestionEntity;
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

import java.util.List;

/**
 * Created by Alejandro on 2/15/2015.
 */

@Controller
@RequestMapping("/questions")
public class QuestionsRest {

    @Autowired
    QuestionnaireServices questionnaireServices;
    Logger log = LoggerFactory.getLogger(QuestionsRest.class);
    Gson gson=new Gson();

    @RequestMapping(value="/{questionnaireId}", method = RequestMethod.GET)
    public @ResponseBody
    String getToDoInJSON(@PathVariable int questionnaireId, Model model) {
        //Gson gson = new Gson();
        List<QuestionEntity> questions = questionnaireServices.getQuestions(questionnaireId);
        log.info("questions:\n ");
        for (QuestionEntity question:questions) {
            log.info (question.toString());
        }
        return gson.toJson(questions);
    }
}
