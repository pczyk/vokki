package de.mupitu.vokki.presentation;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import de.mupitu.vokki.business.words.boundary.LectionManager;
import de.mupitu.vokki.business.words.boundary.WordManager;
import de.mupitu.vokki.business.words.entity.Lection;
import de.mupitu.vokki.business.words.entity.Word;
import java.io.Serializable;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.FacesContext;

@ManagedBean
@ViewScoped
public class EditLection implements Serializable {

    @EJB
    LectionManager lectionManager;

    @EJB
    WordManager wordManager;

    @ManagedProperty(value = "#{userSession}")
    private UserSession userSession;

    private long lectionId;

    private Lection lection;

    private List<Word> words;

    private List<Word> wordsToRemove = new LinkedList<>();
    
    private String newWordForeignTerm;
    private String newWordNativeTerms;
    private String newWordComment;

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
    
    public void saveWord() { 
        final Set<String> nativeTerms = new HashSet<>();
        
        for(final String term : newWordNativeTerms.split("\n")) {
            nativeTerms.add(term.trim());
        }
        
        
        final Word newWord = Word.createWord(newWordForeignTerm, nativeTerms, lection, newWordComment);
        
        wordManager.save(newWord);
        
        newWordForeignTerm = null;
        newWordNativeTerms = null;
        newWordComment = null;
        
        words = wordManager.getWordsForLection(lection);
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

    public void saveWords() {
        wordsToRemove.forEach(w -> wordManager.remove(w.getId()));
    }

    public void removeWord(final Word word) {
        wordsToRemove.add(word);
    }

    public String getNewWordNativeTerms() {
        return newWordNativeTerms;
    }

    public void setNewWordNativeTerms(String newWordNativeTerms) {
        this.newWordNativeTerms = newWordNativeTerms;
    }

    public String getNewWordForeignTerm() {
        return newWordForeignTerm;
    }

    public void setNewWordForeignTerm(String newWordForeignTerm) {
        this.newWordForeignTerm = newWordForeignTerm;
    }

    public String getNewWordComment() {
        return newWordComment;
    }

    public void setNewWordComment(String newWordComment) {
        this.newWordComment = newWordComment;
    }
    
    
}
