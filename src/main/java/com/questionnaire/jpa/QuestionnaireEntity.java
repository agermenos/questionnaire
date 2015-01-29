package com.questionnaire.jpa;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by agermenos on 1/22/15.
 */
@Entity
@Table(name = "questionnaire", schema = "public", catalog = "questionnaire")
public class QuestionnaireEntity {
    @Id
    @SequenceGenerator(name = "pk_sequence", sequenceName = "questionnaire_id_seq")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pk_sequence")
    @Column(name = "id", unique = true, nullable = false)
    private int id;
    @Basic
    @Column(name = "name")
    private String name;
    @Basic
    @Column(name = "created")
    private String created;
    @Basic
    @Column(name = "modified")
    private String modified;
    @Basic
    @Column(name = "status")
    private String status;
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;
    @OneToMany(fetch = FetchType.EAGER, mappedBy= "questionnaire")
    private List<QuestionEntity> questions;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getModified() {
        return modified;
    }

    public void setModified(String modified) {
        this.modified = modified;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public List<QuestionEntity> getQuestions() {
        return questions;
    }

    public void setQuestions(List<QuestionEntity> questions) {
        this.questions = questions;
    }

    public void addQuestion(QuestionEntity question){
        if (questions==null) {
            questions=new ArrayList<QuestionEntity>();
        }
        question.setQuestionnaire(this);
        questions.add(question);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        QuestionnaireEntity that = (QuestionnaireEntity) o;

        if (id != that.id) return false;
        if (!created.equals(that.created)) return false;
        if (modified != null ? !modified.equals(that.modified) : that.modified != null) return false;
        if (!name.equals(that.name)) return false;
        if (questions != null ? !questions.equals(that.questions) : that.questions != null) return false;
        if (!status.equals(that.status)) return false;
        if (!user.equals(that.user)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + name.hashCode();
        result = 31 * result + created.hashCode();
        result = 31 * result + (modified != null ? modified.hashCode() : 0);
        result = 31 * result + status.hashCode();
        result = 31 * result + user.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "QuestionnaireEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", created='" + created + '\'' +
                ", modified='" + modified + '\'' +
                ", status='" + status + '\'' +
                ", user=" + user +
                '}';
    }
}
