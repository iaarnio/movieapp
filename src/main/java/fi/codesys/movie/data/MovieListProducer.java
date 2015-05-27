package fi.codesys.movie.data;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.event.Observes;
import javax.enterprise.event.Reception;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

import fi.codesys.movie.model.Movie;

@RequestScoped
public class MovieListProducer {

    @Inject
    private MovieRepository movieRepository;

    private List<Movie> movies;

    @Produces
    @Named
    public List<Movie> getMovies() {
        return movies;
    }

    public void onMovieListChanged(@Observes(notifyObserver = Reception.IF_EXISTS) final Movie movie) {
        retrieveAllMoviesOrderedByName();
    }

    @PostConstruct
    public void retrieveAllMoviesOrderedByName() {
        movies = movieRepository.findAllOrderedByName();
    }
}
