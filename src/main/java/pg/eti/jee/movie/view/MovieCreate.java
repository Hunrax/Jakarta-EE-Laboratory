package pg.eti.jee.movie.view;

import jakarta.enterprise.context.Conversation;
import jakarta.enterprise.context.ConversationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.java.Log;
import pg.eti.jee.component.ModelFunctionFactory;
import pg.eti.jee.director.model.DirectorModel;
import pg.eti.jee.director.service.DirectorService;
import pg.eti.jee.movie.model.MovieCreateModel;
import pg.eti.jee.movie.service.MovieService;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@ConversationScoped
@Named
@Log
@NoArgsConstructor(force = true)
public class MovieCreate implements Serializable {

    private final MovieService movieService;

    private final DirectorService directorService;

    private final ModelFunctionFactory factory;

    @Getter
    private MovieCreateModel movie;

    @Getter
    private List<DirectorModel> directors;

    private final Conversation conversation;

    @Inject
    public MovieCreate(
            MovieService movieService,
            DirectorService directorService,
            ModelFunctionFactory factory,
            Conversation conversation
    ) {
        this.movieService = movieService;
        this.factory = factory;
        this.directorService = directorService;
        this.conversation = conversation;
    }

    public void init() {
        if (conversation.isTransient()) {
            directors = directorService.findAll().stream()
                    .map(factory.directorToModel())
                    .collect(Collectors.toList());
            movie = MovieCreateModel.builder()
                    .id(UUID.randomUUID())
                    .build();
            conversation.begin();
        }
    }

    public String goToDirectorAction() {
        return "/movie/movie_create__director.xhtml?faces-redirect=true";
    }

    public Object goToBasicAction() {
        return "/movie/movie_create__basic.xhtml?faces-redirect=true";
    }

    public String cancelAction() {
        conversation.end();
        return "/movie/movie_list.xhtml?faces-redirect=true";
    }

    public String goToConfirmAction() {
        return "/movie/movie_create__confirm.xhtml?faces-redirect=true";
    }

    public String saveAction() {
        movieService.create(factory.modelToMovie().apply(movie));
        conversation.end();
        return "/movie/movie_list.xhtml?faces-redirect=true";
    }

    public String getConversationId() {
        return conversation.getId();
    }
}
