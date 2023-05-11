package com.DPC.spring.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.DPC.spring.entities.Utilisateur;
import org.springframework.stereotype.Repository;

@Repository

public interface UtilisateurRepository extends JpaRepository<Utilisateur,Long> {
  //  Utilisateur findById (long idUtilisateur) ;
	Utilisateur findByEmail(String email);

    Utilisateur findByMatricule(String matricule);

	List<Utilisateur> findByArchiverIsFalse();
}
