package com.revature.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.catalina.servlets.DefaultServlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.models.Request;
import com.revature.services.EmployeeServicesImpl;
import com.revature.util.HttpException;

public class CreateRequestServlet extends DefaultServlet{
	
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
		Request request = om.readValue(req.getInputStream(), Request.class);
		try {
			service.createRequest(request.getAuthorId(), request.getAmount(), request.getDescription(), request.getReceipt(), request.getTypeId());
		} catch (HttpException e) {
			res.setStatus(e.getStatus());
			return;
		}
		
	}

}
