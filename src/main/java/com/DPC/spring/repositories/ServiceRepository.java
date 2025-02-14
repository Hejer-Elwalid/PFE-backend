package com.DPC.spring.repositories;

import java.util.List;

import com.DPC.spring.entities.Abscence;
import com.DPC.spring.entities.Sanction;
import com.DPC.spring.entities.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;

import com.DPC.spring.entities.Service;

public interface ServiceRepository extends JpaRepository<Service,Long> {

	Service findByNom(String service);
	List<Service> findByArchiverIsFalse();

}
