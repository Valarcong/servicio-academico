package pe.edu.cibertec.servicioacademico.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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
    public ResponseEntity<List<Periodo>> listarPeriodos() {
        return ResponseEntity.ok(
                periodoService.listarPeriodos()
        );
    }

    @GetMapping("/{idPeriodo}")
    public ResponseEntity<Periodo> obtenerPeriodoPorId(
            @PathVariable Integer idPeriodo
    ) {
        return ResponseEntity.ok(
                periodoService.obtenerPeriodoPorId(idPeriodo)
        );
    }

    @PostMapping
    public ResponseEntity<Periodo> registrarPeriodo(
            @RequestBody Periodo periodo
    ) {
        return ResponseEntity.ok(
                periodoService.registrarPeriodo(periodo)
        );
    }

    @PutMapping("/{idPeriodo}")
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