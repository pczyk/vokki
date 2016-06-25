package de.mupitu.vokki.presentation.session;

import de.mupitu.vokki.business.words.boundary.WordManager;
import de.mupitu.vokki.business.words.entity.Language;
import java.io.Serializable;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import de.mupitu.vokki.business.words.entity.Word;
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

    public void setUpTest(final List<Word> words, final Language language) {
        Objects.requireNonNull(language, "parameter 'words' must not be null");
        Objects.requireNonNull(language, "parameter 'language' must not be null");

        this.words = Collections.unmodifiableList(words);
        this.language = language;
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

    public void endTest() {
        words = null;
        wordIndex = 0;
    }

    public boolean isCompleted() {
        return words == null || wordIndex >= words.size();
    }

    public Word getNextWord() {
        final Word nextWord = words.get(wordIndex);
        wordIndex++;

        return nextWord;
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

}
