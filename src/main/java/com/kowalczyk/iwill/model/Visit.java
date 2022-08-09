package com.kowalczyk.iwill.model;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Visit implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "DESCRIPTION")
    private String desc;

    private String code;

    @OneToMany(mappedBy = "visit", cascade = CascadeType.ALL)
    private Set<ClientServ> clientServSet = new HashSet<>();

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "CLIENT_CARD_ID")
    private ClientCard clientCard;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date date;

    private String time;

    @OneToOne
    @JoinColumn(name = "STATUS_ID")
    private Status status;

    private String title;

    private int promotion;

    public Visit() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Set<ClientServ> getClientServSet() {
        return clientServSet;
    }

    public void setClientServSet(Set<ClientServ> clientServSet) {
        this.clientServSet = clientServSet;
    }

    public ClientCard getClientCard() {
        return clientCard;
    }

    public void setClientCard(ClientCard clientCard) {
        this.clientCard = clientCard;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getPromotion() {
        return promotion;
    }

    public void setPromotion(int promotion) {
        this.promotion = promotion;
    }

    @Override
    public String toString() {
        return "Visit{" +
                "id=" + id +
                ", desc='" + desc + '\'' +
                ", clientServSet=" + clientServSet +
                '}';
    }

    public String shortName() {
        return "Wizyta: " + this.getDesc();
    }

    public Double getSum() {
        double sum = 0;
        if (getClientServSet() != null && getClientServSet().size() > 0) {
            for (ClientServ clientServ : getClientServSet()) {
                sum += clientServ.getFinalPrice();
            }
        }
        return sum;
    }

    public String getNiceStringSumIncludingPromotion() {
        DecimalFormat decimalFormat = new DecimalFormat("##.00");
        double sumIncludingPromotion = getSum() * (1 - getPromotion() * 0.01);
        return "Cena po promocji: " + decimalFormat.format(sumIncludingPromotion) + " zł";
    }

    public String getNiceStringSum() {
        return "Cena podstawowa: " + getSum() + " zł";
    }

    public String getNiceStringPromotion() {
        return "Wysokość promocji: " + getPromotion() + "%";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Visit)) return false;

        Visit visit = (Visit) o;

        if (id != visit.id) return false;
        if (desc != null ? !desc.equals(visit.desc) : visit.desc != null) return false;
        if (code != null ? !code.equals(visit.code) : visit.code != null) return false;
        if (clientServSet != null ? !clientServSet.equals(visit.clientServSet) : visit.clientServSet != null)
            return false;
        if (date != null ? !date.equals(visit.date) : visit.date != null) return false;
        if (time != null ? !time.equals(visit.time) : visit.time != null) return false;
        if (status != null ? !status.equals(visit.status) : visit.status != null) return false;
        return title != null ? title.equals(visit.title) : visit.title == null;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (desc != null ? desc.hashCode() : 0);
        result = 31 * result + (code != null ? code.hashCode() : 0);
        result = 31 * result + (clientServSet != null ? clientServSet.hashCode() : 0);
        result = 31 * result + (date != null ? date.hashCode() : 0);
        result = 31 * result + (time != null ? time.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + (title != null ? title.hashCode() : 0);
        return result;
    }


    public String niceOneLineServicesString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("");

        if (!getClientServSet().isEmpty()) {
            stringBuilder.append("Usługi: ");
            getClientServSet().stream().forEach(clientServ -> stringBuilder.append(clientServ.getTitle() + " // "));
        }

        return String.valueOf(stringBuilder);
    }

    public String niceOneLineString() {
        return code + " " + " | " + title + " | Opis: " + desc + " | Status: " + status + " | " + niceOneLineServicesString();
    }
}
