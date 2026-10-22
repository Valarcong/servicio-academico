package pe.edu.cibertec.servicioacademico.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import pe.edu.cibertec.servicioacademico.model.Periodo;
import pe.edu.cibertec.servicioacademico.repository.PeriodoRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PeriodoService {

    private final PeriodoRepository periodoRepository;

    public List<Periodo> listarPeriodos() {
        return periodoRepository.findAll();
    }

    public Periodo obtenerPeriodoPorId(Integer idPeriodo) {
        return periodoRepository.findById(idPeriodo)
                .orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Periodo no encontrado: " + idPeriodo
                        )
                );
    }

    public Periodo registrarPeriodo(Periodo periodo) {
        periodo.setActivo(true);
        return periodoRepository.save(periodo);
    }

    public Periodo actualizarPeriodo(
            Integer idPeriodo,
            Periodo datos
    ) {

        Periodo periodo = obtenerPeriodoPorId(idPeriodo);

        periodo.setNombre(datos.getNombre());
        periodo.setFechaInicio(datos.getFechaInicio());
        periodo.setFechaFin(datos.getFechaFin());
        periodo.setActivo(datos.isActivo());

        return periodoRepository.save(periodo);
    }

    public void eliminarPeriodo(Integer idPeriodo) {

        Periodo periodo = obtenerPeriodoPorId(idPeriodo);

        periodoRepository.delete(periodo);
    }
}