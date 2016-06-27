package de.mupitu.vokki.business.exams.boundary;

import de.mupitu.vokki.business.statistics.boundary.ExamActionManager;
import de.mupitu.vokki.business.statistics.entity.ExamAction;
import de.mupitu.vokki.business.users.entity.User;
import de.mupitu.vokki.business.words.boundary.WordManager;
import de.mupitu.vokki.business.words.entity.Language;
import de.mupitu.vokki.business.words.entity.Word;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import javax.ejb.Stateless;
import javax.inject.Inject;

@Stateless
public class ExamsManager {

    @Inject
    private WordManager wordManager;
    
    @Inject
    private ExamActionManager examActionManager;
    
    public void processExamResult(final User user, final Language language,
            final List<Word> words, final List<Word> correctWords) {
        Objects.requireNonNull(user, "parameter 'user' must not be null");
        Objects.requireNonNull(words, "parameter 'words' must not be null");
        Objects.requireNonNull(correctWords, "parameter 'correctWords' must not be null");

        if (!words.containsAll(correctWords)) {
            throw new IllegalArgumentException("'correctWords' must be a subset of 'words'");
        }

                // word updates
        final LocalDateTime now = LocalDateTime.now();
        
        words.stream().forEach(word -> {
            word.setLastPracticed(now);
            word.setNumberOfTests(word.getNumberOfTests() + 1);
            
            if(correctWords.contains(word)) {
                word.setNumberOfCorrectAnswers(word.getNumberOfCorrectAnswers() + 1);
            }
        });
        
        wordManager.saveAll(words);
        
        // statistics
        final int numCorrect = correctWords.size();
        final int numIncorrect = words.size() - numCorrect;
        final ExamAction examAction = new ExamAction(user, now, language,
                numCorrect, numIncorrect);
        
        examActionManager.save(examAction);
    }
}
