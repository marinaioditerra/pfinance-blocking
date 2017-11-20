package it.christianb.pfinanceblocking.repos;

import it.christianb.pfinanceblocking.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RestResource;

import java.util.List;
import java.util.Optional;

public interface UserRepo extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);

    @Override
    @RestResource(exported = false)
    <S extends User> List<S> save(Iterable<S> entities);

    @Override
    @RestResource(exported = false)
    <S extends User> S saveAndFlush(S entity);

    @Override
    @RestResource(exported = false)
    <S extends User> S save(S entity);
}
