package org.gycoding.accountsv2.domain.model;

import jakarta.persistence.*;
import lombok.Builder;

@Entity
@Table(name = "User")
public record UserMO(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        String username,
        @Embedded
        Email email,
        @Column
        String password,
        @Column
        String salt,
        @Column
        GYToken token
) implements Parseable {
    @Builder
    public UserMO {}

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