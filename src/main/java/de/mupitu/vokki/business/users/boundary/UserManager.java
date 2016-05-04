package de.mupitu.vokki.business.users.boundary;

import de.mupitu.vokki.business.users.entity.User;
import de.unidue.s3.bcrypt.BCrypt;
import java.time.LocalDate;
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

    public User findByUsername(final String username) {
        final List<User> users = em.createQuery("SELECT u FROM User u WHERE u.username=:username", User.class).setParameter("username", username).getResultList();

        if (users == null || users.isEmpty()) {
            return null;
        } else {
            return users.get(0);
        }
    }

    public User findByEmailAddress(final String emailAddress) {
        final List<User> users = em.createQuery("SELECT u FROM User u WHERE u.emailAddress=:emailAddress", User.class).setParameter("emailAddress", emailAddress).getResultList();

        if (users == null || users.isEmpty()) {
            return null;
        } else {
            return users.get(0);
        }
    }

    public User save(final User user) {
        return em.merge(user);
    }

    public void remove(final long id) {
        try {
            final User user = em.getReference(User.class, id);
            em.remove(user);
        } catch (final EntityNotFoundException e) {
            // 
        }
    }

    public User checkLogin(final String username, final String password) {
        final List<User> users = (List<User>) em.createQuery("FROM User u WHERE u.username=:username", User.class)
                .setParameter("username", username).getResultList();

        final User user;

        if (users != null && users.size() == 1) {
            user = users.get(0);

            if (user != null && checkPassword(password, user.getPassword())) {
                return user;
            } else {
                return null;
            }
        }

        return null;
    }

    public boolean checkPassword(final String password, final String hashedPassword) {
        return BCrypt.check(password, hashedPassword);
    }

    public User changePassword(final User user, final String password) {
        final User attachedUser = findById(user.getId());
        attachedUser.setPassword(BCrypt.hash(password));
        return save(attachedUser);
    }
    
    public User updateLastLogin(final User user) {
        final User attachedUser = findById(user.getId());
        attachedUser.setLastLogin(LocalDate.now());
        return save(attachedUser);
    }
}
