package de.mupitu.vokki.presentation.session;

import de.mupitu.vokki.business.words.boundary.WordManager;
import de.mupitu.vokki.business.words.entity.Language;
import java.io.Serializable;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import de.mupitu.vokki.business.words.entity.Word;
import de.mupitu.vokki.presentation.utils.PrimeFacesKeyboardUtils;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import javax.inject.Inject;

/**
 *
 * @author Martin Filipczyk
 */
@Named
@SessionScoped
public class ExamSession implements Serializable {

    public static enum ExamState {
        INACTIVE,
        READY,
        ACTIVE,
        COMPLETED;
    }
    
    private ExamState examState = ExamState.INACTIVE;

    private List<Word> words;
    private int wordIndex;
    private Language language;
    private Language baseLanguage;
    private String keyboardLayout;
    private String submittedWord;
    private Boolean lastWordResult;

    public void setUpTest(final List<Word> words, final Language language, final Language baseLanguage) {
        Objects.requireNonNull(language, "parameter 'words' must not be null");
        Objects.requireNonNull(language, "parameter 'language' must not be null");

        this.words = Collections.unmodifiableList(words);
        this.language = language;
        this.baseLanguage = baseLanguage;

        keyboardLayout = PrimeFacesKeyboardUtils.getLayoutTemplateForLanguage(language);
        lastWordResult = null;
        wordIndex = 0;
        
        examState = ExamState.READY;
    }

    public void startTest() {
        examState = ExamState.ACTIVE;
    }

    public int getNumberOfWords() {
        if (words != null) {
            return words.size();
        } else {
            return 0;
        }
    }

    public Word getCurrentWord() {
        return words.get(wordIndex);
    }

    public Language getLanguage() {
        return language;
    }

    // Exam state
    public boolean isExamInactive() {
        return examState == ExamState.INACTIVE;
    }

    public boolean isExamReady() {
        return examState == ExamState.READY;
    }

    public boolean isExamActive() {
        return examState == ExamState.ACTIVE;
    }

    public boolean isExamCompleted() {
        return examState == ExamState.COMPLETED;
    }

    public String getLanguageName() {
        return language.getName();
    }

    public String getBaseLanguageName() {
        return baseLanguage.getName();
    }

    public String getKeyboardLayout() {
        return keyboardLayout;
    }

    public String getSubmittedWord() {
        return submittedWord;
    }

    public void setSubmittedWord(String submittedWord) {
        this.submittedWord = submittedWord;
    }

    public void submitWord() {
        final Word currentWord = getCurrentWord();
        
        lastWordResult = currentWord.isCorrectForeignTerm(submittedWord);
        submittedWord = null;
        
        wordIndex++;

        if (wordIndex >= words.size()) {
            examState = ExamState.COMPLETED;
        }
    }
    
    public int getWordIndexForView() {
        return wordIndex + 1;
    }

    public Boolean getLastWordResult() {
        return lastWordResult;
    }
}
