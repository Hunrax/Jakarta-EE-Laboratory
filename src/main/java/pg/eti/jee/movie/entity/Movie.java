package pg.eti.jee.movie.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import pg.eti.jee.director.entity.Director;
import pg.eti.jee.entity.VersionAndCreationDateAuditable;
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
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "movies")
public class Movie extends VersionAndCreationDateAuditable implements Serializable {
    @Id
    private UUID id;

    private String title;

    private int runningTime;

    @ManyToOne
    @JoinColumn(name = "director")
    private Director director;

    private LocalDate releaseDate;

    @ManyToOne
    @JoinColumn(name = "user_name")
    private User user;

    @PrePersist
    @Override
    public void setStartingDateTimes() {
        super.setStartingDateTimes();
    }

    @PreUpdate
    @Override
    public void updateUpdateDateTime() {
        super.updateUpdateDateTime();
    }
}
