package org.bertural.deepforge.data.entities;

import org.bertural.deepforge.data.DMC;
import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

import javax.persistence.*;

@Entity
@Table(name = "user")
public class EntityUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Long id;

    @Column(name = "login", unique = true, nullable = false)
    private String login;

    @Column(name = "createdAt", nullable = false)
    @Type(type = DMC.TYPE.DATETIME)
    private DateTime createdAt;

    @Column(name = "lastLoginAt")
    @Type(type = DMC.TYPE.DATETIME)
    private DateTime lastLoginAt;

    @Column(name = "passwordHash", nullable = false)
    private String passwordHash;

    @Column(name = "passwordSalt", nullable = false)
    private String passwordSalt;


    public EntityUser() {
        this.createdAt = DateTime.now();
    }

    public EntityUser(String login) {
        this();
        this.login = login;
    }


    public Long getId() { return id; }
    public String getLogin() { return login; }
    public DateTime getCreatedAt() { return createdAt; }
    public DateTime getLastLoginAt() { return lastLoginAt; }
    public String getPasswordHash() { return passwordHash; }
    public String getPasswordSalt() { return passwordSalt; }

    public void setId(Long id) { this.id = id; }
    public void setLogin(String login) { this.login = login; }
    public void setCreatedAt(DateTime createdAt) { this.createdAt = createdAt; }
    public void setLastLoginAt(DateTime lastLoginAt) { this.lastLoginAt = lastLoginAt; }
    public void setPasswordHash(String passwordHash) { this.passwordHash = passwordHash; }
    public void setPasswordSalt(String passwordSalt) { this.passwordSalt = passwordSalt; }
}
