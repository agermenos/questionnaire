package com.questionnaire.jpa;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
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
    private int id;
    @Basic
    @Column(name = "question")
    private String question;
    @Basic
    @Column(name = "type")
    private String type;

    @ManyToOne(cascade = CascadeType.ALL, fetch=FetchType.EAGER)
    @JoinColumn(name= "parent_question")
    private QuestionEntity parent;
    @OneToMany(mappedBy = "parent", fetch=FetchType.EAGER)
    @MapKeyColumn(columnDefinition = "parent_question")
    private Set<QuestionEntity> answers;

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

    public QuestionEntity getParent() {
        return parent;
    }

    public void setParent(QuestionEntity parent) {
        this.parent = parent;
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
