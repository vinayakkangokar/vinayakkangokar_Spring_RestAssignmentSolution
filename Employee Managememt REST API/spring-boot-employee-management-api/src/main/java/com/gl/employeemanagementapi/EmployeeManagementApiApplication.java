package com.gl.employeemanagementapi;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.gl.employeemanagementapi.repository.RoleRepository;
import com.gl.employeemanagementapi.repository.UserRepository;
import com.gl.employeemanagementapi.entity.Role;
import com.gl.employeemanagementapi.entity.User;

@SpringBootApplication
public class EmployeeManagementApiApplication implements CommandLineRunner {

	@Autowired
	UserRepository userRepository;
	@Autowired
	RoleRepository roleRepository;

	public static void main(String[] args) {
		SpringApplication.run(EmployeeManagementApiApplication.class, args);
	}

	@Override
	@Transactional
	public void run(String... args) throws Exception {
		// TODO Auto-generated method stub

		BCryptPasswordEncoder bCryptPassEncoder = new BCryptPasswordEncoder();

		Role adminRole = new Role();
		adminRole.setRoleName("ADMIN");
		Role userRole = new Role();
		userRole.setRoleName("USER");
		roleRepository.saveAllAndFlush(new ArrayList<>(Arrays.asList(adminRole, userRole)));

		User adminUser = new User();
		adminUser.setUsername("Admin");
		adminUser.setPassword(bCryptPassEncoder.encode("admin123"));
		adminUser.setRoles(new ArrayList<Role>(Arrays.asList(adminRole, userRole)));

		User user = new User();
		user.setUsername("User");
		user.setPassword(bCryptPassEncoder.encode("user123"));
		user.setRoles(new ArrayList<Role>(Arrays.asList(userRole)));

		// To avoid entering redundant entries
		ExampleMatcher matcher = ExampleMatcher.matchingAny().withMatcher("username",
				ExampleMatcher.GenericPropertyMatchers.exact());

		List<User> adminUsers = userRepository.findAll(Example.of(adminUser, matcher));
		List<User> users = userRepository.findAll(Example.of(user, matcher));
		System.out.println("Admin users present in database = " + adminUsers.size());
		System.out.println("Non-admin users present in database = " + adminUsers.size());

		if (adminUsers.size() == 0 && users.size() == 0)
			userRepository.saveAllAndFlush(new ArrayList<>(Arrays.asList(adminUser, user)));
		else {
			if (adminUsers.size() == 0)
				userRepository.saveAndFlush(adminUser);
			if (users.size() == 0)
				userRepository.saveAndFlush(user);
		}
	}

}