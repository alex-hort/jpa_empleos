package com.example.jpa_empleos.controllers;

import com.example.jpa_empleos.models.Categoria;
import com.example.jpa_empleos.repository.CategoriasJPARepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/jpa-categorias")
public class CategoriaJPAController {

    private final CategoriasJPARepository categoriasJPARepo;

    public CategoriaJPAController(CategoriasJPARepository categoriasJPARepo) {
        this.categoriasJPARepo = categoriasJPARepo;
    }

    // 1. Todas
    @GetMapping
    public List<Categoria> todas() {
        return categoriasJPARepo.findAll();
    }

    // 2. Ordenadas por nombre (descendente)
    @GetMapping("/ordenadas")
    public List<Categoria> ordenadas() {
        return categoriasJPARepo.findAll(Sort.by("nombre").descending());
    }

    // 3. Paginadas
    @GetMapping("/paginadas")
    public Page<Categoria> paginadas(@RequestParam int pagina,
                                     @RequestParam int cantidad) {
        return categoriasJPARepo.findAll(PageRequest.of(pagina, cantidad));
    }

    // 4. Paginadas y ordenadas (nombre descendente)
    @GetMapping("/paginadas/ordenadas")
    public Page<Categoria> paginadasOrdenadas(@RequestParam int pagina,
                                              @RequestParam int cantidad) {
        return categoriasJPARepo.findAll(
                PageRequest.of(pagina, cantidad, Sort.by("nombre").descending()));
    }

    // 5. Eliminar todas
    @DeleteMapping("/todas")
    public ResponseEntity<Void> eliminarTodas() {
        categoriasJPARepo.deleteAllInBatch();
        return ResponseEntity.noContent().build();
    }
}