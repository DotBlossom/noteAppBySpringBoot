package project.repository;

public interface BaseRepository<E, D> {
	E findNoteWithGraph(D id, String graphName);
	
}
