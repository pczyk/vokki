package de.mupitu.vokki.business.words.entity;

import de.mupitu.vokki.business.JPAEntity;
import de.mupitu.vokki.business.users.entity.User;
import static de.mupitu.vokki.business.words.entity.Lection.findForUser;
import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * A lection is a collection of words in a foreign language. To be able to cope
 * with large amounts of words that a user may create, these words are organized
 * in lections. Lections are unique to users but may be copied (including its
 * words) from one to another user.
 *
 * @author Martin Filipczyk
 */
@Entity
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@NamedQueries(value = {
    @NamedQuery(name = findForUser, query = "SELECT l FROM Lection l WHERE l.owner=:owner")})
public class Lection extends JPAEntity {

    // ----- Named Queries -----
    static final String PREFIX = "de.mupitu.vokki.business.words.entity.Lection.";
    public static final String findForUser = PREFIX + "findForUser";

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private long id;

    @Column(nullable = false)
    private String name;

    @ManyToOne(optional = false)
    private Language language;

    @ManyToOne(optional = false)
    private Language baseLanguage;

    @ManyToOne(optional = false)
    private User owner;

    @Column
    private String description;

    @Column(nullable = false)
    private boolean pub;

    @Column(nullable = false)
    private LocalDate creationDate;

    @Column
    private LocalDate modificationDate;

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

    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isPublic() {
        return pub;
    }

    public void setPublic(boolean pub) {
        this.pub = pub;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    public LocalDate getModificationDate() {
        return modificationDate;
    }

    public void setModificationDate(LocalDate modificationDate) {
        this.modificationDate = modificationDate;
    }

    public Language getBaseLanguage() {
        return baseLanguage;
    }

    public void setBaseLanguage(Language baseLanguage) {
        this.baseLanguage = baseLanguage;
    }
}
