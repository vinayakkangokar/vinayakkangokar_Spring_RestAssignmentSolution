package com.gl.employeemanagementapi.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gl.employeemanagementapi.entity.Employee;
import com.gl.employeemanagementapi.service.IEmployeeService;
import com.gl.employeemanagementapi.entity.Role;
import com.gl.employeemanagementapi.entity.User;

@RestController
@RequestMapping("/api")
public class EmployeeRestController {

	@Autowired
	IEmployeeService employeeService;

	@Autowired
	public EmployeeRestController(IEmployeeService employeeService) {
		this.employeeService = employeeService;
	}

	@GetMapping("/employees")
	public List<Employee> findAll() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Collection<? extends GrantedAuthority> currentPrincipalName = authentication.getAuthorities();
		System.out.println(currentPrincipalName);
		return employeeService.findAll();
	}

	@GetMapping("/employees/{employeeId}")
	public Employee getEmployeeById(@PathVariable("employeeId") long employeeId) {
		Employee employee = employeeService.findById(employeeId);
		if (employee == null) {
			throw new RuntimeException("No employee found with employeeId - " + employeeId);
		}
		return employee;
	}

	@PostMapping("/employees")
	public Employee saveEmployee(@RequestBody Employee employee) {
		employee.setEmployeeId(0);
		employeeService.save(employee);
		return employee;
	}

	@PutMapping("/employees")
	public Employee updateEmployee(@RequestBody Employee employee) {
		Employee emp = employeeService.findById(employee.getEmployeeId());
		if (emp == null) {
			throw new RuntimeException(
					"No employee record found with id - " + employee.getEmployeeId() + " for update");
		}
		employeeService.save(employee);

		return employee;
	}

	@DeleteMapping("/employees/{employeeId}")
	public String deleteEmployeeById(@PathVariable long employeeId) {

		Employee employee = employeeService.findById(employeeId);
		if (employee == null) {
			return "No record found with id " + employeeId + " for deletion";
		}
		employeeService.deleteById(employeeId);
		return "Deleted employee id - " + employeeId;
	}

	@GetMapping("/employees/search/{firstName}")
	public List<Employee> searchByFirstName(@PathVariable String firstName) {
		System.out.println("Search with firstName like " + firstName);
		return employeeService.searchByFirstName(firstName.toString());
	}

	@GetMapping("/employees/sort")
	public List<Employee> sortByFirstName(@RequestParam("order") String order) {
		List<Employee> employees = new ArrayList<Employee>();

		if (order.equalsIgnoreCase("asc"))
			employees = employeeService.sortByFirstNameAsc();
		else if (order.equalsIgnoreCase("desc"))
			employees = employeeService.sortByFirstNameDesc();

		return employees;
	}

	@Transactional
	@PostMapping("/user")
	public User saveUser(@RequestBody User user) {
		return employeeService.saveUser(user);
	}

	@PostMapping("/role")
	public Role saveRole(@RequestBody Role role) {
		return employeeService.saveRole(role);
	}

}