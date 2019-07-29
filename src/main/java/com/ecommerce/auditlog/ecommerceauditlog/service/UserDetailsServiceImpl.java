package com.ecommerce.auditlog.ecommerceauditlog.service;



import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.ecommerce.auditlog.ecommerceauditlog.model.Users;
import com.ecommerce.auditlog.ecommerceauditlog.repositoy.UsersRepository;



@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	UsersRepository usersRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
		Users user = usersRepository.findById(username).orElseThrow(() -> 
		new UsernameNotFoundException("User not found on :: " + username));;

		if (user == null) {
			throw new UsernameNotFoundException("Invalid username or password");
		}

		return new User(username, user.getPassword(), true, true, true,
				true, AuthorityUtils.createAuthorityList(user.getRole()));

	}
	

	/**
	 * Adding users at application startup for testing
	 */
	@PostConstruct
	public void loadUsers() {
		List<Users> users = Arrays.asList(
							new Users("user", "password", "USER"),
							new Users("admin", "password", "ADMIN"));
		usersRepository.saveAll(users);
	}

}
