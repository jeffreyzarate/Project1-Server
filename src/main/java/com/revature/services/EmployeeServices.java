package com.revature.services;

import java.util.List;

import com.revature.models.Employee;
import com.revature.models.Request;

public interface EmployeeServices {
	
	Employee loginCheck(String username, String password);
	
	void createRequest(int userId, double amount, String description, String receipt, int typeId );
	
	void resolveRequest(int statusId, int requestId, int resolverId);
	
	List<Request> showAllRequests(int userId, int roleId);
	
	List<Request> showRequestsbyStatus(int userId, int roleId,int statusId);
	

}
