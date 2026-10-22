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
}