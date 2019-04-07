package com.nao4j.subtrol.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Set;

@Data
public class UserCredentials implements Serializable {

    private final String id;
    private final String email;
    private final String password;
    private final Set<String> roles;

}
