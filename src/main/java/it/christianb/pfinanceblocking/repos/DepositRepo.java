package it.christianb.pfinanceblocking.repos;

import it.christianb.pfinanceblocking.model.Deposit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface DepositRepo extends JpaRepository<Deposit, Long> {
}
