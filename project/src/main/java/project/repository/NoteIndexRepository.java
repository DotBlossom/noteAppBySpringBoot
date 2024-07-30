package project.repository;



import org.springframework.data.jpa.repository.JpaRepository;

import project.entity.NoteIndexEntity;

public interface NoteIndexRepository extends JpaRepository<NoteIndexEntity, Integer>{
	
}
