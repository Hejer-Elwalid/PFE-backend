package com.DPC.spring.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Abscence {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    private String date;
    private String typeabs;
    private  String respservice ;


    @ManyToOne
    Utilisateur user ;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTypeabs() {
        return typeabs;
    }

    public void setTypeabs(String typeabs) {
        this.typeabs = typeabs;
    }

    public String getRespservice() {
        return respservice;
    }

    public void setRespservice(String respservice) {
        this.respservice = respservice;
    }

    /* @ManyToOne
    private Service service ;*/
}
