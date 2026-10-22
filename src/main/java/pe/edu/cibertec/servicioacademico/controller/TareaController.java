package pe.edu.cibertec.servicioacademico.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.cibertec.servicioacademico.dto.TareaDto;
import pe.edu.cibertec.servicioacademico.model.Tarea;
import pe.edu.cibertec.servicioacademico.service.TareaService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/tareas")
@RequiredArgsConstructor
public class TareaController {

    private final TareaService tareaService;

    @GetMapping
    public ResponseEntity<List<Tarea>> listarTareas() {
        return ResponseEntity.ok(
                tareaService.listarTareas()
        );
    }

    @GetMapping("/{idTarea}")
    public ResponseEntity<Tarea> obtenerTareaPorId(
            @PathVariable Integer idTarea
    ) {
        return ResponseEntity.ok(
                tareaService.obtenerTareaPorId(idTarea)
        );
    }

    @GetMapping("/asignacion/{idAsignacion}")
    public ResponseEntity<List<Tarea>> listarPorAsignacion(
            @PathVariable Integer idAsignacion
    ) {
        return ResponseEntity.ok(
                tareaService.listarPorAsignacion(idAsignacion)
        );
    }

    @PostMapping
    public ResponseEntity<Tarea> registrarTarea(
            @RequestBody TareaDto dto
    ) {
        return ResponseEntity.ok(
                tareaService.registrarTarea(dto)
        );
    }
}