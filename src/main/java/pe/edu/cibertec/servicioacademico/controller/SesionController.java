package pe.edu.cibertec.servicioacademico.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import pe.edu.cibertec.servicioacademico.dto.SesionDto;
import pe.edu.cibertec.servicioacademico.model.Sesion;
import pe.edu.cibertec.servicioacademico.service.SesionService;
import pe.edu.cibertec.servicioacademico.service.AsignacionDocenteService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/sesiones")
@RequiredArgsConstructor
public class SesionController {

    private final SesionService sesionService;
    private final AsignacionDocenteService asignacionDocenteService;

    @GetMapping
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public ResponseEntity<List<Sesion>> listarSesiones() {
        return ResponseEntity.ok(
                sesionService.listarSesiones()
        );
    }

    @GetMapping("/{idSesion}")
    @PreAuthorize("hasAnyRole('ADMINISTRADOR', 'DOCENTE')")
    public ResponseEntity<Sesion> obtenerSesionPorId(
            @PathVariable Integer idSesion,
            Authentication authentication
    ) {

        Sesion sesion =
                sesionService.obtenerSesionPorId(idSesion);

        if (esDocente(authentication)) {

            Integer idDocente =
                    (Integer) authentication.getDetails();

            asignacionDocenteService.validarAsignacionDelDocente(
                    sesion.getAsignacionDocente().getIdAsignacion(),
                    idDocente
            );
        }

        return ResponseEntity.ok(sesion);
    }

    @GetMapping("/asignacion/{idAsignacion}")
    @PreAuthorize("hasAnyRole('ADMINISTRADOR', 'DOCENTE')")
    public ResponseEntity<List<Sesion>> listarPorAsignacion(
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
                sesionService.listarPorAsignacion(idAsignacion)
        );
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMINISTRADOR', 'DOCENTE')")
    public ResponseEntity<Sesion> registrarSesion(
            @RequestBody SesionDto dto,
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
                sesionService.registrarSesion(dto)
        );
    }

    @PutMapping("/{idSesion}")
    @PreAuthorize("hasAnyRole('ADMINISTRADOR', 'DOCENTE')")
    public ResponseEntity<Sesion> actualizarSesion(
            @PathVariable Integer idSesion,
            @RequestBody SesionDto dto,
            Authentication authentication
    ) {

        if (esDocente(authentication)) {

            Integer idDocente =
                    (Integer) authentication.getDetails();

            Sesion sesionActual =
                    sesionService.obtenerSesionPorId(idSesion);

            asignacionDocenteService.validarAsignacionDelDocente(
                    sesionActual.getAsignacionDocente().getIdAsignacion(),
                    idDocente
            );

            asignacionDocenteService.validarAsignacionDelDocente(
                    dto.getIdAsignacion(),
                    idDocente
            );
        }

        return ResponseEntity.ok(
                sesionService.actualizarSesion(idSesion, dto)
        );
    }

    @PatchMapping("/{idSesion}")
    @PreAuthorize("hasAnyRole('ADMINISTRADOR', 'DOCENTE')")
    public ResponseEntity<Sesion> actualizarParcialmente(
            @PathVariable Integer idSesion,
            @RequestBody SesionDto dto,
            Authentication authentication
    ) {

        if (esDocente(authentication)) {

            Integer idDocente =
                    (Integer) authentication.getDetails();

            Sesion sesionActual =
                    sesionService.obtenerSesionPorId(idSesion);

            asignacionDocenteService.validarAsignacionDelDocente(
                    sesionActual.getAsignacionDocente().getIdAsignacion(),
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
                sesionService.actualizarParcialmente(idSesion, dto)
        );
    }

    @DeleteMapping("/{idSesion}")
    @PreAuthorize("hasAnyRole('ADMINISTRADOR', 'DOCENTE')")
    public ResponseEntity<Map<String, String>> eliminarSesion(
            @PathVariable Integer idSesion,
            Authentication authentication
    ) {

        if (esDocente(authentication)) {

            Integer idDocente =
                    (Integer) authentication.getDetails();

            Sesion sesion =
                    sesionService.obtenerSesionPorId(idSesion);

            asignacionDocenteService.validarAsignacionDelDocente(
                    sesion.getAsignacionDocente().getIdAsignacion(),
                    idDocente
            );
        }

        sesionService.eliminarSesion(idSesion);

        return ResponseEntity.ok(
                Map.of(
                        "mensaje",
                        "Sesión eliminada correctamente"
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