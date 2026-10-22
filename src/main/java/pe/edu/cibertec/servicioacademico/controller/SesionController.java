package pe.edu.cibertec.servicioacademico.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.cibertec.servicioacademico.dto.SesionDto;
import pe.edu.cibertec.servicioacademico.model.Sesion;
import pe.edu.cibertec.servicioacademico.service.SesionService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/sesiones")
@RequiredArgsConstructor
public class SesionController {

    private final SesionService sesionService;

    @GetMapping
    public ResponseEntity<List<Sesion>> listarSesiones() {
        return ResponseEntity.ok(
                sesionService.listarSesiones()
        );
    }

    @GetMapping("/{idSesion}")
    public ResponseEntity<Sesion> obtenerSesionPorId(
            @PathVariable Integer idSesion
    ) {
        return ResponseEntity.ok(
                sesionService.obtenerSesionPorId(idSesion)
        );
    }

    @GetMapping("/asignacion/{idAsignacion}")
    public ResponseEntity<List<Sesion>> listarPorAsignacion(
            @PathVariable Integer idAsignacion
    ) {
        return ResponseEntity.ok(
                sesionService.listarPorAsignacion(idAsignacion)
        );
    }

    @PostMapping
    public ResponseEntity<Sesion> registrarSesion(
            @RequestBody SesionDto dto
    ) {
        return ResponseEntity.ok(
                sesionService.registrarSesion(dto)
        );
    }
}