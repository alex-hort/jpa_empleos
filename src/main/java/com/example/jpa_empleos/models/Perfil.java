package com.example.jpa_empleos.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "Perfiles")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Perfil {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "perfil", nullable = false, length = 100)
    private String perfil;
}
