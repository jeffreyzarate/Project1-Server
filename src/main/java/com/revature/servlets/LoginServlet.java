package com.revature.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.catalina.servlets.DefaultServlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.models.Employee;
import com.revature.models.Request;
import com.revature.services.EmployeeServicesImpl;
import com.revature.util.HttpException;

public class LoginServlet extends DefaultServlet{
	EmployeeServicesImpl service = new EmployeeServicesImpl();
	
	@Override
	public void service(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		response.addHeader("Access-Control-Allow-Headers", "content-type");
		response.addHeader("Access-Control-Allow-Origin", request.getHeader("Origin"));
		super.service(request, response);
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException
	{
		ObjectMapper om = new ObjectMapper();
		Employee employee = om.readValue(req.getInputStream(), Employee.class);
		Integer id = null;
		try {
			employee = service.loginCheck(employee.getUsername(), employee.getPassword());
			id = employee.getId();
		} catch (HttpException e) {
			
			res.setStatus(e.getStatus());
			return;
		}
		String cache = employee.getId() +" "+employee.getRoleId()+" "+employee.getFirstName() +" "+employee.getLastName();
		
		HttpSession session = req.getSession(true);
		session.setAttribute("id", id);
		om.writeValue(res.getOutputStream(),cache);
	}
	
}
