package com.sahil.smart_parking_project.security;

import java.util.Collections;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.sahil.smart_parking_project.entity.User;
import com.sahil.smart_parking_project.repository.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

private final UserRepository userRepository;


	
	public CustomUserDetailsService(UserRepository userRepository) {
	super();
	this.userRepository = userRepository;
}



	@Override
	public UserDetails loadUserByUsername(String username)
	        throws UsernameNotFoundException {

	    User user = userRepository.findByEmail(username)
	            .orElseThrow(() ->
	                    new UsernameNotFoundException(
	                            "User not found with email: " + username
	                    )
	            );
	    
	    System.out.println("User found: " + user);

	 org.springframework.security.core.userdetails.User user2= new org.springframework.security.core.userdetails.User(
	            user.getEmail(),
	            user.getPassword(),
	            Collections.singletonList(
	                    new SimpleGrantedAuthority(user.getRole().getName().name())
	            )
	    );
	 
	 System.out.println("UserDetails: " + user2.getAuthorities().toString());
	 
	 return user2;
	}
	
}
