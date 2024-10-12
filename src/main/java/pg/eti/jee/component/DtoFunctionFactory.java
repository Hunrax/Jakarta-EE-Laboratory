package pg.eti.jee.component;

import jakarta.enterprise.context.ApplicationScoped;
import pg.eti.jee.director.dto.function.DirectorToResponseFunction;
import pg.eti.jee.director.dto.function.DirectorsToResponseFunction;
import pg.eti.jee.movie.dto.function.MovieToResponseFunction;
import pg.eti.jee.movie.dto.function.MoviesToResponseFunction;
import pg.eti.jee.movie.dto.function.RequestToMovieFunction;
import pg.eti.jee.movie.dto.function.UpdateMovieWithRequestFunction;
import pg.eti.jee.user.dto.function.*;

@ApplicationScoped
public class DtoFunctionFactory {
    public RequestToUserFunction requestToUser() {
        return new RequestToUserFunction();
    }
    
    public UpdateUserWithRequestFunction updateUser() {
        return new UpdateUserWithRequestFunction();
    }
    
    public UpdateUserPasswordWithRequestFunction updateUserPassword() {
        return new UpdateUserPasswordWithRequestFunction();
    }
    
    public UsersToResponseFunction usersToResponse() {
        return new UsersToResponseFunction();
    }
    
    public UserToResponseFunction userToResponse() {
        return new UserToResponseFunction();
    }

    public MovieToResponseFunction movieToResponse() {
        return new MovieToResponseFunction();
    }

    public MoviesToResponseFunction moviesToResponse() {
        return new MoviesToResponseFunction();
    }

    public DirectorToResponseFunction directorToResponse() {
        return new DirectorToResponseFunction();
    }

    public DirectorsToResponseFunction directorsToResponse() { return new DirectorsToResponseFunction(); }

    public RequestToMovieFunction requestToMovie() {
        return new RequestToMovieFunction();
    }

    public UpdateMovieWithRequestFunction updateMovie() {
        return new UpdateMovieWithRequestFunction();
    }


}
