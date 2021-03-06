package de.mupitu.vokki.presentation.session;

import de.mupitu.vokki.business.statistics.boundary.LoginActionManager;
import de.mupitu.vokki.business.statistics.entity.LoginAction;
import de.mupitu.vokki.business.users.boundary.UserManager;
import de.mupitu.vokki.business.users.entity.User;
import java.io.Serializable;
import javax.faces.application.FacesMessage;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@SessionScoped
public class UserSession implements Serializable {

    @Inject
    private UserManager userManager;

    @Inject
    private LoginActionManager loginActionManager;

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
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Unknown login", "Try again!"));

            username = null;
            password = null;
            return "";
        } else {
            currentUser = userManager.updateLastLogin(currentUser);
            loginActionManager.save(LoginAction.newInstance(currentUser));
            
            if (requestedPage != null) {
                System.out.println("requested page = " + requestedPage);
                return requestedPage;
            } else {
                System.out.println("redirect to dashboard");
                return "/secured/dashboard?faces-redirect=true";
            }
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
