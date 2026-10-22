package pe.edu.cibertec.servicioacademico.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.edu.cibertec.servicioacademico.model.Sesion;

import java.util.List;

public interface SesionRepository extends JpaRepository<Sesion, Integer> {

    List<Sesion> findByAsignacionDocenteIdAsignacion(Integer idAsignacion);
}