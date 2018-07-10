/**
 * 
 */
package com.springboot.microservice.poll.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.springboot.microservice.poll.model.Role;
import com.springboot.microservice.poll.model.RoleName;

/**
 * @author gargv
 *
 */
@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
	public Optional<Role> findByName(RoleName roleName);
}
