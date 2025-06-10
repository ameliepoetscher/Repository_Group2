package org.example.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class Benutzer {

    @Id
    @NonNull
    private String benutzername;

    @NonNull
    private String passwort;

    @Enumerated(EnumType.STRING)
    private Role role = Role.USER;
}
