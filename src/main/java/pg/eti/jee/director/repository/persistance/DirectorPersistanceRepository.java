package pg.eti.jee.director.repository.persistance;

import jakarta.enterprise.context.RequestScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import pg.eti.jee.director.entity.Director;
import pg.eti.jee.director.repository.api.DirectorRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequestScoped
public class DirectorPersistanceRepository implements DirectorRepository {
    private EntityManager em;

    @PersistenceContext
    public void setEm(EntityManager em) {
        this.em = em;
    }

    @Override
    public Optional<Director> find(UUID id) {
        return Optional.ofNullable(em.find(Director.class, id));
    }

    @Override
    public List<Director> findAll() {
        return em.createQuery("select d from Director d", Director.class).getResultList();
    }

    @Override
    public void create(Director entity) {
        em.persist(entity);
    }

    @Override
    public void delete(UUID id) {
        em.remove(em.find(Director.class, id));
    }

    @Override
    public void update(Director entity) {
        em.merge(entity);
        em.flush();
        em.clear();
    }

}