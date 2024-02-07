package lat.alanaguirre.msvc.usuarios.repositories;

import lat.alanaguirre.msvc.usuarios.models.entity.Usuario;
import org.springframework.data.repository.CrudRepository;

public interface UsuarioRepository extends CrudRepository<Usuario, Long> {
}
