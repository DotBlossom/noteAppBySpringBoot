package project.service;

import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import project.dto.ErrorDto;
import project.dto.projection.BlockDetailProjection;
import project.dto.projection.BlockPageDto;
import project.entity.BlockEntity;
import project.entity.BlockFileEntity;
import project.repository.BlockRepository;

@Slf4j
@Service
public class BlockServiceImpl implements BlockService {
	@Autowired
	BlockRepository blockRepository;
	
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
		Optional<BlockDetailProjection> optional = blockRepository.findByBlockId(blockId);
	
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
	public void insertRefBlock(int blockId, int noteIdx) {
		
		return ;
	}
	
	@Override 
	public BlockEntity SelectOverrideBlock(int blockId) {
		
		BlockEntity qs = new BlockEntity();
		return qs;
	}
}
	
