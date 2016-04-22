package de.mupitu.vokki.presentation;

import de.mupitu.vokki.business.words.boundary.LectionManager;
import de.mupitu.vokki.business.words.boundary.WordManager;
import de.mupitu.vokki.business.words.entity.Lection;
import de.mupitu.vokki.business.words.entity.Word;
import de.mupitu.vokki.presentation.session.UserSession;
import java.io.IOException;
import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletResponse;

@Named
@ViewScoped
public class PracticeLection implements Serializable {

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
            final FacesContext context = FacesContext.getCurrentInstance();
            try {
                // TODO
                context.getExternalContext().responseSendError(400, "lorem");
            } catch (IOException ex) {
                Logger.getLogger(PracticeLection.class.getName()).log(Level.SEVERE, null, ex);
            }

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
}
