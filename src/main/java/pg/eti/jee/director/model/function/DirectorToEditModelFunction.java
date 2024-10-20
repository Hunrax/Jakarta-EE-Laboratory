package pg.eti.jee.director.model.function;



import pg.eti.jee.director.entity.Director;
import pg.eti.jee.director.model.DirectorEditModel;

import java.io.Serializable;
import java.util.function.Function;

public class DirectorToEditModelFunction implements Function<Director, DirectorEditModel>, Serializable {

    @Override
    public DirectorEditModel apply(Director entity) {
        return DirectorEditModel.builder()
                .name(entity.getName())
                .surname(entity.getSurname())
                .oscarAwards(entity.getOscarAwards())
                .birthDate(entity.getBirthDate())
                .build();
    }

}
