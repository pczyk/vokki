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

    public List<Language> findAll() {
        return em.createQuery("SELECT l FROM Language l", Language.class)
                .getResultList();
    }
    
    public Language findById(final long id) {
        return em.find(Language.class, id);
    }
}
