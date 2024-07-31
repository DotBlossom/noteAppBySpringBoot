package project.repository;


import java.util.Optional;



import org.springframework.data.repository.NoRepositoryBean;




@NoRepositoryBean
public interface BaseBlockRepository<E, D> {



	<T> Optional<T> findByBlockId(D id , Class<T> type);


}
