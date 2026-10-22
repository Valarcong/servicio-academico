package pe.edu.cibertec.servicioacademico.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.cibertec.servicioacademico.dto.SesionDto;
import pe.edu.cibertec.servicioacademico.model.Sesion;
import pe.edu.cibertec.servicioacademico.service.SesionService;

import java.util.List;
import java.util.Map;

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

    @PutMapping("/{idSesion}")
    public ResponseEntity<Sesion> actualizarSesion(
            @PathVariable Integer idSesion,
            @RequestBody SesionDto dto
    ) {
        return ResponseEntity.ok(
                sesionService.actualizarSesion(idSesion, dto)
        );
    }

    @DeleteMapping("/{idSesion}")
    public ResponseEntity<Map<String, String>> eliminarSesion(
            @PathVariable Integer idSesion
    ) {

        sesionService.eliminarSesion(idSesion);

        return ResponseEntity.ok(
                Map.of(
                        "mensaje",
                        "Sesión eliminada correctamente"
                )
        );
    }

    @PatchMapping("/{idSesion}")
    public ResponseEntity<Sesion> actualizarParcialmente(
            @PathVariable Integer idSesion,
            @RequestBody SesionDto dto
    ) {
        return ResponseEntity.ok(
                sesionService.actualizarParcialmente(idSesion, dto)
        );
    }
}