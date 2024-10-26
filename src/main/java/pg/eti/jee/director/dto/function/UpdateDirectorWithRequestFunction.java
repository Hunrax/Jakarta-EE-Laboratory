package pg.eti.jee.director.dto.function;

import pg.eti.jee.director.dto.PatchDirectorRequest;
import pg.eti.jee.director.entity.Director;
import pg.eti.jee.movie.entity.Movie;

import java.util.function.BiFunction;

public class UpdateDirectorWithRequestFunction implements BiFunction<Director, PatchDirectorRequest, Director> {

    @Override
    public Director apply(Director entity, PatchDirectorRequest request) {
        return Director.builder()
                .id(entity.getId())
                .name(request.getName())
                .surname(request.getSurname())
                .birthDate(request.getBirthDate())
                .oscarAwards(request.getOscarAwards())
                .build();
    }
}
