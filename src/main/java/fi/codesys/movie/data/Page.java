package fi.codesys.movie.data;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

@SessionScoped
@Named
public class Page implements Serializable {

    private String activePage;

    @PostConstruct
    public void initPage() {
    	System.out.println("initPage");
        activePage = "catalog";
    }

    public String getActivePage() {
    	System.out.println("getActivePage");
        return activePage;
    }

    public void setActivePage(String activePage) {
    	System.out.println("setActivePage: " + activePage);
        this.activePage = activePage;
    }

    public boolean isActivePage(String page) {
    	System.out.println("isActivePage");
        return activePage.equals(page);
    }
}
