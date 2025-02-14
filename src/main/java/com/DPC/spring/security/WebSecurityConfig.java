package com.DPC.spring.security;

import com.DPC.spring.security.jwt.JwtAuthenticationEntryPoint;
import com.DPC.spring.security.jwt.JwtRequestFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
@Order(1)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    @Autowired
    private UserDetailsService jwtUserDetailsService;

    @Autowired
    private JwtRequestFilter jwtRequestFilter;

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
     auth.userDetailsService(jwtUserDetailsService).passwordEncoder(passwordEncoder());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.cors().and().csrf().disable()
                // dont authenticate this particular request
                .authorizeRequests()
                .antMatchers("/auth/**").permitAll()
                .antMatchers("/users/add").permitAll()
                .antMatchers("/users/GetCandidacy").permitAll()
                .antMatchers("/users/archiver").permitAll()
                .antMatchers("/users/userbyid").permitAll()
                .antMatchers("/users/getuserbyemail").permitAll()
                .antMatchers("/users/update").permitAll()
                .antMatchers("/users/add").permitAll()
                .antMatchers("/abs/abscbyuser").permitAll()
                .antMatchers("/abs/sanctionbyuser").permitAll()
                .antMatchers("/allabscencebyid/abs").permitAll()
                .antMatchers("/abs/modifier").permitAll()
                .antMatchers("/conge/all").permitAll()
                .antMatchers("/conge/chefcongebyservice").permitAll()
                .antMatchers("/conge/planificationchef").permitAll()
                .antMatchers("/conge/refuserchef").permitAll()
                .antMatchers("/conge/congerefuserchef").permitAll()
                .antMatchers("/conge/congebloquerchef").permitAll()
                .antMatchers("/conge/congeaccepterchef").permitAll()
                .antMatchers("/conge/planificationrh").permitAll()
                .antMatchers("/conge/congechefall").permitAll()
                .antMatchers("/conge/congeaccepte").permitAll()
                .antMatchers("/conge/conge").permitAll()
                .antMatchers("/conge/addconge").permitAll()
                .antMatchers("/conge/usercongeaccepte").permitAll()
                .antMatchers("/service/servicebyid").permitAll()
                .antMatchers("/service/all").permitAll()
                .antMatchers("/service/archiver").permitAll()
                .antMatchers("/authority/add").permitAll()
                .antMatchers("/login2").permitAll()
                .antMatchers("/v2/api-docs", "/configuration/**", "/swagger*/**", "/webjars/**").permitAll()
                // toutes les autres requêtes doivent être authentifiées
                .anyRequest().authenticated().and()
                // Assurez-vous d'utiliser une session sans état (stateless)
                // la session ne sera pas utilisée pour stocker l'état de l'utilisateur
                .exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint).and().sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);


        // Ajouter un filtre pour valider les jetons avec chaque requête.
        httpSecurity.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
    }

}
