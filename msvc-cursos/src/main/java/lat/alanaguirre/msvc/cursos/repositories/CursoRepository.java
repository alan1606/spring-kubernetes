package lat.alanaguirre.msvc.cursos.repositories;

import lat.alanaguirre.msvc.cursos.models.entity.Curso;
import org.springframework.data.repository.CrudRepository;

public interface CursoRepository extends CrudRepository<Curso, Long> {
}
