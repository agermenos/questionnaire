package com.questionnaire.jpa;

import org.codehaus.jackson.annotate.JsonIgnore;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

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
    private Integer id;

    @Basic
    @Column(name="parent_question")
    private Integer parent;

    @Basic
    @Column(name = "question")
    private String question;
    @Basic
    @Column(name = "type")
    private String type;

    @OneToMany(fetch=FetchType.EAGER)
    @JsonIgnore
    @JoinColumn(name = "parent_question")
    private Set<QuestionEntity> answers;

    public Integer getId() {
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

    public int getParent() {
        return parent;
    }

    public void setParent(int parent) {
        this.parent = parent;
    }

    public QuestionnaireEntity getQuestionnaire() {
        return null;
    }

    public Set<QuestionEntity> getAnswers() {
        return answers;
    }

    public void setAnswers(Set<QuestionEntity> answers) {
        this.answers = answers;
    }

    public void addAnswer(QuestionEntity answer){
        if (answers==null){
            answers = new HashSet<QuestionEntity>();
        }
        answers.add(answer);
    }

    @Override
    public String toString() {
        return "QuestionEntity{" +
                "id:" + id +
                ", question:\"" + question + '\"' +
                ", type:\"" + type + '\"' +
                //", questions=" + questions +
                ", answers:" + answers +
                '}';
    }
}
