package project.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import project.dto.NoteJoinUserDto;
import project.entity.NoteEntity;

public interface NoteRepository extends JpaRepository<NoteEntity, Integer>{
	List<NoteEntity> findAllByOrderByNoteIdxDesc();
	
	@Query("SELECT new project.dto.NoteJoinUserDto(n.noteIdx,  n.noteContents, n.noteTitle)" +
		       "FROM NoteEntity n " +
		       "WHERE n.author.username = :username")
	List<NoteJoinUserDto> findNoteInfoProjectionByUserId(@Param("username") String username);
}
