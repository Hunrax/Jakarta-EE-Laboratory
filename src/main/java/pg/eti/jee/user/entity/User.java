package pg.eti.jee.user.entity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class User implements Serializable {
    private UUID id;

    private String login;

    @ToString.Exclude
    private String password;

    private String name;

    private String surname;

    private LocalDate birthDate;

    private List<String> roles;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private String portrait;
}