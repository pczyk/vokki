package de.mupitu.vokki.presentation;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.view.ViewScoped;
import de.mupitu.vokki.business.words.boundary.LectionManager;
import de.mupitu.vokki.business.words.boundary.WordManager;
import de.mupitu.vokki.business.words.entity.Lection;
import de.mupitu.vokki.business.words.entity.Word;
import de.mupitu.vokki.presentation.session.ExamSession;
import de.mupitu.vokki.presentation.session.UserSession;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@ViewScoped
public class ViewLection implements Serializable {

    @Inject
    LectionManager lectionManager;

    @Inject
    WordManager wordManager;

    @Inject
    private UserSession userSession;
    
    @Inject
    private ExamSession examSession;

    private long lectionId;

    private Lection lection;

    private List<Word> words;

    public String loadLection() {
        lection = lectionManager.findById(lectionId);

        if (lection == null || !userSession.getCurrentUser().equals(lection.getOwner())) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            "Unbekannte Lektion",
                            String.format("Die Lektion mit der ID '%d' existiert nicht.", lectionId)));
        } else {
            words = wordManager.getWordsForLection(lection);
        }

        return null;
    }

    // --- getters and setters ---
    public long getLectionId() {
        return lectionId;
    }

    public void setLectionId(long lectionId) {
        this.lectionId = lectionId;
    }

    public Lection getLection() {
        return lection;
    }

    public UserSession getUserSession() {
        return userSession;
    }

    public void setUserSession(UserSession userSession) {
        this.userSession = userSession;
    }

    public String getLectionLanguage() {
        return lection.getLanguage().getName();
    }

    public String getLectionBaseLanguage() {
        return lection.getBaseLanguage().getName();
    }

    public String formatNativeTerms(final Word word) {
        final StringBuilder builder = new StringBuilder();
        boolean first = true;
        
        for(final String term : word.getNativeTerms()) {
            if(first) {
                first = false;
            } else {
                builder.append(", ");
            }
            
            builder.append(term);
        }
        
        return builder.toString();
    }

    public List<Word> getWords() {
        return words;
    }
    
    public String startExam() {
        examSession.setUpTest(words);
        
        return "exam";
    }

}
