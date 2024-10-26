package pg.eti.jee.director.dto.function;

import pg.eti.jee.director.dto.PutDirectorRequest;
import pg.eti.jee.director.entity.Director;

import java.util.UUID;
import java.util.function.BiFunction;

public class RequestToDirectorFunction implements BiFunction<UUID, PutDirectorRequest, Director> {

    @Override
    public Director apply(UUID id, PutDirectorRequest request) {
        return Director.builder()
                .id(id)
                .name(request.getName())
                .surname(request.getSurname())
                .birthDate(request.getBirthDate())
                .oscarAwards(request.getOscarAwards())
                .build();
    }
}
