package de.mupitu.vokki.presentation;

import de.mupitu.vokki.business.users.entity.User;
import de.mupitu.vokki.business.words.boundary.LectionManager;
import de.mupitu.vokki.business.words.entity.Language;
import de.mupitu.vokki.business.words.entity.Lection;
import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

@ManagedBean
@ViewScoped
public class Lections implements Serializable {

    @EJB
    private LectionManager lectionManager;
    
    @EJB
    private UserSession userSession;
    
    private List<Language> languages;
    
    private Map<Language, List<Lection>> lections;
    
    private User user;
    
    @PostConstruct
    public void init() {
        user = userSession.getCurrentUser();
        lections = lectionManager.getLectionsByLanguageForUser(user);
        languages = lections.keySet().stream().collect(Collectors.toList());
    }
    
    public List<Language> getLanguages() {
        return languages;
    }
    
    public List<Lection> getLectionByLanguage(final Language language) {
        if(lections.containsKey(language)) {
            return lections.get(language);
        } else {
            return Collections.EMPTY_LIST;
        }
    }
}
