package com.revature.services;

import java.util.List;

import com.revature.dao.ErsDaoImpl;
import com.revature.models.Employee;
import com.revature.models.Request;
import com.revature.util.HttpException;

public class EmployeeServicesImpl implements EmployeeServices{
	
	ErsDaoImpl dao = new ErsDaoImpl();

	@Override
	public Employee loginCheck(String username, String password) {
		
		Employee currentEmployee=dao.getEmployee(username, password);
		if(currentEmployee==null)
		{
			throw new HttpException(400);
		}
		return currentEmployee;
		
	}

	@Override
	public void createRequest(int userId, double amount, String description, String receipt, int typeId) {
		dao.insertRequest(userId, amount, description, receipt, typeId);
	}

	@Override
	public void resolveRequest(int statusId, int requestId, int resolverId) {
		dao.updateRequest(statusId, requestId, resolverId);
		
	}

	@Override
	public List<Request> showAllRequests(int userId, int roleId) {
		List<Request>requests= dao.getRequests(userId, roleId);
		return requests;
	}

	@Override
	public List<Request> showRequestsbyStatus(int userId, int roleId,int statusId) {
		List<Request> requests = dao.getRequestsbyStatus(userId, roleId,statusId);
		return requests;
		
	}

}
