package de.mupitu.vokki.presentation;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import de.mupitu.vokki.business.words.boundary.LectionManager;
import de.mupitu.vokki.business.words.entity.Lection;
import java.io.Serializable;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

@ManagedBean
@ViewScoped
public class EditLection implements Serializable {

    @EJB
    LectionManager lectionManager;

    private long lectionId;

    private Lection lection;

    public String loadLection() {
        lection = lectionManager.findById(lectionId);

        if (lection == null) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            "Unbekannte Lektion",
                            String.format("Die Lektion mit der ID '%d' existiert nicht.", lectionId)));
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

}
