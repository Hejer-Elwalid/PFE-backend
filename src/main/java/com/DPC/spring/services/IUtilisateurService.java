package com.DPC.spring.services;

import com.DPC.spring.entities.Utilisateur;

import java.util.List;

public interface IUtilisateurService {

   // void  add(Utilisateur utilisateur);
    void  updateUtilisateur (Utilisateur utilisateur);
    void  deleteUtilisateur(Long idUtilisateur);
    List<Utilisateur> GetUtilisateur();


}
