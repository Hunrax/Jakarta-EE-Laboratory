package pg.eti.jee.director.repository.memory;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import pg.eti.jee.datastore.component.DataStore;
import pg.eti.jee.director.entity.Director;
import pg.eti.jee.director.repository.api.DirectorRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequestScoped
public class DirectorInMemoryRepository implements DirectorRepository {
    private final DataStore store;

    @Inject
    public DirectorInMemoryRepository(DataStore store) {
        this.store = store;
    }


    @Override
    public Optional<Director> find(UUID id) {
        return store.findAllDirectors().stream()
                .filter(profession -> profession.getId().equals(id))
                .findFirst();
    }

    @Override
    public List<Director> findAll() {
        return store.findAllDirectors();
    }

    @Override
    public void create(Director entity) {
        store.createDirector(entity);
    }

    @Override
    public void delete(UUID id) {
        store.deleteDirector(id);
    }

    @Override
    public void update(Director entity) {
        store.updateDirector(entity);
    }

}
