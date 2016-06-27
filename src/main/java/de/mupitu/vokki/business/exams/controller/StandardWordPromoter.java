package de.mupitu.vokki.business.exams.controller;

import de.mupitu.vokki.business.exams.boundary.WordPromoter;
import de.mupitu.vokki.business.words.entity.Word;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class StandardWordPromoter implements WordPromoter {

    @Override
    public boolean promoteWord(final Word word) {
        final LocalDate now = LocalDate.now();
        final int wordLevel = word.getLevel();
        final LocalDate nextDate = word.getNextExerciseDate();

        if (wordLevel < Word.MAX_LEVEL && !nextDate.isAfter(now)) {
            word.setLevel(wordLevel + 1);
            final int delta = DELTAS.get(word.getLevel());
            word.setNextExerciseDate(now.plusDays(delta));
            //Logger.getLogger(WordPromoter.class).info(message);
            
            return true;
        }
        
        return false;
    }

    private static final List<Integer> DELTAS = Collections.unmodifiableList(Arrays.asList(0, 1, 2, 4, 7, 14, 28, 60));
}
