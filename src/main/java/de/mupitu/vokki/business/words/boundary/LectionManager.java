package de.mupitu.vokki.business.words.boundary;

import de.mupitu.vokki.business.users.entity.User;
import de.mupitu.vokki.business.words.entity.Language;
import de.mupitu.vokki.business.words.entity.Lection;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;

@Stateless
public class LectionManager {

    @PersistenceContext(name = "vokkiPU")
    private EntityManager em;

    /**
     * Counts the number of words for a given <code>lection</code>.
     *
     * @param lection this methods counts the number of this lection
     * @return number of words in <code>lection</code>
     */
    public long countWordsForLection(final Lection lection) {
        return em.createQuery("SELECT COUNT (*) FROM Word w WHERE w.lection=:lection", Long.class)
                .setParameter("lection", lection)
                .getSingleResult();
    }

    public List<Lection> getLectionsForUser(final User user) {
        return em.createNamedQuery(Lection.findForUser,
                Lection.class).setParameter("owner", user).getResultList();
    }

    public Map<Language, List<Lection>> getLectionsByLanguageForUser(final User user) {
        final Map<Language, List<Lection>> mappedLections = new HashMap<>();

        getLectionsForUser(user).stream().forEach((lection) -> {
            final Language language = lection.getLanguage();

            List<Lection> list = mappedLections.get(language);

            if (list == null) {
                list = new LinkedList<>();
                mappedLections.put(language, list);
            }

            list.add(lection);
        });

        return mappedLections;
    }

    public Lection findById(final long id) {
        return em.find(Lection.class, id);
    }

    public Lection save(final Lection lection) {
        lection.setCreationDate(LocalDate.now());
        return em.merge(lection);
    }

    public void remove(final long id) {
        try {
            final Lection lection = em.getReference(Lection.class, id);
            em.remove(lection);
        } catch (final EntityNotFoundException e) {
            // nothing to do here.
        }
    }

    public Lection createLection(final String name, final Language language, final String description, final User user) {
        final Lection lection = new Lection();

        lection.setLanguage(language);
        lection.setBaseLanguage(user.getLanguage());
        lection.setCreationDate(LocalDate.now());
        lection.setDescription(description);
        lection.setName(name);
        lection.setOwner(user);

        return save(lection);
    }
}
