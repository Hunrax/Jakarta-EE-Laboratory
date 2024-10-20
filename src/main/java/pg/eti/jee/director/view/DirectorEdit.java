package pg.eti.jee.director.view;

import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Getter;
import lombok.Setter;
import pg.eti.jee.component.ModelFunctionFactory;
import pg.eti.jee.director.entity.Director;
import pg.eti.jee.director.model.DirectorEditModel;
import pg.eti.jee.director.service.DirectorService;
import pg.eti.jee.movie.entity.Movie;
import pg.eti.jee.movie.model.MovieEditModel;
import pg.eti.jee.movie.service.MovieService;

import java.io.IOException;
import java.io.Serializable;
import java.util.Optional;
import java.util.UUID;

@ViewScoped
@Named
public class DirectorEdit implements Serializable {

    private final DirectorService service;

    private final ModelFunctionFactory factory;

    @Setter
    @Getter
    private UUID id;

    @Getter
    private DirectorEditModel director;

    @Inject
    public DirectorEdit(DirectorService service, ModelFunctionFactory factory) {
        this.service = service;
        this.factory = factory;
    }

    public void init() throws IOException {
        Optional<Director> director = service.find(id);
        if (director.isPresent()) {
            this.director = factory.directorToEditModel().apply(director.get());
        } else {
            FacesContext.getCurrentInstance().getExternalContext().responseSendError(HttpServletResponse.SC_NOT_FOUND, "Director not found");
        }
    }

    public String saveAction() {
        service.update(factory.updateDirector().apply(service.find(id).orElseThrow(), director));
        String viewId = FacesContext.getCurrentInstance().getViewRoot().getViewId();
        return viewId + "?faces-redirect=true&includeViewParams=true";
    }

}
