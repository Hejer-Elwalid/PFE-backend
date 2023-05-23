package com.DPC.spring.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PasswordResetToken {
    private static final int EXPIRATION=60*24;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String token ;
    @OneToOne(targetEntity = Utilisateur.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false)
    private Utilisateur user;
    private Date DateExpiration ;

    public PasswordResetToken(String token, Utilisateur user,Date date) {
        this.token = token;
        this.user = user;
        this.DateExpiration =date;
    }
}
