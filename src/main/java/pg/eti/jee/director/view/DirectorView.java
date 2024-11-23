package pg.eti.jee.director.view;

import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Getter;
import lombok.Setter;
import pg.eti.jee.component.ModelFunctionFactory;
import pg.eti.jee.director.entity.Director;
import pg.eti.jee.director.model.DirectorModel;
import pg.eti.jee.director.model.DirectorsModel;
import pg.eti.jee.director.service.DirectorService;
import pg.eti.jee.movie.model.MoviesModel;
import pg.eti.jee.movie.service.MovieService;

import java.io.IOException;
import java.io.Serializable;
import java.util.Optional;
import java.util.UUID;

@ViewScoped
@Named
public class DirectorView implements Serializable {

    private final DirectorService directorService;

    private final MovieService movieService;

    private final ModelFunctionFactory factory;

    @Setter
    @Getter
    private UUID id;

    @Getter
    private DirectorModel director;

    @Getter
    private MoviesModel movies;

    @Inject
    public DirectorView(DirectorService directorService, MovieService movieService, ModelFunctionFactory factory) {
        this.directorService = directorService;
        this.movieService = movieService;
        this.factory = factory;
    }

    public void init() throws IOException {
        Optional<Director> director = directorService.find(id);
        if (director.isPresent()) {
            this.director = factory.directorToModel().apply(director.get());
            this.movies = factory.moviesToModel().apply(movieService.findAllForCallerPrincipal().stream()
                    .filter(movie -> movie.getDirector() != null && movie.getDirector().getId().equals(id))
                    .toList());
        } else {
            FacesContext.getCurrentInstance().getExternalContext().responseSendError(HttpServletResponse.SC_NOT_FOUND, "Director not found");
        }
    }

    public String deleteDirectorsMovieAction(MoviesModel.Movie movie) {
        movieService.delete(movie.getId());
        return "director_list?faces-redirect=true";
    }

}
