package com.DPC.spring.repositories;

import com.DPC.spring.entities.Abscence;
import com.DPC.spring.entities.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AbscenceRepository extends JpaRepository<Abscence,Long> {
    List<Abscence> findByDate(String date);

    List<Abscence> findByUser(Utilisateur user);
    @Query(nativeQuery = true,value = "select COUNT(*) from abscence where year(?) and user_id=?")
    int countabs(String date, Long id);
}
