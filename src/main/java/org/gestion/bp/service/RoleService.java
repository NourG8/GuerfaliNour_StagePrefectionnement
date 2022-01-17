package org.gestion.bp.service;

import org.gestion.bp.dao.RoleRepository;
import org.gestion.bp.entities.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleService {
	
	@Autowired
	RoleRepository roleRepository;
	
	
	public Role newRole(Role r) {
        return roleRepository.save(r);
    }

    public Role findByRoleType(String roleType) {
	    return roleRepository.findByRoleType(roleType);
    }
}