package com.gl.employeemanagementapi.service;

import java.util.List;

import com.gl.employeemanagementapi.entity.Employee;
import com.gl.employeemanagementapi.entity.Role;
import com.gl.employeemanagementapi.entity.User;

public interface IEmployeeService {

	public List<Employee> findAll();

	public Employee findById(long employeeId);

	public void save(Employee employee);

	public void deleteById(long employeeId);

	public List<Employee> searchByFirstName(String firstName);

	public List<Employee> sortByFirstNameAsc();

	public List<Employee> sortByFirstNameDesc();

	public User saveUser(User user);

	public Role saveRole(Role role);

}