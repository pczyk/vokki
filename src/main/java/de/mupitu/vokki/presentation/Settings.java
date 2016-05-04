package de.mupitu.vokki.presentation;

import de.mupitu.vokki.business.users.boundary.UserManager;
import de.mupitu.vokki.business.users.entity.User;
import de.mupitu.vokki.presentation.session.UserSession;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@ViewScoped
public class Settings extends BaseController {

    @Inject
    private UserManager userManager;

    @Inject
    private UserSession userSession;

    // Password
    private String oldPassword = "";
    private String newPassword = "";
    private String newPasswordRepeat = "";

    // actions
    public void changePassword() {
        final User user = getUser();
        final FacesContext context = FacesContext.getCurrentInstance();

        if (!userManager.checkPassword(oldPassword, user.getPassword())) {
            final String message = "Das alte Passwort stimmt nicht mit dem aktuellen Passwort überein";
            context.addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            message, message));
        } else if (!newPassword.equals(newPasswordRepeat)) {
            final String message = "Die Wiederholung des neuen Passworts stimmt nicht mit dem neuen Passwort überein";
            context.addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            message, message));
        } else {
            // okay!
            userManager.changePassword(user, newPassword);

            final String message = "Das Passwort wurde erfolgreich geändert";

            context.addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO,
                            message, message));
        }
    }

    public User getUser() {
        return userSession.getCurrentUser();
    }

    // general getter/setter
    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getNewPasswordRepeat() {
        return newPasswordRepeat;
    }

    public void setNewPasswordRepeat(String newPasswordRepeat) {
        this.newPasswordRepeat = newPasswordRepeat;
    }

}
