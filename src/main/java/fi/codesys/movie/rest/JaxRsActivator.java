package fi.codesys.movie.rest;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

/**
 * Resources are served relative to the servlet path specified in the {@link ApplicationPath} annotation.
 */
@ApplicationPath("/api")
public class JaxRsActivator extends Application {
    /* class body intentionally left blank */
}
