package it.christianb.pfinanceblocking.repos;

import it.christianb.pfinanceblocking.model.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.Set;

public interface TagsRepo extends JpaRepository<Tag, Long> {

    Set<Tag> findByUserUsername(@Param("username") String username);

}
