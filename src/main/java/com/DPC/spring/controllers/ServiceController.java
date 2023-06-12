package com.DPC.spring.controllers;

import java.util.List;

import com.DPC.spring.entities.Utilisateur;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.DPC.spring.entities.Service;
import com.DPC.spring.repositories.ServiceRepository;


@CrossOrigin("*")
@RestController
@RequestMapping("service")

public class ServiceController {
@Autowired
ServiceRepository servicerepos ;


	@PutMapping("/update")
	public String update(@RequestBody Service service ) {
		Service service1 = this.servicerepos.findById(service.getId()).get();
		service1.setNom(service.getNom());
		service1.setDep(service.getDep());
		service1.setProfil(service.getProfil());
		service1.setResp(service.getResp());
		servicerepos.saveAndFlush(service1);
		return "true";
	}


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
		newservice.setDep(service.getDep());
		newservice.setResp(service.getResp());
		newservice.setProfil(service.getProfil());
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

   @GetMapping("/getservicebynom")
   public Service rechercheservice (@RequestParam String nom) {
		Service service = this.servicerepos.findByNom(nom);
		return  service ;
	}


  @PostMapping("/archiver")
  public String archiver(Long  id){
  Service s =this.servicerepos.findById(id).get();
  s.setArchiver(true);
  this.servicerepos.saveAndFlush(s);
  return "true";
}



}
