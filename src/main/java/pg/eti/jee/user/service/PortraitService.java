package pg.eti.jee.user.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.servlet.ServletContext;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;
import lombok.NoArgsConstructor;
import pg.eti.jee.user.entity.User;
import pg.eti.jee.user.repository.api.UserRepository;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;
import java.util.UUID;

@ApplicationScoped
@NoArgsConstructor(force = true)
public class PortraitService {

    private final UserRepository repository;

    private final String portraitPath;

    @Inject
    public PortraitService(UserRepository repository, ServletContext servletContext) {
        this.repository = repository;
        this.portraitPath = servletContext.getInitParameter("portraitPath");
    }

    @Transactional
    public void updatePortrait(UUID id, InputStream is) {
        repository.find(id).ifPresent(user -> {
            String portraitFullPath = portraitPath + user.getId() + ".jpg";
            try {
                Files.write(Path.of(portraitFullPath), is.readAllBytes());
                user.setPortrait(portraitFullPath);
                repository.update(user);
            }
            catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public byte[] getPortrait(UUID id){
        Optional<User> u = repository.find(id);
        if(u.isPresent()){
            try{
                return Files.readAllBytes(Path.of(u.get().getPortrait()));
            }
            catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        throw new NotFoundException();
    }

    @Transactional
    public void deletePortrait(UUID id) {
        repository.find(id).ifPresent(user -> {
            try {
                Files.delete(Path.of(user.getPortrait()));
            }
            catch (IOException e) {
                throw new RuntimeException(e);
            }
            user.setPortrait(null);
            repository.update(user);
        });
    }
}
