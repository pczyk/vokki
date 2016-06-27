package de.mupitu.vokki.business.words.boundary;

import de.mupitu.vokki.business.users.entity.User;
import de.mupitu.vokki.business.words.entity.Language;
import de.mupitu.vokki.business.words.entity.Lection;
import de.mupitu.vokki.business.words.entity.Word;
import java.time.LocalDate;
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

    public List<Word> getWordsForLection(final Lection lection) {
        return em.createQuery("SELECT w FROM Word w WHERE w.lection=:lection",
                Word.class).setParameter("lection", lection).getResultList();
    }

    public Word save(final Word word) {
        return em.merge(word);
    }
    
    public List<Word> saveAll(final List<Word> words) {
        words.stream().forEach(word -> save(word));
        
        return words;
    }

    public void remove(final long id) {
        try {
            final Word reference = em.getReference(Word.class, id);
            em.remove(reference);
        } catch (final EntityNotFoundException e) {
            // nothing to do here
        }
    }

    public List<Word> getOverdueWordsForUser(final User user, final Language language) {
        return em
                .createQuery("SELECT w FROM Word w WHERE w.lection.language=:language AND w.lection.owner=:user AND w.nextExerciseDate <= :now", Word.class)
                .setParameter("language", language)
                .setParameter("user", user)
                .setParameter("now", LocalDate.now())
                .getResultList();
    }
}
