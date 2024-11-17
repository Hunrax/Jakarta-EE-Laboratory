package pg.eti.jee.director.service;

import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import lombok.NoArgsConstructor;
import pg.eti.jee.director.entity.Director;
import pg.eti.jee.director.repository.api.DirectorRepository;
import pg.eti.jee.user.entity.UserRoles;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@LocalBean
@Stateless
@NoArgsConstructor(force = true)
public class DirectorService {
    private final DirectorRepository repository;

    @Inject
    public DirectorService(DirectorRepository repository) {
        this.repository = repository;
    }

    public Optional<Director> find(UUID id) {
        Optional<Director> director = repository.find(id);
        return director;
    }

    @PermitAll
    public List<Director> findAll() {
        return repository.findAll();
    }

    @RolesAllowed(UserRoles.ADMIN)
    public void create(Director director) {
        repository.create(director);
    }

    @RolesAllowed(UserRoles.USER)
    public void update(Director director) {repository.update(director);};

    @RolesAllowed(UserRoles.ADMIN)
    public void delete(UUID id) {repository.delete(id);};
}
