package com.DPC.spring.controllers;

import com.DPC.spring.entities.Utilisateur;
import com.DPC.spring.exceptions.EmailAlreadyUsedException;
import com.DPC.spring.payload.requests.LoginRequest;
import com.DPC.spring.payload.requests.RegisterRequest;
import com.DPC.spring.payload.responses.LoginResponse;
import com.DPC.spring.payload.responses.MessageResponse;
import com.DPC.spring.repositories.UtilisateurRepository;
import com.DPC.spring.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@CrossOrigin("*")
@RestController
@RequestMapping("auth")
public class AuthController {

    @Autowired
    AuthService authService;
    @Autowired
    UtilisateurRepository userrepos ;
    @PostMapping("/login2")
    public ResponseEntity<LoginResponse> login2(@Valid @RequestBody LoginRequest loginRequest)
    {
		String token = this.authService.login(loginRequest);
		Utilisateur contact = this.userrepos.findByEmail(loginRequest.getEmail());
		return ResponseEntity.ok(new LoginResponse(token, "Bearer", "Login successfully",contact.getAuthorities().getName(),loginRequest.getEmail(),contact.getService().getNom()));
  }
    
}
