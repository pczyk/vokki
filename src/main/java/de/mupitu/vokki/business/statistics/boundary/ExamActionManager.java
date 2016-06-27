package de.mupitu.vokki.business.statistics.boundary;

import de.mupitu.vokki.business.statistics.entity.ExamAction;
import de.mupitu.vokki.business.users.entity.User;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class ExamActionManager {

    @PersistenceContext(name = "vokkiPU")
    private EntityManager em;

    public List<ExamAction> getLoginActionsForUser(final User user) {
        return em.createNamedQuery(ExamAction.findAllForUser, ExamAction.class)
                .setParameter("user", user)
                .getResultList();
    }

    public ExamAction save(final ExamAction examAction) {
        return em.merge(examAction);
    }
}
