package pe.edu.cibertec.servicioacademico.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import pe.edu.cibertec.servicioacademico.dto.TareaDto;
import pe.edu.cibertec.servicioacademico.model.Tarea;
import pe.edu.cibertec.servicioacademico.service.AsignacionDocenteService;
import pe.edu.cibertec.servicioacademico.service.TareaService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/tareas")
@RequiredArgsConstructor
public class TareaController {

    private final TareaService tareaService;
    private final AsignacionDocenteService asignacionDocenteService;

    @GetMapping
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public ResponseEntity<List<Tarea>> listarTareas() {
        return ResponseEntity.ok(
                tareaService.listarTareas()
        );
    }

    @GetMapping("/{idTarea}")
    @PreAuthorize("hasAnyRole('ADMINISTRADOR', 'DOCENTE', 'ALUMNO')")
    public ResponseEntity<Tarea> obtenerTareaPorId(
            @PathVariable Integer idTarea,
            Authentication authentication
    ) {

        Tarea tarea =
                tareaService.obtenerTareaPorId(idTarea);

        if (esDocente(authentication)) {

            Integer idDocente =
                    (Integer) authentication.getDetails();

            asignacionDocenteService.validarAsignacionDelDocente(
                    tarea.getAsignacionDocente().getIdAsignacion(),
                    idDocente
            );
        }

        return ResponseEntity.ok(tarea);
    }

    @GetMapping("/asignacion/{idAsignacion}")
    @PreAuthorize("hasAnyRole('ADMINISTRADOR', 'DOCENTE', 'ALUMNO')")
    public ResponseEntity<List<Tarea>> listarPorAsignacion(
            @PathVariable Integer idAsignacion,
            Authentication authentication
    ) {

        if (esDocente(authentication)) {

            Integer idDocente =
                    (Integer) authentication.getDetails();

            asignacionDocenteService.validarAsignacionDelDocente(
                    idAsignacion,
                    idDocente
            );
        }

        return ResponseEntity.ok(
                tareaService.listarPorAsignacion(idAsignacion)
        );
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMINISTRADOR', 'DOCENTE')")
    public ResponseEntity<Tarea> registrarTarea(
            @RequestBody TareaDto dto,
            Authentication authentication
    ) {

        if (esDocente(authentication)) {

            Integer idDocente =
                    (Integer) authentication.getDetails();

            asignacionDocenteService.validarAsignacionDelDocente(
                    dto.getIdAsignacion(),
                    idDocente
            );
        }

        return ResponseEntity.ok(
                tareaService.registrarTarea(dto)
        );
    }

    @PutMapping("/{idTarea}")
    @PreAuthorize("hasAnyRole('ADMINISTRADOR', 'DOCENTE')")
    public ResponseEntity<Tarea> actualizarTarea(
            @PathVariable Integer idTarea,
            @RequestBody TareaDto dto,
            Authentication authentication
    ) {

        if (esDocente(authentication)) {

            Integer idDocente =
                    (Integer) authentication.getDetails();

            Tarea tareaActual =
                    tareaService.obtenerTareaPorId(idTarea);

            asignacionDocenteService.validarAsignacionDelDocente(
                    tareaActual.getAsignacionDocente().getIdAsignacion(),
                    idDocente
            );

            asignacionDocenteService.validarAsignacionDelDocente(
                    dto.getIdAsignacion(),
                    idDocente
            );
        }

        return ResponseEntity.ok(
                tareaService.actualizarTarea(
                        idTarea,
                        dto
                )
        );
    }

    @PatchMapping("/{idTarea}")
    @PreAuthorize("hasAnyRole('ADMINISTRADOR', 'DOCENTE')")
    public ResponseEntity<Tarea> actualizarParcialmente(
            @PathVariable Integer idTarea,
            @RequestBody TareaDto dto,
            Authentication authentication
    ) {

        if (esDocente(authentication)) {

            Integer idDocente =
                    (Integer) authentication.getDetails();

            Tarea tareaActual =
                    tareaService.obtenerTareaPorId(idTarea);

            asignacionDocenteService.validarAsignacionDelDocente(
                    tareaActual.getAsignacionDocente().getIdAsignacion(),
                    idDocente
            );

            if (dto.getIdAsignacion() != null) {

                asignacionDocenteService.validarAsignacionDelDocente(
                        dto.getIdAsignacion(),
                        idDocente
                );
            }
        }

        return ResponseEntity.ok(
                tareaService.actualizarParcialmente(
                        idTarea,
                        dto
                )
        );
    }

    @DeleteMapping("/{idTarea}")
    @PreAuthorize("hasAnyRole('ADMINISTRADOR', 'DOCENTE')")
    public ResponseEntity<Map<String, String>> eliminarTarea(
            @PathVariable Integer idTarea,
            Authentication authentication
    ) {

        if (esDocente(authentication)) {

            Integer idDocente =
                    (Integer) authentication.getDetails();

            Tarea tarea =
                    tareaService.obtenerTareaPorId(idTarea);

            asignacionDocenteService.validarAsignacionDelDocente(
                    tarea.getAsignacionDocente().getIdAsignacion(),
                    idDocente
            );
        }

        tareaService.eliminarTarea(idTarea);

        return ResponseEntity.ok(
                Map.of(
                        "mensaje",
                        "Tarea eliminada correctamente"
                )
        );
    }

    private boolean esDocente(Authentication authentication) {

        return authentication.getAuthorities()
                .stream()
                .anyMatch(authority ->
                        authority.getAuthority()
                                .equals("ROLE_DOCENTE")
                );
    }
}