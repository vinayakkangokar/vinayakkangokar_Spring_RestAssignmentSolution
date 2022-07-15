package com.gl.employeemanagementapi.security.handlers;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.core.Authentication;

public class CustomAccessDeniedHandler implements AccessDeniedHandler {

	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response,
			AccessDeniedException accessDeniedException) throws IOException, ServletException {
		// TODO Auto-generated method stub
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication != null) {

			System.out.println(
					"User '" + authentication.getName() + "' attempted to access the URL: " + request.getRequestURI());
		}

		response.setContentType("application/json;charset=UTF-8");
		response.setStatus(403);
		response.getWriter().write("{\"message\":\"Access Denied\"}");
	}
}