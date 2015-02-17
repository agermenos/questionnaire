package com.questionnaire.jpa;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by agermenos on 1/22/15.
 */
@Entity
@Table(name = "question", schema = "public", catalog = "questionnaire")
public class QuestionEntity {
    @Id
    @SequenceGenerator(name = "pk_sequence", sequenceName = "question_id_seq")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pk_sequence")
    @Column(name = "id", unique = true, nullable = false)
    private int id;
    @Basic
    @Column(name = "question")
    private String question;
    @Basic
    @Column(name = "type")
    private String type;
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.DETACH)
    @JoinColumn(name= "questionnaire_id")
    private QuestionnaireEntity questionnaire;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name= "parent_question")
    private QuestionEntity parentQuestion;
    @Transient
    private List<AnswerEntity> answers;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public QuestionnaireEntity getQuestionnaire() {
        return null;
    }

    public void setQuestionnaire(QuestionnaireEntity questionnaire) {
        this.questionnaire = questionnaire;
    }

    public QuestionEntity getParentQuestion() {
        return parentQuestion;
    }

    public void setParentQuestion(QuestionEntity parentQuestion) {
        this.parentQuestion = parentQuestion;
    }

    public List<AnswerEntity> getAnswers() {
        return answers;
    }

    public void setAnswers(List<AnswerEntity> answers) {
        this.answers = answers;
    }

    public void addAnswer(AnswerEntity answer){
        if (answers==null){
            answers = new ArrayList<AnswerEntity>();
        }
        answers.add(answer);
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

    @Override
    public String toString() {
        return "QuestionEntity{" +
                "id=" + id +
                ", question='" + question + '\'' +
                ", type='" + type + '\'' +
                ", questionnaire=" + questionnaire +
                ", parentQuestion=" + parentQuestion +
                ", answers=" + answers +
                '}';
    }
}
