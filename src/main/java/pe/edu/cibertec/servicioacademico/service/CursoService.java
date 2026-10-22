package pe.edu.cibertec.servicioacademico.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import pe.edu.cibertec.servicioacademico.model.Curso;
import pe.edu.cibertec.servicioacademico.repository.CursoRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CursoService {

    private final CursoRepository cursoRepository;

    public List<Curso> listarCursos() {
        return cursoRepository.findAll();
    }

    public Curso obtenerCursoPorId(Integer idCurso) {
        return cursoRepository.findById(idCurso)
                .orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Curso no encontrado: " + idCurso
                        )
                );
    }

    public Curso registrarCurso(Curso curso) {
        curso.setActivo(true);
        return cursoRepository.save(curso);
    }

    public Curso actualizarCurso(
            Integer idCurso,
            Curso datos
    ) {

        Curso curso = obtenerCursoPorId(idCurso);

        curso.setCodigo(datos.getCodigo());
        curso.setNombre(datos.getNombre());
        curso.setDescripcion(datos.getDescripcion());
        curso.setActivo(datos.isActivo());

        return cursoRepository.save(curso);
    }

    public void eliminarCurso(Integer idCurso) {

        Curso curso = obtenerCursoPorId(idCurso);

        cursoRepository.delete(curso);
    }
}