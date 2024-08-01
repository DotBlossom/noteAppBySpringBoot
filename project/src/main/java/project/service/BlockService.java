package project.service;

import java.io.IOException;
import java.util.List;

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
	BlockDetailProjection selectBlockToModify(int blockId);

	// selectBlock -> blockPersist/change && searchVectorImport. Entity -> projection DTO. no file.
	// 
	Page<BlockPageDto> searchParseContents(String kw, Pageable pageable);
	
	void insertRefBlock(int WBlockId, int WNoteIdx, int hasRefCnt, BlockDetailProjection ref);
	void deleteFile(String imageUrl) throws IOException;
	
	
}
