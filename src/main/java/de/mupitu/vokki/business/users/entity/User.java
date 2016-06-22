package de.mupitu.vokki.business.users.entity;

import de.mupitu.vokki.business.JPAEntity;
import static de.mupitu.vokki.business.users.entity.User.findAll;
import de.mupitu.vokki.business.words.entity.Language;
import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Human user that interacts with the vokki system.
 *
 * @author Martin Filipczyk
 */
@Entity
@NamedQuery(name = findAll, query = "SELECT u FROM User u")
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class User extends JPAEntity {

    private static final String PREFIX = "de.mupitu.vokki.business.users.entity.User.";
    public static final String findAll = PREFIX + "findAll";

    @Id
    @GeneratedValue
    private long id;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String passwordHash;

    @Column(nullable = false)
    private String emailAddress;

    @Column(nullable = false)
    private LocalDate registerDate;

    @Column
    private LocalDate lastLogin;

    @ManyToOne(optional = false)
    private Language language;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public LocalDate getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(LocalDate registerDate) {
        this.registerDate = registerDate;
    }

    public LocalDate getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(LocalDate lastLogin) {
        this.lastLogin = lastLogin;
    }

    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

}
