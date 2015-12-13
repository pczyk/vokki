package de.mupitu.vokki.business.words.entity;

import de.mupitu.vokki.business.users.entity.User;
import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Lection implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private long id;
    
    @Column(nullable = false)
    private String name;
    
    @ManyToOne
    private Language language;
    
    @ManyToOne
    private Language baseLanguage;
    
    @ManyToOne
    private User owner;
    
    @Column
    private String description;
    
    @Column(nullable = false)
    private boolean pub;
    
    @Column(nullable = false)
    private LocalDate creationDate;
    
    @Column
    private LocalDate modificationDate;

    public long getId() {
        return id;
    }

    public void setId(long id) {
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
