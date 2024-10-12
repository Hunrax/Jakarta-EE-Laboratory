package pg.eti.jee.director.dto.function;

import pg.eti.jee.director.entity.Director;
import pg.eti.jee.director.dto.GetDirectorResponse;

import java.util.function.Function;

public class DirectorToResponseFunction implements Function<Director, GetDirectorResponse> {

    @Override
    public GetDirectorResponse apply(Director entity) {
        return GetDirectorResponse.builder()
                .id(entity.getId())
                .name(entity.getName())
                .surname(entity.getSurname())
                .birthDate(entity.getBirthDate())
                .oscarAwards(entity.getOscarAwards())
                .build();
    }
}
