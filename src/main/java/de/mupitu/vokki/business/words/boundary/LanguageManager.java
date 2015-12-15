package de.mupitu.vokki.business.words.boundary;

import de.mupitu.vokki.business.words.entity.Language;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class LanguageManager {

    @PersistenceContext(name = "vokkiPU")
    private EntityManager em;

    /**
     * Finds all languages
     *
     * @return all languages
     */
    public List<Language> findAll() {
        return em.createQuery("SELECT l FROM Language l", Language.class)
                .getResultList();
    }

    /**
     * Finds a language by its internal <code>id</code>
     *
     * @param id internal id
     * @return the <code>Language</code> object with the given <code>id</code>.
     * Returns <code>null</code> if there is no language with the given id.
     */
    public Language findById(final long id) {
        return em.find(Language.class, id);
    }
}
