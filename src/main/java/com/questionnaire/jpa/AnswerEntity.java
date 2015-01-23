package com.questionnaire.jpa;

import javax.persistence.*;

/**
 * Created by agermenos on 1/22/15.
 */
@Entity
@Table(name = "answer", schema = "public", catalog = "questionnaire")
public class AnswerEntity {
    private int id;
    private String answer;
    private UserEntity user;
    private QuestionEntity question;

    @Id
    @SequenceGenerator(name = "pk_sequence", sequenceName = "answer_id_seq")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pk_sequence")
    @Column(name = "id", unique = true, nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @OneToOne
    public QuestionEntity getQuestion() {
        return question;
    }

    public void setQuestion(QuestionEntity question) {
        this.question = question;
    }

    @Basic
    @Column(name = "answer")
    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinColumn(name="user_id")
    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AnswerEntity)) return false;

        AnswerEntity that = (AnswerEntity) o;

        if (id != that.id) return false;
        if (!answer.equals(that.answer)) return false;
        if (!question.equals(that.question)) return false;
        if (!user.equals(that.user)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + answer.hashCode();
        result = 31 * result + user.hashCode();
        result = 31 * result + question.hashCode();
        return result;
    }
}


