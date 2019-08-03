package com.ecommerce.auditlog.ecommerceauditlog.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.auditlog.ecommerceauditlog.auditlogsaver.AuditLogSaver;
import com.ecommerce.auditlog.ecommerceauditlog.exceptions.ResourceNotFoundException;
import com.ecommerce.auditlog.ecommerceauditlog.model.Purchase;
import com.ecommerce.auditlog.ecommerceauditlog.repositoy.PurchaseRepository;

import io.swagger.annotations.ApiOperation;



/**
 * The type purchase controller.
 *
 * @author VISHNU
 */
@RestController
@RequestMapping("/api/purchase")
public class PurchaseController extends AuditLogSaver{
	
	
	final static Logger logger = Logger.getLogger(PurchaseController.class);
	
  @Autowired
  private PurchaseRepository purchaseRepository;
  
  /**
   * Get all purchases
   *
   * @return the list
   */
  @GetMapping("/purchase")
  @ApiOperation(value = "Get all purchase details", response = ResponseEntity.class)
  public List<Purchase> getAllpurchases() {
    return purchaseRepository.findAll();
  }

  /**
   * Gets purchases by id.
   *
   * @param purchaseId
   * @return purchase by id
   * @throws ResourceNotFoundException the resource not found exception
   */
  @GetMapping("/purchase/{id}")
  @ApiOperation(value = "View purchase details with id {id}", response = ResponseEntity.class)
  public ResponseEntity<Purchase> getUsersById(@PathVariable(value = "id") Long id)
      throws ResourceNotFoundException {
    Purchase purchase =
        purchaseRepository
            .findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("purchase not found on :: " + id));
    return ResponseEntity.ok().body(purchase);
  }

  /**
   * Create purchase
   *
   * @param purchase
   * @return purchase
   */
  @PostMapping("/purchases")
  @ApiOperation(value = "Create a Purchase", response = ResponseEntity.class)
  public ResponseEntity<Purchase> createPurchase(@Valid @RequestBody Purchase purchase) {
	  purchase.setCreatedBy(getUserName());
	  purchase.setUpdatedBy(getUserName());
	  final Purchase purchased = purchaseRepository.save(purchase);
	  if(purchased.getId()!= null){
		  auditSaveOperation(INSERT_ACTION, getModuleName(),STATUS_OK);
		  logger.debug("new purchase added");
		  return new ResponseEntity<Purchase>(purchased,HttpStatus.OK);
	  }else{
		  auditSaveOperation(INSERT_ACTION, getModuleName(),STATUS_ERROR);
		  logger.debug("new purchase added");
		  return new ResponseEntity<Purchase>(HttpStatus.INTERNAL_SERVER_ERROR);
	  }
  }

  /**
   * Update purchase response entity.
   *
   * @param purchaseId the purchase id
   * @param purchaseDetails the purchase details
   * @return the response entity
   * @throws ResourceNotFoundException the resource not found exception
   */
  @PutMapping("/purchases/{id}")
  @ApiOperation(value = "Update purchase details with id {id}", response = ResponseEntity.class)
  public ResponseEntity<Purchase> updatePurchase(
      @PathVariable(value = "id") Long id, @Valid @RequestBody Purchase purchaseDetails)
      throws ResourceNotFoundException {

    Purchase purchase =
        purchaseRepository
            .findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("purchase not found on :: " + id));
    if(purchase!=null){
    	purchase.setItemName(purchaseDetails.getItemName());
    	purchase.setItemClassification(purchaseDetails.getItemClassification());
    	purchase.setItemPrice(purchaseDetails.getItemPrice());
    	purchase.setItemName(purchaseDetails.getItemName());
    	purchase.setUpdatedAt(new Date());
    	purchase.setUpdatedBy(getUserName());
    	final Purchase updatedPurchase = purchaseRepository.save(purchase);
    	if(updatedPurchase.getId()!= null){
    		auditSaveOperation(UPDATE_ACTION, getModuleName(),STATUS_OK);
    		logger.debug("purchase updated");
    		return new ResponseEntity<Purchase>(updatedPurchase,HttpStatus.OK);
    	}else{
    		auditSaveOperation(UPDATE_ACTION, getModuleName(),STATUS_ERROR);
    		logger.debug("purchase updated");
    		return new ResponseEntity<Purchase>(HttpStatus.INTERNAL_SERVER_ERROR);
    	}
    }else{
    	auditSaveOperation(UPDATE_ACTION, getModuleName(),STATUS_NOTFOUND);
    	logger.debug("purchase not found");
    	return new ResponseEntity<Purchase>(HttpStatus.NOT_FOUND);
    	
    }
  }

  /**
   * Delete Purchase map.
   *
   * @param purchaseId
   * @return the map
   * @throws Exception the exception
   */
  @DeleteMapping("/purchase/{id}")
  @ApiOperation(value = "Delete a purchase with id {id}", response = Map.class)
  public Map<String, Boolean> deletePurchase(@PathVariable(value = "id") Long id) throws Exception {
	  Map<String, Boolean> response = new HashMap<>();
    Purchase purchase =
        purchaseRepository
            .findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("purchase not found on :: " + id));
    if(purchase==null){
    	auditSaveOperation(DELETE_ACTION, getModuleName(),STATUS_NOTFOUND);
    	response.put("deleted", Boolean.FALSE);
    	logger.debug("purchase not found");
    }else{
    	purchaseRepository.delete(purchase);
    	auditSaveOperation(DELETE_ACTION, getModuleName(),STATUS_OK);
    	response.put("deleted", Boolean.TRUE);
    }
    return response;
  }
}
