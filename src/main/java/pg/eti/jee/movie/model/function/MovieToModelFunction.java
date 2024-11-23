package pg.eti.jee.movie.model.function;

import pg.eti.jee.director.model.DirectorModel;
import pg.eti.jee.movie.entity.Movie;
import pg.eti.jee.movie.model.MovieModel;
import pg.eti.jee.user.model.UserModel;

import java.io.Serializable;
import java.util.function.Function;

public class MovieToModelFunction implements Function<Movie, MovieModel>, Serializable {

    @Override
    public MovieModel apply(Movie entity) {
        return MovieModel.builder()
                .title(entity.getTitle())
                .releaseDate(entity.getReleaseDate())
                .runningTime(entity.getRunningTime())
                .director(DirectorModel.builder()
                        .name(entity.getDirector().getName())
                        .surname(entity.getDirector().getSurname())
                        .build())
                .user(UserModel.builder()
                        .login(entity.getUser().getLogin())
                        .build())
                .build();
    }

}
