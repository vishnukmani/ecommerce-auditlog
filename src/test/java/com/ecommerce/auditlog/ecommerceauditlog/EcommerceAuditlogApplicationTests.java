package com.ecommerce.auditlog.ecommerceauditlog;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.HttpClientErrorException;

import com.ecommerce.auditlog.ecommerceauditlog.model.Product;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = EcommerceAuditlogApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class EcommerceAuditlogApplicationTests {

	@Autowired
	private TestRestTemplate restTemplate;

	@LocalServerPort
	private int port;

	private String getRootUrl() {
		return "http://localhost:" + port;
	}

	@Test
	public void contextLoads() {
	}

//	@Test
//	public void testGetAllProducts() {
//		HttpHeaders headers = new HttpHeaders();
//		HttpEntity<String> entity = new HttpEntity<String>(null, headers);
//
//		ResponseEntity<String> response = restTemplate.exchange(getRootUrl()+"/api/v1"+ "/products",
//				HttpMethod.GET, entity, String.class);
//
//		Assert.assertNotNull(response.getBody());
//	}
	
//
//	@Test
//	public void testGetProductById() {
//		Product user = restTemplate.getForObject(getRootUrl()+"/api/v1" + "/users/1", Product.class);
//		System.out.println(user.getFirstName());
//		Assert.assertNotNull(user);
//	}
//
//	@Test
//	public void testCreateProduct() {
//		Product product = new Product();
//		product.setId(1);
//		product.setItemName("Car");
//		product.setItemPrice("1200");
//		product.setItemClassification("Vehicle");
//		
//
//		ResponseEntity<Product> postResponse = restTemplate.postForEntity(getRootUrl()+"/api/v1" + "/products", product, Product.class);
//		Assert.assertNotNull(postResponse);
//		Assert.assertNotNull(postResponse.getBody());
//	}
//
//	@Test
//	public void testUpdatePost() {
//		int id = 1;
//		Product user = restTemplate.getForObject(getRootUrl()+"/api/v1" + "/users/" + id, Product.class);
//		user.setFirstName("admin1");
//		user.setLastName("admin2");
//
//		restTemplate.put(getRootUrl() + "/users/" + id, user);
//
//		Product updatedProduct = restTemplate.getForObject(getRootUrl()+"/api/v1"+ "/users/" + id, Product.class);
//		Assert.assertNotNull(updatedProduct);
//	}
//
//	@Test
//	public void testDeletePost() {
//		int id = 2;
//		Product user = restTemplate.getForObject(getRootUrl()+"/api/v1" + "/users/" + id, Product.class);
//		Assert.assertNotNull(user);
//
//		restTemplate.delete(getRootUrl()+"/api/v1" + "/users/" + id);
//
//		try {
//			user = restTemplate.getForObject(getRootUrl()+"/api/v1" + "/users/" + id, Product.class);
//		} catch (final HttpClientErrorException e) {
//			Assert.assertEquals(e.getStatusCode(), HttpStatus.NOT_FOUND);
//		}
//	}
	

	@Test
	public void testGetAllEmployee() {
		HttpHeaders headers = new HttpHeaders();
		HttpEntity<String> entity = new HttpEntity<String>(null, headers);

		ResponseEntity<String> response = restTemplate.exchange(getRootUrl(),
				HttpMethod.GET, entity, String.class);

		Assert.assertNotNull(response.getBody());
	}

}
