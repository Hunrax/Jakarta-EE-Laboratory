package pg.eti.jee.director.dto;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class PatchDirectorRequest {

    private String name;

    private String surname;

    private LocalDate birthDate;

    private int oscarAwards;
}
