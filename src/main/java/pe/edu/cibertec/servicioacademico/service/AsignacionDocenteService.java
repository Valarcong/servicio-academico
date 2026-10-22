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

    public AsignacionDocente actualizarAsignacion(
            Integer idAsignacion,
            AsignacionDocenteDto dto
    ) {

        AsignacionDocente asignacion =
                obtenerAsignacionPorId(idAsignacion);

        Curso curso = cursoService.obtenerCursoPorId(
                dto.getIdCurso()
        );

        Periodo periodo = periodoService.obtenerPeriodoPorId(
                dto.getIdPeriodo()
        );

        asignacion.setCurso(curso);
        asignacion.setPeriodo(periodo);
        asignacion.setIdDocente(dto.getIdDocente());

        return asignacionDocenteRepository.save(asignacion);
    }

    public void eliminarAsignacion(Integer idAsignacion) {

        AsignacionDocente asignacion =
                obtenerAsignacionPorId(idAsignacion);

        asignacionDocenteRepository.delete(asignacion);
    }

    public AsignacionDocente actualizarParcialmente(
            Integer idAsignacion,
            AsignacionDocenteDto dto
    ) {

        AsignacionDocente asignacion =
                obtenerAsignacionPorId(idAsignacion);

        if (dto.getIdCurso() != null) {
            Curso curso = cursoService.obtenerCursoPorId(
                    dto.getIdCurso()
            );

            asignacion.setCurso(curso);
        }

        if (dto.getIdPeriodo() != null) {
            Periodo periodo = periodoService.obtenerPeriodoPorId(
                    dto.getIdPeriodo()
            );

            asignacion.setPeriodo(periodo);
        }

        if (dto.getIdDocente() != null) {
            asignacion.setIdDocente(dto.getIdDocente());
        }

        return asignacionDocenteRepository.save(asignacion);
    }

    public AsignacionDocente validarAsignacionDelDocente(
            Integer idAsignacion,
            Integer idDocente
    ) {

        AsignacionDocente asignacion =
                obtenerAsignacionPorId(idAsignacion);

        if (!asignacion.getIdDocente().equals(idDocente)) {
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN,
                    "No tiene acceso a esta asignación"
            );
        }

        return asignacion;
    }
}