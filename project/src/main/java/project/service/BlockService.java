package project.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import project.dto.projection.BlockDetailProjection;
import project.dto.projection.BlockPageDto;
import project.entity.BlockEntity;
import project.entity.BlockFileEntity;

public interface BlockService {
	BlockFileEntity selectBlockFileInfo(int idx, int blockId);
	//
	BlockDetailProjection selectBlock(int blockId);
	
	BlockEntity SelectOverrideBlock(int blockId);
	// selectBlock -> blockPersist/change && searchVectorImport. Entity -> projection DTO. no file.
	// 
	Page<BlockPageDto> searchParseContents(String kw, Pageable pageable);
	
	void insertRefBlock(int blockId, int noteIdx);
}
