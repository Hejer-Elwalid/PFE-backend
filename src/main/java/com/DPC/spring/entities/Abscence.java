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
    @ManyToOne
    Utilisateur user ;

}
