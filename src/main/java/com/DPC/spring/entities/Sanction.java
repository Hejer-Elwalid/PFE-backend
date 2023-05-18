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
public class Sanction {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    private  String matriculeuser;

    @ManyToOne
    private Utilisateur user ;

}
