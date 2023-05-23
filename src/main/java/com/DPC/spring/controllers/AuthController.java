package com.DPC.spring.controllers;

import com.DPC.spring.entities.PasswordResetToken;
import com.DPC.spring.entities.Utilisateur;
import com.DPC.spring.exceptions.TokenNotFoundException;
import com.DPC.spring.payload.requests.LoginRequest;
import com.DPC.spring.payload.responses.LoginResponse;
import com.DPC.spring.repositories.PasswordResetTokenRepository;
import com.DPC.spring.repositories.UtilisateurRepository;
import com.DPC.spring.services.AuthService;
import com.DPC.spring.services.MailService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@CrossOrigin("*")
@RestController
@RequestMapping("auth")
public class AuthController {

    @Autowired
    AuthService authService;
    @Autowired
    UtilisateurRepository userrepos ;
    @Autowired
    MailService mailService;
    @Autowired
    PasswordResetTokenRepository passwordResetTokenRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @PostMapping("/login2")
    public ResponseEntity<LoginResponse> login2(@Valid @RequestBody LoginRequest loginRequest)
    {
        String token = this.authService.login(loginRequest);
        Utilisateur contact = this.userrepos.findByEmail(loginRequest.getEmail());
        return ResponseEntity.ok(new LoginResponse(token, "Bearer", "Login successfully",contact.getAuthorities().getName(),loginRequest.getEmail(),contact.getService().getNom()));
    }

    @GetMapping("/resetPassword/{email}")
    public ResponseEntity<LoginResponse> resetPassword(@PathVariable("email") String email){
        Utilisateur user = userrepos.findByEmail(email);
        System.out.println(user);
        if(user == null ) throw new UsernameNotFoundException("utilisateur n'existe pas");
        String token = UUID.randomUUID().toString();
        System.out.println(token);
        mailService.createPasswordResetForUser(user,token);
        mailService.ResetPasswordMail(user,token);
        return null;
    }
    @PostMapping("/changePassword")
    public Utilisateur changePassword(@RequestBody changePasswordForm  changePasswordForm) {
        PasswordResetToken passwordResetToken= passwordResetTokenRepository.findByToken(changePasswordForm.getToken());
        if (passwordResetToken == null) throw new TokenNotFoundException("Token not found");
        Utilisateur user= passwordResetToken.getUser();
        System.out.println(changePasswordForm.getPassword());
        System.out.println(changePasswordForm.getToken());
        user.setPassword(bCryptPasswordEncoder.encode(changePasswordForm.getPassword()));
        System.out.println(passwordResetToken);
        passwordResetTokenRepository.deleteById(passwordResetToken.getId());
        userrepos.save(user);
        return null;
    }
}
@Data
class changePasswordForm{
    private String token;
    private String password;
}
