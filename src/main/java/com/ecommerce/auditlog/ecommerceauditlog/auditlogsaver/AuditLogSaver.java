package com.ecommerce.auditlog.ecommerceauditlog.auditlogsaver;

import java.io.IOException;
import java.util.Date;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.ecommerce.auditlog.ecommerceauditlog.model.AuditLog;
import com.ecommerce.auditlog.ecommerceauditlog.repositoy.AuditLogRepository;


public class AuditLogSaver implements Filter{
	
	public static final String DELETE_ACTION="DELETION";
	public static final String UPDATE_ACTION="UPDATION";
	public static final String INSERT_ACTION="INSERTION";
	public static final String STATUS_OK="OK";
	public static final String STATUS_NOTFOUND="NOT_FOUND";
	public static final String STATUS_ERROR ="ERROR";
	
	public Long deltaTime;
	
	@Autowired
	AuditLogRepository auditLogRepository;
	/**
	 * auditSaveOperation for saving audit log for each api
	 * @param actionName
	 * @param methodName
	 */
	public void auditSaveOperation(String actionName,String methodName,String status){
		AuditLog auditLog = new AuditLog();
		  auditLog.setUserName(getUserName());
		  auditLog.setModuleName(methodName);
		  auditLog.setTimeStamp(new Date());
		  auditLog.setActionName(actionName);
		  auditLog.setResponseTime(deltaTime);
		  auditLog.setResponseStatus(status);
		  System.out.println("got response for action in"+deltaTime+"ms");
		  auditLogRepository.save(auditLog);
		
	}
	/**
	 * getUserName For Getting user information
	 * @return
	 */
	public String getUserName(){
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		return authentication.getName();
		
	}
	/**
	 * getModuleName For getting class information
	 * @return
	 */
	public String getModuleName(){
		return this.getClass().getName().toString();
		
	}
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		long startTime = System.currentTimeMillis();
		chain.doFilter(request, response);
		long endTime = System.currentTimeMillis();
		deltaTime = (endTime-startTime);
		
		
	}
	

}
