package com.matiasbadano.challeng.controllers;

import com.matiasbadano.challeng.dto.AlumnoDTO;
import com.matiasbadano.challeng.dto.CursoDTO;
import com.matiasbadano.challeng.dto.InscripcionDTO;
import com.matiasbadano.challeng.dto.ProfesorDTO;
import com.matiasbadano.challeng.models.*;
import com.matiasbadano.challeng.repository.CursoRepository;
import com.matiasbadano.challeng.repository.ProfesorCursoRepository;
import com.matiasbadano.challeng.repository.ProfesorRepository;
import com.matiasbadano.challeng.repository.UsuarioRepository;
import com.matiasbadano.challeng.services.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class AdministradorController {
    private UsuarioRepository usuarioRepository;
    private final ProfesorService profesorService;
    private final CursoService cursoService;
    private final CursoRepository cursoRepository;
    private final CategoriaService categoriaService;
    private final ProfesorRepository profesorRepository;

    private final AlumnoService alumnoService;

    private UserDetailsServiceImpl customUserDetailsService;
    private ProfesorCursoService profesorCursoService;
    private final Logger logger = LoggerFactory.getLogger(HomeController.class);

    @Autowired
    private PasswordEncoder passwordEncoder;
    private ProfesorCursoRepository profesorCursoRepository;
    private InscripcionService inscripcionService;


    @Autowired
    public AdministradorController(PasswordEncoder passwordEncoder, UsuarioRepository usuarioRepository,
                                   ProfesorRepository profesorRepositor, CursoService cursoService,
                                   ProfesorService profesorService,
                                   AlumnoService alumnoService,
                                   CategoriaService categoriaService,
                                   CursoRepository cursoRepository,
                                   ProfesorCursoRepository profesorCursoRepository,
                                   ProfesorRepository profesorRepository, ProfesorCursoService profesorCursoService,
                                   InscripcionService inscripcionService) {
        this.passwordEncoder = passwordEncoder;
        this.usuarioRepository = usuarioRepository;
        this.profesorService = profesorService;
        this.cursoService = cursoService;
        this.alumnoService = alumnoService;
        this.categoriaService = categoriaService;
        this.cursoRepository = cursoRepository;
        this.profesorRepository = profesorRepository;
        this.profesorCursoService = profesorCursoService;
        this.profesorCursoRepository=profesorCursoRepository;
        this.inscripcionService = inscripcionService;

    }


    @GetMapping("/admin")
    public String admin(Model model, Authentication authentication) {
        String email = authentication.getName();
        Usuario usuario = usuarioRepository.findByEmail(email);
        String nombre = usuario.getNombre();
        logger.debug("Nombre de usuario obtenido de la autenticación: " + email + " Nombre " + nombre);
        model.addAttribute("nombreUsuario", nombre);
        model.addAttribute("email", email);

        return "admin";
    }


    @PostMapping("/admin/profesores")
    public String crearProfesor(@RequestParam("nombre") String nombre,
                                @RequestParam("email") String email,
                                @RequestParam("contrasena") String contrasena,
                                @RequestParam("categoriaId") int categoriaId, Model model) {
        profesorService.crearProfesor(nombre, email, contrasena, categoriaId);
        model.addAttribute("mensaje", "Profesor creado");
        return "profesores";
    }


    @GetMapping("/admin/profesores")
    public String obtenerProfesores(Model model) {
        List<ProfesorDTO> profesores = profesorService.obtenerTodosLosProfesoresDTO();
        model.addAttribute("profesores", profesores);
        return "profesores";
    }

    @GetMapping("/admin/cursos")
    public String mostrarCursos(Model model) {
        List<CursoDTO> cursosDTO = cursoService.getAllCursos();
        model.addAttribute("cursos", cursosDTO);
        logger.debug("cursos", cursosDTO);
        return "cursos";
    }

    @GetMapping("/admin/alumnos")
    public String getAllAlumnos(Model model) {
        List<AlumnoDTO> alumnosDTO = alumnoService.getAllAlumnos();
        model.addAttribute("alumnos", alumnosDTO);
        return "alumnos";
    }

    @PostMapping("/admin/cursos/crear")
    public String crearCurso(@RequestParam("nombre") String nombre,
                             @RequestParam("categoriaId") int categoriaId,
                             @RequestParam("turno") Turno turno, Model model) {
        Curso curso = new Curso();
        curso.setNombre(nombre);

        Categoria categoria = new Categoria();
        categoria.setId((int) categoriaId);
        curso.setCategoria(categoria);

        curso.setTurno(turno);

        cursoRepository.save(curso);
        model.addAttribute("mensaje", "Curso creado con éxito");
        return "redirect:/admin/cursos";
    }

    @PostMapping("/admin/alumnos")
    public String crearProfesor(@RequestParam("nombre") String nombre,
                                @RequestParam("email") String email,
                                @RequestParam("contrasena") String contrasena, Model model) {
        alumnoService.crearAlumno(nombre, email, contrasena);
        model.addAttribute("mensaje", "Alumno creado");
        return "alumnos";
    }

    @GetMapping("/admin/profesores/{profesorId}")
    public String obtenerProfesorPorId(@PathVariable("profesorId") Long profesorId, Model model) {
        ProfesorDTO profesorDTO = profesorService.obtenerProfesorDTOPorId(profesorId);
        model.addAttribute("profesor", profesorDTO);
        return "detalles-profesor";

    }

    @PostMapping("/admin/profesores/{profesorId}/actualizar")
    public String actualizarProfesor(@PathVariable("profesorId") Integer profesorId, @ModelAttribute ProfesorDTO profesorDTO, @RequestParam("categoriaId") int categoriaId) {
        profesorService.actualizarProfesor(profesorId, profesorDTO, categoriaId);
        return "redirect:/admin/profesores";
    }


    @GetMapping("/admin/cursos/{id}")
    public String obtenerCursoPorId(@PathVariable("id") Long id, Model model) {
        Curso curso = cursoService.obtenerCursoPorId(id);
        List<Categoria> categorias = categoriaService.obtenerTodasLasCategorias();
        List<Profesor> profesores = profesorService.obtenerTodosLosProfesores();
        Profesor profesor = curso.getProfesor();
        String nombreProfesor = null;

        if (profesor != null) {
            Usuario usuario = profesor.getUsuario();
            nombreProfesor = usuario.getNombre();
        }

        model.addAttribute("curso", curso);
        model.addAttribute("nombreProfesor", nombreProfesor);
        return "detalle-curso";
    }

    @PostMapping("/admin/cursos/{id}")
    public String actualizarCurso(@PathVariable("id") Long id, @ModelAttribute("curso") Curso cursoActualizado) {
        Curso curso = cursoService.obtenerCursoPorId(id);

        Integer profesorId = cursoActualizado.getProfesor().getId().intValue();

        if (profesorRepository.existsById(profesorId)) {
            Profesor profesor = profesorService.obtenerProfesorPorId(profesorId);
            curso.setProfesor(profesor);
            curso.setNombre(cursoActualizado.getNombre());
            curso.setCategoria(cursoActualizado.getCategoria());

            cursoService.guardarCurso(curso);


            Turno turno = curso.getTurno();

            ProfesorCurso profesorCurso = new ProfesorCurso();
            profesorCurso.setProfesor(profesor);
            profesorCurso.setCurso(curso);
            profesorCurso.setTurno(turno);

            profesorCursoRepository.save(profesorCurso);
        } else {
            return "redirect:/admin/cursos/" + id + "?error";
        }

        return "redirect:/admin/cursos/" + id;
    }

    @GetMapping("/admin/alumnos/{id}")
    public String obtenerAlumnoPorId(@PathVariable("id") Long id, Model model) {
        AlumnoDTO alumno = alumnoService.obtenerAlumnoPorId(id);
        model.addAttribute("alumno", alumno);
        return "detalle-alumno";
    }

    @PostMapping("/admin/alumnos/{id}")
    public String actualizarAlumno(@PathVariable("id") Long id, @ModelAttribute("alumno") AlumnoDTO alumnoActualizado) {
        AlumnoDTO alumnoDTO = alumnoService.obtenerAlumnoPorId(id);
        alumnoDTO.setNombre(alumnoActualizado.getNombre());
        alumnoDTO.setEmail(alumnoActualizado.getEmail());
        alumnoDTO.setNacionalidad(alumnoActualizado.getNacionalidad());
        alumnoDTO.setPaisResidencia(alumnoActualizado.getPaisResidencia());
        alumnoDTO.setEdad(alumnoActualizado.getEdad());
        alumnoDTO.setTelefono(alumnoActualizado.getTelefono());
        alumnoService.actualizarAlumno(alumnoDTO);
        return "redirect:/admin/alumnos/" + id;
    }
    @PostMapping("/admin/alumnos/{id}/inscribirse")
    public String inscribirAlumnoACurso(@PathVariable("id") Long id, @RequestParam("cursoId") Long cursoId) {
        AlumnoDTO alumnoDTO = alumnoService.obtenerAlumnoPorId(id);
        Curso cursoDTO = cursoService.obtenerCursoPorId(cursoId);

        InscripcionDTO inscripcionDTO = new InscripcionDTO();
        inscripcionDTO.setAlumnoId((long) alumnoDTO.getId());
        inscripcionDTO.setCursoId((long) cursoDTO.getId());

        inscripcionService.guardarInscripcion(inscripcionDTO);

        return "redirect:/admin/alumnos/" + id;
    }


    @PostMapping("/admin/profesores/{id}")
    public String eliminarProfesor(@PathVariable("id") Integer id) {
        profesorService.eliminarProfesor(id);
        return "redirect:/admin/profesores";
    }

    @PostMapping("/admin/cursos/{id}/delete")
    public String eliminarCurso(@PathVariable("id") Long id) {
        cursoService.eliminarCurso(id);
        return "redirect:/admin/cursos";
    }

    @PostMapping("/admin/alumnos/{id}/delete")
    public String eliminarAlumno(@PathVariable("id") Integer id){
        alumnoService.eliminarAlumno(id);
        return "redirect:/admin/alumnos";
    }

    @PostMapping("/admin/profesores/{id}/remover-curso")
    public String removerProfesorDeCurso(@PathVariable("id") Long profesorId, @RequestParam("cursoId") Long cursoId) {
        profesorCursoService.removerProfesorDeCurso(profesorId, cursoId);
        return "redirect:/admin/profesores";
    }

}



