package com.example.jpa_empleos.repository;

import com.example.jpa_empleos.models.Categoria;
import org.springframework.data.repository.CrudRepository;

public interface CategoriasRepository extends CrudRepository<Categoria, Integer> {
}
