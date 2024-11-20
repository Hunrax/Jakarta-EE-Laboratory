package pg.eti.jee.component;

import jakarta.enterprise.context.ApplicationScoped;
import pg.eti.jee.director.model.function.*;
import pg.eti.jee.movie.model.function.*;
import pg.eti.jee.user.model.function.UserToModelFunction;
import pg.eti.jee.user.model.function.UsersToModelFunction;

@ApplicationScoped
public class ModelFunctionFactory {

    public MovieToModelFunction movieToModel() {
        return new MovieToModelFunction();
    }

    public MoviesToModelFunction moviesToModel() {
        return new MoviesToModelFunction();
    }

    public MovieToEditModelFunction movieToEditModel() {
        return new MovieToEditModelFunction(userToModel());
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

    public UserToModelFunction userToModel() { return new UserToModelFunction(); }
    public UsersToModelFunction usersToModel() { return new UsersToModelFunction(); }
}
