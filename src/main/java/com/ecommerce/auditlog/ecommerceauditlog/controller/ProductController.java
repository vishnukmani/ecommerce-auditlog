package com.ecommerce.auditlog.ecommerceauditlog.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
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
			auditSaveOperation(INSERT_ACTION, getModuleName());
		}
		logger.debug("new product added");
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

		product.setItemName(productDetails.getItemName());
		product.setItemClassification(productDetails.getItemClassification());
		product.setItemPrice(productDetails.getItemPrice());
		product.setItemName(productDetails.getItemName());
		product.setUpdatedAt(new Date());
		product.setUpdatedBy(getUserName());

		final Product updatedProduct = productRepository.save(product);
		if(updatedProduct.getId()!= null){
			auditSaveOperation(UPDATE_ACTION, getModuleName());
			logger.debug("product updated");
		}
		return ResponseEntity.ok(updatedProduct);
	}

	/**
	 * Delete Product map.
	 *
	 * @param productId
	 * @return the map
	 * @throws Exception the exception
	 */
	@DeleteMapping("/product/{id}")
	public Map<String, Boolean> deleteProduct(@PathVariable(value = "id") Long id) throws Exception {
		Product product =
				productRepository
				.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("product not found on :: " + id));

		productRepository.delete(product);
		Map<String, Boolean> response = new HashMap<>();

		auditSaveOperation(DELETE_ACTION, getModuleName());
		logger.debug("product deleted");
		response.put("deleted", Boolean.TRUE);
		return response;
	}
}
