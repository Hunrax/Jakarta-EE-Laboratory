package pg.eti.jee.director.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
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
        Optional<Director> director = repository.find(id);
        return director;
    }

    public List<Director> findAll() {
        return repository.findAll();
    }

    @Transactional
    public void create(Director director) {
        repository.create(director);
    }

    @Transactional
    public void update(Director director) {repository.update(director);};

    @Transactional
    public void delete(UUID id) {repository.delete(id);};
}
