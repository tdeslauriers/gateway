package world.deslauriers.repository;

import io.micronaut.data.annotation.Join;
import io.micronaut.data.annotation.Repository;
import io.micronaut.data.jdbc.annotation.JdbcRepository;
import io.micronaut.data.model.query.builder.sql.Dialect;
import io.micronaut.data.repository.CrudRepository;
import world.deslauriers.model.User;

import java.util.List;
import java.util.Optional;

@Repository
@JdbcRepository(dialect = Dialect.MYSQL)
public interface UserRepository extends CrudRepository<User, Long> {

    @Join(value = "userRoles", type = Join.Type.LEFT_FETCH)
    @Join(value = "userRoles.role", type = Join.Type.LEFT_FETCH)
    List<User> findAll();

    @Join(value = "userRoles", type = Join.Type.LEFT_FETCH)
    @Join(value = "userRoles.role", type = Join.Type.LEFT_FETCH)
    Optional<User> findById(Long id);

    @Join(value = "userRoles", type = Join.Type.LEFT_FETCH)
    @Join(value = "userRoles.role", type = Join.Type.LEFT_FETCH)
    Optional<User> findByUsername(String username);

}
