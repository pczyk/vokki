package de.mupitu.vokki.presentation;

import de.mupitu.vokki.business.users.boundary.UserManager;
import de.mupitu.vokki.business.users.entity.User;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

@ManagedBean(name = "userSession")
@SessionScoped
public class UserSession implements Serializable {
    
	private String username;
	private String password;
	private User currentUser;

	private String requestedPage;

	public String getRequestedPage() {
		return requestedPage;
	}

	public void setRequestedPage(final String requestedPage) {
		this.requestedPage = requestedPage;
	}

	@EJB
	private UserManager userManager;

	public String getUsername() {
		return username;
	}

	public void setUsername(final String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(final String password) {
		this.password = password;
	}

	public User getCurrentUser() {
		return currentUser;
	}

	public String login() {
		currentUser = userManager.checkLogin(username, password);

		if (currentUser == null) {
			// Error. User not found or wrong credentials
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Unknown login, try again"));

			username = null;
			password = null;
			return "";
		} else {
			return "/secured/dashboard?faces-redirect=true";
		}
	}

	public String logout() {
		FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
		return "/login?faces-redirect=true";
	}

	public boolean isLoggedIn() {
		return currentUser != null;
	}
}
