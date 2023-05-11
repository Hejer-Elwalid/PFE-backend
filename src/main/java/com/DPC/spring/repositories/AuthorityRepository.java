package com.DPC.spring.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.DPC.spring.entities.Autority;

public interface AuthorityRepository extends JpaRepository<Autority,Long> {

	Autority findByName(String string);

}
