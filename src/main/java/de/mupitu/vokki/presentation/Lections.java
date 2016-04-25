package de.mupitu.vokki.presentation;

import de.mupitu.vokki.business.users.entity.User;
import de.mupitu.vokki.business.words.boundary.LanguageManager;
import de.mupitu.vokki.business.words.boundary.LectionManager;
import de.mupitu.vokki.business.words.entity.Language;
import de.mupitu.vokki.business.words.entity.Lection;
import de.mupitu.vokki.presentation.session.UserSession;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@ViewScoped
public class Lections extends BaseController {

    @Inject
    private LectionManager lectionManager;

    @Inject
    private LanguageManager languageManager;

    @Inject
    private UserSession userSession;

    private List<Language> languagesForUser;

    private Map<Language, List<Lection>> lections;

    private Map<Lection, Long> numberOfWordsPerLection = new HashMap<>();

    private User user;

    private String newLectionName;
    private String newLectionDescription;
    private Language newLectionLanguage;

    @PostConstruct
    public void init() {
        user = userSession.getCurrentUser();
        lections = lectionManager.getLectionsByLanguageForUser(user);
        languagesForUser = lections.keySet().stream().collect(Collectors.toList());

        lections.values().stream().forEach((ls) -> {
            ls.stream().forEach((l) -> {
                numberOfWordsPerLection.put(l, lectionManager.countWordsForLection(l));
            });
        });
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

    public long getNumberOfWordsInLection(final Lection lection) {
        return numberOfWordsPerLection.get(lection);
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
