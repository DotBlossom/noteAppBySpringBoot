package project.repository;

public interface BaseBlockEntityRepository<E,D> {
	E findBlockWithGraph(D id, String graphName);
	
}
