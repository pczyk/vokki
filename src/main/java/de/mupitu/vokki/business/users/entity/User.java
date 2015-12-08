package de.mupitu.vokki.business.users.entity;

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

@Entity
@NamedQuery(name = findAll, query="SELECT u FROM User u")
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class User {

    private static final String PREFIX = "de.mupitu.vokki.business.users.entity.";
    public static final String findAll = PREFIX + "findAll";
    
    @Id
    @GeneratedValue
    private long id;
    
    @Column(nullable = false)
    private String username;
    
    @Column(nullable = false)
    private String password;
    
    @Column(nullable = false)
    private String emailAddress;
    
    @Column(nullable = false)
    private LocalDate registerDate;
    
    @Column
    private LocalDate lastLogin;
    
    @ManyToOne(optional = false)
    private Language language;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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
