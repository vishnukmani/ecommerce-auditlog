package com.ecommerce.auditlog.ecommerceauditlog.controller;

import java.util.Date;
import java.util.List;

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
import com.ecommerce.auditlog.ecommerceauditlog.model.Product;
import com.ecommerce.auditlog.ecommerceauditlog.repositoy.ProductRepository;



/**
 * The type product controller.
 *
 * @author VISHNU
 */
@RestController
@RequestMapping("/api/product")
public class ProductController extends AuditLogSaver{

	final static Logger logger = Logger.getLogger(ProductController.class);

	@Autowired
	private ProductRepository productRepository;



	/**
	 * Get all products
	 *
	 * @return the list
	 */
	@GetMapping("/product")
	public List<Product> getAllProducts() {
		return productRepository.findAll();
	}

	/**
	 * Gets products by id.
	 *
	 * @param productId
	 * @return product by id
	 * @throws ResourceNotFoundException the resource not found exception
	 */
	@GetMapping("/product/{id}")
	public ResponseEntity<Product> getUsersById(@PathVariable(value = "id") Long id)
			throws ResourceNotFoundException {
		Product product =
				productRepository
				.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("product not found on :: " + id));
		return ResponseEntity.ok().body(product);
	}

	/**
	 * Create product
	 *
	 * @param product
	 * @return product
	 */
	@PostMapping("/products")
	public ResponseEntity<Product> createProduct(@Valid @RequestBody Product product) {
		product.setCreatedBy(getUserName());
		product.setUpdatedBy(getUserName());
		final Product updatedProduct = productRepository.save(product);
		if(updatedProduct.getId()!= null){
			auditSaveOperation(INSERT_ACTION, getModuleName(),STATUS_OK);
			logger.debug("new product added");
		}else{
			auditSaveOperation(INSERT_ACTION, getModuleName(),STATUS_ERROR);
			logger.debug("error happened while performing add operation");
		}
		return ResponseEntity.ok(updatedProduct);
	}

	/**
	 * Update product response entity.
	 *
	 * @param productId the product id
	 * @param productDetails the product details
	 * @return the response entity
	 * @throws ResourceNotFoundException the resource not found exception
	 */
	@PutMapping("/products/{id}")
	public ResponseEntity<Product> updateProduct(
			@PathVariable(value = "id") Long id, @Valid @RequestBody Product productDetails)
					throws ResourceNotFoundException {

		Product product =
				productRepository
				.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("product not found on :: " + id));
		if(product!=null){
			product.setItemName(productDetails.getItemName());
			product.setItemClassification(productDetails.getItemClassification());
			product.setItemPrice(productDetails.getItemPrice());
			product.setItemName(productDetails.getItemName());
			product.setUpdatedAt(new Date());
			product.setUpdatedBy(getUserName());
			final Product updatedProduct = productRepository.save(product);
			if(updatedProduct.getId()!= null){
				auditSaveOperation(UPDATE_ACTION, getModuleName(),STATUS_OK);
				logger.debug("product updated");
				return new ResponseEntity<Product>(updatedProduct,HttpStatus.OK);
			}else{
				auditSaveOperation(UPDATE_ACTION, getModuleName(),STATUS_ERROR);
				logger.debug("product updated");
				return new ResponseEntity<Product>(HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}else{
			auditSaveOperation(UPDATE_ACTION, getModuleName(),STATUS_NOTFOUND);
			return new ResponseEntity<Product>(HttpStatus.NOT_FOUND);
		}
	}

	/**
	 * Delete Product map.
	 *
	 * @param productId
	 * @return the map
	 * @throws Exception the exception
	 */
	@DeleteMapping("/product/{id}")
	public ResponseEntity<Void> deleteProduct(@PathVariable(value = "id") Long id) throws Exception {
		Product product =
				productRepository
				.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("product not found on :: " + id));
		if (product == null) {
			auditSaveOperation(DELETE_ACTION, getModuleName(),STATUS_NOTFOUND);
			logger.debug("could not find product"+product);
			return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
		}else{
			productRepository.delete(product);
			auditSaveOperation(DELETE_ACTION, getModuleName(),STATUS_OK);
			logger.debug("product deleted");
			return new ResponseEntity<Void>(HttpStatus.GONE);
		}
	}
}
