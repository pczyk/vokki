package de.mupitu.vokki.business.words.entity;

import de.mupitu.vokki.business.JPAEntity;
import java.time.LocalDate;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import static de.mupitu.vokki.business.words.entity.Word.findAll;
import java.time.LocalDateTime;
import javax.persistence.ManyToOne;

/**
 * Representation of a word in a foreign language including its translation(s)
 * to the mother tongue (there may be more than one translation). Every word
 * instance belongs to a lection and is unqiue for every user.
 *
 * @author Martin Filipczyk
 */
@Entity
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement
@NamedQuery(name = findAll, query = "SELECT w FROM Word w")
public class Word extends JPAEntity {

    // ----- Named Queries -----
    static final String PREFIX = "de.mupitu.vokki.business.words.entity.Word.";
    public static final String findAll = PREFIX + "findAll";

    public static final int MIN_LEVEL = 0;
    public static final int MAX_LEVEL = 6;

    @Id
    @GeneratedValue
    private long id;

    @ElementCollection(fetch = FetchType.EAGER)
    private Set<String> nativeTerms;

    @Column(nullable = false)
    private String foreignTerm;

    @Column
    private String comment;

    @Column(nullable = false)
    @Min(value = MIN_LEVEL)
    @Max(value = MAX_LEVEL)
    private int wordLevel;

    @Column
    private LocalDateTime lastPracticed;

    @Column
    private LocalDateTime lastRelegation;

    @Column
    private LocalDate nextExerciseDate;

    @ManyToOne(optional = false)
    private Lection lection;

    @Column
    @Min(value = 0)
    private int numberOfTests = 0;

    @Column
    @Min(value = 0)
    private int numberOfCorrectAnswers = 0;

    // ------------
    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public Set<String> getNativeTerms() {
        return nativeTerms;
    }

    public void setNativeTerms(Set<String> nativeTerms) {
        this.nativeTerms = nativeTerms;
    }

    public String getForeignTerm() {
        return foreignTerm;
    }

    public void setForeignTerm(String foreignTerm) {
        this.foreignTerm = foreignTerm;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getWordLevel() {
        return wordLevel;
    }

    public void setWordLevel(int wordLevel) {
        this.wordLevel = wordLevel;
    }

    public LocalDateTime getLastPracticed() {
        return lastPracticed;
    }

    public void setLastPracticed(LocalDateTime lastPracticed) {
        this.lastPracticed = lastPracticed;
    }

    public void setLevel(final int wordLevel) {
        if (wordLevel < MIN_LEVEL || wordLevel > MAX_LEVEL) {
            throw new IllegalArgumentException();
        }

        this.wordLevel = wordLevel;
    }

    public Lection getLection() {
        return lection;
    }

    public void setLection(Lection lection) {
        this.lection = lection;
    }

    public int getNumberOfTests() {
        return numberOfTests;
    }

    public void setNumberOfTests(int numberOfTests) {
        this.numberOfTests = numberOfTests;
    }

    public int getNumberOfCorrectAnswers() {
        return numberOfCorrectAnswers;
    }

    public void setNumberOfCorrectAnswers(int numberOfCorrectAnswers) {
        this.numberOfCorrectAnswers = numberOfCorrectAnswers;
    }

    public LocalDateTime getLastRelegation() {
        return lastRelegation;
    }

    public void setLastRelegation(LocalDateTime lastRelegation) {
        this.lastRelegation = lastRelegation;
    }

    public LocalDate getNextExerciseDate() {
        return nextExerciseDate;
    }

    public void setNextExerciseDate(LocalDate nextExerciseDate) {
        this.nextExerciseDate = nextExerciseDate;
    }

    // --------------
    /**
     * Creates and returns a new Word instance
     */
    public static Word createWord(final String foreignTerm,
            final Set<String> nativeTerms, final Lection lection, final String comment) {
        final Word word = new Word();

        word.setForeignTerm(foreignTerm);
        word.setNativeTerms(nativeTerms);
        word.setLection(lection);
        word.setComment(comment);

        return word;
    }

    /**
     * Checks whether a foreign term is correct for this word (e.g. for exam
     * situations)
     *
     * @param foreignTerm the foreign term to check
     * @return <code>true</code> if foreign term is correct, <code>false</code>
     * otherwise
     */
    public boolean isCorrectForeignTerm(final String foreignTerm) {
        if (foreignTerm == null) {
            return false;
        }

        return foreignTerm.trim().equals(this.foreignTerm);
    }
}
