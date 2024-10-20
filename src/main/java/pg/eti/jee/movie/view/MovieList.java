package pg.eti.jee.movie.view;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import pg.eti.jee.component.ModelFunctionFactory;
import pg.eti.jee.movie.model.MoviesModel;
import pg.eti.jee.movie.service.MovieService;

@RequestScoped
@Named
public class MovieList {

    private final MovieService service;

    private MoviesModel movies;

    private final ModelFunctionFactory factory;

    @Inject
    public MovieList(MovieService service, ModelFunctionFactory factory) {
        this.service = service;
        this.factory = factory;
    }

    public MoviesModel getMovies() {
        if (movies == null) {
            movies = factory.moviesToModel().apply(service.findAll());
        }
        return movies;
    }

    public String deleteAction(MoviesModel.Movie movie) {
        service.delete(movie.getId());
        return "movie_list?faces-redirect=true";
    }

}
