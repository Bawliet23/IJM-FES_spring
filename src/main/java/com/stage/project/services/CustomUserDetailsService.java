package com.stage.project.services;

import com.stage.project.entities.Role;
import com.stage.project.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


@Service("customUserDetailsService")
@Transactional
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
	private UserService userService;

	@Override
	@Transactional(readOnly = true)
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		if (username.trim().isEmpty()) {
			throw new UsernameNotFoundException("username is empty");
		}

		User user = userService.findUserByUsername(username);

		if (user == null) {
			throw new UsernameNotFoundException("User " + username + " not found");
		}

		//new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), getGrantedAuthorities(user));

		return new  UserPrincipal(user,getGrantedAuthorities(user));

	}

	private List<GrantedAuthority> getGrantedAuthorities(User user) {
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		Role role = user.getRole();
		authorities.add(new SimpleGrantedAuthority(role.getRoleName()));
		return authorities;
	}
}
