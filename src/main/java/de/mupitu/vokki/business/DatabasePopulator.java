package de.mupitu.vokki.business;

import de.mupitu.vokki.business.words.entity.Language;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Singleton
@Startup
public class DatabasePopulator {

    @PersistenceContext(name = "vokkiPU")
    private EntityManager em;
    
    @PostConstruct
    public void init() {
        createLanguages();
    }
    
    private void createLanguages() {
        createLanguage("Deutsch", "de.png");
        createLanguage("Englisch", "en.png");
        createLanguage("Italienisch", "de.png");
        createLanguage("Polnisch", "de.png");
    }
    
    private Language createLanguage(final String name, final String flagPath) {
        final Language language = new Language();
        
        language.setName(name);
        language.setFlagPath(flagPath);
        
        em.persist(language);
        
        return language;
    }
}
