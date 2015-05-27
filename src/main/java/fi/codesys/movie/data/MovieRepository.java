package fi.codesys.movie.data;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

import fi.codesys.movie.model.Movie;

@ApplicationScoped
public class MovieRepository {

    @Inject
    private EntityManager em;

    public Movie findById(Long id) {
        return em.find(Movie.class, id);
    }

    public Movie findByName(String name) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Movie> criteria = cb.createQuery(Movie.class);
        Root<Movie> movie = criteria.from(Movie.class);
        criteria.select(movie).where(cb.equal(movie.get("name"), name));
        return em.createQuery(criteria).getSingleResult();
    }

    public List<Movie> findAllOrderedByName() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Movie> criteria = cb.createQuery(Movie.class);
        Root<Movie> movie = criteria.from(Movie.class);
        criteria.select(movie).orderBy(cb.asc(movie.get("name")));
        return em.createQuery(criteria).getResultList();
    }
}
