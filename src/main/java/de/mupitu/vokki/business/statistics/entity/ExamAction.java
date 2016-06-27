package de.mupitu.vokki.business.statistics.entity;

import de.mupitu.vokki.business.JPAEntity;
import de.mupitu.vokki.business.users.entity.User;
import de.mupitu.vokki.business.words.entity.Language;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Min;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
/**
 * Summary of an exam a user has taken
 */
public class ExamAction extends JPAEntity {

    @Id
    @GeneratedValue
    private long id;

    @ManyToOne
    private User user;

    @Column(nullable = false)
    private LocalDateTime localDateTime;

    @ManyToOne
    private Language language;

    @Column(nullable = false)
    @Min(value = 0)
    private int numberOfWordsCorrect;

    @Column(nullable = false)
    @Min(value = 0)
    private int numberOfWordsIncorrect;

    public ExamAction() {
    }
    
    public ExamAction(final User user, final LocalDateTime localDateTime,
            final Language language, final int numberOfWordsCorrect,
            final int numberOfWordsIncorrect) {
        this.user = user;
        this.localDateTime = localDateTime;
        this.language = language;
        this.numberOfWordsCorrect = numberOfWordsCorrect;
        this.numberOfWordsIncorrect = numberOfWordsIncorrect;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }

    public void setLocalDateTime(LocalDateTime localDateTime) {
        this.localDateTime = localDateTime;
    }

    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    public int getNumberOfWordsCorrect() {
        return numberOfWordsCorrect;
    }

    public void setNumberOfWordsCorrect(int numberOfWordsCorrect) {
        this.numberOfWordsCorrect = numberOfWordsCorrect;
    }

    public int getNumberOfWordsIncorrect() {
        return numberOfWordsIncorrect;
    }

    public void setNumberOfWordsIncorrect(int numberOfWordsIncorrect) {
        this.numberOfWordsIncorrect = numberOfWordsIncorrect;
    }

    // -----
    public int getNumberOfWordsTotal() {
        return numberOfWordsCorrect + numberOfWordsIncorrect;
    }
}
