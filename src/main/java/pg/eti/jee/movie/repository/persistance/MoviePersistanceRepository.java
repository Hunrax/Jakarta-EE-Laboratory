package pg.eti.jee.movie.repository.persistance;

import jakarta.enterprise.context.RequestScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import pg.eti.jee.director.entity.Director;
import pg.eti.jee.movie.entity.Movie;
import pg.eti.jee.movie.repository.api.MovieRepository;
import pg.eti.jee.user.entity.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequestScoped
public class MoviePersistanceRepository implements MovieRepository {

    private EntityManager em;

    @PersistenceContext
    public void setEm(EntityManager em) {
        this.em = em;
    }

    @Override
    public Optional<Movie> find(UUID id) {
        return Optional.ofNullable(em.find(Movie.class, id));
    }

    @Override
    public List<Movie> findAll() {
        return em.createQuery("select m from Movie m", Movie.class).getResultList();
    }

    @Override
    public void create(Movie entity) {
        em.persist(entity);
    }

    @Override
    public void delete(UUID id) {
        em.remove(em.find(Movie.class, id));
    }

    @Override
    public void update(Movie entity) {
        em.merge(entity);
    }

    @Override
    public Optional<Movie> findByIdAndUser(UUID id, User user) {
        try {
            return Optional.of(em.createQuery("select m from Movie m where m.id = :id and m.user = :user", Movie.class)
                    .setParameter("user", user)
                    .setParameter("id", id)
                    .getSingleResult());
        } catch (NoResultException ex) {
            return Optional.empty();
        }
    }

    @Override
    public List<Movie> findAllByUser(User user) {
        return em.createQuery("select m from Movie m where m.user = :user", Movie.class)
                .setParameter("user", user)
                .getResultList();
    }

    @Override
    public List<Movie> findAllByDirector(Director director) {
        return em.createQuery("select m from Movie m where m.director = :director", Movie.class)
                .setParameter("director", director)
                .getResultList();
    }
}
