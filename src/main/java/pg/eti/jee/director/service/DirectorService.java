package pg.eti.jee.director.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.NoArgsConstructor;
import pg.eti.jee.director.entity.Director;
import pg.eti.jee.director.repository.api.DirectorRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@ApplicationScoped
@NoArgsConstructor(force = true)
public class DirectorService {
    private final DirectorRepository repository;

    @Inject
    public DirectorService(DirectorRepository repository) {
        this.repository = repository;
    }

    public Optional<Director> find(UUID id) {
        return repository.find(id);
    }

    public List<Director> findAll() {
        return repository.findAll();
    }

    public void create(Director profession) {
        repository.create(profession);
    }

}
