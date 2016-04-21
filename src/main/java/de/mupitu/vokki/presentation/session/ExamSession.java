package de.mupitu.vokki.presentation.session;

import de.mupitu.vokki.business.words.boundary.WordManager;
import java.io.Serializable;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import de.mupitu.vokki.business.words.entity.Word;
import java.util.List;
import javax.inject.Inject;

/**
 *
 * @author Martin Filipczyk
 */
@Named
@SessionScoped
public class ExamSession implements Serializable {
    
    private List<Word> words = null;
    
    
    public void setUpTest(final List<Word> words) {
        this.words = words;
    }
    
    public void endTest() {
        words = null;
    }
    
    public boolean isActive() {
        return words != null;
    }
    
    public Word getNextWord() {
        throw new UnsupportedOperationException();
    }
}
