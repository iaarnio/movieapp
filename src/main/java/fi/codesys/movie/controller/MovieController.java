package fi.codesys.movie.controller;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.enterprise.inject.Produces;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import fi.codesys.movie.model.Movie;
import fi.codesys.movie.service.MovieRegistration;

@Model
public class MovieController {

    @Inject
    private FacesContext facesContext;

    @Inject
    private MovieRegistration movieRegistration;

    @Produces
    @Named
    private Movie movie;

    @PostConstruct
    public void initMovie() {
        movie = new Movie();
    }

    public void register() throws Exception {
        try {
            movieRegistration.register(movie);
            FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_INFO, "Registered!", "Registration successful");
            facesContext.addMessage(null, m);
            initMovie();
        } catch (Exception e) {
            String errorMessage = getRootErrorMessage(e);
            FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_ERROR, errorMessage, "Registration unsuccessful");
            facesContext.addMessage(null, m);
        }
    }

    private String getRootErrorMessage(Exception e) {
        // Default to general error message that registration failed.
        String errorMessage = "Registration failed. See server log for more information";

        Throwable t = e;
        while (t != null) {
            errorMessage = t.getLocalizedMessage();
            t = t.getCause();
        }

        return errorMessage;
    }

}
