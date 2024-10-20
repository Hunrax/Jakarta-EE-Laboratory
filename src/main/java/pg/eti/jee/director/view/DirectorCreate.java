package pg.eti.jee.director.view;

import jakarta.enterprise.context.Conversation;
import jakarta.enterprise.context.ConversationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.java.Log;
import pg.eti.jee.component.ModelFunctionFactory;
import pg.eti.jee.director.model.DirectorCreateModel;
import pg.eti.jee.director.service.DirectorService;

import java.io.Serializable;
import java.util.UUID;

@ConversationScoped
@Named
@Log
@NoArgsConstructor(force = true)
public class DirectorCreate implements Serializable {

    private final DirectorService service;

    private final ModelFunctionFactory factory;

    private final Conversation conversation;

    @Getter
    private DirectorCreateModel director;

    @Inject
    public DirectorCreate(
            DirectorService service,
            ModelFunctionFactory factory,
            Conversation conversation
    ) {
        this.factory = factory;
        this.service = service;
        this.conversation = conversation;
    }

    public void init() {
        if (conversation.isTransient()) {
            director = DirectorCreateModel.builder()
                    .id(UUID.randomUUID())
                    .build();
            conversation.begin();
        }
    }
    public Object goToBasicAction() {
        return "/director/director_create__basic.xhtml?faces-redirect=true";
    }

    public String cancelAction() {
        conversation.end();
        return "/director/director_list.xhtml?faces-redirect=true";
    }

    public String goToConfirmAction() {
        return "/director/director_create__confirm.xhtml?faces-redirect=true";
    }

    public String saveAction() {
        service.create(factory.modelToDirector().apply(director));
        conversation.end();
        return "/director/director_list.xhtml?faces-redirect=true";
    }

    public String getConversationId() {
        return conversation.getId();
    }
}
