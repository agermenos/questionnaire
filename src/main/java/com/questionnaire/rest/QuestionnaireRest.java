package com.questionnaire.rest;

import com.questionnaire.jpa.QuestionnaireEntity;
import com.questionnaire.services.QuestionnaireServices;
import org.springframework.beans.factory.annotation.Autowired;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by Alejandro on 2/12/2015.
 */

@Path("/questionnaire")
public class QuestionnaireRest {
    @Autowired
    QuestionnaireServices questionnaireServices;

    @GET
    @Path("/{element}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getToDoInJSON(@PathParam("element") int id) {
        QuestionnaireEntity questionnaire = questionnaireServices.getQuestionnaireById(id);
        System.out.println("questionnaire: " + questionnaireServices);
        return Response.status(Response.Status.OK).entity(questionnaire).build();
    }
}
