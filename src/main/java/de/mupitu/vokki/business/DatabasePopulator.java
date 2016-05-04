package de.mupitu.vokki.business;

import de.mupitu.vokki.business.users.entity.User;
import de.mupitu.vokki.business.words.entity.Language;
import de.mupitu.vokki.business.words.entity.Lection;
import de.mupitu.vokki.business.words.entity.Word;
import de.unidue.s3.bcrypt.BCrypt;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Singleton
@Startup
public class DatabasePopulator {
    
    Logger logger = Logger.getLogger(DatabasePopulator.class.getName());
    
    @PersistenceContext(name = "vokkiPU")
    private EntityManager em;
    
    private User userMartin;
    private User userJohn;
    
    private Lection lectionOne;
    
    private Language languageGerman;
    private Language languageEnglish;
    private Language languageItalian;
    private Language languagePolish;
    
    @PostConstruct
    public void init() {
        createLanguages();
        userMartin = createUser("martin", "martin", "info@example.com", languageGerman);
        userJohn = createUser("john", "john", "john.doe@example.com", languageEnglish);
        lectionOne = createLection("Lection I", "My first lection", languageEnglish, languageGerman, userMartin);
        createLection("Lezione Uno", "Buon giorno!", languageItalian, languageGerman, userMartin);
        createLection("Lektion Eins", "Meine erste Lektion", languageGerman, languageEnglish, userJohn);
        createLection("Lekcja Pierwsza", "Erste polnische Lektion", languagePolish, languageGerman, userMartin);
        createWord(lectionOne, "hello", null, "hallo");
        createWord(lectionOne, "play", "Theater", "Schauspiel", "Aufführung");
        createWord(lectionOne, "to open", null, "öffen");
        createWord(lectionOne, "free", null, "frei");
        createWord(lectionOne, "house", null, "Haus");
    }
    
    private void createLanguages() {
        languageGerman = createLanguage("Deutsch", "de.png");
        languageEnglish = createLanguage("Englisch", "en.png");
        languageItalian = createLanguage("Italienisch", "it.png");
        languagePolish = createLanguage("Polnisch", "pl.png");
    }
    
    private Language createLanguage(final String name, final String flagPath) {
        final Language language = new Language();
        
        language.setName(name);
        language.setFlagPath(flagPath);
        
        em.persist(language);
        
        logger.info("Language created: " + language.toString());
        
        return language;
    }
    
    private User createUser(final String login, final String password, final String emailAddress, final Language lang) {
        final User user = new User();
        
        user.setEmailAddress(emailAddress);
        user.setUsername(login);
        user.setRegisterDate(LocalDate.now());
        user.setPasswordHash(BCrypt.hash(password));
        user.setLanguage(lang);
        
        em.persist(user);
        
        logger.info("User created: " + user.toString());
        
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
        
        logger.info("Lection created: " + lection.toString());
        
        return lection;
    }
    
    private Word createWord(final Lection lection, final String foreignTerm, final String comment, final String... nativeTerms) {
        final Word word = new Word();
        
        final Set<String> terms = new HashSet<>();
        for(final String nativeTerm : nativeTerms) {
            terms.add(nativeTerm);
        }
        
        word.setLection(lection);
        word.setComment(comment);
        word.setNativeTerms(terms);
        word.setForeignTerm(foreignTerm);
        
        em.persist(word);
        
        logger.info("Word created: " + word.toString());
        
        return word;
    }
    
}
