package com.DPC.spring.entities;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Utilisateur {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	private String matricule ;
	private String password ;
	private String login ;
	private String email ;
	private String profil ;
	private String adresse;
    @Temporal(TemporalType.DATE)
	private Date datenaissance ;
	private  Long soldeconge ;
	private Boolean archiver ;
	//matricule
	@ManyToOne
	private Service service ;
	@ManyToOne
    private Autority authorities;
}
