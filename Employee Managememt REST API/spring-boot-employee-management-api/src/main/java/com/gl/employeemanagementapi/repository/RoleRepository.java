package com.gl.employeemanagementapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gl.employeemanagementapi.entity.Role;

public interface RoleRepository extends JpaRepository<Role, Integer> {

}
