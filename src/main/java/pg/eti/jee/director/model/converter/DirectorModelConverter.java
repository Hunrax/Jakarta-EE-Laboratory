package pg.eti.jee.director.model.converter;

import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.convert.Converter;
import jakarta.faces.convert.FacesConverter;
import jakarta.inject.Inject;
import pg.eti.jee.component.ModelFunctionFactory;
import pg.eti.jee.director.entity.Director;
import pg.eti.jee.director.model.DirectorModel;
import pg.eti.jee.director.service.DirectorService;

import java.util.Optional;
import java.util.UUID;

@FacesConverter(forClass = DirectorModel.class, managed = true)
public class DirectorModelConverter implements Converter<DirectorModel>{
    private final DirectorService service;

    private final ModelFunctionFactory factory;

    @Inject
    public DirectorModelConverter(DirectorService service, ModelFunctionFactory factory) {
        this.service = service;
        this.factory = factory;
    }

    @Override
    public DirectorModel getAsObject(FacesContext context, UIComponent component, String value) {
        if (value == null || value.isBlank()) {
            return null;
        }
        Optional<Director> director = service.find(UUID.fromString(value));
        return director.map(factory.directorToModel()).orElse(null);
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, DirectorModel value) {
        return value == null ? "" : value.getId().toString();
    }
}
