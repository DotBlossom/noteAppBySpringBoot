package project.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import project.dto.projection.BlockDetailProjection;
import project.dto.projection.BlockPageDto;
import project.entity.BlockEntity;
import project.entity.BlockFileEntity;


//long == nullable

public interface BlockRepository extends JpaRepository<BlockEntity, Integer> {
	
	
	//file -> entityVar designated 
	@Query("SELECT file FROM BlockFileEntity file WHERE file.idx = :idx AND file.block.blockId = :blockId")
	Optional<BlockFileEntity> findBlockFile(@Param("idx") int idx, @Param("blockId") int blockId);
	
	
	// 아마.. 
	Optional<BlockDetailProjection> findByBlockId(int blockId);
	
	Page<BlockPageDto> findByParseContentsContaining(String kw, Pageable pageable);
	


}
