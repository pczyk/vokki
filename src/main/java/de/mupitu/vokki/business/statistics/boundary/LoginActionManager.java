package de.mupitu.vokki.business.statistics.boundary;

import de.mupitu.vokki.business.statistics.entity.LoginAction;
import de.mupitu.vokki.business.users.entity.User;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class LoginActionManager {

    @PersistenceContext(name = "vokkiPU")
    private EntityManager em;

    public List<LoginAction> getLoginActionsForUser(final User user) {
        return em.createNamedQuery(LoginAction.findAllForUser, LoginAction.class)
                .setParameter("user", user)
                .getResultList();
    }

    public LoginAction save(final LoginAction loginAction) {
        return em.merge(loginAction);
    }
}
