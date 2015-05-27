package fi.codesys.movie.rest;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.NoResultException;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;
import javax.validation.Validator;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import fi.codesys.movie.data.MovieRepository;
import fi.codesys.movie.model.Movie;
import fi.codesys.movie.service.MovieRegistration;

@Path("/movies")
@RequestScoped
public class MovieResourceRESTService {

    @Inject
    private Logger log;

    @Inject
    private Validator validator;

    @Inject
    private MovieRepository repository;

    @Inject
    MovieRegistration registration;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Movie> listAllMovies() {
        return repository.findAllOrderedByName();
    }

    @GET
    @Path("/{id:[0-9][0-9]*}")
    @Produces(MediaType.APPLICATION_JSON)
    public Movie lookupMovieById(@PathParam("id") long id) {
        Movie movie = repository.findById(id);
        if (movie == null) {
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }
        return movie;
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createMovie(Movie movie) {

        Response.ResponseBuilder builder = null;

        try {
            validateMovie(movie);
            registration.register(movie);
            builder = Response.ok();
        } catch (ConstraintViolationException ce) {
            // Bean validation issues
            builder = createViolationResponse(ce.getConstraintViolations());
        } catch (ValidationException e) {
            // Unique constrain violation
            Map<String, String> responseObj = new HashMap<>();
            responseObj.put("name", "Name taken");
            builder = Response.status(Response.Status.CONFLICT).entity(responseObj);
        } catch (Exception e) {
            // Handle generic exceptions
            Map<String, String> responseObj = new HashMap<>();
            responseObj.put("error", e.getMessage());
            builder = Response.status(Response.Status.BAD_REQUEST).entity(responseObj);
        }

        return builder.build();
    }

    /**
     * Validates the given Movie and throws validation exceptions based on the type of error. If the error is standard
     * bean validation errors then it will throw a ConstraintValidationException with the set of the constraints violated.

     * If the error is caused because an existing movie with the same name is registered it throws a regular validation
     * exception so that it can be interpreted separately.
     */
    private void validateMovie(Movie movie) throws ConstraintViolationException, ValidationException {
        // Create a bean validator and check for issues.
        Set<ConstraintViolation<Movie>> violations = validator.validate(movie);

        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(new HashSet<ConstraintViolation<?>>(violations));
        }

        // Check the uniqueness of the email address
        if (nameAlreadyExists(movie.getName())) {
            throw new ValidationException("Unique Email Violation");
        }
    }

    /**
     * Creates a JAX-RS "Bad Request" response including a map of all violation fields, and their message. This can then be used
     * by clients to show violations.
     */
    private Response.ResponseBuilder createViolationResponse(Set<ConstraintViolation<?>> violations) {
        log.fine("Validation completed. violations found: " + violations.size());

        Map<String, String> responseObj = new HashMap<>();

        for (ConstraintViolation<?> violation : violations) {
            responseObj.put(violation.getPropertyPath().toString(), violation.getMessage());
        }

        return Response.status(Response.Status.BAD_REQUEST).entity(responseObj);
    }

    /**
     * Checks if a movie with the same name is already registered
     */
    public boolean nameAlreadyExists(String name) {
        Movie movie = null;
        try {
            movie = repository.findByName(name);
        } catch (NoResultException e) {
            // ignore
        }
        return movie != null;
    }
}
