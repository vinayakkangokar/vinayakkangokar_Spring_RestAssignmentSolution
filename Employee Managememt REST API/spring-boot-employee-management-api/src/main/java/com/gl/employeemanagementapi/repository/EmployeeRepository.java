package com.gl.employeemanagementapi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.gl.employeemanagementapi.entity.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

	@Query("SELECT e FROM Employee e WHERE LOWER(e.firstName) LIKE LOWER(?1)")
	List<Employee> findByFirstNameContainsAllIgnoreCase(String firstName);

	List<Employee> findAllByOrderByFirstNameAsc();

	List<Employee> findAllByOrderByFirstNameDesc();
}
