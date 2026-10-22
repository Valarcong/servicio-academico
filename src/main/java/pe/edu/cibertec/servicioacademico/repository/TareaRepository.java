package pe.edu.cibertec.servicioacademico.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.edu.cibertec.servicioacademico.model.Tarea;

import java.util.List;

public interface TareaRepository extends JpaRepository<Tarea, Integer> {

    List<Tarea> findByAsignacionDocenteIdAsignacion(Integer idAsignacion);
}