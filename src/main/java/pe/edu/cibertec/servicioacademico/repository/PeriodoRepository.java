package pe.edu.cibertec.servicioacademico.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.edu.cibertec.servicioacademico.model.Periodo;

public interface PeriodoRepository extends JpaRepository<Periodo, Integer> {
}