package pg.eti.jee.movie.view;

import jakarta.ejb.EJB;
import jakarta.ejb.EJBException;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.persistence.OptimisticLockException;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.TransactionalException;
import lombok.Getter;
import lombok.Setter;
import pg.eti.jee.component.ModelFunctionFactory;
import pg.eti.jee.movie.entity.Movie;
import pg.eti.jee.movie.model.MovieEditModel;
import pg.eti.jee.movie.service.MovieService;

import java.io.IOException;
import java.io.Serializable;
import java.util.Optional;
import java.util.UUID;

@ViewScoped
@Named
public class MovieEdit implements Serializable {

    private MovieService service;

    private final ModelFunctionFactory factory;

    @Setter
    @Getter
    private UUID id;

    @Getter
    private MovieEditModel movie;

    private final FacesContext facesContext;

    @Inject
    public MovieEdit(ModelFunctionFactory factory, FacesContext facesContext) {
        this.factory = factory;
        this.facesContext = facesContext;
    }
    @EJB
    public void setService(MovieService service) {
        this.service = service;
    }

    public void init() throws IOException {
        Optional<Movie> movie = service.findForCallerPrincipal(id);
        if (movie.isPresent()) {
            this.movie = factory.movieToEditModel().apply(movie.get());
        } else {
            FacesContext.getCurrentInstance().getExternalContext().responseSendError(HttpServletResponse.SC_NOT_FOUND, "Movie not found");
        }
    }

    public String saveAction() throws IOException {
        try {
            service.update(factory.updateMovie().apply(service.findForCallerPrincipal(id).orElseThrow(), movie));
            String viewId = FacesContext.getCurrentInstance().getViewRoot().getViewId();
            return viewId + "?faces-redirect=true&includeViewParams=true";
        } catch (EJBException ex) {
            if (ex.getCause() instanceof OptimisticLockException) {
                //init();
                facesContext.addMessage(null, new FacesMessage("Version collision."));
            }
            return null;
        }
    }

}
