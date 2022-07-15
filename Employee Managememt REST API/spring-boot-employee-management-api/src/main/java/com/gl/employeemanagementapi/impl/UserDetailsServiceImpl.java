package com.gl.employeemanagementapi.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.gl.employeemanagementapi.entity.User;
import com.gl.employeemanagementapi.repository.UserRepository;
import com.gl.employeemanagementapi.security.AppUserDetails;

public class UserDetailsServiceImpl implements UserDetailsService {
	@Autowired
	private UserRepository userRepository;

	@Override
	public AppUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.getUserByUsername(username);
		if (user == null) {
			throw new UsernameNotFoundException("User not found");
		}
		return new AppUserDetails(user);
	}
}