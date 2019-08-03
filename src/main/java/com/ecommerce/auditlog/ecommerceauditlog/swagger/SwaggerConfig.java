package com.ecommerce.auditlog.ecommerceauditlog.swagger;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
/**
 * Configuaration file for swagger-2 for api-documentation
 * @author VISHNU
 *
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {
	
	@Bean
	public Docket productApi() {
		return new Docket(DocumentationType.SWAGGER_2)
				.select().apis(RequestHandlerSelectors.basePackage("com.ecommerce.auditlog.ecommerceauditlog"))
				.paths(PathSelectors.regex("/api/.*"))
				.build();
			//	.apiInfo(metaData());
	}

	
	
//	private ApiInfo metaData() {
//		List<VendorExtension> vendorExtensions = new ArrayList<>();
//        ApiInfo apiInfo = new ApiInfo(
//                "Spring Boot REST API",
//                "Spring Boot REST API for Online Store",
//                "1.0",
//                "Terms of service",
//                new Contact("VISHNU", "https://github.com/vishnukmani/ecommerce-auditlog", "vishnukmani1994@gmail.com"),
//               "Apache License Version 2.0",
//                "https://www.apache.org/licenses/LICENSE-2.0", vendorExtensions);
//        return apiInfo;
//    }
}
