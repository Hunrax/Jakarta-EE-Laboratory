package pg.eti.jee.movie.view;

import jakarta.ejb.EJB;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import pg.eti.jee.component.ModelFunctionFactory;
import pg.eti.jee.movie.model.MoviesModel;
import pg.eti.jee.movie.service.MovieService;

@RequestScoped
@Named
public class MovieList {

    private MovieService service;

    private MoviesModel movies;

    private final ModelFunctionFactory factory;

    @Inject
    public MovieList(ModelFunctionFactory factory) {
        this.factory = factory;
    }

    @EJB
    public void setService(MovieService service) {
        this.service = service;
    }

    public MoviesModel getMovies() {
        if (movies == null) {
            movies = factory.moviesToModel().apply(service.findAllForCallerPrincipal());
        }
        return movies;
    }

    public String deleteAction(MoviesModel.Movie movie) {
        service.delete(movie.getId());
        return "movie_list?faces-redirect=true";
    }

}
