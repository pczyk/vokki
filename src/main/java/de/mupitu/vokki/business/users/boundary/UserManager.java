package de.mupitu.vokki.business.users.boundary;

import de.mupitu.vokki.business.users.entity.User;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;

@Stateless
public class UserManager {

    @PersistenceContext(name = "vokkiPU")
    private EntityManager em;
    
    public User findById(final long id) {
        return em.find(User.class, id);
    }
    
    public List<User> findAll() {
        return em.createNamedQuery(User.findAll).getResultList();
    }
    
    public User save(final User user) {
        return em.merge(user);
    }
    
    public void remove(final long id) {
        try {
            final User user = em.getReference(User.class, id);
            em.remove(user);
        } catch(final EntityNotFoundException e) {
            // 
        }
    }
}
