package project.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import project.entity.NoteEntity;

public interface NoteRepository extends JpaRepository<NoteEntity, Integer>{
	List<NoteEntity> findAllByOrderByNoteIdxDesc();
	

}
