package it.christianb.pfinanceblocking.repos;

import it.christianb.pfinanceblocking.model.Movement;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource
public interface MovementRepo extends PagingAndSortingRepository<Movement, Long> {

}
