package com.nao4j.subtrol.security;

import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public final class Roles {

    public static final String ADMIN = "ROLE_ADMIN";
    public static final String USER = "ROLE_USER";

}
