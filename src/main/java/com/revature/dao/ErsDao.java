package com.revature.dao;


import java.util.List;

import com.revature.models.Employee;
import com.revature.models.Request;

public interface ErsDao {
	
	Employee getEmployee(String username, String password);
	
	List<Request> getRequests(int userId, int roleId);
	
	List<Request> getRequestsbyStatus(int userId, int roleId,int typeId);
	
	void updateRequest(int statusId, int requestId, int resolverId);
	
	void insertRequest(int userId, double amount, String description, String receipt, int statusId);
	
}
