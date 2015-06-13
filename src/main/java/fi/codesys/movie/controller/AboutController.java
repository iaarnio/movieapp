package fi.codesys.movie.controller;

import java.io.IOException;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import fi.codesys.movie.data.Page;

@Model
public class AboutController {

    @Inject
    private FacesContext facesContext;

    @Inject
    private Page page;

    @Inject
    private Logger log;

    private String[] features = {
        "EJB 3.2",
        "JSF 2.2",
        "CDI 1.1",
        "Bean validation",
        "JPA 2.1",
        "JAX-RS 2.0"
    };

    private String[] tools = {
        "Wildfly 8.2",
        "Maven",
        "H2 in-memory DB"
    };

    public void foo() {
    	log.info("AboutController.foo");
    	init();
    }

    @PostConstruct
	public void init() {
		log.info("AboutController.init");
		setPage();
    }

    public void setPage() {
        page.setActivePage("technology");
    }

    public String[] getFeatures() {
        return features;
    }

    public String[] getTools() {
        return tools;
    }

}
