package com.nao4j.subtrol.document;

import com.nao4j.subtrol.document.internal.Service;
import com.nao4j.subtrol.document.internal.Settings;
import lombok.Data;
import lombok.NonNull;
import lombok.experimental.Wither;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Immutable;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Collection;
import java.util.Set;

import static java.util.Collections.emptyList;
import static java.util.Collections.emptySet;

@Data
@Wither
@Immutable
@Document("users")
public class User {

    @Id
    private String id;
    private final String email;
    private final String password;
    private final Set<String> roles;
    private final Settings settings;
    private final Collection<Service> services;

    public User(
            final String id,
            @NonNull final String email,
            @NonNull final String password,
            final Set<String> roles,
            @NonNull final Settings settings,
            final Collection<Service> services
    ) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.roles = roles != null ? roles : emptySet();
        this.settings = settings;
        this.services = services != null ? services : emptyList();
    }

}
