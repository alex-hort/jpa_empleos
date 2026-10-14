package com.example.jpa_empleos.controllers;

import com.example.jpa_empleos.models.EstatusVacante;
import com.example.jpa_empleos.models.Vacante;
import com.example.jpa_empleos.repository.VacantesRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/jpa-vacante")
public class VacanteController {

    private final VacantesRepository vacantesRepo;

    public VacanteController(VacantesRepository vacantesRepo) {
        this.vacantesRepo = vacantesRepo;
    }

    // ---------- Actividad "Relaciones" ----------

    /** GET /api/jpa-vacante  -> buscarVacantes */
    @GetMapping
    public List<Vacante> buscarVacantes() {
        return vacantesRepo.findAll();
    }

    /** POST /api/jpa-vacante  -> guardarVacante (JSON en el body) */
    @PostMapping
    public Vacante guardarVacante(@RequestBody Vacante vacante) {
        return vacantesRepo.save(vacante);
    }

    // ---------- Actividad "Query Methods" ----------

    /** GET /api/jpa-vacante/estatus?estatus=Aprobada */
    @GetMapping("/estatus")
    public List<Vacante> porEstatus(@RequestParam EstatusVacante estatus) {
        return vacantesRepo.findByEstatus(estatus);
    }

    /** GET /api/jpa-vacante/estatus-varios?estatus=Eliminada,Creada */
    @GetMapping("/estatus-varios")
    public List<Vacante> porVariosEstatus(@RequestParam List<EstatusVacante> estatus) {
        return vacantesRepo.findByEstatusIn(estatus.toArray(new EstatusVacante[0]));
    }

    /** GET /api/jpa-vacante/salario?minimo=7000&maximo=14000 */
    @GetMapping("/salario")
    public List<Vacante> porSalario(@RequestParam Double minimo, @RequestParam Double maximo) {
        return vacantesRepo.findBySalarioBetween(minimo, maximo);
    }

    /** GET /api/jpa-vacante/salario/desc?minimo=7000&maximo=14000 */
    @GetMapping("/salario/desc")
    public List<Vacante> porSalarioDesc(@RequestParam Double minimo, @RequestParam Double maximo) {
        return vacantesRepo.findBySalarioBetweenOrderBySalarioDesc(minimo, maximo);
    }

    /** GET /api/jpa-vacante/destacadas?destacado=1&estatus=Aprobada */
    @GetMapping("/destacadas")
    public List<Vacante> destacadas(@RequestParam int destacado, @RequestParam EstatusVacante estatus) {
        return vacantesRepo.findByDestacadoAndEstatusOrderByIdDesc(destacado, estatus);
    }
}
