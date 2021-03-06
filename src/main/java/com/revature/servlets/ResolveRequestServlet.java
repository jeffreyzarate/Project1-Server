package com.revature.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.catalina.servlets.DefaultServlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.models.Employee;
import com.revature.models.Request;
import com.revature.services.EmployeeServicesImpl;

public class ResolveRequestServlet extends DefaultServlet{
	
	EmployeeServicesImpl service = new EmployeeServicesImpl();
	
	@Override
	public void service(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		response.addHeader("Access-Control-Allow-Headers", "content-type");
		response.addHeader("Access-Control-Allow-Origin", request.getHeader("Origin"));
		response.addHeader("Access-Control-Allow-Methods", "POST, PUT");
		super.service(request, response);
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException
	{
		ObjectMapper om = new ObjectMapper();
		Employee employee = om.readValue(req.getInputStream(), Employee.class);
		List<Request> requests = service.showRequestsbyStatus(employee.getId(), employee.getRoleId(), 1);
		om.writeValue(res.getOutputStream(), requests);
	}
	@Override
	protected void doPut(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException
	{
		ObjectMapper om = new ObjectMapper();
		Request request = om.readValue(req.getInputStream(), Request.class);
		service.resolveRequest(request.getStatusId(), request.getId(), request.getResolverId());
	}

}
