package pg.eti.jee.director.model.function;

import lombok.SneakyThrows;
import pg.eti.jee.director.entity.Director;
import pg.eti.jee.director.model.DirectorEditModel;

import java.io.Serializable;
import java.util.function.BiFunction;

public class UpdateDirectorWithModelFunction implements BiFunction<Director, DirectorEditModel, Director>, Serializable {

    @Override
    @SneakyThrows
    public Director apply(Director entity, DirectorEditModel request) {
        return Director.builder()
                .id(entity.getId())
                .name(request.getName())
                .surname(request.getSurname())
                .oscarAwards(request.getOscarAwards())
                .birthDate(request.getBirthDate())
                .build();
    }

}
