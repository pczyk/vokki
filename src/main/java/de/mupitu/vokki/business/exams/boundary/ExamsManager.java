package de.mupitu.vokki.business.exams.boundary;

import de.mupitu.vokki.business.exams.controller.StandardWordPromoter;
import de.mupitu.vokki.business.statistics.boundary.ExamActionManager;
import de.mupitu.vokki.business.statistics.entity.ExamAction;
import de.mupitu.vokki.business.users.entity.User;
import de.mupitu.vokki.business.words.boundary.WordManager;
import de.mupitu.vokki.business.words.entity.Language;
import de.mupitu.vokki.business.words.entity.Word;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import javax.ejb.Stateless;
import javax.inject.Inject;

@Stateless
public class ExamsManager {

    @Inject
    private WordManager wordManager;

    @Inject
    private ExamActionManager examActionManager;

    /**
     * Handles all the logic for completing an exam, i.e. updating the words,
     * creating statistics
     *
     * @param user the user who did the exam
     * @param language the exam's language
     * @param words the complete list of words tested in the exam
     * @param correctWords the word which the user knew correctly (must be a
     * subset of words)
     * @return a map containing all the words from <code>words</code> with their
     * change in level
     */
    public Map<Word, LevelEffect> processExamResult(final User user, final Language language,
            final List<Word> words, final List<Word> correctWords) {
        // check parameters
        Objects.requireNonNull(user, "parameter 'user' must not be null");
        Objects.requireNonNull(words, "parameter 'words' must not be null");
        Objects.requireNonNull(correctWords, "parameter 'correctWords' must not be null");

        // check for correctWords being a subset of words
        if (!words.containsAll(correctWords)) {
            throw new IllegalArgumentException("'correctWords' must be a subset of 'words'");
        }

        final Map<Word, LevelEffect> result = new HashMap<>();

        // *** single word updates ***
        final LocalDateTime now = LocalDateTime.now();

        // use the standard promoter for now. May change in the future
        final StandardWordPromoter promoter = new StandardWordPromoter();

        // update the words (promotion, lastPractised, numberOfTests, numberOfCorrectAnswers)
        words.stream().forEach(word -> {
            word.setLastPracticed(now);
            word.setNumberOfTests(word.getNumberOfTests() + 1);

            final LevelEffect levelEffect;

            if (correctWords.contains(word)) {
                word.setNumberOfCorrectAnswers(word.getNumberOfCorrectAnswers() + 1);

                if (promoter.promoteWord(word)) {
                    levelEffect = LevelEffect.INCREASED;
                } else {
                    levelEffect = LevelEffect.UNCHANGED;
                }
            } else {
                final int wordLevel = word.getLevel();

                if (wordLevel > Word.MIN_LEVEL) {
                    word.setLevel(wordLevel - 1);
                    levelEffect = LevelEffect.DECREASED;
                } else {
                    levelEffect = LevelEffect.UNCHANGED;
                }

                word.setNextExerciseDate(now.toLocalDate().plusDays(1));
            }

            result.put(word, levelEffect);
        });

        wordManager.saveAll(words);

        // *** create statistics ***
        final int numCorrect = correctWords.size();
        final int numIncorrect = words.size() - numCorrect;
        final ExamAction examAction = new ExamAction(user, now, language,
                numCorrect, numIncorrect);

        examActionManager.save(examAction);

        return result;
    }
}
