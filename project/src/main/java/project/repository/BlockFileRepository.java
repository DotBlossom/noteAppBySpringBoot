package project.repository;

import org.springframework.data.repository.CrudRepository;

import project.entity.BlockFileEntity;

public interface BlockFileRepository extends CrudRepository<BlockFileEntity, Integer> {
	
}
