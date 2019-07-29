package com.ecommerce.auditlog.ecommerceauditlog.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.auditlog.ecommerceauditlog.auditlogsaver.AuditLogSaver;
import com.ecommerce.auditlog.ecommerceauditlog.exceptions.ResourceNotFoundException;
import com.ecommerce.auditlog.ecommerceauditlog.model.AuditLog;
import com.ecommerce.auditlog.ecommerceauditlog.repositoy.AuditLogRepository;



/**
 * The type audit log controller.
 *
 * @author VISHNU
 */
@RestController
@RequestMapping("/api/auditlog")
public class AuditLogController extends AuditLogSaver{

	final static Logger logger = Logger.getLogger(AuditLogController.class);

	@Autowired
	private AuditLogRepository auditLogRepository;



	/**
	 * Get all products
	 *
	 * @return the list
	 */
	@GetMapping("/getAll")
	public List<AuditLog> getAllProducts() {


		return auditLogRepository.findAll();
	}

	/**
	 * Gets log by user.
	 *
	 * @param user
	 * @return entity  AuditLog
	 * @throws ResourceNotFoundException the resource not found exception
	 */
	@GetMapping("/user/{user}")
	public ResponseEntity<List<AuditLog>> getLogByUser(@PathVariable(value = "user") String user)
			throws ResourceNotFoundException {
		List<AuditLog> auditlog = auditLogRepository.findAll();
		if(auditlog==null){
			logger.debug("Log with user" + user + " does not exists");
			return new ResponseEntity<List<AuditLog>>(HttpStatus.NOT_FOUND);
		}else{
			logger.debug("Found Log with user" + user);
			List<AuditLog> auditlogfiltered=auditlog.stream().filter(x->x.getUserName().equals(user)).collect(Collectors.toList());
			return new ResponseEntity<List<AuditLog>>(auditlogfiltered,HttpStatus.OK);
		}
	}

	/**
	 * Gets log by module.
	 *
	 * @param module
	 * @return list of AuditLog
	 */
	@GetMapping("/module/{module}")
	public ResponseEntity<List<AuditLog>> getLogByModuleName(@PathVariable(value = "module") String module)
			throws ResourceNotFoundException {
		List<AuditLog> auditlog = auditLogRepository.findAll();
		if(auditlog==null){
			logger.debug("Log with module" + module + " does not exists");
			return new ResponseEntity<List<AuditLog>>(HttpStatus.NOT_FOUND);
		}else{
			logger.debug("Found Log with user" + module);
			List<AuditLog> auditlogfiltered = auditlog.stream().filter(x->x.getmoduleName().contains(module)).collect(Collectors.toList());
			return new ResponseEntity<List<AuditLog>>(auditlogfiltered, HttpStatus.OK);
		}
	}
	
	

	/**
	 * Gets log by action.
	 *
	 * @param action
	 * @return list of AuditLog
	 */
	@GetMapping("/action/{action}")
	public ResponseEntity<List<AuditLog>> getLogByActionName(@PathVariable(value = "action") String action)
			throws ResourceNotFoundException {
		List<AuditLog> auditlog = auditLogRepository.findAll();
		if(auditlog==null){
			logger.debug("Log with action" + action + " does not exists");
			return new ResponseEntity<List<AuditLog>>(HttpStatus.NOT_FOUND);
		}else{
			logger.debug("Found Log with action" + action);
			List<AuditLog> auditlogfiltered=auditlog.stream().filter(x->x.getActionName().equalsIgnoreCase(action)).collect(Collectors.toList());
			return new ResponseEntity<List<AuditLog>>(auditlogfiltered,HttpStatus.OK);
		}
	}
	
	/**
	 * Delete logtrail map.
	 *
	 * @param productId
	 * @return the map
	 * @throws Exception the exception
	 */
	@DeleteMapping("/delete")
	public Map<String, Boolean> deleteAllLogs(@PathVariable(value = "id") Long id) throws Exception {
		auditLogRepository.deleteAll();
		Map<String, Boolean> response = new HashMap<>();
		response.put("all deleted", Boolean.TRUE);
		logger.debug("all logs have been deleted");
		return response;
	}
}
