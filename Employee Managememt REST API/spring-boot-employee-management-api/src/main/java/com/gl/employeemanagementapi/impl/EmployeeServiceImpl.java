package com.gl.employeemanagementapi.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gl.employeemanagementapi.entity.Employee;
import com.gl.employeemanagementapi.entity.Role;
import com.gl.employeemanagementapi.entity.User;
import com.gl.employeemanagementapi.repository.EmployeeRepository;
import com.gl.employeemanagementapi.repository.RoleRepository;
import com.gl.employeemanagementapi.repository.UserRepository;
import com.gl.employeemanagementapi.service.IEmployeeService;

@Service
public class EmployeeServiceImpl implements IEmployeeService {

	private EmployeeRepository employeeRepository;

	@Autowired
	private RoleRepository roleRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	BCryptPasswordEncoder bcryptPasswordEncoder;

	@Autowired
	public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
		this.employeeRepository = employeeRepository;
	}

	@Override
	public List<Employee> findAll() {
		// TODO Auto-generated method stub
		return employeeRepository.findAll();
	}

	@Override
	public Employee findById(long employeeId) {
		// TODO Auto-generated method stub
		Optional<Employee> employeeOptional = employeeRepository.findById(employeeId);
		Employee employee = null;
		if (employeeOptional.isPresent()) {
			employee = employeeOptional.get();
		}
		return employee;
	}

	@Override
	public void save(Employee employee) {
		// TODO Auto-generated method stub
		employeeRepository.save(employee);
	}

	@Override
	public void deleteById(long employeeId) {
		// TODO Auto-generated method stub
		employeeRepository.deleteById(employeeId);
	}

	@Override
	public List<Employee> searchByFirstName(String firstName) {
		// TODO Auto-generated method stub
		return employeeRepository.findByFirstNameContainsAllIgnoreCase(firstName);
	}

	@Override
	public List<Employee> sortByFirstNameAsc() {
		// TODO Auto-generated method stub
		return employeeRepository.findAllByOrderByFirstNameAsc();
	}

	@Override
	public User saveUser(User user) {
		// TODO Auto-generated method stub
		System.out.println(user.toString());
		user.setPassword(bcryptPasswordEncoder.encode(user.getPassword()));
		return userRepository.save(user);
	}

	@Transactional
	@Override
	public Role saveRole(Role role) {
		// TODO Auto-generated method stub
		return roleRepository.save(role);
	}

	@Override
	public List<Employee> sortByFirstNameDesc() {
		// TODO Auto-generated method stub
		return employeeRepository.findAllByOrderByFirstNameDesc();
	}

}