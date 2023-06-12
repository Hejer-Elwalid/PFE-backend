package com.DPC.spring.entities;

import java.util.Date;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Service {
	 @Id
	 @GeneratedValue(strategy= GenerationType.AUTO)
	   private Long id;
	   private String nom ;
	   private String dep ;
	   private String  resp ;
	   private String profil;
	   private Boolean archiver ;

}
