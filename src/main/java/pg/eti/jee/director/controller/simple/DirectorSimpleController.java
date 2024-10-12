package pg.eti.jee.director.controller.simple;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import pg.eti.jee.component.DtoFunctionFactory;
import pg.eti.jee.director.controller.api.DirectorController;
import pg.eti.jee.director.dto.GetDirectorsResponse;
import pg.eti.jee.director.service.DirectorService;

@RequestScoped
public class DirectorSimpleController implements DirectorController {

    private final DirectorService service;

    private final DtoFunctionFactory factory;

    @Inject
    public DirectorSimpleController(DirectorService service, DtoFunctionFactory factory) {
        this.service = service;
        this.factory = factory;
    }

    @Override
    public GetDirectorsResponse getDirectors() {
        return factory.directorsToResponse().apply(service.findAll());
    }
}
