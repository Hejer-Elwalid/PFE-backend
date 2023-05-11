package com.DPC.spring.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.DPC.spring.entities.Service;

public interface ServiceRepository extends JpaRepository<Service,Long> {

	Service findByNom(String service);

	List<Service> findByArchiverIsFalse();

}
