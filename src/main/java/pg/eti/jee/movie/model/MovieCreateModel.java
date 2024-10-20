package pg.eti.jee.movie.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import pg.eti.jee.director.model.DirectorModel;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class MovieCreateModel {

    private UUID id;

    private String title;

    private int runningTime;

    private DirectorModel director;

    private LocalDate releaseDate;
}
