package com.ecommerce.auditlog.ecommerceauditlog.auditlogsaver;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.ecommerce.auditlog.ecommerceauditlog.model.AuditLog;
import com.ecommerce.auditlog.ecommerceauditlog.repositoy.AuditLogRepository;

public class AuditLogSaver {
	
	public static final String DELETE_ACTION="DELETION";
	public static final String UPDATE_ACTION="UPDATION";
	public static final String INSERT_ACTION="INSERTION";
	
	@Autowired
	AuditLogRepository auditLogRepository;
	/**
	 * auditSaveOperation for saving audit log for each api
	 * @param actionName
	 * @param methodName
	 */
	public void auditSaveOperation(String actionName,String methodName){
		AuditLog auditLog = new AuditLog();
		  auditLog.setUserName(getUserName());
		  auditLog.setModuleName(methodName);
		  auditLog.setTimeStamp(new Date());
		  auditLog.setActionName(actionName);
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
	

}
