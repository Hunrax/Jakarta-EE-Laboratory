package pg.eti.jee.component;

import jakarta.enterprise.context.ApplicationScoped;
import pg.eti.jee.director.model.function.*;
import pg.eti.jee.movie.model.function.*;

@ApplicationScoped
public class ModelFunctionFactory {

    public MovieToModelFunction movieToModel() {
        return new MovieToModelFunction();
    }

    public MoviesToModelFunction moviesToModel() {
        return new MoviesToModelFunction();
    }

    public MovieToEditModelFunction movieToEditModel() {
        return new MovieToEditModelFunction();
    }

    public ModelToMovieFunction modelToMovie() {
        return new ModelToMovieFunction();
    }

    public UpdateMovieWithModelFunction updateMovie() {
        return new UpdateMovieWithModelFunction();
    }

    public DirectorToModelFunction directorToModel() {
        return new DirectorToModelFunction();
    }

    public DirectorsToModelFunction directorsToModel() {
        return new DirectorsToModelFunction();
    }

    public DirectorToEditModelFunction directorToEditModel() {
        return new DirectorToEditModelFunction();
    }

    public ModelToDirectorFunction modelToDirector() {
        return new ModelToDirectorFunction();
    }

    public UpdateDirectorWithModelFunction updateDirector() {
        return new UpdateDirectorWithModelFunction();
    }

}
