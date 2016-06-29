package de.mupitu.vokki.presentation;

import de.mupitu.vokki.business.users.entity.User;
import de.mupitu.vokki.business.words.boundary.LanguageManager;
import de.mupitu.vokki.business.words.boundary.WordManager;
import de.mupitu.vokki.business.words.entity.Language;
import de.mupitu.vokki.business.words.entity.Word;
import de.mupitu.vokki.presentation.session.ExamSession;
import de.mupitu.vokki.presentation.session.UserSession;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@ViewScoped
public class Dashboard extends BaseController {

    @Inject
    private WordManager wordManager;

    @Inject
    private UserSession userSession;

    @Inject
    private LanguageManager languageManager;

    @Inject
    private ExamSession examSession;
    
    private List<Language> languages;

    private Map<Language, List<Word>> wordsMap;

    private User user;

    @PostConstruct
    public void init() {
        user = userSession.getCurrentUser();

        languages = Collections.unmodifiableList(languageManager.getLanguagesForUser(user));

        wordsMap = new HashMap<>();
        languages.forEach(language -> wordsMap.put(language, wordManager.getOverdueWordsForUser(user, language)));
        wordsMap = Collections.unmodifiableMap(wordsMap);
    }

    public List<Language> getLanguages() {
        return languages;
    }
    
    public int getNumberOfOverdueWordsForLanguage(final Language language) {
        return wordsMap.get(language).size();
    }
    
    public String startExamForLanguage(final Language language) {
        examSession.setUpTest(wordsMap.get(language), language, language);
        
        return "exam";
    }
}
