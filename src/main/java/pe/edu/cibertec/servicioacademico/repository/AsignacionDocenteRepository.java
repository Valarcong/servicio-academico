package pe.edu.cibertec.servicioacademico.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.edu.cibertec.servicioacademico.model.AsignacionDocente;

import java.util.List;

public interface AsignacionDocenteRepository
        extends JpaRepository<AsignacionDocente, Integer> {

    List<AsignacionDocente> findByIdDocente(Integer idDocente);
}