package com.DPC.spring.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class Notification {
        @Id
        @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "notif")
        @SequenceGenerator(name = "notif", sequenceName = "SEQ_NOTIF", allocationSize = 1)

        private Long id_notif;
        @JsonFormat(pattern = "dd/MM/yyyy")
        private Date date_notif;
        private String libelle_notif;
        private String nom;
        private String type_notif;
        private String cod_soc;
        private String mat_pers;
        private String id_sender;
        private String id_reciver;
        private Long counter;
        private String etat_notif;
        private String etat_notif_agent;
        @Column(insertable = false, updatable = false)

        private String libnomprenom;


}


