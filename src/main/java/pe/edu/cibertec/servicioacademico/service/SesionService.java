package pe.edu.cibertec.servicioacademico.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import pe.edu.cibertec.servicioacademico.dto.SesionDto;
import pe.edu.cibertec.servicioacademico.model.AsignacionDocente;
import pe.edu.cibertec.servicioacademico.model.Sesion;
import pe.edu.cibertec.servicioacademico.repository.SesionRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SesionService {

    private final SesionRepository sesionRepository;
    private final AsignacionDocenteService asignacionDocenteService;

    public List<Sesion> listarSesiones() {
        return sesionRepository.findAll();
    }

    public Sesion obtenerSesionPorId(Integer idSesion) {
        return sesionRepository.findById(idSesion)
                .orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Sesión no encontrada: " + idSesion
                        )
                );
    }

    public List<Sesion> listarPorAsignacion(Integer idAsignacion) {
        return sesionRepository
                .findByAsignacionDocenteIdAsignacion(idAsignacion);
    }

    public Sesion registrarSesion(SesionDto dto) {

        AsignacionDocente asignacion =
                asignacionDocenteService.obtenerAsignacionPorId(
                        dto.getIdAsignacion()
                );

        Sesion sesion = new Sesion();

        sesion.setAsignacionDocente(asignacion);
        sesion.setNumeroSesion(dto.getNumeroSesion());
        sesion.setTitulo(dto.getTitulo());
        sesion.setDescripcion(dto.getDescripcion());
        sesion.setFecha(dto.getFecha());
        sesion.setActivo(true);

        return sesionRepository.save(sesion);
    }

    public Sesion actualizarSesion(
            Integer idSesion,
            SesionDto dto
    ) {

        Sesion sesion = obtenerSesionPorId(idSesion);

        AsignacionDocente asignacion =
                asignacionDocenteService.obtenerAsignacionPorId(
                        dto.getIdAsignacion()
                );

        sesion.setAsignacionDocente(asignacion);
        sesion.setNumeroSesion(dto.getNumeroSesion());
        sesion.setTitulo(dto.getTitulo());
        sesion.setDescripcion(dto.getDescripcion());
        sesion.setFecha(dto.getFecha());

        return sesionRepository.save(sesion);
    }

    public void eliminarSesion(Integer idSesion) {

        Sesion sesion = obtenerSesionPorId(idSesion);

        sesionRepository.delete(sesion);
    }

    public Sesion actualizarParcialmente(
            Integer idSesion,
            SesionDto dto
    ) {

        Sesion sesion = obtenerSesionPorId(idSesion);

        if (dto.getIdAsignacion() != null) {
            AsignacionDocente asignacion =
                    asignacionDocenteService.obtenerAsignacionPorId(
                            dto.getIdAsignacion()
                    );

            sesion.setAsignacionDocente(asignacion);
        }

        if (dto.getNumeroSesion() != null) {
            sesion.setNumeroSesion(dto.getNumeroSesion());
        }

        if (dto.getTitulo() != null) {
            sesion.setTitulo(dto.getTitulo());
        }

        if (dto.getDescripcion() != null) {
            sesion.setDescripcion(dto.getDescripcion());
        }

        if (dto.getFecha() != null) {
            sesion.setFecha(dto.getFecha());
        }

        return sesionRepository.save(sesion);
    }




}