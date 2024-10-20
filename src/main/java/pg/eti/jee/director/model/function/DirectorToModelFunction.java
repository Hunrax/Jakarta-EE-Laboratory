package pg.eti.jee.director.model.function;

import pg.eti.jee.director.entity.Director;
import pg.eti.jee.director.model.DirectorModel;

import java.io.Serializable;
import java.util.function.Function;

public class DirectorToModelFunction implements Function<Director, DirectorModel>, Serializable {

    @Override
    public DirectorModel apply(Director entity) {
        return DirectorModel.builder()
                .id(entity.getId())
                .name(entity.getName())
                .surname(entity.getSurname())
                .birthDate(entity.getBirthDate())
                .oscarAwards(entity.getOscarAwards())
                .build();
    }

}
