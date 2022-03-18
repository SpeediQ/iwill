package com.kowalczyk.iwill.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "jobinterview")
public class JobInterview {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String desc;
    private long proposedSalary;
    private boolean result;
    @OneToMany(mappedBy = "jobInterview")
    private List<Question> question;
    private boolean isActive;

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
    public JobInterview() {
    }

    public JobInterview(String desc, long proposedSalary, boolean result) {
        this.desc = desc;
        this.proposedSalary = proposedSalary;
        this.result = result;
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

    public long getProposedSalary() {
        return proposedSalary;
    }

    public void setProposedSalary(long proposedSalary) {
        this.proposedSalary = proposedSalary;
    }

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "JobInterview{" +
                "id=" + id +
                ", desc='" + desc + '\'' +
                ", proposedSalary=" + proposedSalary +
                ", result=" + result +
                '}';
    }
}
