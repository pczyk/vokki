package de.mupitu.vokki.business.words.entity;

import de.mupitu.vokki.business.JPAEntity;

import static de.mupitu.vokki.business.words.entity.Language.findAll;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Captures a language that is supported by vokki. Every lection (and therefore,
 * every word) is assigned to a language. Languages are shared between users.
 *
 * @author Martin Filipczyk
 */
@Entity
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@NamedQuery(name = findAll, query = "SELECT l FROM Language l")
public class Language extends JPAEntity {

    // ----- Named Queries -----
    static final String PREFIX = "de.mupitu.vokki.business.words.entity.Language.";
    public static final String findAll = PREFIX + "findAll";

    @Id
    @GeneratedValue
    private long id;

    @Column(nullable = false)
    private String name;

    @Column
    private String flagPath;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the path to the image showing the language's associated flag
     *
     * @return path to flag image path
     */
    public String getFlagPath() {
        return flagPath;
    }

    public void setFlagPath(String flagPath) {
        this.flagPath = flagPath;
    }

    @Override
    public String toString() {
        return String.format("%s[id=%d]", getClass().getSimpleName(), getId());
    }

}
