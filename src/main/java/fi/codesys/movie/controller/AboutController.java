package fi.codesys.movie.controller;

import javax.enterprise.inject.Model;

@Model
public class AboutController {

    private String[] features = {
        "EJB 3.2",
        "JSF 2.2",
        "CDI 1.1",
        "Bean validation",
        "JPA 2.1",
        "JAX-RS 2.0",
        "Bootstrap 3"
    };

    private String[] tools = {
        "Wildfly 8.2",
        "Maven",
        "H2 in-memory DB"
    };

	public void init() {
    }

    public String[] getFeatures() {
        return features;
    }

    public String[] getTools() {
        return tools;
    }

}
