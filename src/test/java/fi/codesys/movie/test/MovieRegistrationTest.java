package fi.codesys.movie.test;

import static org.junit.Assert.assertNotNull;

import java.util.logging.Logger;

import javax.inject.Inject;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import fi.codesys.movie.model.Movie;
import fi.codesys.movie.service.MovieRegistration;
import fi.codesys.movie.util.Resources;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(Arquillian.class)
public class MovieRegistrationTest {
    @Deployment
    public static Archive<?> createTestArchive() {
        return ShrinkWrap.create(WebArchive.class, "test.war")
                .addClasses(Movie.class, MovieRegistration.class, Resources.class)
                .addAsResource("META-INF/test-persistence.xml", "META-INF/persistence.xml")
                .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml")
                // Deploy our test datasource
                .addAsWebInfResource("test-ds.xml");
    }

    @Inject
    MovieRegistration movieRegistration;

    @Inject
    Logger log;

    @Test
    public void testRegister() throws Exception {
    	Movie movie = new Movie();
    	movie.setName("Jurassic Park");
    	movie.setYear(1993);
    	movie.setDirector("Steven Spielberg");
        movieRegistration.add(movie);
        assertNotNull(movie.getId());
        log.info(movie.getName() + " was persisted with id " + movie.getId());
    }

}
