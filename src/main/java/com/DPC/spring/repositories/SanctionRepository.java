package com.DPC.spring.repositories;

import com.DPC.spring.entities.Sanction;
import com.DPC.spring.entities.Utilisateur;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SanctionRepository extends JpaRepository<Sanction,Long> {

	List<Sanction> findByUser(Utilisateur user);

}
