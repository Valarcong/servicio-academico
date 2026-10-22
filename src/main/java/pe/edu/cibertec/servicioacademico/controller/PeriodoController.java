package pe.edu.cibertec.servicioacademico.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;
import pe.edu.cibertec.servicioacademico.model.Periodo;
import pe.edu.cibertec.servicioacademico.service.PeriodoService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/periodos")
@RequiredArgsConstructor
public class PeriodoController {

    private final PeriodoService periodoService;

    @GetMapping
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public ResponseEntity<List<Periodo>> listarPeriodos() {
        return ResponseEntity.ok(
                periodoService.listarPeriodos()
        );
    }

    @GetMapping("/{idPeriodo}")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public ResponseEntity<Periodo> obtenerPeriodoPorId(
            @PathVariable Integer idPeriodo
    ) {
        return ResponseEntity.ok(
                periodoService.obtenerPeriodoPorId(idPeriodo)
        );
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public ResponseEntity<Periodo> registrarPeriodo(
            @RequestBody Periodo periodo
    ) {
        return ResponseEntity.ok(
                periodoService.registrarPeriodo(periodo)
        );
    }

    @PutMapping("/{idPeriodo}")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public ResponseEntity<Periodo> actualizarPeriodo(
            @PathVariable Integer idPeriodo,
            @RequestBody Periodo periodo
    ) {
        return ResponseEntity.ok(
                periodoService.actualizarPeriodo(
                        idPeriodo,
                        periodo
                )
        );
    }

    @DeleteMapping("/{idPeriodo}")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public ResponseEntity<Map<String, String>> eliminarPeriodo(
            @PathVariable Integer idPeriodo
    ) {

        periodoService.eliminarPeriodo(idPeriodo);

        return ResponseEntity.ok(
                Map.of(
                        "mensaje",
                        "Periodo eliminado correctamente"
                )
        );
    }
}