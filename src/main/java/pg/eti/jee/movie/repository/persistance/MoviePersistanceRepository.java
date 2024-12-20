package pg.eti.jee.movie.repository.persistance;

import jakarta.enterprise.context.Dependent;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import pg.eti.jee.director.entity.Director;
import pg.eti.jee.movie.entity.Movie;
import pg.eti.jee.movie.entity.Movie_;
import pg.eti.jee.movie.repository.api.MovieRepository;
import pg.eti.jee.user.entity.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Dependent
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
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Movie> query = cb.createQuery(Movie.class);
        Root<Movie> root = query.from(Movie.class);
        query.select(root);
        return em.createQuery(query).getResultList();
    }

    @Override
    public void create(Movie entity) {
        em.persist(entity);
        Director director = em.find(Director.class, entity.getDirector().getId());
        em.refresh(director);
    }

    @Override
    public void delete(UUID id) {
        Movie movie = em.find(Movie.class, id);
        Director director = em.find(Director.class, movie.getDirector().getId());
        User user = em.find(User.class, movie.getUser().getId());

        em.remove(em.find(Movie.class, id));

        em.refresh(director);
        em.refresh(user);
    }

    @Override
    public void update(Movie entity) {
        em.merge(entity);
    }

    @Override
    public Optional<Movie> findByIdAndUser(UUID id, User user) {
        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Movie> query = cb.createQuery(Movie.class);
            Root<Movie> root = query.from(Movie.class);
            query.select(root)
                    .where(cb.and(
                            cb.equal(root.get(Movie_.user), user),
                            cb.equal(root.get(Movie_.id), id)
                    ));
            return Optional.of(em.createQuery(query).getSingleResult());
        } catch (NoResultException ex) {
            return Optional.empty();
        }
    }

    @Override
    public List<Movie> findAllByUser(User user) {
//        return em.createQuery("select m from Movie m where m.user = :user", Movie.class)
//                .setParameter("user", user)
//                .getResultList();
        User entity = em.find(User.class, user.getId());
        return entity.getMovies();
    }

    @Override
    public List<Movie> findAllByDirector(Director director) {
//        return em.createQuery("select m from Movie m where m.director = :director", Movie.class)
//                .setParameter("director", director)
//                .getResultList();
        Director entity = em.find(Director.class, director.getId());
        return entity.getMovies();
    }
}
