package com.DPC.spring.controllers;

import com.DPC.spring.entities.Conge;
import com.DPC.spring.entities.Utilisateur;
import com.DPC.spring.repositories.CongeRepository;
import com.DPC.spring.repositories.UtilisateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("conge")

public class CongeController {
    @Autowired
    UtilisateurRepository userrepos ;
    @Autowired
    CongeRepository congerepos ;

    @PostMapping("/refuserchef")
    public String refusezchef (String matricule ) {
    	Conge c = this.congerepos.findByMatricule(matricule);	
    	c.setValider("refuser");
    	this.congerepos.saveAndFlush(c);
    	
    		return "true";
    }
    @GetMapping("/congerefuserchef")
    public List<Conge> congerefuserchef (String service ) {
    	
    		return this.congerepos.findByServiceAndValider(service, "refuser");
    }
    @GetMapping("/congeaccepte")
    public List<Conge> congeaccepter () {
    	
    		return this.congerepos.findByValiderAndValiderrhIsFalse("accepter");
    }

    @GetMapping("/usercongeaccepte")
    public List<Object>listconguser(String email){
    	Utilisateur u =this.userrepos.findByEmail(email);

    	return this.congerepos.listconguser(u.getId());
    }
  
    @GetMapping("/congeaccepterchef")
    public List<Conge> congeaccepterchef (String service ) {
    	
    		return this.congerepos.findByServiceAndValiderAndValiderrhIsFalse(service, "accepter");
    }
    @GetMapping("/congebloquerchef")
    public List<Conge> congebloquerchef (String service ) {
    	
    		return this.congerepos.findByServiceAndValider(service, "bloquer");
    }
     
    @PostMapping("/planificationrh")
    public String planificationrh(String matricule) {
    	Conge c = this.congerepos.findByMatricule(matricule);
        Utilisateur user = this.userrepos.findByMatricule(c.getUser().getMatricule());

    	 SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
         String strDate1 = formatter.format(c.getDatedebut());
         String strDate2 = formatter.format(c.getDatefin());
         Long nbr =this.congerepos.nbrjour(strDate2,strDate1);
         if(user.getSoldeconge()>=nbr){
             c.setValiderrh(true);
             this.congerepos.save(c);
             user.setSoldeconge(user.getSoldeconge()-nbr);
             this.userrepos.saveAndFlush(user);
             return "true";
         }
         else{
             return "false";
         }
    }
    @GetMapping("/chefcongebyservice")
    public List<Conge> chefcongebyservice(String service){
    	return this.congerepos.findByServiceAndValider(service,"non");
    }
    
    @PostMapping("/planificationchef")
    public String planificationchef (String matricule){
    	Conge c = this.congerepos.findByMatricule(matricule);
        Utilisateur user = this.userrepos.findByMatricule(c.getUser().getMatricule());

    	 SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
         String strDate1 = formatter.format(c.getDatedebut());
         String strDate2 = formatter.format(c.getDatefin());
         List<Object> listconge = this.congerepos.listconge(user.getService().getId(),strDate1,strDate2);
         System.out.println(listconge);
        if(listconge.size()>=2) {
        c.setValider("bloquer");
        this.congerepos.saveAndFlush(c);
        return "false";
        }
        else {
        	c.setValider("accepter");
        	c.setValiderrh(false);
            this.congerepos.saveAndFlush(c);
            return "true"; 	
        }
         
         
    }
    
    
    @PostMapping("/planification")
    public  String planification(@RequestBody Conge conge) {

        Utilisateur user = this.userrepos.findByMatricule(conge.getUser().getMatricule());
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String strDate1 = formatter.format(conge.getDatedebut());
        String strDate2 = formatter.format(conge.getDatefin());

        List<Object> listconge = this.congerepos.listconge(user.getService().getId(),strDate1,strDate2);
        Long nbr =this.congerepos.nbrjour(strDate2,strDate1);
if(listconge.size()>=2){
    return "saisie impossible";
}
else{
    if(user.getSoldeconge()>=nbr){
        conge.setUser(user);
        this.congerepos.save(conge);
        user.setSoldeconge(user.getSoldeconge()-nbr);
        this.userrepos.saveAndFlush(user);
    }
    else{
        return "solde conge inferieur au nbr jour";
    }
}
    return "true";
    }
    @GetMapping("/conge")
    public List<Object>listall(){
    	return this.congerepos.listcong();
    }
    @GetMapping("/congechefall")
    public List<Object>congechefall(String service){
    	return this.congerepos.listcongchef(service);
    }
    @PostMapping("/addconge")
    public  String addconge(@RequestBody Conge conge) {
    	Conge c =this.congerepos.findByMatricule(conge.getMatricule());
    	if(c!=null) {
    		return "false";
    	}
    	else {
        Utilisateur user = this.userrepos.findByMatricule(conge.getUser().getMatricule());
                conge.setUser(user);
                conge.setValider("non");
                conge.setService(user.getService().getNom());
                this.congerepos.save(conge);
        return "true";
    }}
    @PostMapping("/confirmer")
    public  String reponseconge(Long id) {
        Conge c =this.congerepos.findById(id).get();
        c.setValider("valider");
        this.congerepos.saveAndFlush(c);
        return "true";
    }
    @PostMapping("/refuser")
    public  String refuser(Long id) {
        Conge c =this.congerepos.findById(id).get();
        c.setValider("refuser");
        this.congerepos.saveAndFlush(c);
        return "true";
    }
    @GetMapping("/all")
    public List<Conge> all(){
    	return this.congerepos.findAll();
    	
    }

}
