package de.mupitu.vokki.presentation;

import de.mupitu.vokki.business.users.entity.User;
import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

@ManagedBean
@ViewScoped
public class Register implements Serializable {

    private User newUser = new User();

    private String password;
    private String passwordRepeat;
    
    public User getNewUser() {
        return newUser;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPasswordRepeat() {
        return passwordRepeat;
    }

    public void setPasswordRepeat(String passwordRepeat) {
        this.passwordRepeat = passwordRepeat;
    }
    
    public void registerNewUser() {
        
    }
}
