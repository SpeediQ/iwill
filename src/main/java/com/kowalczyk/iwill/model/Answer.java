package com.kowalczyk.iwill.model;

import javax.persistence.*;

@Entity
@Table(name = "answer")
public class Answer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String desc;
    private boolean result;
    @OneToOne
    private Question answer;
    private boolean isActive;

    public Answer() {
    }

    public Answer(String desc, boolean result, Question answer) {
        this.desc = desc;
        this.result = result;
        this.answer = answer;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public Question getAnswer() {
        return answer;
    }

    public void setAnswer(Question answer) {
        this.answer = answer;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    @Override
    public String toString() {
        return "Answer{" +
                "id=" + id +
                ", desc='" + desc + '\'' +
                ", result=" + result +
                ", answer=" + answer +
                '}';
    }
}