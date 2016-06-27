package de.mupitu.vokki.business.exams.controller;

import de.mupitu.vokki.business.exams.boundary.WordPromoter;
import de.mupitu.vokki.business.words.entity.Word;
import java.time.LocalDate;
import org.junit.Assert;
import org.junit.Test;

public class StandardWordPromoterTest {

    private WordPromoter promoter = new StandardWordPromoter();
    
    @Test
    public void testPromoteWordPromotionLevel1() {
        final LocalDate now = LocalDate.now();
        final Word word = createWord(now, 1);
        
        promoter.promoteWord(word);
        
        Assert.assertEquals(2, word.getLevel());
    }
    
    @Test
    public void testPromoteWordPromotionLevel2() {
        final LocalDate now = LocalDate.now();
        final Word word = createWord(now, 2);
        
        promoter.promoteWord(word);
        
        Assert.assertEquals(3, word.getLevel());
    }
    
    @Test
    public void testPromoteWordPromotionLevel3() {
        final LocalDate now = LocalDate.now();
        final Word word = createWord(now, 3);
        
        promoter.promoteWord(word);
        
        Assert.assertEquals(4, word.getLevel());
    }

    @Test
    public void testPromoteWordNoPromotionLevel1() {
        final LocalDate now = LocalDate.now();
        final Word word = createWord(now.plusDays(1), 1);
        
        promoter.promoteWord(word);
        
        Assert.assertEquals(1, word.getLevel());
    }
    
    @Test
    public void testPromoteWordNoPromotionLevel2() {
        final LocalDate now = LocalDate.now();
        final Word word = createWord(now.plusDays(1), 2);
        
        promoter.promoteWord(word);
        
        Assert.assertEquals(2, word.getLevel());
    }
    
    @Test
    public void testPromoteWordNoPromotionLevel7() {
        final LocalDate now = LocalDate.now();
        final Word word = createWord(now, 7);
        
        promoter.promoteWord(word);
        
        Assert.assertEquals(7, word.getLevel());
    }
    
    
    private static Word createWord(final LocalDate nextExerciseDate, final int wordLevel) {
        final Word word = new Word();
        
        word.setNextExerciseDate(nextExerciseDate);
        word.setLevel(wordLevel);
        
        return word;
    }
}
