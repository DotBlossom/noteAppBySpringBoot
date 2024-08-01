package project.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import project.dto.NoteJoinUserDto;
import project.dto.updater.NotePropsUpdate;
import project.entity.BlockEntity;
import project.entity.NoteEntity;

public interface NoteService {
	List<NoteEntity> selectNoteList();
	void insertNote(List<BlockEntity> blocks, NoteEntity note, MultipartFile[] files, String authorName) throws Exception;
	NoteEntity selectNoteDetailWithGraph(Integer noteIdx, String relation); 
	
	NoteEntity selectNoteByEntity(int noteIdx);
	List<NoteJoinUserDto> selectAllNoteInfoProjectionByUserId(String username);
	void updateNote(List<BlockEntity> blocks, NotePropsUpdate noteProps, MultipartFile[] files, int noteIdx) throws Exception;
	
}
	