package com.DPC.spring.controllers;

import com.DPC.spring.services.IUtilisateurService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import com.DPC.spring.entities.Abscence;
import com.DPC.spring.entities.Autority;
import com.DPC.spring.entities.Service;
import com.DPC.spring.entities.Utilisateur;
import com.DPC.spring.repositories.AuthorityRepository;
import com.DPC.spring.repositories.ServiceRepository;
import com.DPC.spring.repositories.UtilisateurRepository;

import java.util.List;


@CrossOrigin("*")
@RestController
@RequestMapping("users")
public class UtilisateurController {


	@Autowired
     private IUtilisateurService iUtilisateurService;


	@PreAuthorize("hasAuthority('admin')")
	@DeleteMapping("/delete")
	@ApiOperation(value = "supprimer Utilisateur ")
	public void deleteUtilisateur(long idUtilisateur)  {

		iUtilisateurService.deleteUtilisateur(idUtilisateur);
	}
	@PreAuthorize("hasAuthority('admin')")
	@GetMapping("/userbyid")
	public Utilisateur userById(Long id) {
		return this.userrepos.findById(id).get();
	}


	@PutMapping("/update")
	//@ApiOperation(value = " update personne ")
	public String updatePersonne(@RequestBody Utilisateur utilisateur,String service ) {

		Utilisateur user = this.userrepos.findById(utilisateur.getId()).get();
		Service s = this.servicerepos.findByNom(service);
		utilisateur.setService(s);
		utilisateur.setArchiver(false);
		utilisateur.setAuthorities(user.getAuthorities());
		utilisateur.setPassword(user.getPassword());
		user= userrepos.saveAndFlush(utilisateur);
	
	return "true";
	}


	@PostMapping("/archiver")
	public String archiver(Long id) {
	 Utilisateur u =this.userrepos.findById(id).get();
	 u.setArchiver(true);
	 this.userrepos.saveAndFlush(u);
	 return "true";
	}
	
	@GetMapping("/GetCandidacy")
	public List<Utilisateur> GetUtilisateur() {
		return iUtilisateurService.GetUtilisateur();
	}


	@Autowired
	private PasswordEncoder encoder;
	
	@Autowired
	UtilisateurRepository userrepos ; 
	@Autowired
	AuthorityRepository authrepos ;
	@Autowired
	ServiceRepository servicerepos ;


	@PreAuthorize("hasAuthority('admin')")
	@GetMapping("/getuserbyemail")
	public Utilisateur user(String email) {
		return this.userrepos.findByEmail(email);
	}


	@PreAuthorize("hasAuthority('admin')")
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String Ajout(@RequestBody Utilisateur user,String service) {
		Utilisateur userexist = this.userrepos.findByMatricule(user.getMatricule());
		if(userexist==null){
		Autority auth = this.authrepos.findByName(user.getProfil());
		Service s =this.servicerepos.findByNom(service);
		String pass = encoder.encode(user.getPassword());
		user.setAuthorities(auth);
		user.setPassword(pass);
		user.setArchiver(false);
		user.setService(s);
		user.setSoldeconge((long) 20);
		

	 this.userrepos.save(user);
		}
		else{
			return  "user exist";
		}
	return "true";
	}

	@PreAuthorize("hasAuthority('admin')")
	@RequestMapping(value = "/userBymatricule", method = RequestMethod.GET)
	public Utilisateur Userbymatricule(String matricule) {
		Utilisateur user = this.userrepos.findByMatricule(matricule);
		return user;
	}

	
	@PreAuthorize("hasAuthority('admin')")
	@RequestMapping(value = "/admintest", method = RequestMethod.GET)
	public String testconnectadmin() {
		return "profil admin";
	}



}
