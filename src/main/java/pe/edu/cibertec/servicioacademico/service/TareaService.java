package pe.edu.cibertec.servicioacademico.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import pe.edu.cibertec.servicioacademico.dto.TareaDto;
import pe.edu.cibertec.servicioacademico.model.AsignacionDocente;
import pe.edu.cibertec.servicioacademico.model.Tarea;
import pe.edu.cibertec.servicioacademico.repository.TareaRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TareaService {

    private final TareaRepository tareaRepository;
    private final AsignacionDocenteService asignacionDocenteService;

    public List<Tarea> listarTareas() {
        return tareaRepository.findAll();
    }

    public Tarea obtenerTareaPorId(Integer idTarea) {
        return tareaRepository.findById(idTarea)
                .orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Tarea no encontrada: " + idTarea
                        )
                );
    }

    public List<Tarea> listarPorAsignacion(Integer idAsignacion) {
        return tareaRepository
                .findByAsignacionDocenteIdAsignacion(idAsignacion);
    }

    public Tarea registrarTarea(TareaDto dto) {

        AsignacionDocente asignacion =
                asignacionDocenteService.obtenerAsignacionPorId(
                        dto.getIdAsignacion()
                );

        Tarea tarea = new Tarea();

        tarea.setAsignacionDocente(asignacion);
        tarea.setTitulo(dto.getTitulo());
        tarea.setDescripcion(dto.getDescripcion());
        tarea.setFechaPublicacion(dto.getFechaPublicacion());
        tarea.setFechaEntrega(dto.getFechaEntrega());
        tarea.setActivo(true);

        return tareaRepository.save(tarea);
    }

    public Tarea actualizarTarea(
            Integer idTarea,
            TareaDto dto
    ) {

        Tarea tarea = obtenerTareaPorId(idTarea);

        AsignacionDocente asignacion =
                asignacionDocenteService.obtenerAsignacionPorId(
                        dto.getIdAsignacion()
                );

        tarea.setAsignacionDocente(asignacion);
        tarea.setTitulo(dto.getTitulo());
        tarea.setDescripcion(dto.getDescripcion());
        tarea.setFechaPublicacion(dto.getFechaPublicacion());
        tarea.setFechaEntrega(dto.getFechaEntrega());

        return tareaRepository.save(tarea);
    }

    public Tarea actualizarParcialmente(
            Integer idTarea,
            TareaDto dto
    ) {

        Tarea tarea = obtenerTareaPorId(idTarea);

        if (dto.getIdAsignacion() != null) {
            AsignacionDocente asignacion =
                    asignacionDocenteService.obtenerAsignacionPorId(
                            dto.getIdAsignacion()
                    );

            tarea.setAsignacionDocente(asignacion);
        }

        if (dto.getTitulo() != null) {
            tarea.setTitulo(dto.getTitulo());
        }

        if (dto.getDescripcion() != null) {
            tarea.setDescripcion(dto.getDescripcion());
        }

        if (dto.getFechaPublicacion() != null) {
            tarea.setFechaPublicacion(dto.getFechaPublicacion());
        }

        if (dto.getFechaEntrega() != null) {
            tarea.setFechaEntrega(dto.getFechaEntrega());
        }

        return tareaRepository.save(tarea);
    }

    public void eliminarTarea(Integer idTarea) {

        Tarea tarea = obtenerTareaPorId(idTarea);

        tareaRepository.delete(tarea);
    }
}