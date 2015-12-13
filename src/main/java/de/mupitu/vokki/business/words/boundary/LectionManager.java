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

    public List<Lection> getLectionsForUser(final User user) {
        return em.createQuery("SELECT l FROM Lection l WHERE l.owner=:owner",
                Lection.class).setParameter("owner", user).getResultList();
    }
    
    public Map<Language, List<Lection>> getLectionsByLanguageForUser(final User user) {
        final Map<Language, List<Lection>> mappedLections = new HashMap<>();
        
        getLectionsForUser(user).stream().forEach((lection) -> {
            final Language language = lection.getLanguage();
            
            List<Lection> list = mappedLections.get(language);
            
            if(list == null) {
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
}
