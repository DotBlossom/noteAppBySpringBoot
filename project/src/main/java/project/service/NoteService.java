package project.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;


import project.entity.BlockEntity;
import project.entity.NoteEntity;

public interface NoteService {
	List<NoteEntity> selectNoteList();
	void insertNote(List<BlockEntity> blocks, NoteEntity note, MultipartFile[] files, String authorName) throws Exception;
	NoteEntity selectNoteDetailWithGraph(Integer noteIdx, String relation); 
}
