package pg.eti.jee.director.dto.function;

import pg.eti.jee.director.entity.Director;
import pg.eti.jee.director.dto.GetDirectorsResponse;

import java.util.List;
import java.util.function.Function;

public class DirectorsToResponseFunction implements Function<List<Director>, GetDirectorsResponse> {

   @Override
    public GetDirectorsResponse apply(List<Director> entities) {
        return GetDirectorsResponse.builder()
                .directors(entities.stream()
                        .map(director -> GetDirectorsResponse.Director.builder()
                                .id(director.getId())
                                .name(director.getName())
                                .surname(director.getSurname())
                                .build())
                        .toList())
                .build();
    }
}
