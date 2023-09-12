package com.omada5.ergasia_omadas_5.security.auth;

import com.omada5.ergasia_omadas_5.user.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {

    private String username;
    private String password;
    private String email;
    private Set<Role> roles;
}
