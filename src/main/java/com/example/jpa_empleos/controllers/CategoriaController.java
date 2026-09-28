package com.example.jpa_empleos.controllers;

import com.example.jpa_empleos.models.Categoria;
import com.example.jpa_empleos.repository.CategoriasRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/categorias")
public class CategoriaController {

    private final CategoriasRepository categoriasRepo;

    public CategoriaController(CategoriasRepository categoriasRepo) {
        this.categoriasRepo = categoriasRepo;
    }

    // GET /api/categorias -> Consultar todas las categorias
    @GetMapping
    public List<Categoria> listar() {
        return (List<Categoria>) categoriasRepo.findAll();
    }

    // GET /api/categorias/{id} -> Buscar una categoria por su ID
    @GetMapping("/{id}")
    public ResponseEntity<Categoria> buscarPorId(@PathVariable Integer id) {
        Optional<Categoria> categoria = categoriasRepo.findById(id);
        return categoria.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // POST /api/categorias -> Crear una nueva categoria
    @PostMapping
    public ResponseEntity<Categoria> crear(@RequestBody Categoria categoria) {
        Categoria guardada = categoriasRepo.save(categoria);
        return ResponseEntity.status(HttpStatus.CREATED).body(guardada);
    }

    // PUT /api/categorias/{id} -> Actualizar una categoria
    @PutMapping("/{id}")
    public ResponseEntity<Categoria> actualizar(@PathVariable Integer id, @RequestBody Categoria datos) {
        Optional<Categoria> categoriaBuscada = categoriasRepo.findById(id);
        if (categoriaBuscada.isPresent()) {
            Categoria categoriaTmp = categoriaBuscada.get();
            categoriaTmp.setNombre(datos.getNombre());
            categoriaTmp.setDescripcion(datos.getDescripcion());
            Categoria actualizada = categoriasRepo.save(categoriaTmp);
            return ResponseEntity.ok(actualizada);
        }
        return ResponseEntity.notFound().build();
    }

    // DELETE /api/categorias/{id} -> Eliminar una categoria
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        if (!categoriasRepo.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        categoriasRepo.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
