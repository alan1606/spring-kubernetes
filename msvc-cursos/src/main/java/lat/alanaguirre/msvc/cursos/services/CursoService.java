package lat.alanaguirre.msvc.cursos.services;

import lat.alanaguirre.msvc.cursos.entity.Curso;

import java.util.List;
import java.util.Optional;


public interface CursoService {

    List<Curso> findAll();

    Optional<Curso> findById(Long id);

    Curso save(Curso curso);

    void delete(Long id);
}
