package org.gycoding.accounts.model.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "User")
public class User implements Parseable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    @Setter
    private Integer id;

    @Column
    @Getter
    @Setter
    private String username;

    @Embedded
    @Getter
    @Setter
    private Email email;

    @Column
    @Getter
    @Setter
    private String password;

    @Column
    @Getter
    @Setter
    private String salt;

    @Embedded
    @Getter
    @Setter
    private GYToken token;

    @Builder
    public User(String username, Email email) {
        this.username       = username;
        this.email          = email;
    }

    public User() {}

    @Override
    public String toString() {
        return this.toJSON();
    }

    @Override
    public String toJSON() {
        return "{" +
                "\"username\":\"" + username + '\"' +
                ", \"email\":\"" + email + '\"' +
                ", \"token\":\"" + token.toString() + '\"' +
                '}';
    }

    @Override
    public String toXML() {
        return "<user>" +
                "<username>" + username + "</username>" +
                "<email>" + email + "</email>" +
                "<token>" + token.toString() + "</token>" +
                "</user>";
    }

    @Override
    public String toTXT() {
        return String.format("%s%s%s%s%s", this.username, Parseable.DELIM, this.email, Parseable.DELIM, this.token.toString());
    }
}