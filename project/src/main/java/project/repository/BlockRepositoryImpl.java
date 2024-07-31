package project.repository;

import java.util.HashMap;
import java.util.Map;

import jakarta.persistence.EntityGraph;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import project.entity.BlockEntity;


public class BlockRepositoryImpl implements BaseBlockEntityRepository<BlockEntity, Integer>{

	@PersistenceContext
	private EntityManager em;
	
	@Override
	public BlockEntity findBlockWithGraph(Integer blockId, String graphName) {
		EntityGraph<?> entityGraph = em.getEntityGraph(graphName);
		Map<String, Object> properties = new HashMap<>();
		properties.put("jakarta.persistence.fetchgraph", entityGraph);
		BlockEntity blockEntity = em.find(BlockEntity.class, blockId, properties);
		return blockEntity;
	}

}
