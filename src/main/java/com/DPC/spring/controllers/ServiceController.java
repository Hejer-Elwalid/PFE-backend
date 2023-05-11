package com.DPC.spring.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.DPC.spring.entities.Service;
import com.DPC.spring.repositories.ServiceRepository;


@CrossOrigin("*")
@RestController
@RequestMapping("service")

public class ServiceController {
@Autowired
ServiceRepository servicerepos ; 


@PreAuthorize("hasAuthority('admin')")
@PostMapping("/add")
public String ajouter(@RequestBody Service service) {
	Service s =this.servicerepos.findByNom(service.getNom());
	if(s!=null) {
		return "false"; 
	}
	else {
		Service newservice = new Service();
		newservice.setNom(service.getNom());
		newservice.setArchiver(false);
		this.servicerepos.save(newservice);
				return "true";
	}
}
@GetMapping("/all")
public List<Service> allservice(){
	return this.servicerepos.findByArchiverIsFalse();
}

@GetMapping("/servicebyid")
public Service servicebyid(Long  id){
	return this.servicerepos.findById(id).get();
}

@PostMapping("/archiver")
public String archiver(Long  id){
 Service s =this.servicerepos.findById(id).get();
 s.setArchiver(true);	
 this.servicerepos.saveAndFlush(s);
 return "true";
}

}
