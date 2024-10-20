package pg.eti.jee.director.model.function;

import lombok.SneakyThrows;
import pg.eti.jee.director.entity.Director;
import pg.eti.jee.director.model.DirectorCreateModel;

import java.io.Serializable;
import java.util.function.Function;

public class ModelToDirectorFunction implements Function<DirectorCreateModel, Director>, Serializable {

    @Override
    @SneakyThrows
    public Director apply(DirectorCreateModel model) {
        return Director.builder()
                .id(model.getId())
                .name(model.getName())
                .surname(model.getSurname())
                .birthDate(model.getBirthDate())
                .oscarAwards(model.getOscarAwards())
                .build();
    }
}
