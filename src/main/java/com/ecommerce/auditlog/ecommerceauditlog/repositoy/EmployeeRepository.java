package com.ecommerce.auditlog.ecommerceauditlog.repositoy;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ecommerce.auditlog.ecommerceauditlog.model.Employee;
/**
 * 
 * @author VISHNU
 *
 */

@Repository
public interface EmployeeRepository extends JpaRepository<Employee,Long >  {


}
