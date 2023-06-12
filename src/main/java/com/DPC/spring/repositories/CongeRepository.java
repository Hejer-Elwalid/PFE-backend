package com.DPC.spring.repositories;

import com.DPC.spring.entities.Autority;
import com.DPC.spring.entities.Conge;
import com.DPC.spring.entities.Utilisateur;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

public interface CongeRepository extends JpaRepository<Conge,Long> {

@Query(nativeQuery = true,value = "SELECT c.id FROM conge c , utilisateur u where u.id=c.user_id and u.service_id=? and ? >=(SELECT MIN(datedebut) FROM conge) and ? <=(SELECT MAX(datefin) from conge) and c.valider='accepter'")
    List<Object> listconge(Long service, String d1, String d2);
    @Query(nativeQuery = true,value = " select DATEDIFF(?,?)")
    Long nbrjour( String d1, String d2);
	Conge findByMatricule(String matricule);
//@Query(nativeQuery=true,value="select c.datedebut - DATE( NOW() ) as datedebut ,c.datefin - DATE( NOW() ) as datefin , c.color , c.matricule, c.typeconge , u.matricule  as matriculeuser from conge c , utilisateur u where u.id=c.user_id and c.validerrh=1")
//List<Object>listcong(); //Verifier avec iheb ( Date now - Date debut )
    @Query(nativeQuery=true,value="select DATEDIFF(c.datefin, c.datedebut) as datedebut,ABS(DATE( NOW() ) - c.datefin) as datefin , c.color , c.matricule, c.typeconge , u.matricule  as matriculeuser, c.datedebut as dated from conge c , utilisateur u where u.id=c.user_id and c.validerrh=1")
    List<Object>listcong();
@Query(nativeQuery=true,value="select DATEDIFF(c.datefin, c.datedebut) as datedebut,ABS(DATE( NOW() ) - c.datefin) as datefin , c.color , c.matricule, c.typeconge , u.matricule as matriculeuser, c.datedebut as dated  from conge c , utilisateur u where u.id=c.user_id and c.service=? and c.validerrh=1")
List<Object>listcongchef(String service);

@Query(nativeQuery=true,value="select DATEDIFF(c.datefin, c.datedebut) as datedebut,ABS(DATE( NOW() ) - c.datefin) as datefin , c.color , c.matricule, c.typeconge , u.matricule as matriculeuser, c.datedebut as dated  from conge c , utilisateur u where u.id=c.user_id and  c.user_id=? and c.validerrh=1")
List<Object>listconguser(Long id);


List<Conge> findByServiceAndValider(String service, String string);
List<Conge> findByServiceAndValiderAndValiderrhIsFalse(String service, String string);
List<Conge> findByValiderAndValiderrhIsFalse(String string);
List<Conge> findByValiderAndValiderrhIsFalseAndUser(String string, Utilisateur u);
List<Conge> findByValiderAndValiderrhIsTrueAndUser(String string, Utilisateur u);
List<Conge> findByUserAndValiderAndValiderrhIsTrue(Utilisateur u, String string);
}
