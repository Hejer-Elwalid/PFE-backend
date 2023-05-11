package com.DPC.spring;

import com.DPC.spring.entities.*;
import com.DPC.spring.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.ApplicationContext;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@SpringBootApplication
@EnableSwagger2
public class Application   extends SpringBootServletInitializer /* implements ApplicationRunner*/  {

	@Autowired
	private ApplicationContext applicationContext;


	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(Application.class);
	}

//	public BCryptPasswordEncoder passwordEncoder() {
//		BCryptPasswordEncoder passwordEncoderBean = applicationContext.getBean(BCryptPasswordEncoder.class);
//		return passwordEncoderBean;
//	}

//	@Override
//	public void run(ApplicationArguments args) throws Exception {
//
//		// Save roles
//		Role superAdminRole = this.roleRepository.save(new Role(ERole.SUPER_ADMIN));
//		Role adminRole = this.roleRepository.save(new Role(ERole.ADMIN));
//		Role userRole = this.roleRepository.save(new Role(ERole.USER));
//		Role guestRole = this.roleRepository.save(new Role(ERole.GUEST));
//
//
//
//		// Save users
//		User user1 = new User("marwen", "sghair",
//					"marwen@dipower.fr",
//				this.passwordEncoder().encode("12345"));
//
//		// Save users details
//		Calendar dateOfBirth = Calendar.getInstance();
//		dateOfBirth.set(1992, 7, 21);
//		UserDetails userDetails1 = new UserDetails(30, "+91-8197882053",  dateOfBirth.getTime(),
//				"747", "2nd Cross", "Golf View Road, Kodihalli", "Bangalore",
//				"Karnataka", "India", "560008", new Date("11/11/1994"),
//				"", "");
//
//		// Affect user1 to userDetails1
//		user1.setDetails(userDetails1); // Set child reference
//		userDetails1.setUser(user1); // Set parent reference
//		this.userRepository.save(user1);
//
//
//
//		// ManyToMany Relations
//		Set<Role> roles = new HashSet<>();
//		roles.add(superAdminRole);
//		roles.add(adminRole);
//		roles.add(userRole);
//		roles.add(guestRole);
//		user1.setRoles(roles);
//		this.userRepository.save(user1);
//
//
//	}
}
