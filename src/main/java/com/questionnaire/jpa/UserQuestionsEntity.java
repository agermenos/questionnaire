package com.questionnaire.jpa;

import javax.persistence.*;
import java.sql.Date;

/**
 * Created by Alejandro on 1/23/2015.
 */
@Entity
@Table(name = "user_questions", schema = "public", catalog = "questionnaire")
public class UserQuestionsEntity {
    private int id;
    private int userId;
    private int questionnaireId;
    private Date date;

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "user_id")
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Basic
    @Column(name = "questionnaire_id")
    public int getQuestionnaireId() {
        return questionnaireId;
    }

    public void setQuestionnaireId(int questionnaireId) {
        this.questionnaireId = questionnaireId;
    }

    @Basic
    @Column(name = "date")
    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserQuestionsEntity that = (UserQuestionsEntity) o;

        if (id != that.id) return false;
        if (questionnaireId != that.questionnaireId) return false;
        if (userId != that.userId) return false;
        if (date != null ? !date.equals(that.date) : that.date != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + userId;
        result = 31 * result + questionnaireId;
        result = 31 * result + (date != null ? date.hashCode() : 0);
        return result;
    }
}
