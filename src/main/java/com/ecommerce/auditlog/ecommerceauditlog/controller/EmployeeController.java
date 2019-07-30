package com.ecommerce.auditlog.ecommerceauditlog.controller;

import java.util.Arrays;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.auditlog.ecommerceauditlog.auditlogsaver.AuditLogSaver;
import com.ecommerce.auditlog.ecommerceauditlog.exceptions.ResourceNotFoundException;
import com.ecommerce.auditlog.ecommerceauditlog.model.Employee;
import com.ecommerce.auditlog.ecommerceauditlog.repositoy.EmployeeRepository;


/**
 * 
 * @author VISHNU
 */

@RestController
@RequestMapping("/employee")
public class EmployeeController extends AuditLogSaver{

	final static Logger logger = Logger.getLogger(EmployeeController.class);

	@Autowired
	EmployeeRepository empRepository;

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Employee> addEmployee(@RequestBody Employee employee) {
		//empService.save(employee);
		empRepository.save(employee);
		auditSaveOperation(INSERT_ACTION, getModuleName(),STATUS_OK);
		logger.debug("Added:: " + employee);
		return new ResponseEntity<Employee>(employee, HttpStatus.CREATED);
	}


	@RequestMapping(method = RequestMethod.PUT)
	public ResponseEntity<Void> updateEmployee(@RequestBody Employee employee) throws ResourceNotFoundException {
		//Employee existingEmp = empService.getById(employee.getId());
		Employee existingEmp = empRepository.findById(employee.getId()).orElseThrow(() -> new ResourceNotFoundException("User not found on :: " + employee.getId()));
		if (existingEmp == null) {
			logger.debug("Employee with id " + employee.getId() + " does not exists");
			auditSaveOperation(UPDATE_ACTION, getModuleName(),STATUS_NOTFOUND);
			return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
		} else {
			empRepository.save(employee);
			auditSaveOperation(UPDATE_ACTION, getModuleName(),STATUS_OK);
			return new ResponseEntity<Void>(HttpStatus.OK);
		}
	}


	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<Employee> getEmployee(@PathVariable("id") Long id) throws ResourceNotFoundException {
		Employee employee = empRepository.findById(id).orElseThrow(() ->
		new ResourceNotFoundException("User not found on :: " + id ));
		if (employee == null) {
			logger.debug("Employee with id " + id + " does not exists");
			return new ResponseEntity<Employee>(HttpStatus.NOT_FOUND);
		}
		logger.debug("Found Employee:: " + employee);
		return new ResponseEntity<Employee>(employee, HttpStatus.FOUND);
	}


	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<Employee>> getAllEmployees() {
		List<Employee> employees = empRepository.findAll();
		if (employees.isEmpty()) {
			logger.debug("Employees does not exists");
			return new ResponseEntity<List<Employee>>(HttpStatus.NO_CONTENT);
		}
		logger.debug("Found " + employees.size() + " Employees");
		logger.debug(Arrays.toString(employees.toArray()));
		return new ResponseEntity<List<Employee>>(employees, HttpStatus.OK);
	}


	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> deleteEmployee(@PathVariable("id") Long id) throws ResourceNotFoundException {
		Employee employee = empRepository.findById(id).orElseThrow(() -> 
		new ResourceNotFoundException("User not found on :: " + id));
		if (employee == null) {
			logger.debug("Employee with id " + id + " does not exists");
			auditSaveOperation(DELETE_ACTION, getModuleName(),STATUS_NOTFOUND);
			return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
		} else {
			empRepository.deleteById(id);
			auditSaveOperation(DELETE_ACTION, getModuleName(),STATUS_OK);
			logger.debug("Employee with id " + id + " deleted");
			return new ResponseEntity<Void>(HttpStatus.GONE);
		}
	}

}
