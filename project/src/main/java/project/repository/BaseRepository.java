package project.repository;

public interface BaseRepository<D, T> {
	D findNoteWithGraph(T id, String graphName);
}
