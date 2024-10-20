package pg.eti.jee.director.model;

import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class DirectorCreateModel {

    private UUID id;

    private String name;

    private String surname;

    private LocalDate birthDate;

    private int oscarAwards;
}
