package com.ecommerce.auditlog.ecommerceauditlog.security;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;

/**
 * 
 * @author VISHNU
 *
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	private static String REALM_NAME ="RESTFUL_REALM";
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	
	
//	@Bean
//    public PasswordEncoder passwordEncoder() {
//        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
//    }
//	
//	 @Autowired
//	    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
//	        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
//	    }
//
//	 @Bean
//	    public UserDetailsService userDetailsService() {
//
//	        User.UserBuilder users = User.withDefaultPasswordEncoder();
//	        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
//	        manager.createUser(users.username("user").password("password").roles("USER").build());
//	        manager.createUser(users.username("admin").password("password").roles("USER", "ADMIN").build());
//	        return manager;
//
//	    }

	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		//auth.userDetailsService(userDetailsService);
		auth.inMemoryAuthentication()
        .withUser("user").password("{noop}password").roles("USER");
		auth.inMemoryAuthentication().withUser("admin").password("{noop}password").roles("ADMIN");

	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception { 
	  http.csrf().disable()
	  	.authorizeRequests()
	  	.antMatchers(HttpMethod.POST, "/employee/**").hasAnyRole("ADMIN")
	  	.antMatchers(HttpMethod.PUT, "/employee/**").hasAnyRole("ADMIN")
	  	.antMatchers(HttpMethod.DELETE, "/employee/**").hasAnyRole("ADMIN")
	  	.antMatchers(HttpMethod.POST, "/api/product/**").hasAnyRole("ADMIN")
	  	.antMatchers(HttpMethod.PUT, "/api/product/**").hasAnyRole("ADMIN")
	  	.antMatchers(HttpMethod.DELETE, "/api/product/**").hasAnyRole("ADMIN")
	  	.antMatchers(HttpMethod.GET, "/api/auditlog/**").hasAnyRole("ADMIN")
	  	.antMatchers(HttpMethod.DELETE, "/api/auditlog/**").hasAnyRole("ADMIN")
	  	.anyRequest().authenticated()
		.and().httpBasic()
		.realmName(REALM_NAME).authenticationEntryPoint(getBasicAuthEntryPoint())
		.and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
	  }
	
	
	@Bean
	public BasicAuthenticationEntryPoint getBasicAuthEntryPoint(){
		BasicAuthenticationEntryPoint basicAuthEntryPoint = new BasicAuthenticationEntryPoint();
		basicAuthEntryPoint.setRealmName(REALM_NAME);
		return basicAuthEntryPoint;
	}

}
