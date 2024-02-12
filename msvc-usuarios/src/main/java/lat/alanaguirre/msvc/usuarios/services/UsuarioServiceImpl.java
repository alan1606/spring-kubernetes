package lat.alanaguirre.msvc.usuarios.services;

import feign.FeignException;
import lat.alanaguirre.msvc.usuarios.client.CursoClienteRest;
import lat.alanaguirre.msvc.usuarios.models.entity.Usuario;
import lat.alanaguirre.msvc.usuarios.repositories.UsuarioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioServiceImpl implements  UsuarioService{
    private UsuarioRepository repository;
    private CursoClienteRest cursoClienteRest;

    public UsuarioServiceImpl(UsuarioRepository repository, CursoClienteRest cursoClienteRest){
        this.repository = repository;
        this.cursoClienteRest = cursoClienteRest;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Usuario> findAll() {
        return (List<Usuario>) repository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Usuario> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    @Transactional
    public Usuario save(Usuario usuario) {
        return repository.save(usuario);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        repository.deleteById(id);
        cursoClienteRest.eliminarUsuario(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Usuario> findByEmail(String email) {
        return repository.findByEmail(email);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByEmail(String email) {
        return repository.existsByEmail(email);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Usuario> findAllByIds(Iterable<Long> ids) {
        return (List<Usuario>) repository.findAllById(ids);
    }
}
