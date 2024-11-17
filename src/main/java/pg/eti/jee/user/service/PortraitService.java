package pg.eti.jee.user.service;

import jakarta.annotation.Resource;
import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.servlet.ServletContext;
import jakarta.ws.rs.NotFoundException;
import lombok.NoArgsConstructor;
import pg.eti.jee.user.entity.User;
import pg.eti.jee.user.entity.UserRoles;
import pg.eti.jee.user.repository.api.UserRepository;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;
import java.util.UUID;

@LocalBean
@Stateless
@NoArgsConstructor(force = true)
public class PortraitService {

    private final UserRepository repository;

    @Resource(name="portraitPath")
    private String portraitPath;

    @Inject
    public PortraitService(UserRepository repository) {
        this.repository = repository;
    }

    @RolesAllowed(UserRoles.USER)
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

    @PermitAll
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

    @RolesAllowed(UserRoles.ADMIN)
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
