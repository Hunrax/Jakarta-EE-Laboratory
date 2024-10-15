package pg.eti.jee.controller.servlet;

import jakarta.inject.Inject;
import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import pg.eti.jee.director.controller.api.DirectorController;
import pg.eti.jee.movie.controller.api.MovieController;
import pg.eti.jee.user.controller.api.UserController;
import pg.eti.jee.user.dto.PatchUserRequest;
import pg.eti.jee.user.dto.PutUserRequest;

import java.io.IOException;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@WebServlet(urlPatterns = {
        ApiServlet.Paths.API + "/*"
})
@MultipartConfig(maxFileSize = 200 * 1024)
public class ApiServlet extends HttpServlet {
    private final UserController userController;

    private final MovieController movieController;

    private final DirectorController directorController;

    @Inject
    public ApiServlet(UserController userController, MovieController movieController, DirectorController directorController) {
        this.userController = userController;
        this.movieController = movieController;
        this.directorController = directorController;
    }

    public static final class Paths {
        public static final String API = "/api";
    }

    public static final class Patterns {

        private static final Pattern UUID = Pattern.compile("[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}");

        public static final Pattern USERS = Pattern.compile("/users/?");

        public static final Pattern USER = Pattern.compile("/users/(%s)".formatted(UUID.pattern()));

        public static final Pattern USER_PORTRAIT = Pattern.compile("/users/(%s)/portrait".formatted(UUID.pattern()));

        public static final Pattern DIRECTORS = Pattern.compile("/directors/?");

        public static final Pattern DIRECTOR_MOVIES = Pattern.compile("/directors/(%s)/movies".formatted(UUID.pattern()));

        public static final Pattern USER_MOVIES = Pattern.compile("/users/(%s)/movies".formatted(UUID.pattern()));

        public static final Pattern MOVIES = Pattern.compile("/movies/?");

    }
    private final Jsonb jsonb = JsonbBuilder.create();

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getMethod().equals("PATCH")) {
            doPatch(request, response);
        } else {
            super.service(request, response);
        }
    }

    @SuppressWarnings("RedundantThrows")
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = parseRequestPath(request);
        String servletPath = request.getServletPath();
        if (Paths.API.equals(servletPath)) {
            if (path.matches(Patterns.USERS.pattern())) {
                response.setContentType("application/json");
                response.getWriter().write(jsonb.toJson(userController.getUsers()));
                return;
            } else if (path.matches(Patterns.USER.pattern())) {
                response.setContentType("application/json");
                UUID uuid = extractUuid(Patterns.USER, path);
                response.getWriter().write(jsonb.toJson(userController.getUser(uuid)));
                return;
            } else if (path.matches(Patterns.USER_PORTRAIT.pattern())) {
                response.setContentType("image/jpg");
                UUID uuid = extractUuid(Patterns.USER_PORTRAIT, path);
                byte[] portrait = userController.getUserPortrait(uuid);
                response.setContentLength(portrait.length);
                response.getOutputStream().write(portrait);
                return;
            } else if (path.matches(Patterns.USER_MOVIES.pattern())) {
                response.setContentType("application/json");
                UUID uuid = extractUuid(Patterns.USER_MOVIES, path);
                response.getWriter().write(jsonb.toJson(movieController.getUserMovies(uuid)));
                return;
            } else if (path.matches(Patterns.DIRECTORS.pattern())) {
                response.setContentType("application/json");
                response.getWriter().write(jsonb.toJson(directorController.getDirectors()));
                return;
            } else if (path.matches(Patterns.DIRECTOR_MOVIES.pattern())) {
                response.setContentType("application/json");
                UUID uuid = extractUuid(Patterns.DIRECTOR_MOVIES, path);
                response.getWriter().write(jsonb.toJson(movieController.getDirectorMovies(uuid)));
                return;
            } else if (path.matches(Patterns.MOVIES.pattern())) {
                response.setContentType("application/json");
                response.getWriter().write(jsonb.toJson(movieController.getMovies()));
                return;
            }
        }
        response.sendError(HttpServletResponse.SC_BAD_REQUEST);
    }

    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = parseRequestPath(request);
        String servletPath = request.getServletPath();
        if (Paths.API.equals(servletPath)) {
            if (path.matches(Patterns.USER.pattern())) {
                UUID uuid = extractUuid(Patterns.USER, path);
                userController.createUser(uuid, jsonb.fromJson(request.getReader(), PutUserRequest.class));
                response.addHeader("Location", createUrl(request, Paths.API, "users", uuid.toString()));
                return;
            } else if (path.matches(Patterns.USER_PORTRAIT.pattern())) {
                UUID uuid = extractUuid(Patterns.USER_PORTRAIT, path);
                userController.putUserPortrait(uuid, request.getPart("portrait").getInputStream());
                return;
            }
        }
        response.sendError(HttpServletResponse.SC_BAD_REQUEST);
    }

    @SuppressWarnings("RedundantThrows")
    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = parseRequestPath(request);
        String servletPath = request.getServletPath();
        if (Paths.API.equals(servletPath)) {
            if (path.matches(Patterns.USER.pattern())) {
                UUID uuid = extractUuid(Patterns.USER, path);
                userController.deleteUser(uuid);
                return;
            } else if (path.matches(Patterns.USER_PORTRAIT.pattern())) {
                UUID uuid = extractUuid(Patterns.USER_PORTRAIT, path);
                userController.deleteUserPortrait(uuid);
                return;
            }
        }
        response.sendError(HttpServletResponse.SC_BAD_REQUEST);
    }

    @SuppressWarnings("RedundantThrows")
    protected void doPatch(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = parseRequestPath(request);
        String servletPath = request.getServletPath();
        if (Paths.API.equals(servletPath)) {
            if (path.matches(Patterns.USER.pattern())) {
                UUID uuid = extractUuid(Patterns.USER, path);
                userController.updateUser(uuid, jsonb.fromJson(request.getReader(), PatchUserRequest.class));
                return;
            } else if (path.matches(Patterns.USER_PORTRAIT.pattern())) {
                UUID uuid = extractUuid(Patterns.USER_PORTRAIT, path);
                userController.putUserPortrait(uuid, request.getPart("portrait").getInputStream());
                return;
            }
        }
        response.sendError(HttpServletResponse.SC_BAD_REQUEST);
    }

    private static UUID extractUuid(Pattern pattern, String path) {
        Matcher matcher = pattern.matcher(path);
        if (matcher.matches()) {
            return UUID.fromString(matcher.group(1));
        }
        throw new IllegalArgumentException("No UUID in path.");
    }

    private String parseRequestPath(HttpServletRequest request) {
        String path = request.getPathInfo();
        path = path != null ? path : "";
        return path;
    }
    public static String createUrl(HttpServletRequest request, String... paths) {
        StringBuilder builder = new StringBuilder();
        builder.append(request.getScheme())
                .append("://")
                .append(request.getServerName())
                .append(":")
                .append(request.getServerPort())
                .append(request.getContextPath());
        for (String path : paths) {
            builder.append("/")
                    .append(path, path.startsWith("/") ? 1 : 0, path.endsWith("/") ? path.length() - 1 : path.length());
        }
        return builder.toString();
    }
}
