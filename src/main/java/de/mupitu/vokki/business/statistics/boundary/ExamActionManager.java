package de.mupitu.vokki.business.statistics.boundary;

import de.mupitu.vokki.business.statistics.entity.ExamAction;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class ExamActionManager {

    @PersistenceContext(name = "vokkiPU")
    private EntityManager em;
    
    public ExamAction save(final ExamAction examAction) {
        return em.merge(examAction);
    }
}
