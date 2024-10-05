package pg.eti.jee.user.repository.api;

import pg.eti.jee.repository.api.Repository;
import pg.eti.jee.user.entity.User;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends Repository<User, UUID> {
    Optional<User> findByLogin(String login);

}
