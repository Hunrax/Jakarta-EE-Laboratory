package pg.eti.jee.movie.view;

import jakarta.ejb.EJB;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Getter;
import lombok.Setter;
import pg.eti.jee.component.ModelFunctionFactory;
import pg.eti.jee.movie.entity.Movie;
import pg.eti.jee.movie.model.MovieModel;
import pg.eti.jee.movie.service.MovieService;

import java.io.IOException;
import java.io.Serializable;
import java.util.Optional;
import java.util.UUID;

@ViewScoped
@Named
public class MovieView implements Serializable {

    private MovieService service;

    private final ModelFunctionFactory factory;

    @Setter
    @Getter
    private UUID id;

    @Getter
    private MovieModel movie;

    @Inject
    public MovieView(ModelFunctionFactory factory) {
        this.factory = factory;
    }
    @EJB
    public void setService(MovieService service) {
        this.service = service;
    }

    public void init() throws IOException {
        Optional<Movie> movie = service.findForCallerPrincipal(id);
        if (movie.isPresent()) {
            this.movie = factory.movieToModel().apply(movie.get());
        } else {
            FacesContext.getCurrentInstance().getExternalContext().responseSendError(HttpServletResponse.SC_NOT_FOUND, "Movie not found");
        }
    }

}
