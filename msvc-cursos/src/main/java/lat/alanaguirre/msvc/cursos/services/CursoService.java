package lat.alanaguirre.msvc.cursos.services;

import lat.alanaguirre.msvc.cursos.models.Usuario;
import lat.alanaguirre.msvc.cursos.models.entity.Curso;

import java.util.List;
import java.util.Optional;


public interface CursoService {

    List<Curso> findAll();

    Optional<Curso> findById(Long id);

    Curso save(Curso curso);

    void delete(Long id);

    Optional<Usuario> asignarUsuario(Usuario usuario, Long cursoId);

    Optional<Usuario> crearUsuario(Usuario usuario, Long cursoId);

    Optional<Usuario> eliminarUsuario(Usuario usuario, Long cursoId);
}
