package project.repository;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import jakarta.persistence.EntityGraph;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import project.entity.NoteEntity;

@Repository
public class CustomNoteRepositoryImpl implements BaseRepository<NoteEntity, Integer> {
	
	@PersistenceContext
	private EntityManager em;
	
	// "javax.persistence.fetchgraph"
	@Override
	public NoteEntity findNoteWithGraph(Integer noteIdx, String graphName) {
		EntityGraph<?> entityGraph = em.getEntityGraph(graphName);
		Map<String, Object> properties = new HashMap<>();
		properties.put("jakarta.persistence.fetchgraph", entityGraph);
		NoteEntity noteEntity = em.find(NoteEntity.class, noteIdx, properties);
		return noteEntity;
	}
}
