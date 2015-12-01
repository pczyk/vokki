package de.mupitu.vokki.business.words.entity;

import de.mupitu.vokki.business.users.entity.User;
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
public class Lection {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private long id;
    
    @Column(nullable = false)
    private String name;
    
    @ManyToOne
    private User owner;
    
    @Column
    private String description;
}
