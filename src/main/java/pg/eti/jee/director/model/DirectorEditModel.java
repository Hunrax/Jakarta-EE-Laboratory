package pg.eti.jee.director.model;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class DirectorEditModel {

    private String name;

    private String surname;

    private LocalDate birthDate;

    private int oscarAwards;
}
