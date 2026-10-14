package com.example.jpa_empleos.controllers;

import com.example.jpa_empleos.models.Perfil;
import com.example.jpa_empleos.models.Usuario;
import com.example.jpa_empleos.repository.PerfilesRepository;
import com.example.jpa_empleos.repository.UsuarioRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/jpa-usuario")
public class UsuarioController {

    private final UsuarioRepository usuarioRepo;
    private final PerfilesRepository perfilesRepo;

    public UsuarioController(UsuarioRepository usuarioRepo, PerfilesRepository perfilesRepo) {
        this.usuarioRepo = usuarioRepo;
        this.perfilesRepo = perfilesRepo;
    }

    /** GET /api/jpa-usuario/1  -> usuario con sus perfiles (EAGER) */
    @GetMapping("/{id}")
    public ResponseEntity<Usuario> buscarUsuario(@PathVariable Integer id) {
        return usuarioRepo.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /** POST /api/jpa-usuario  -> crear usuario (perfiles solo con "id") */
    @PostMapping
    public Usuario guardarUsuario(@RequestBody Usuario usuario) {
        return usuarioRepo.save(usuario);
    }

    /** POST /api/jpa-usuario/perfiles  -> crear varios perfiles */
    @PostMapping("/perfiles")
    public List<Perfil> guardarPerfiles(@RequestBody List<Perfil> perfiles) {
        return perfilesRepo.saveAll(perfiles);
    }
}
