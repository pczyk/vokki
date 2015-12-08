package de.mupitu.vokki.presentation;

import de.mupitu.vokki.business.users.entity.User;
import de.mupitu.vokki.business.words.boundary.LanguageManager;
import de.mupitu.vokki.business.words.entity.Language;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

@ManagedBean
@ViewScoped
public class Register implements Serializable {

    // Injections
    @EJB
    LanguageManager languageManager;
    
    // For presentation
    private List<Language> languages;
    
    // User input
    private User newUser = new User();

    private String password;
    private String passwordRepeat;
    
    private Language language;
    
    @PostConstruct
    public void init() {
        languages = languageManager.findAll();
    }
    
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

    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }
    
    public List<Language> getLanguages() {
        return languages;
    }
    
    public void registerNewUser() {
        System.out.println("newUser.login=" + newUser.getUsername());
        System.out.println("newUser.emailAddress=" + newUser.getEmailAddress());
        System.out.println("password=" + password);
        System.out.println("passwordRepeat=" + passwordRepeat);
        System.out.println("language=" + language);
    }
    
    
}
