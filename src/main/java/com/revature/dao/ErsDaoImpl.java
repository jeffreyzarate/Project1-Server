package com.revature.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.revature.models.Employee;
import com.revature.models.Request;
import com.revature.util.ConnectionUtility;

public class ErsDaoImpl implements ErsDao{
	
	@Override
	public Employee getEmployee(String username, String password) {
		Employee employee=null;
		
		try(Connection conn=ConnectionUtility.getConnection())
		{
			String sql ="select * from ers_users where ers_username=? and ers_password=?";
			PreparedStatement ps= conn.prepareStatement(sql);
			ps.setString(1,username);
			ps.setString(2, password);
			ResultSet res= ps.executeQuery();
			if(res.next())
			{
				int userId = res.getInt("ers_users_id");
				String myusername= res.getString("ers_username");
				String mypassword=res.getString("ers_password");
				String firstName=res.getString("user_first_name");
				String lastName=res.getString("user_last_name");
				String email=res.getString("user_email");
				int roleId= res.getInt("user_role_id");
				employee = new Employee(userId,myusername,mypassword,firstName,lastName,email,roleId);
			}
			
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		return employee;
		
		
	}

	@Override
	public List<Request> getRequests(int userId, int roleId) {
		
		List<Request> requests = new ArrayList<Request>();
		String sql = "select * from ers_reimbursement where reimb_author =?";
		String sql2 = "select * from ers_reimbursement";
		
		
		try(Connection conn =ConnectionUtility.getConnection())
		{
			PreparedStatement ps;
			if(roleId==2)
			{
				ps = conn.prepareStatement(sql);
				ps.setInt(1, userId);
			}
			else
			{
				ps = conn.prepareStatement(sql2);
			}
			
			ResultSet res= ps.executeQuery();
			while(res.next())
			{
				int requestId = res.getInt("reimb_id");
				double amount= res.getDouble("reimb_amount");
				Timestamp submitted=res.getTimestamp("reimb_submitted");
				Timestamp resolved=res.getTimestamp("reimb_resolved");
				String description=res.getString("reimb_description");
				String receipt=res.getString("reimb_receipt");
				int authorId= res.getInt("reimb_author");
				int resolverId= res.getInt("reimb_resolver");
				int statusId = res.getInt("reimb_status_id");
				int typeId = res.getInt("reimb_type_id");
						
				Request request = new Request(requestId,amount,submitted,resolved,description,receipt,authorId,resolverId,statusId,typeId);
				requests.add(request);
			}
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		return requests;
		
	}

	@Override
	public void updateRequest(int statusId, int requestId, int resolverId) {
		try(Connection conn = ConnectionUtility.getConnection())
		{
			String sql = "update ers_reimbursement set reimb_status_id =?, reimb_resolved=now(), reimb_resolver = ? where reimb_id = ?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, statusId);
			ps.setInt(2, resolverId);
			ps.setInt(3, requestId);
			ps.executeUpdate();		
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		
		
	}

	@Override
	public List<Request> getRequestsbyStatus(int userId, int roleId,int statusId) {
		List<Request> requests = new ArrayList<Request>();
		try(Connection conn = ConnectionUtility.getConnection())
		{
			String sql= "select * from ers_reimbursement where reimb_author !=? and reimb_status_id=?";
			PreparedStatement ps= conn.prepareStatement(sql);
			ps.setInt(1, userId);
			ps.setInt(2, statusId);
			ResultSet res= ps.executeQuery();
			while(res.next())
			{
				int requestId = res.getInt("reimb_id");
				double amount= res.getDouble("reimb_amount");
				Timestamp submitted=res.getTimestamp("reimb_submitted");
				Timestamp resolved=res.getTimestamp("reimb_resolved");
				String description=res.getString("reimb_description");
				String receipt=res.getString("reimb_receipt");
				int authorId= res.getInt("reimb_author");
				int resolverId= res.getInt("reimb_resolver");
				int mystatusId = res.getInt("reimb_status_id");
				int typeId = res.getInt("reimb_type_id");
						
				Request request = new Request(requestId,amount,submitted,resolved,description,receipt,authorId,resolverId,mystatusId,typeId);
				requests.add(request);
			}
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		
		
		
		return requests;
	}

	@Override
	public void insertRequest(int userId, double amount, String description, String receipt, int typeId) {
		try(Connection conn = ConnectionUtility.getConnection())
		{
			String sql = "insert into ers_reimbursement (reimb_author, reimb_amount, reimb_description, reimb_receipt, reimb_status_id, reimb_type_id) values(?,?,?,?,?,?)";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, userId);
			ps.setDouble(2, amount);
			ps.setString(3, description);
			ps.setString(4, receipt);
			ps.setInt(5, 1);
			ps.setInt(6,typeId);
			ps.executeUpdate();
			} catch (SQLException e) {
			
			e.printStackTrace();
		}
		
	}

}
