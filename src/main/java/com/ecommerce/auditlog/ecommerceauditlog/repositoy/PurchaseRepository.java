package com.ecommerce.auditlog.ecommerceauditlog.repositoy;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ecommerce.auditlog.ecommerceauditlog.model.Purchase;
/**
 * 
 * @author VISHNU
 *
 */

@Repository
public interface PurchaseRepository extends JpaRepository<Purchase,Long > {}
