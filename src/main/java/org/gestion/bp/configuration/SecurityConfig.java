package org.gestion.bp.configuration;

import java.io.IOException;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.gestion.bp.dao.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	@Autowired
	DataSource dataSource;
	
	@Autowired
    private UserRepository ur;
	
	
	 @Bean
	    public PasswordEncoder passwordEncoder() {
	        return new BCryptPasswordEncoder();
	    }
	 
	   @Override
	    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
	  PasswordEncoder passwordEncoder=passwordEncoder();
	    	 auth.inMemoryAuthentication()
	    	 .withUser("user").password(passwordEncoder.encode("1234")).roles("USER")
	         .and()
	         .withUser("admin").password(passwordEncoder.encode("1234")).roles("ADMIN");
	    	 
	    	 auth.jdbcAuthentication()
	     	.dataSource(dataSource)
	     	.usersByUsernameQuery("select username as principal ,password as credentials,enabled from User where username=?")
	     	.authoritiesByUsernameQuery("select username as principal , role as role from users_roles where username=?")
	     	.passwordEncoder(passwordEncoder)
	   	    .rolePrefix("ROLE_");
	   }
	    
	   
	   
	   public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {

	        Set<String> roles = AuthorityUtils.authorityListToSet(authentication.getAuthorities());

	        if (roles.contains("GMat")) {
	            httpServletResponse.sendRedirect("/gestionMateriel");
	        } else {
	            httpServletResponse.sendRedirect("/gestionArticle");
	        }
	    }
	
	   
	   
	    @Override
	    protected void configure(HttpSecurity http) throws Exception {
	        
	    	
	    	http.authorizeRequests().antMatchers("/gestionMateriel").hasRole("GMat");
	    	http.authorizeRequests().antMatchers("/gestionArticle").hasRole("GArt");
	    	//http.authorizeRequests().antMatchers("/index").hasRole("GMat");
	    	//http.authorizeRequests().antMatchers("/").hasRole("GMat");
	    	http.authorizeRequests().antMatchers("/ListArticleC").hasRole("GArt");
	    	http.authorizeRequests().antMatchers("/insertOperation").hasRole("OpMat");
	    	http.authorizeRequests().antMatchers("/insertArticleC").hasRole("GArt");
	    	http.authorizeRequests().antMatchers("/insertMateriel").hasRole("GMatt");
	    	http.authorizeRequests().antMatchers("/insertOpArticleC").hasRole("OpArt");
	    	//http.authorizeRequests().antMatchers("/ListUsers").hasRole("ADMIN");
	    	//http.authorizeRequests().antMatchers("/consulterCompte").permitAll();
	    	http.authorizeRequests().antMatchers("register").anonymous();
	    	http.exceptionHandling().accessDeniedPage("/403");
	    	http.formLogin().loginPage("/login");
	    	http.authorizeRequests().antMatchers("/")
	    	.permitAll().and().formLogin().defaultSuccessUrl("/redirect").and().logout().permitAll();
	    
	    }
}
