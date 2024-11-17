package pg.eti.jee.user.service;

import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import lombok.NoArgsConstructor;
import jakarta.security.enterprise.identitystore.Pbkdf2PasswordHash;
import pg.eti.jee.user.entity.User;
import pg.eti.jee.user.entity.UserRoles;
import pg.eti.jee.user.repository.api.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@LocalBean
@Stateless
@NoArgsConstructor(force = true)
public class UserService {
    private final UserRepository repository;

    private final Pbkdf2PasswordHash passwordHash;

    @Inject
    public UserService(UserRepository repository,
                       @SuppressWarnings("CdiInjectionPointsInspection") Pbkdf2PasswordHash passwordHash) {
        this.repository = repository;
        this.passwordHash = passwordHash;
    }

    @RolesAllowed(UserRoles.ADMIN)
    public List<User> findAll() {
        return repository.findAll();
    }

    @RolesAllowed(UserRoles.ADMIN)
    public Optional<User> find(UUID id) {
        return repository.find(id);
    }

    @RolesAllowed(UserRoles.ADMIN)
    public Optional<User> find(String login) {
        return repository.findByLogin(login);
    }

    @PermitAll
    public void create(User user) {
        user.setPassword(passwordHash.generate(user.getPassword().toCharArray()));
        repository.create(user);
    }

    @RolesAllowed(UserRoles.USER)
    public void update(User user) { repository.update(user); }

    @RolesAllowed(UserRoles.ADMIN)
    public void delete(UUID id) {
        repository.delete(id);
    }

    @PermitAll
    public boolean verify(String login, String password) {
        return find(login)
                .map(user -> passwordHash.verify(password.toCharArray(), user.getPassword()))
                .orElse(false);
    }
}
