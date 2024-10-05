package pg.eti.jee.movie.entity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import pg.eti.jee.user.entity.User;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString(callSuper = true)
@EqualsAndHashCode()
public class Movie implements Serializable {

    private UUID id;

    private String title;

    private int runningTime;

    private Director director;

    private LocalDate birthDate;

    private User user;
}
