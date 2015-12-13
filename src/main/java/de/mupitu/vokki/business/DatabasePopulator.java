package de.mupitu.vokki.business;

import de.mupitu.vokki.business.users.entity.User;
import de.mupitu.vokki.business.words.entity.Language;
import de.mupitu.vokki.business.words.entity.Lection;
import de.unidue.s3.bcrypt.BCrypt;
import java.time.LocalDate;
import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Singleton
@Startup
public class DatabasePopulator {
    
    @PersistenceContext(name = "vokkiPU")
    private EntityManager em;
    
    private User userMartin;
    
    private Language languageGerman;
    private Language languageEnglish;
    private Language languageItalian;
    private Language languagePolish;
    
    @PostConstruct
    public void init() {
        createLanguages();
        userMartin = createUser("martin", "martin", "info@example.com", languageGerman);
        createLection("Lection I", "My first lection", languageEnglish, languageGerman, userMartin);
        createLection("Lezione Uno", "Buon giorno!", languageItalian, languageGerman, userMartin);
    }
    
    private void createLanguages() {
        languageGerman = createLanguage("Deutsch", "de.png");
        languageEnglish = createLanguage("Englisch", "en.png");
        languageItalian = createLanguage("Italienisch", "de.png");
        languagePolish = createLanguage("Polnisch", "de.png");
    }
    
    private Language createLanguage(final String name, final String flagPath) {
        final Language language = new Language();
        
        language.setName(name);
        language.setFlagPath(flagPath);
        
        em.persist(language);
        
        return language;
    }
    
    private User createUser(final String login, final String password, final String emailAddress, final Language lang) {
        final User user = new User();
        
        user.setEmailAddress(emailAddress);
        user.setUsername(login);
        user.setRegisterDate(LocalDate.now());
        user.setPassword(BCrypt.hash(password));
        user.setLanguage(lang);
        
        em.persist(user);
        
        return user;
    }
    
    private Lection createLection(final String name, final String description, final Language language, final Language baseLanguage, final User user) {
        final Lection lection = new Lection();
        final LocalDate now = LocalDate.now();
        
        lection.setName(name);
        lection.setLanguage(language);
        lection.setOwner(user);
        lection.setCreationDate(now);
        lection.setModificationDate(now);
        lection.setDescription(description);
        lection.setBaseLanguage(baseLanguage);
        
        em.persist(lection);
        
        return lection;
    }
    
}
