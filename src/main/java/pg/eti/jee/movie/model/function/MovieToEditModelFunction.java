package pg.eti.jee.movie.model.function;


import pg.eti.jee.movie.entity.Movie;
import pg.eti.jee.movie.model.MovieEditModel;
import pg.eti.jee.user.model.function.UserToModelFunction;

import java.io.Serializable;
import java.util.function.Function;

public class MovieToEditModelFunction implements Function<Movie, MovieEditModel>, Serializable {

    private final UserToModelFunction userToModelFunction;

    public MovieToEditModelFunction(UserToModelFunction userToModelFunction) {
        this.userToModelFunction = userToModelFunction;
    }

    @Override
    public MovieEditModel apply(Movie entity) {
        return MovieEditModel.builder()
                .title(entity.getTitle())
                .runningTime(entity.getRunningTime())
                .releaseDate(entity.getReleaseDate())
                .user(userToModelFunction.apply(entity.getUser()))
                .build();
    }

}
