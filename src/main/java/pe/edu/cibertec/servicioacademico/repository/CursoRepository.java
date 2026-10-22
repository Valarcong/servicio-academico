package pe.edu.cibertec.servicioacademico.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.edu.cibertec.servicioacademico.model.Curso;

public interface CursoRepository extends JpaRepository<Curso, Integer> {
}