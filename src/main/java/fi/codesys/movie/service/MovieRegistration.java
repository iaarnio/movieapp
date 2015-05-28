package fi.codesys.movie.service;

import fi.codesys.movie.model.Movie;

import javax.ejb.Stateless;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.logging.Logger;

@Stateless
public class MovieRegistration {

    @Inject
    private Logger log;

    @Inject
    private EntityManager em;

    @Inject
    private Event<Movie> movieEventSrc;

    public void add(Movie movie) throws Exception {
        log.info("Adding " + movie.getName());
        em.persist(movie);
        movieEventSrc.fire(movie);
    }
}
