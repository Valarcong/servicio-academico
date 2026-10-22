package pe.edu.cibertec.servicioacademico.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import pe.edu.cibertec.servicioacademico.dto.AsignacionDocenteDto;
import pe.edu.cibertec.servicioacademico.model.AsignacionDocente;
import pe.edu.cibertec.servicioacademico.model.Curso;
import pe.edu.cibertec.servicioacademico.model.Periodo;
import pe.edu.cibertec.servicioacademico.repository.AsignacionDocenteRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AsignacionDocenteService {

    private final AsignacionDocenteRepository asignacionDocenteRepository;
    private final CursoService cursoService;
    private final PeriodoService periodoService;

    public List<AsignacionDocente> listarAsignaciones() {
        return asignacionDocenteRepository.findAll();
    }

    public AsignacionDocente obtenerAsignacionPorId(Integer idAsignacion) {

        return asignacionDocenteRepository.findById(idAsignacion)
                .orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Asignación no encontrada: " + idAsignacion
                        )
                );
    }

    public List<AsignacionDocente> listarPorDocente(Integer idDocente) {
        return asignacionDocenteRepository.findByIdDocente(idDocente);
    }

    public AsignacionDocente registrarAsignacion(
            AsignacionDocenteDto dto
    ) {

        Curso curso = cursoService.obtenerCursoPorId(
                dto.getIdCurso()
        );

        Periodo periodo = periodoService.obtenerPeriodoPorId(
                dto.getIdPeriodo()
        );

        AsignacionDocente asignacion = new AsignacionDocente();

        asignacion.setCurso(curso);
        asignacion.setPeriodo(periodo);
        asignacion.setIdDocente(dto.getIdDocente());
        asignacion.setActivo(true);

        return asignacionDocenteRepository.save(asignacion);
    }
}