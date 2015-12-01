package de.mupitu.vokki.business.words.entity;

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

@Entity
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement
@NamedQuery(name = findAll, query = "SELECT w FROM Word w")
public class Word {

    // ----- Named Queries -----
    
    static final String PREFIX = "de.mupitu.vokki.business.words.entity.Word.";
    public static final String findAll = PREFIX + "findAll";
    
    public static final int MIN_LEVEL = 0;
    public static final int MAX_LEVEL = 7;

    @Id
    @GeneratedValue
    private long id;

    @ElementCollection(fetch = FetchType.EAGER)
    private Set<String> nativeTerms;

    @Column
    private String foreignTerm;

    @Column
    private String comment;

    @Column
    @Min(value = MIN_LEVEL)
    @Max(value = MAX_LEVEL)
    private int wordLevel;

    @Column
    private LocalDate lastPracticed;

    // ------------
    public long getId() {
        return id;
    }

    public void setId(long id) {
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

    public LocalDate getLastPracticed() {
        return lastPracticed;
    }

    public void setLastPracticed(LocalDate lastPracticed) {
        this.lastPracticed = lastPracticed;
    }

    public void setLevel(final int wordLevel) {
        if (wordLevel < MIN_LEVEL || wordLevel > MAX_LEVEL) {
            throw new IllegalArgumentException();
        }

        this.wordLevel = wordLevel;
    }

    public static Word createWord(final String foreignTerm,
            final Set<String> nativeTerms, final String comment) {
        final Word word = new Word();

        word.setForeignTerm(foreignTerm);
        word.setNativeTerms(nativeTerms);
        word.setComment(comment);

        return word;
    }
}
