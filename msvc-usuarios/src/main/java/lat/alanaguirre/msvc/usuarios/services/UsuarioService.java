package lat.alanaguirre.msvc.usuarios.services;

import lat.alanaguirre.msvc.usuarios.models.entity.Usuario;

import java.util.List;
import java.util.Optional;

public interface UsuarioService {
    List<Usuario> findAll();
    Optional<Usuario> findById(Long id);
    Usuario save(Usuario usuario);
    void delete(Long id);

    Optional<Usuario> findByEmail(String email);

    boolean existsByEmail(String email);
}
