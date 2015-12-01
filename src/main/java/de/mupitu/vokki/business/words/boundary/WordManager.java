package de.mupitu.vokki.business.words.boundary;

import de.mupitu.vokki.business.words.entity.Word;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;

@Stateless
public class WordManager {

    @PersistenceContext(name = "vokkiPU")
    private EntityManager em;

    public List<Word> findAll() {
        return em.createNamedQuery(Word.findAll, Word.class).getResultList();
    }

    public Word findById(final long id) {
        return em.find(Word.class, id);
    }

    public Word save(final Word word) {
        return em.merge(word);
    }

    public void remove(final long id) {
        try {
            final Word reference = em.getReference(Word.class, id);
            em.remove(reference);
        } catch (final EntityNotFoundException e) {
            // nothing to do here
        }
    }
}
