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
import pg.eti.jee.director.service.DirectorService;
import pg.eti.jee.movie.model.MovieModel;
import pg.eti.jee.movie.service.MovieService;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@ViewScoped
@Named
public class DirectorView implements Serializable {

    private final DirectorService service;

    private final MovieService movieService;

    private final ModelFunctionFactory factory;

    @Setter
    @Getter
    private UUID id;

    @Getter
    private DirectorModel director;

    @Getter
    private List<MovieModel> movies;

    @Inject
    public DirectorView(DirectorService service, ModelFunctionFactory factory, MovieService movieService) {
        this.service = service;
        this.factory = factory;
        this.movieService = movieService;
    }

    public void init() throws IOException {
        Optional<Director> director = service.find(id);
        if (director.isPresent()) {
            this.director = factory.directorToModel().apply(director.get());
//            this.movies = movieService.findAllByDirector(id);
//            this.movies.stream()
//                    .filter(movie -> movie.getDirector().getId().equals(id))
//                    .collect(Collectors.toList());
        } else {
            FacesContext.getCurrentInstance().getExternalContext().responseSendError(HttpServletResponse.SC_NOT_FOUND, "Director not found");
        }
    }

}
