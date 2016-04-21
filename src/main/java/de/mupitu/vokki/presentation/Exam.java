package de.mupitu.vokki.presentation;

import de.mupitu.vokki.presentation.session.ExamSession;
import java.io.Serializable;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 * 
 * @author Martin Filipczyk
 */
@Named
@ViewScoped
public class Exam implements Serializable {

    @Inject
    private ExamSession examSession;

    @Deprecated
    public boolean isExamActive() {
        return examSession.isActive();
    }
}
