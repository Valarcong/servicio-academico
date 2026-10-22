package pe.edu.cibertec.servicioacademico.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;
import pe.edu.cibertec.servicioacademico.model.Curso;
import pe.edu.cibertec.servicioacademico.service.CursoService;


import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/cursos")
@RequiredArgsConstructor
public class CursoController {

    private final CursoService cursoService;


    @GetMapping
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public ResponseEntity<List<Curso>> listarCursos() {
        return ResponseEntity.ok(
                cursoService.listarCursos()
        );
    }

    @GetMapping("/{idCurso}")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public ResponseEntity<Curso> obtenerCursoPorId(
            @PathVariable Integer idCurso
    ) {
        return ResponseEntity.ok(
                cursoService.obtenerCursoPorId(idCurso)
        );
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public ResponseEntity<Curso> registrarCurso(
            @RequestBody Curso curso
    ) {
        return ResponseEntity.ok(
                cursoService.registrarCurso(curso)
        );
    }

    @PutMapping("/{idCurso}")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public ResponseEntity<Curso> actualizarCurso(
            @PathVariable Integer idCurso,
            @RequestBody Curso curso
    ) {
        return ResponseEntity.ok(
                cursoService.actualizarCurso(
                        idCurso,
                        curso
                )
        );
    }

    @DeleteMapping("/{idCurso}")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public ResponseEntity<Map<String, String>> eliminarCurso(
            @PathVariable Integer idCurso
    ) {

        cursoService.eliminarCurso(idCurso);

        return ResponseEntity.ok(
                Map.of(
                        "mensaje",
                        "Curso eliminado correctamente"
                )
        );
    }
}