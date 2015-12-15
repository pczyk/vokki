package de.mupitu.vokki.presentation;

import de.mupitu.vokki.business.users.entity.User;
import de.mupitu.vokki.business.words.boundary.LanguageManager;
import de.mupitu.vokki.business.words.boundary.LectionManager;
import de.mupitu.vokki.business.words.entity.Language;
import de.mupitu.vokki.business.words.entity.Lection;
import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

@ManagedBean
@ViewScoped
public class Lections implements Serializable {

    @EJB
    private LectionManager lectionManager;

    @EJB
    private LanguageManager languageManager;

    @ManagedProperty(value = "#{userSession}")
    private UserSession userSession;

    private List<Language> languagesForUser;

    private Map<Language, List<Lection>> lections;

    private Map<Lection, Long> wordCount = new HashMap<>();

    private User user;

    private String newLectionName;
    private String newLectionDescription;
    private Language newLectionLanguage;

    @PostConstruct
    public void init() {
        user = userSession.getCurrentUser();
        lections = lectionManager.getLectionsByLanguageForUser(user);
        languagesForUser = lections.keySet().stream().collect(Collectors.toList());
    }

    public List<Language> getLanguagesForUser() {
        return languagesForUser;
    }

    public List<Lection> getLectionsByLanguage(final Language language) {
        if (lections.containsKey(language)) {
            return lections.get(language);
        } else {
            return Collections.EMPTY_LIST;
        }
    }

    public UserSession getUserSession() {
        return userSession;
    }

    public void setUserSession(UserSession userSession) {
        this.userSession = userSession;
    }

    public List<Language> getLanguages() {
        return languageManager.findAll();
    }

    public void createLection() {
        lectionManager.createLection(newLectionName, newLectionLanguage, newLectionDescription, user);

        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO,
                        String.format("Lektion '%s' wurde angelegt.", newLectionName),
                        String.format("Die Lektion '%s' wurde erfolgreich angelegt.", newLectionName)));
    }

    public String getNewLectionName() {
        return newLectionName;
    }

    public void setNewLectionName(String newLectionName) {
        this.newLectionName = newLectionName;
    }

    public String getNewLectionDescription() {
        return newLectionDescription;
    }

    public void setNewLectionDescription(String newLectionDescription) {
        this.newLectionDescription = newLectionDescription;
    }

    public Language getNewLectionLanguage() {
        return newLectionLanguage;
    }

    public void setNewLectionLanguage(Language newLectionLanguage) {
        this.newLectionLanguage = newLectionLanguage;
    }

}
