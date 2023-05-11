package com.DPC.spring.controllers;

import com.DPC.spring.entities.Abscence;
import com.DPC.spring.entities.Sanction;
import com.DPC.spring.entities.Utilisateur;
import com.DPC.spring.repositories.AbscenceRepository;
import com.DPC.spring.repositories.SanctionRepository;
import com.DPC.spring.repositories.UtilisateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("abs")

public class AbscenceController {
    @Autowired
    UtilisateurRepository userrepos ;
    @Autowired
    AbscenceRepository absrepos ;
    @Autowired
    SanctionRepository sanctionrepos ;

    
    
	@PreAuthorize("hasAuthority('admin')")
    @PostMapping("/add")
  public String add(@RequestBody Abscence abscence){

    Utilisateur user = this.userrepos.findByMatricule(abscence.getUser().getMatricule());
    Date dt=new Date();
    int year=dt.getYear()+1900;
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    String strDate1 = formatter.format(year);
    int count = this.absrepos.countabs(strDate1, user.getId());
if(count>=15){
    abscence.setUser(user);
    this.absrepos.save(abscence);
    Sanction s = new Sanction();
    s.setUser(user);
    s.setMatriculeuser(user.getMatricule());
    this.sanctionrepos.save(s);
    return "false";
}
else{
    abscence.setUser(user);
    this.absrepos.save(abscence);
return "true";
}

}
	
	@GetMapping("/abscbyuser")
	public List<Abscence> abscbyuser(String email){
		Utilisateur user =this.userrepos.findByEmail(email);
		return this.absrepos.findByUser(user);
	}
	@GetMapping("/sanctionbyuser")
	public List<Sanction> sanctionbyuser(String email){
		Utilisateur user =this.userrepos.findByEmail(email);
		return this.sanctionrepos.findByUser(user);
	}
	//@PreAuthorize("hasAuthority('admin')")
    @GetMapping("/allabscence")
    public List<Abscence> listabscence(){
    return this.absrepos.findAll();	
    }

    //@PreAuthorize("hasAuthority('admin')")

    @GetMapping("/modifier")
    public String modifier(@RequestBody  Abscence abs){
	Abscence absc = this.absrepos.findById(abs.getId()).get();
	absc= absrepos.saveAndFlush(abs);
	return "true";

    }
@PreAuthorize("hasAuthority('admin')")
    @GetMapping("/allabscencebyid")
    public Abscence listabscence(Long id){
    return this.absrepos.findById(id).get();	
    }

	//@PreAuthorize("hasAuthority('admin')")
    @GetMapping("/allsansation")
    public List<Sanction> listsabsation(){
    return this.sanctionrepos.findAll();	
    }

}
