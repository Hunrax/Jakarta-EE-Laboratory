package pg.eti.jee.user.service;

import pg.eti.jee.controller.servlet.exception.NotFoundException;
import pg.eti.jee.crypto.component.Pbkdf2PasswordHash;
import pg.eti.jee.user.entity.User;
import pg.eti.jee.user.repository.api.UserRepository;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class UserService {
    private final UserRepository repository;

    private final Pbkdf2PasswordHash passwordHash;

    private final String portraitPath;

    public UserService(UserRepository repository, Pbkdf2PasswordHash passwordHash, String portraitPath) {
        this.repository = repository;
        this.passwordHash = passwordHash;
        this.portraitPath = portraitPath;
    }

    public Optional<User> find(UUID id) {
        return repository.find(id);
    }

    public Optional<User> find(String login) {
        return repository.findByLogin(login);
    }

    public List<User> findAll() {
        return repository.findAll();
    }

    public void create(User user) {
        user.setPassword(passwordHash.generate(user.getPassword().toCharArray()));
        repository.create(user);
    }

    public void create(UUID id) { repository.delete(id); }

    public void update(User user) { repository.update(user); }

    public void delete(UUID id) { repository.delete(id); }

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

    public boolean verify(String login, String password) {
        return find(login)
                .map(user -> passwordHash.verify(password.toCharArray(), user.getPassword()))
                .orElse(false);
    }
}
