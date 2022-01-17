package org.gestion.bp.service;

import java.util.List;
import java.util.Optional;
import org.gestion.bp.dao.UserRepository;
import org.gestion.bp.dto.OrderResponse;
import org.gestion.bp.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
	@Autowired
	private UserRepository ur;
	
	 public User getLoggedUser() {

	        Authentication loggedInUser = SecurityContextHolder.getContext().getAuthentication();
	        String nom = loggedInUser.getName();

	        System.out.println("$$ nom de l'utilisateur = " + nom);
	        User user = new User() ;
	        try {
	            user = ur.findByUsername(nom).get();
	        }catch(Exception e) {
	            e.printStackTrace();
	        }

	       return user;

	    }
		
		public User insertUser(User user) {	
			return ur.save(user);
		}
		
		public List<User> findAllUsers() {	
			return ur.findAll();
		}
		
		public User UpdateHardUser(User user) {	
			return ur.save(user);
		}

		public void deleteUser(User user) {	
			ur.delete(user);
		}


	    public User findById(String username) {
			return ur.getById(username);
	    }

	    public User updateUser(User user) {
			return ur.save(user);
	    }
	    
	    //les ouvriers sauf l'admin
	   /* public List<OrderResponse> findUsers(){
	    	String nomRole="USER";
	    	return  ur.findUsers(nomRole);
	    }*/
	    
	    
	    public List<User> findUsersV2(){
	    	String nomRole="USER";
	    	return  ur.findUsersV2(nomRole);
	    }
	    
	    
	    public List<User> findAdminV2(){
	    	String nomRole="ADMIN";
	    	return  ur.findUsersV2(nomRole);
	    }
	    
//	    // un seul ouvrier
//	    public User findUser(String username) {
//	    	return ur.findUser(username);
//	    }
	    
	}

