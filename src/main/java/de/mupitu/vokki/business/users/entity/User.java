package de.mupitu.vokki.business.users.entity;

import static de.mupitu.vokki.business.users.entity.User.findAll;
import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQuery;

@Entity
@NamedQuery(name = findAll, query="SELECT u FROM User u")
public class User {

    private static final String PREFIX = "de.mupitu.vokki.business.users.entity.";
    public static final String findAll = PREFIX + "findAll";
    
    @Id
    @GeneratedValue
    private long id;
    
    @Column(nullable = false)
    private String login;
    
    @Column(nullable = false)
    private String password;
    
    @Column(nullable = false)
    private String emailAddress;
    
    @Column(nullable = false)
    private LocalDate registerDate;
    
    @Column
    private LocalDate lastLogin;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
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
    
}
