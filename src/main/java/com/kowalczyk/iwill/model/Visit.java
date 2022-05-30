package  com.kowalczyk.iwill.model;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Visit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "DESCRIPTION")
    private String desc;

    @OneToMany(mappedBy = "visit")
    private Set<ClientServ> clientServSet = new HashSet<>();

    public Visit() {
    }

    public Visit(String desc) {
        this.desc = desc;
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

    public Set<ClientServ> getClientServSet() {
        return clientServSet;
    }

    public void setClientServSet(Set<ClientServ> clientServSet) {
        this.clientServSet = clientServSet;
    }

    @Override
    public String toString() {
        return "Visit{" +
                "id=" + id +
                ", desc='" + desc + '\'' +
                ", clientServSet=" + clientServSet +
                '}';
    }

    public String shortName(){
        return "Wizyta: "+ this.getDesc();
    }
}
