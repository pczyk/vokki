package de.mupitu.vokki.business;

import de.mupitu.vokki.business.users.entity.User;
import de.mupitu.vokki.business.words.entity.Language;
import de.unidue.s3.bcrypt.BCrypt;
import java.time.LocalDate;
import java.util.Arrays;
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

    private Language languageGerman;
    private Language languageEnglish;
    private Language languageItalian;
    private Language languagePolish;
    
    @PostConstruct
    public void init() {
        createLanguages();
        createUser("martin", "martin", "info@example.com", languageGerman);
        
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
        user.setLogin(login);
        user.setRegisterDate(LocalDate.now());
        user.setPassword(BCrypt.hash(password));
        user.setLanguage(lang);
        
        em.persist(user);
        
        return user;
    }
    
}
