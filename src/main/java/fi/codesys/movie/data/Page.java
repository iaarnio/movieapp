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
    	activePage = "movies";
    }

	public String getActivePage() {
		return activePage;
	}

	public void setActivePage(String activePage) {
		this.activePage = activePage;
	}
    
	public boolean isActivePage(String page) {
		return activePage.equals(page);
	}
}
