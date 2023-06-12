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
	private PasswordEncoder encoder;
	@Autowired
	UtilisateurRepository userrepos ;
	@Autowired
	AuthorityRepository authrepos ;
	@Autowired
	ServiceRepository servicerepos ;
	@Autowired
     private IUtilisateurService iUtilisateurService;


	@PreAuthorize("hasAuthority('admin')")
	@DeleteMapping("/delete")
	@ApiOperation(value = "supprimer Utilisateur ")
	public void deleteUtilisateur(long idUtilisateur)  {

		iUtilisateurService.deleteUtilisateur(idUtilisateur);
	}

	//@PreAuthorize("hasAuthority('admin')")
	@GetMapping("/userbyid")
	public Utilisateur userById(Long id) {
		return this.userrepos.findById(id).get();
	}

     //modifier un uttilisateur
	@PutMapping("/update")
	public String updatePersonne(@RequestBody Utilisateur utilisateur,String service ) {

		Utilisateur user = this.userrepos.findById(utilisateur.getId()).get();
		Service s = this.servicerepos.findByNom(service);
		user.setService(s);
		user.setArchiver(false);
		user.setProfil(user.getProfil());
		user.setAuthorities(user.getAuthorities());
		user.setPassword(user.getPassword());
		userrepos.saveAndFlush(user);
		return "true";
	}


    // archiver un utilisateur
	@PostMapping("/archiver")
	public String archiver(Long id) {
	 Utilisateur u =this.userrepos.findById(id).get();
	 u.setArchiver(true);
	 this.userrepos.saveAndFlush(u);
	 return "true";
	}

	//
	@GetMapping("/GetCandidacy")
	public List<Utilisateur> GetUtilisateur() {
		return iUtilisateurService.GetUtilisateur();
	}

    //@PreAuthorize("hasAuthority('admin')")
	@GetMapping("/getuserbyemail")
	public Utilisateur user(String email) {
		return this.userrepos.findByEmail(email);
	}

    // get user by matricule
	//@PreAuthorize("hasAuthority('admin')")
	@RequestMapping(value = "/userBymatricule", method = RequestMethod.GET)
	public Utilisateur Userbymatricule( @RequestParam String matricule) {
		Utilisateur user = this.userrepos.findByMatricule(matricule);
		return user;
	}

   // ajouter un utilisateur
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
    // Recherche user par matricule ou par email
	@GetMapping("/rechercheuser")
	public Utilisateur rechercheuser(@RequestParam String critere, @RequestParam String valeur) {
		if (critere.equalsIgnoreCase("email")) {
			Utilisateur user = this.userrepos.findByEmail(valeur);
			return user;

		} else if (critere.equalsIgnoreCase("matricule")) {
			Utilisateur user = this.userrepos.findByMatricule(valeur);
			return  user;
	}
		return null;
	}


	@PreAuthorize("hasAuthority('admin')")
	@RequestMapping(value = "/admintest", method = RequestMethod.GET)
	public String testconnectadmin() {
		return "profil admin";
	}



}
