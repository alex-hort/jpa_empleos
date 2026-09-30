package com.example.jpa_empleos.repository;

import com.example.jpa_empleos.models.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoriasJPARepository extends JpaRepository<Categoria, Integer> {
}