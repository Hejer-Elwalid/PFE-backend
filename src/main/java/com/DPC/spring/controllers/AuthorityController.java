package com.DPC.spring.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.DPC.spring.entities.Autority;
import com.DPC.spring.entities.Utilisateur;
import com.DPC.spring.repositories.AuthorityRepository;

@CrossOrigin("*")
@RestController
@RequestMapping("authority")
public class AuthorityController {
	@Autowired
	AuthorityRepository authrepos ; 
	
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String Ajout(@RequestBody Autority auth) {
	this.authrepos.save(auth);
	return "true";
	}
}
