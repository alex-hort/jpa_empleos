package com.example.jpa_empleos.repository;

import com.example.jpa_empleos.models.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
}
