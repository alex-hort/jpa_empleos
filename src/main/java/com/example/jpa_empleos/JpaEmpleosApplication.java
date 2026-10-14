package com.example.jpa_empleos;

import com.example.jpa_empleos.models.*;
import com.example.jpa_empleos.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.annotation.Order;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@SpringBootApplication
@Order(2)
public class JpaEmpleosApplication implements CommandLineRunner {

    private final CategoriasRepository categoriasRepo;
    private final CategoriasJPARepository categoriasJPARepo;
    private final VacantesRepository vacantesRepo;
    private final PerfilesRepository perfilesRepo;
    private final UsuarioRepository usuarioRepo;

    public JpaEmpleosApplication(CategoriasRepository categoriasRepo,
                                 CategoriasJPARepository categoriasJPARepo,
                                 VacantesRepository vacantesRepo,
                                 PerfilesRepository perfilesRepo,
                                 UsuarioRepository usuarioRepo) {
        this.categoriasRepo = categoriasRepo;
        this.categoriasJPARepo = categoriasJPARepo;
        this.vacantesRepo = vacantesRepo;
        this.perfilesRepo = perfilesRepo;
        this.usuarioRepo = usuarioRepo;
    }

    public static void main(String[] args) {
        SpringApplication.run(JpaEmpleosApplication.class, args);
    }

    /**
     * Descomenta UNA línea a la vez para probar cada práctica de los PDFs
     * (y haz el commit que indica el PDF después de cada una).
     */
    @Override
    public void run(String... args) throws Exception {
//         buscarVacantes();                    // PDF 05: findAll + categoría
        // guardarVacante();                    // PDF 05: guardar una vacante
        // buscarVacantesPorEstatus();          // PDF 07: findByEstatus
        // buscarVacantesPorDestacadoEstatus(); // PDF 07: And + OrderBy
        // buscarVacantesSalario();             // PDF 07: Between
        // buscarVacantesVariosEstatus();       // PDF 07: In
        // crearUsuarioConPerfiles();           // PDF 06: ManyToMany (solo una vez: username/email únicos)
        buscarUsuario();                     // PDF 06: usuario + perfiles

//        crearPerfiles();
//        crearUsuarioConPerfiles();

    }

    // ===================== PDF 05 - Relaciones =====================

    private void buscarVacantes() {
        List<Vacante> vacantes = vacantesRepo.findAll();
        for (Vacante vacante : vacantes) {
            System.out.println(vacante.getId() + ". " + vacante.getNombre()
                    + " -> " + vacante.getCategoria().getNombre());
        }
    }

    private void guardarVacante() {
        Vacante vacante = new Vacante();
        vacante.setNombre("Desarrollador Java PRO");
        vacante.setDescripcion("Se busca desarrollador con experiencia en Spring Boot y JPA.");
        vacante.setFecha(LocalDate.now());
        vacante.setSalario(25000.0);
        vacante.setEstatus(EstatusVacante.Creada);
        vacante.setDestacado(1);
        vacante.setImagen("logo_empresa.png");
        vacante.setDetalles("Trabajo remoto con horario flexible. Beneficios y capacitación incluidos.");

        Categoria categoria = new Categoria();
        categoria.setId(1); // categoría existente: solo se asigna el id
        vacante.setCategoria(categoria);

        vacantesRepo.save(vacante);
    }

    // ===================== PDF 07 - Query Methods =====================

    private void buscarVacantesPorEstatus() {
        List<Vacante> vacantesAprobadas = vacantesRepo.findByEstatus(EstatusVacante.Aprobada);
        System.out.println("Registros encontrados: " + vacantesAprobadas.size());
        for (Vacante vacante : vacantesAprobadas) {
            System.out.println(vacante.getId() + ": " + vacante.getNombre() + " - " + vacante.getEstatus());
        }
    }

    private void buscarVacantesPorDestacadoEstatus() {
        List<Vacante> lista = vacantesRepo
                .findByDestacadoAndEstatusOrderByIdDesc(1, EstatusVacante.Aprobada);
        System.out.println("Registros encontrados: " + lista.size());
        for (Vacante vacante : lista) {
            System.out.println(vacante.getId() + ": " + vacante.getNombre() + " - "
                    + vacante.getEstatus() + ", destacada: " + vacante.getDestacado());
        }
    }

    private void buscarVacantesSalario() {
        List<Vacante> lista = vacantesRepo.findBySalarioBetween(7000.0, 14000.0);
        System.out.println("Registros encontrados: " + lista.size());
        for (Vacante vacante : lista) {
            System.out.println(vacante.getId() + ": " + vacante.getNombre() + " - $" + vacante.getSalario());
        }

        System.out.println("-".repeat(100));
        List<Vacante> listaDesc = vacantesRepo.findBySalarioBetweenOrderBySalarioDesc(7000.0, 14000.0);
        System.out.println("Registros encontrados: " + listaDesc.size());
        for (Vacante vacante : listaDesc) {
            System.out.println(vacante.getId() + ": " + vacante.getNombre() + " - $" + vacante.getSalario());
        }
    }

    private void buscarVacantesVariosEstatus() {
        EstatusVacante[] listaEstatus = new EstatusVacante[]{
                EstatusVacante.Eliminada, EstatusVacante.Creada
        };
        List<Vacante> lista = vacantesRepo.findByEstatusIn(listaEstatus);
        System.out.println("Registros encontrados: " + lista.size());
        for (Vacante vacante : lista) {
            System.out.println(vacante.getId() + ": " + vacante.getNombre() + " - " + vacante.getEstatus());
        }
    }

    // ===================== PDF 06 - Otras Relaciones =====================

    private void crearUsuarioConPerfiles() {
        Usuario nuevoUsuario = new Usuario();
        nuevoUsuario.setNombre("José García");
        nuevoUsuario.setEmail("jose@gmail.com");
        nuevoUsuario.setUsername("joseg");
        nuevoUsuario.setPassword("12345");
        nuevoUsuario.setEstatus(1);
        nuevoUsuario.setFechaRegistro(LocalDate.now());

        Perfil perfil1 = new Perfil();
        perfil1.setId(2); // ADMINISTRADOR
        Perfil perfil2 = new Perfil();
        perfil2.setId(3); // USUARIO

        Set<Perfil> perfilesUsuario = new HashSet<>();
        perfilesUsuario.add(perfil1);
        perfilesUsuario.add(perfil2);
        nuevoUsuario.setPerfiles(perfilesUsuario);

        usuarioRepo.save(nuevoUsuario);
    }

    private void buscarUsuario() {
        Optional<Usuario> usuarioOptional = usuarioRepo.findById(2);
        if (usuarioOptional.isPresent()) {
            Usuario usuario = usuarioOptional.get();
            System.out.println("Nombre: " + usuario.getNombre());
            System.out.println("Perfiles asignados");
            for (Perfil perfil : usuario.getPerfiles()) {
                System.out.println(perfil.getPerfil());
            }
        } else {
            System.out.println("Usuario no encontrado");
        }
    }
    private void crearPerfiles() {
        perfilesRepo.saveAll(List.of(
                Perfil.builder().perfil("SUPERVISOR").build(),
                Perfil.builder().perfil("ADMINISTRADOR").build(),
                Perfil.builder().perfil("USUARIO").build()));
    }





}