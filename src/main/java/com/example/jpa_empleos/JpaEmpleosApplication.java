package com.example.jpa_empleos;

import com.example.jpa_empleos.models.Categoria;
import com.example.jpa_empleos.repository.CategoriasJPARepository;
import com.example.jpa_empleos.repository.CategoriasRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.Optional;

@SpringBootApplication
public class JpaEmpleosApplication implements CommandLineRunner {

    private final CategoriasRepository categoriasRepo;
    private final CategoriasJPARepository categoriasJPARepo;



    public JpaEmpleosApplication(CategoriasRepository categoriasRepo,
                                 CategoriasJPARepository categoriasJPARepo) {
        this.categoriasRepo = categoriasRepo;
        this.categoriasJPARepo = categoriasJPARepo;
    }

    public static void main(String[] args) {
        SpringApplication.run(JpaEmpleosApplication.class, args);
    }

    /**
     * IMPORTANTE (siguiendo el tutorial paso a paso):
     * Descomenta UNA llamada a la vez para probar cada operacion CRUD,
     * tal como lo indican los PDFs (guardar solo se ejecuta una vez,
     * luego se prueba buscarPorId, luego modificar, luego eliminar, etc.)
     */
    @Override
    public void run(String... args) throws Exception {
        System.out.println(categoriasRepo);
        // guardar();          // Create -> ejecutar solo una vez
        //buscarPorId(999);     // Read
        // modificar(1);       // Update
        // eliminar(1);        // Delete
       // listarTodos();         // Read all -> seguro para dejar activo por defecto

        //nuevas jpa
        //buscarTodasJPA();
        //borrarTodasEnBloque();
//        buscarTodosOrdenados();
//        buscarTodosPaginacion();
//       DSw
    }

    /**
     * Metodo save - Interfaz CrudRepository (Create)
     */
    private void guardar() {
        System.out.println("Guardando...");

        Categoria nuevaCategoria = new Categoria();
        nuevaCategoria.setNombre("Finanzas");
        nuevaCategoria.setDescripcion("Trabajos relacionados con finanzas y contabilidad");

        categoriasRepo.save(nuevaCategoria);
        System.out.println(nuevaCategoria);
    }

    /**
     * Metodo findById - Interfaz CrudRepository (Read)
     */
    private void buscarPorId(int id) {
        Optional<Categoria> categoriaBuscada = categoriasRepo.findById(id);
        if (categoriaBuscada.isPresent()) {
            System.out.println(categoriaBuscada.get());
        } else {
            System.out.println("Categoria no encontrada");
        }
    }

    /**
     * Metodo save(actualizar) - Interfaz CrudRepository (Update)
     */
    private void modificar(int id) {
        Optional<Categoria> categoriaBuscada = categoriasRepo.findById(id);
        if (categoriaBuscada.isPresent()) {
            Categoria categoriaTmp = categoriaBuscada.get();
            categoriaTmp.setNombre("Ingenieria de Software");
            categoriaTmp.setDescripcion("Desarrollo de sistemas");
            categoriasRepo.save(categoriaTmp);

            System.out.println(categoriaTmp);
            System.out.println("Categoria actualizada...");
        } else {
            System.out.println("Categoria no encontrada");
        }
    }

    /**
     * Metodo deleteById(borrar) - Interfaz CrudRepository (Delete)
     */
    private void eliminar(int id) {
        categoriasRepo.deleteById(id);
        System.out.println("Registro eliminado...");
    }

    /**
     * Metodo findAll - Interfaz CrudRepository (mostrar todos los registros)
     */
    private void listarTodos() {
        categoriasRepo.findAll().forEach(System.out::println);
    }


    private void buscarTodasJPA() {
        List<Categoria> categorias = categoriasJPARepo.findAll();
        for (Categoria categoria : categorias) {
            System.out.println(categoria.getId() + " " + categoria.getNombre());
        }
    }

    private void borrarTodasEnBloque() {
        categoriasJPARepo.deleteAllInBatch();
    }

    private void buscarTodosOrdenados() {
        List<Categoria> categorias = categoriasJPARepo.findAll(Sort.by("nombre"));
        for (Categoria categoria : categorias) {
            System.out.println(categoria.getId() + " " + categoria.getNombre()); //.descending()
        }
    }
    private void buscarTodosPaginacion() {
        Page<Categoria> page = categoriasJPARepo.findAll(PageRequest.of(0, 5));
        System.out.println("Total Registros: " + page.getTotalElements());
        System.out.println("Total Paginas: " + page.getTotalPages());
        for (Categoria c : page.getContent()) {
            System.out.println(c.getId() + " " + c.getNombre());
        }
    }

    private void buscarTodosPaginacionOrdenados() {
        Page<Categoria> page = categoriasJPARepo.findAll(
                PageRequest.of(0, 5, Sort.by("nombre")));  //.descending()
        System.out.println("Total Registros: " + page.getTotalElements());
        System.out.println("Total Paginas: " + page.getTotalPages());
        for (Categoria c : page.getContent()) {
            System.out.println(c.getId() + " " + c.getNombre());
        }
    }
}
