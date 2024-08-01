package project.service;

import java.io.File;
import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;
import project.dto.projection.BlockDetailProjection;
import project.dto.projection.BlockPageDto;
import project.entity.BlockEntity;
import project.entity.BlockFileEntity;
import project.entity.NoteEntity;
import project.repository.BlockFileRepository;
import project.repository.BlockRepository;
import project.repository.CustomNoteRepository;

@Slf4j
@Service
public class BlockServiceImpl implements BlockService {
	
	
	@Value("${spring.servlet.multipart.location}")
	private String uploadDir;
	
	@Autowired
	BlockRepository blockRepository;
	@Autowired
	CustomNoteRepository cNoteRepository;	
	@Autowired
	EntityManager em;
	@Autowired
	BlockFileRepository blockFileRepository;
	
	@Override
	public BlockFileEntity selectBlockFileInfo(int idx, int blockId) throws NoSuchElementException{
		Optional<BlockFileEntity> optional = blockRepository.findBlockFile(idx ,blockId);
			if (optional.isPresent()) {
				BlockFileEntity blockFileEntity = optional.get();
				return blockFileEntity;

		} else {
			throw new NoSuchElementException("일치하는 데이터가 없음");
		
		}
	
	}
	
	@Override 
	public BlockDetailProjection selectBlock(int blockId) throws NoSuchElementException{
		Integer genericId = blockId;
		Optional<BlockDetailProjection> optional = blockRepository.findByBlockId(genericId, BlockDetailProjection.class);
	
		if (optional.isPresent()) {
			BlockDetailProjection qs = optional.get();
			return qs;
		} else {
			/*
			ErrorDto err = new ErrorDto();
			err.setErrorCode("302");
			err.setErrorMessage("일치하는 데이터가 없음");
			*/
			
			throw new NoSuchElementException("일치하는 데이터가 없음");
		}
		
	}
	

	@Override
	public Page<BlockPageDto> searchParseContents(String kw, Pageable pageable) {
		Page<BlockPageDto> qs = blockRepository.findByParseContentsContaining(kw, pageable);
		return qs;
	}
	
	@Override
	public void insertRefBlock(int WBlockId, int WNoteIdx, int hasRefCnt, BlockDetailProjection ref) {
		
		
		String tempKey = Integer.toString(WBlockId);
		String tempCnt = Integer.toString(hasRefCnt);
		String generatedKey = tempKey + tempCnt;
		
		NoteEntity temp = cNoteRepository.findNoteWithGraph(0,"note-with-blocks-and-blockFiles");
		BlockEntity res = new ModelMapper().map(ref, BlockEntity.class);
		res.setBlockId(Integer.parseInt(generatedKey));
		res.setNote(temp);
		
		// if not..
		blockRepository.updateBlockRefCnt(WBlockId, hasRefCnt + 1);
		
		blockRepository.save(res);
		
		
	}
	
	@Override
	public BlockDetailProjection selectBlockToModify(int blockId) throws NoSuchElementException{
		Integer genericId = blockId;
		Optional<BlockDetailProjection> optional = blockRepository.findByBlockId(genericId, BlockDetailProjection.class);
		
		
		if (optional.isPresent()) {
			BlockDetailProjection qs = optional.get();
			return qs;
		} else {
			throw new NoSuchElementException("일치하는 데이터가 없음");
		}
	}

	@Override
	public void deleteFile(String imageUrl) throws IOException {

		// / 까지 컷.
		String filePath = uploadDir + "/" + imageUrl; 
		boolean deleteFile = new File(filePath).delete();
	    
	    
	}


	
	
}
