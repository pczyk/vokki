package de.mupitu.vokki.presentation;

import de.mupitu.vokki.business.words.boundary.LectionManager;
import de.mupitu.vokki.business.words.boundary.WordManager;
import de.mupitu.vokki.business.words.entity.Lection;
import de.mupitu.vokki.business.words.entity.Word;
import de.mupitu.vokki.presentation.session.UserSession;
import java.util.Collections;
import java.util.List;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@ViewScoped
public class PracticeLection extends BaseController {

    @Inject
    WordManager wordManager;

    @Inject
    LectionManager lectionManager;

    @Inject
    UserSession userSession;

    private long lectionId;

    private Lection lection;

    private List<Word> words;

    private int wordIndex;

    private boolean solutionShown;

    public void loadLection() {
        lection = lectionManager.findById(lectionId);

        if (lection == null || !userSession.getCurrentUser().equals(lection.getOwner())) {
            sendBadRequestResponse("ungültige Lektions-ID");
        } else {
            words = wordManager.getWordsForLection(lection);
            Collections.shuffle(words);
        }
    }

    public boolean isLectionComplete() {
        return wordIndex == words.size();
    }

    public Word getCurrentWord() {
        return words.get(wordIndex);
    }

    public void restart() {
        Collections.shuffle(words);
        wordIndex = 0;
    }

    public int getTotalWords() {
        return words.size();
    }

    public void showSolution() {
        solutionShown = true;
    }

    public void showNextWord() {
        solutionShown = false;
        wordIndex++;
    }

    // ----- Rendering -----
    public boolean isRenderShowSolutionButton() {
        return !solutionShown;
    }

    public boolean isRenderNextWordButton() {
        return solutionShown && wordIndex < words.size() - 1;
    }

    public boolean isRenderRestartButton() {
        return solutionShown && wordIndex == words.size() - 1;
    }

    // ----- GET/SET -----
    public long getLectionId() {
        return lectionId;
    }

    public void setLectionId(long lectionId) {
        this.lectionId = lectionId;
    }

    public Lection getLection() {
        return lection;
    }

    public List<Word> getWords() {
        return words;
    }

    public int getWordIndex() {
        return wordIndex + 1;
    }

    public boolean isSolutionShown() {
        return solutionShown;
    }

    public void setSolutionShown(boolean solutionShown) {
        this.solutionShown = solutionShown;
    }
    
    public String getLanguageFlagPath() {
        return getFlagPathForLanguage(lection.getLanguage());
    }
}
