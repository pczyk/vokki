package de.mupitu.vokki.business.statistics.entity;

import de.mupitu.vokki.business.JPAEntity;
import de.mupitu.vokki.business.users.entity.User;
import java.time.LocalDateTime;
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
 * Stores a login action performed by a user
 */
@Entity
@NamedQuery(name = LoginAction.findAllForUser, query = "FROM LoginAction action WHERE action.user=:user")
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class LoginAction extends JPAEntity {

    private static final String PREFIX = "de.mupitu.vokki.business.statistics.entity.LoginAction";
    public static final String findAllForUser = PREFIX + "findAllForUser";

    @Id
    @GeneratedValue
    private long id;

    @ManyToOne
    private User user;

    @Column(nullable = false)
    private LocalDateTime loginTime;

    public User getUser() {
        return user;
    }

    public void setUser(final User user) {
        this.user = user;
    }

    public LocalDateTime getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(final LocalDateTime loginTime) {
        this.loginTime = loginTime;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(final Long id) {
        this.id = id;
    }

    public static LoginAction newInstance(final User user) {
        final LoginAction loginAction = new LoginAction();
        loginAction.setUser(user);
        loginAction.setLoginTime(LocalDateTime.now());

        return loginAction;
    }
}
