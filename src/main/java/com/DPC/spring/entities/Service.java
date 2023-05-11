package com.DPC.spring.entities;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

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
	   private Boolean archiver ;
}
