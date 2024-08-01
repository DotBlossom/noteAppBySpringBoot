package project.service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import lombok.extern.slf4j.Slf4j;
import project.common.FileUtils;
import project.dto.NoteJoinUserDto;
import project.dto.updater.NotePropsUpdate;
import project.entity.BlockEntity;
import project.entity.BlockFileEntity;
import project.entity.NoteEntity;
import project.entity.NoteIndexEntity;
import project.repository.CustomNoteRepository;
import project.repository.NoteRepository;
import project.repository.UserRepository;

@Slf4j
@Service
public class NoteServiceImpl implements NoteService {

	@Autowired
	private CustomNoteRepository customNoteRepository;
	
	@Autowired 
	private UserRepository userRepository;
	
	@Autowired
	private FileUtils fileUtils;
	
	@Autowired
	private NoteRepository noteRepository;


	

	@Override
	public List<NoteEntity> selectNoteList() {
		return customNoteRepository.findAllByOrderByNoteIdxDesc();

	}
	
	
	@Override
	public List<NoteJoinUserDto> selectAllNoteInfoProjectionByUserId(String username) {
		return noteRepository.findNoteInfoProjectionByUserId(username);
	}
	
	
	public int generateUniquePK; 
	private String tempKey;
	private int noteId;

	//Queryset.. and pk mapper. setPk(params) , insert는 여기서 save로 override 가능.
	
	@Override
	public void insertNote(List<BlockEntity> blocks, NoteEntity note, 
			MultipartFile[] files, String authorName) 
		throws Exception {
		
		noteId = note.getNoteIdx();
		tempKey = String.valueOf(noteId);
		
		//valid User.
		if (userRepository.existsByUsername(authorName)) {
			
			note.setAuthor(userRepository.findByUsername(authorName));			
			
		} else {
			throw new Exception("Bad Request2");
		}
		
		// -------------------block-file-mapping-Init ----------------------
		
	    List<BlockFileEntity> fileInfoList = new ArrayList<>();
	    
	    if (files != null) { 
	    	fileInfoList = fileUtils.parseFileInfo(files);
	    	
	    }
	    
	    int filesPointer = 0;
	    
	    // -------------------blockIndexInit ----------------------
	    
	    List<NoteIndexEntity> noteIndexEntityList = new ArrayList<>();
	    int cnt = 0;
	    boolean prevSwitch = false;
	    int rootController = 0; 
	    //blockMapper 과정에서, tag is h? class -> 여기서 따로 entitySetter. -> 만약 이렇게 관리 안하면, 업데이트 일어나면 또 해야뎀.
	    // setEntity로 데이터 꺼내서 저장, one api call. no additional.
	    
	    // -------------------init-End ----------------------
	    
	    for (BlockEntity block : blocks) {
	    		String res;
	    		
	    		// -------------------key sorted cnt generate ----------------------	
	    		block.setNoteIdxForQ(noteId);
	    		block.setNote(note);
	            res = tempKey + block.getCountLine();
	          
	            generateUniquePK = Integer.parseInt(res);
	            block.setBlockId(generateUniquePK);
	            //blockID = 10001 ,100001 : 순서 유지 및 유일성 존재.
	            
	            
	        	// -------------------noteIndexRenderer setting ----------------------
	           
	            String discriminator = block.getTag();
	            String sq = block.getParseContents();
	            int blockId = block.getBlockId();
	            if (discriminator  == "h1" || discriminator  == "h2" || discriminator  == "h3") {
	            	NoteIndexEntity noteIndexEntity = new NoteIndexEntity();
	            	noteIndexEntity.setParseContents(sq);
	            	
	            	
	            	
	            	if (discriminator == "h1" && prevSwitch == false) {
	            		noteIndexEntity.setRootBlockRocation(cnt);
	            		rootController = cnt;
	            		prevSwitch = true;
	            		// 이후에 , h1 이면서 true가 올때면, 새로운 root가 생기는 것.
	            	}
	            	
	            	// context switching occured
	            	if (discriminator == "h1" && prevSwitch == true) {
	            		noteIndexEntity.setRootBlockRocation(cnt);
	            		rootController = cnt;
	            		prevSwitch = false;
	            	}
	            		
	            	noteIndexEntity.setRootBlockRocation(rootController);
	            	noteIndexEntity.setBlockIdOfIndex(blockId);
	            	noteIndexEntity.setIndexBlockNoteIdx(noteId);
	            	noteIndexEntityList.add(noteIndexEntity);
	            	
	            	cnt ++;
	            	
	            }
	            
	            // -------------------fileMapping setting ----------------------
	            if (block.getFileNum() != 0) {
	            	List<BlockFileEntity> entityList = new ArrayList<>();	            	
	            	
	            	for (int i = 0; i < block.getFileNum(); i ++) {
	            		BlockFileEntity entity = fileInfoList.get(filesPointer);
	            		entity.setBlock(block);
	            		
	            		entityList.add(entity);
	            		filesPointer ++;
	            		}
	            	
	            	block.setBlockFiles(entityList);
	            	
	            }
	    }
		note.setIndexes(noteIndexEntityList);
	    note.setBlocks(blocks);
	    customNoteRepository.save(note);	
	}
	
	
	@Override
	public NoteEntity selectNoteDetailWithGraph(Integer noteIdx, String relation) throws NoSuchElementException{
		NoteEntity noteEntity = customNoteRepository.findNoteWithGraph(noteIdx, relation);
		if (noteEntity != null) {
			return noteEntity;
		} else {
			throw new NoSuchElementException("일치하는 데이터가 없음");
		}
	}

	@Override
	public NoteEntity selectNoteByEntity(int noteIdx) {
		
		return noteRepository.findByNoteIdx(noteIdx);
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void removeTransactionContext() {
		
	}

	
	@Override
	public void updateNote(List<BlockEntity> blocks, NotePropsUpdate noteProps, MultipartFile[] files, int noteIdx) throws Exception {
		
		NoteEntity qs = noteRepository.findByNoteIdx(noteIdx);

		if (qs == null) throw new Exception("Bad Request2");
		

		qs.setNoteTitle(noteProps.getNoteTitle());
		qs.setNoteContents(noteProps.getNoteContents());
		qs.setUpdated(true);
		
		// List<BlockEntity> prev = qs.getBlocks();
		
		
		
		
		
		List<BlockFileEntity> fileInfoList = new ArrayList<>();
		    
		    if (files != null) { 
		    	fileInfoList = fileUtils.parseFileInfo(files);
		    	
		    }
		    
		    int filesPointer = 0;
		    
		    // -------------------blockIndexInit ----------------------
		    
		    List<NoteIndexEntity> noteIndexEntityList = new ArrayList<>();
		    int cnt = 0;
		    boolean prevSwitch = false;
		    int rootController = 0; 
		    
		    tempKey = String.valueOf(noteIdx);
		    //blockMapper 과정에서, tag is h? class -> 여기서 따로 entitySetter. -> 만약 이렇게 관리 안하면, 업데이트 일어나면 또 해야뎀.
		    // setEntity로 데이터 꺼내서 저장, one api call. no additional.
		    
		    // -------------------init-End ----------------------
		    
		    
		    qs.getBlocks().clear();
		    
		    
		    
		    for (BlockEntity block : blocks) {
		    		String res;

		            res = tempKey + block.getCountLine();
			          
		            generateUniquePK = Integer.parseInt(res);
		            block.setBlockId(generateUniquePK);
		    		
		    		
		    		
		    		// -------------------key sorted cnt generate ----------------------	
		    		block.setNoteIdxForQ(noteIdx);
		    		block.setNote(qs);
		    		
		    		
		    		
		    		
		            //blockID = 10001 ,100001 : 순서 유지 및 유일성 존재.
		            
		            
		        	// -------------------noteIndexRenderer setting ----------------------
		           
		            String discriminator = block.getTag();
		            String sq = block.getParseContents();
		            int blockId = block.getBlockId();
		            if (discriminator  == "h1" || discriminator  == "h2" || discriminator  == "h3") {
		            	NoteIndexEntity noteIndexEntity = new NoteIndexEntity();
		            	noteIndexEntity.setParseContents(sq);
		            	
		            	
		            	
		            	if (discriminator == "h1" && prevSwitch == false) {
		            		noteIndexEntity.setRootBlockRocation(cnt);
		            		rootController = cnt;
		            		prevSwitch = true;
		            		// 이후에 , h1 이면서 true가 올때면, 새로운 root가 생기는 것.
		            	}
		            	
		            	// context switching occured
		            	if (discriminator == "h1" && prevSwitch == true) {
		            		noteIndexEntity.setRootBlockRocation(cnt);
		            		rootController = cnt;
		            		prevSwitch = false;
		            	}
		            		
		            	noteIndexEntity.setRootBlockRocation(rootController);
		            	noteIndexEntity.setBlockIdOfIndex(blockId);
		            	noteIndexEntity.setIndexBlockNoteIdx(noteIdx);
		            	noteIndexEntityList.add(noteIndexEntity);
		            	
		            	cnt ++;
		            	
		            }
		            
		            // -------------------fileMapping setting ----------------------
		            if (block.getFileNum() != 0) {
		            	List<BlockFileEntity> entityList = new ArrayList<>();	            	
		            	
		            	for (int i = 0; i < block.getFileNum(); i ++) {
		            		BlockFileEntity entity = fileInfoList.get(filesPointer);
		            		entity.setBlock(block);
		            		
		            		entityList.add(entity);
		            		filesPointer ++;
		            		}
		            	
		            	block.setBlockFiles(entityList);
		            	
		            }
		            
		            qs.getBlocks().add(block);
		    }
		    
		    
			
		    
		    noteRepository.save(qs);
		
	}



}



