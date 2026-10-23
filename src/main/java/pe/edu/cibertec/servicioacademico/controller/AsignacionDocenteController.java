package pe.edu.cibertec.servicioacademico.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.cibertec.servicioacademico.dto.AsignacionDocenteDto;
import pe.edu.cibertec.servicioacademico.model.AsignacionDocente;
import pe.edu.cibertec.servicioacademico.service.AsignacionDocenteService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/asignaciones")
@RequiredArgsConstructor
public class AsignacionDocenteController {

    private final AsignacionDocenteService asignacionDocenteService;

    @GetMapping
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public ResponseEntity<List<AsignacionDocente>> listarAsignaciones() {
        return ResponseEntity.ok(
                asignacionDocenteService.listarAsignaciones()
        );
    }

    @GetMapping("/{idAsignacion}")
    @PreAuthorize("hasAnyRole('ADMINISTRADOR', 'ALUMNO')")
    public ResponseEntity<AsignacionDocente> obtenerAsignacionPorId(
            @PathVariable Integer idAsignacion
    ) {
        return ResponseEntity.ok(
                asignacionDocenteService.obtenerAsignacionPorId(idAsignacion)
        );
    }

    @GetMapping("/docente/{idDocente}")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public ResponseEntity<List<AsignacionDocente>> listarPorDocente(
            @PathVariable Integer idDocente
    ) {
        return ResponseEntity.ok(
                asignacionDocenteService.listarPorDocente(idDocente)
        );
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public ResponseEntity<AsignacionDocente> registrarAsignacion(
            @RequestBody AsignacionDocenteDto dto
    ) {
        return ResponseEntity.ok(
                asignacionDocenteService.registrarAsignacion(dto)
        );
    }

    @PutMapping("/{idAsignacion}")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public ResponseEntity<AsignacionDocente> actualizarAsignacion(
            @PathVariable Integer idAsignacion,
            @RequestBody AsignacionDocenteDto dto
    ) {
        return ResponseEntity.ok(
                asignacionDocenteService.actualizarAsignacion(
                        idAsignacion,
                        dto
                )
        );
    }

    @DeleteMapping("/{idAsignacion}")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public ResponseEntity<Map<String, String>> eliminarAsignacion(
            @PathVariable Integer idAsignacion
    ) {

        asignacionDocenteService.eliminarAsignacion(idAsignacion);

        return ResponseEntity.ok(
                Map.of(
                        "mensaje",
                        "Asignación eliminada correctamente"
                )
        );
    }

    @PatchMapping("/{idAsignacion}")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public ResponseEntity<AsignacionDocente> actualizarParcialmente(
            @PathVariable Integer idAsignacion,
            @RequestBody AsignacionDocenteDto dto
    ) {
        return ResponseEntity.ok(
                asignacionDocenteService.actualizarParcialmente(
                        idAsignacion,
                        dto
                )
        );
    }

    @GetMapping("/mis-asignaciones")
    @PreAuthorize("hasRole('DOCENTE')")
    public ResponseEntity<List<AsignacionDocente>> listarMisAsignaciones(
            Authentication authentication
    ) {

        Integer idDocente =
                (Integer) authentication.getDetails();

        return ResponseEntity.ok(
                asignacionDocenteService.listarPorDocente(idDocente)
        );
    }
}