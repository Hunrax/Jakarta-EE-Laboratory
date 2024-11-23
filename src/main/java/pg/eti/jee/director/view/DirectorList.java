package pg.eti.jee.director.view;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import pg.eti.jee.component.ModelFunctionFactory;
import pg.eti.jee.director.model.DirectorsModel;
import pg.eti.jee.director.service.DirectorService;

@RequestScoped
@Named
public class DirectorList {

    private final DirectorService service;

    private DirectorsModel directors;

    private final ModelFunctionFactory factory;

    @Inject
    public DirectorList(DirectorService service, ModelFunctionFactory factory) {
        this.service = service;
        this.factory = factory;
    }

    public DirectorsModel getDirectors() {
        if (directors == null) {
            directors = factory.directorsToModel().apply(service.findAll());
        }
        return directors;
    }

    public void deleteAction(DirectorsModel.Director director) {
        service.delete(director.getId());
        directors = null;
    }

}
