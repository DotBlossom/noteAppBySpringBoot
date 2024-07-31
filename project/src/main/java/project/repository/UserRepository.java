package project.repository;

import org.springframework.data.repository.CrudRepository;


import project.entity.UserEntity;


public interface UserRepository extends CrudRepository<UserEntity, Integer> {
	boolean existsByUsername(String username);
	UserEntity findByUsername(String username);
	

	

}
