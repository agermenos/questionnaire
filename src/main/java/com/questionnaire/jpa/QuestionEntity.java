package com.questionnaire.jpa;

import javax.persistence.*;

/**
 * Created by agermenos on 1/22/15.
 */
@Entity
@Table(name = "question", schema = "public", catalog = "questionnaire")
public class QuestionEntity {
    private int id;

    private String question;
    private String type;
    private QuestionnaireEntity questionnaire;
    private QuestionEntity parentQuestion;

    @Id
    @SequenceGenerator(name = "pk_sequence", sequenceName = "question_id_seq")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pk_sequence")
    @Column(name = "id", unique = true, nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "question")
    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    @Basic
    @Column(name = "type")
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @ManyToOne(cascade = CascadeType.ALL)
    public QuestionnaireEntity getQuestionnaire() {
        return questionnaire;
    }

    public void setQuestionnaire(QuestionnaireEntity questionnaire) {
        this.questionnaire = questionnaire;
    }

    @ManyToOne(cascade = CascadeType.ALL)
    public QuestionEntity getParentQuestion() {
        return parentQuestion;
    }

    public void setParentQuestion(QuestionEntity parentQuestion) {
        this.parentQuestion = parentQuestion;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof QuestionEntity)) return false;

        QuestionEntity that = (QuestionEntity) o;

        if (id != that.id) return false;
        if (parentQuestion != null ? !parentQuestion.equals(that.parentQuestion) : that.parentQuestion != null)
            return false;
        if (!question.equals(that.question)) return false;
        if (!questionnaire.equals(that.questionnaire)) return false;
        if (!type.equals(that.type)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + question.hashCode();
        result = 31 * result + type.hashCode();
        result = 31 * result + questionnaire.hashCode();
        result = 31 * result + (parentQuestion != null ? parentQuestion.hashCode() : 0);
        return result;
    }
}
