package com.example.jpa_empleos.models;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "Usuarios",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "username"),
                @UniqueConstraint(columnNames = "email")
        })
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "nombre", nullable = false, length = 45)
    private String nombre;

    @Column(name = "email", nullable = false, length = 100)
    private String email;

    @Column(name = "username", nullable = false, length = 45)
    private String username;

    @Column(name = "password", nullable = false, length = 100)
    private String password;

    @Column(name = "estatus", nullable = false)
    private Integer estatus = 1;

    @Column(name = "fechaRegistro")
    private LocalDate fechaRegistro;

    // Relacion ManyToMany con Perfiles usando la tabla UsuarioPerfil
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "UsuarioPerfil",
            joinColumns = @JoinColumn(name = "idUsuario"),
            inverseJoinColumns = @JoinColumn(name = "idPerfil"),
            uniqueConstraints = @UniqueConstraint(columnNames = {"idUsuario", "idPerfil"})
    )
    @Builder.Default
    private Set<Perfil> perfiles = new HashSet<>();
}
