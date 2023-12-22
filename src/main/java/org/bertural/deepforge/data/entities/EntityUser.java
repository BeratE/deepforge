package org.bertural.deepforge.data.entities;

import org.bertural.deepforge.DMC;
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

    @Column(name = "createdAt", nullable = false)
    @Type(type = DMC.TYPE.DATETIME)
    private DateTime createdAt = DateTime.now();

    @Column(name = "lastLogin")
    @Type(type = DMC.TYPE.DATETIME)
    private DateTime lastLogin;

    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @Column(name = "login", unique = true, nullable = false)
    private String login;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "salt", nullable = false)
    private String salt;


    public Long getId() { return id; }
    public DateTime getCreatedAt() { return createdAt; }
    public DateTime getLastLogin() { return lastLogin; }
    public String getEmail() { return email; }
    public String getLogin() { return login; }
    public String getPassword() { return password; }
    public String getSalt() { return salt; }

    public void setId(Long id) { this.id = id; }
    public void setCreatedAt(DateTime createdAt) { this.createdAt = createdAt; }
    public void setLastLogin(DateTime lastLogin) { this.lastLogin = lastLogin; }
    public void setLogin(String login) { this.login = login; }
    public void setEmail(String email) { this.email = email; }
    public void setPassword(String password) { this.password = password; }
    public void setSalt(String salt) { this.salt = salt; }
}
