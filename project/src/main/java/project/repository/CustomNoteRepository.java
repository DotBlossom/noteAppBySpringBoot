package project.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import project.entity.NoteEntity;

public interface CustomNoteRepository extends 
	CrudRepository<NoteEntity, Integer>, 
	BaseRepository<NoteEntity, Integer> {
	
	
	List<NoteEntity> findAllByOrderByNoteIdxDesc();
	NoteEntity findNoteWithGraph(Integer noteIdx, String graphName);
	
	
}
