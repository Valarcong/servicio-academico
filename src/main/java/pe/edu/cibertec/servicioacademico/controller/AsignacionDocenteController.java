package pe.edu.cibertec.servicioacademico.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.cibertec.servicioacademico.dto.AsignacionDocenteDto;
import pe.edu.cibertec.servicioacademico.model.AsignacionDocente;
import pe.edu.cibertec.servicioacademico.service.AsignacionDocenteService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/asignaciones")
@RequiredArgsConstructor
public class AsignacionDocenteController {

    private final AsignacionDocenteService asignacionDocenteService;

    @GetMapping
    public ResponseEntity<List<AsignacionDocente>> listarAsignaciones() {
        return ResponseEntity.ok(
                asignacionDocenteService.listarAsignaciones()
        );
    }

    @GetMapping("/{idAsignacion}")
    public ResponseEntity<AsignacionDocente> obtenerAsignacionPorId(
            @PathVariable Integer idAsignacion
    ) {
        return ResponseEntity.ok(
                asignacionDocenteService.obtenerAsignacionPorId(idAsignacion)
        );
    }

    @GetMapping("/docente/{idDocente}")
    public ResponseEntity<List<AsignacionDocente>> listarPorDocente(
            @PathVariable Integer idDocente
    ) {
        return ResponseEntity.ok(
                asignacionDocenteService.listarPorDocente(idDocente)
        );
    }

    @PostMapping
    public ResponseEntity<AsignacionDocente> registrarAsignacion(
            @RequestBody AsignacionDocenteDto dto
    ) {
        return ResponseEntity.ok(
                asignacionDocenteService.registrarAsignacion(dto)
        );
    }
}