package com.example.jpa_empleos.repository;

import com.example.jpa_empleos.models.EstatusVacante;
import com.example.jpa_empleos.models.Vacante;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VacantesRepository extends JpaRepository<Vacante, Integer> {

    // Keyword findBy: SELECT * FROM Vacantes WHERE estatus = ?
    List<Vacante> findByEstatus(EstatusVacante estatus);

    // Keyword And + OrderBy: destacado = ? AND estatus = ? ORDER BY id DESC
    List<Vacante> findByDestacadoAndEstatusOrderByIdDesc(int destacado, EstatusVacante estatus);

    // Keyword Between: salario BETWEEN ? AND ?
    List<Vacante> findBySalarioBetween(Double salarioInicial, Double salarioFinal);

    List<Vacante> findBySalarioBetweenOrderBySalarioDesc(Double salarioInicial, Double salarioFinal);

    // Keyword In: estatus IN (?, ?, ...)
    List<Vacante> findByEstatusIn(EstatusVacante[] listaEstatus);
}
