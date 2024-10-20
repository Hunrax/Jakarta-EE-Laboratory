package pg.eti.jee.director.model.function;

import pg.eti.jee.director.entity.Director;
import pg.eti.jee.director.model.DirectorsModel;

import java.util.List;
import java.util.function.Function;

public class DirectorsToModelFunction implements Function<List<Director>, DirectorsModel> {

    @Override
    public DirectorsModel apply(List<Director> entity) {
        return DirectorsModel.builder()
                .directors(entity.stream()
                        .map(director -> DirectorsModel.Director.builder()
                                .id(director.getId())
                                .name(director.getName())
                                .surname(director.getSurname())
                                .build())
                        .toList())
                .build();
    }

}
