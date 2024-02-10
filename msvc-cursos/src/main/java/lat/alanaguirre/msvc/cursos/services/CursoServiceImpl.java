package lat.alanaguirre.msvc.cursos.services;

import lat.alanaguirre.msvc.cursos.clients.UsuarioClientRest;
import lat.alanaguirre.msvc.cursos.models.Usuario;
import lat.alanaguirre.msvc.cursos.models.entity.Curso;
import lat.alanaguirre.msvc.cursos.models.entity.CursoUsuario;
import lat.alanaguirre.msvc.cursos.repositories.CursoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class CursoServiceImpl implements CursoService{

    private CursoRepository repository;

    private UsuarioClientRest usuarioClientRest;
    public CursoServiceImpl(CursoRepository repository, UsuarioClientRest usuarioClientRest){
        this.repository = repository;
        this.usuarioClientRest = usuarioClientRest;
    }
    @Override
    @Transactional(readOnly = true)
    public List<Curso> findAll() {
        return (List<Curso>) repository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Curso> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    @Transactional
    public Curso save(Curso curso) {
        return repository.save(curso);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        repository.deleteById(id);
    }

    @Override
    @Transactional
    public Optional<Usuario> asignarUsuario(Usuario usuario, Long cursoId) {
        Optional<Curso> o = repository.findById(cursoId);
        if(o.isEmpty()){
            return Optional.empty();
        }
        Usuario usuarioMsvc = usuarioClientRest.buscarPorId(usuario.getId());
        Curso curso = o.get();
        CursoUsuario cursoUsuario = new CursoUsuario();
        cursoUsuario.setUsuarioId(usuario.getId());
        curso.addCursoUsuario(cursoUsuario);
        repository.save(curso);
        return Optional.of(usuarioMsvc);
    }

    @Override
    @Transactional
    public Optional<Usuario> crearUsuario(Usuario usuario, Long cursoId) {
        Optional<Curso> o = repository.findById(cursoId);
        if(o.isEmpty()){
            return Optional.empty();
        }
        Usuario usuarioMsvc = usuarioClientRest.registrar(usuario);
        Curso curso = o.get();
        CursoUsuario cursoUsuario = new CursoUsuario();
        cursoUsuario.setUsuarioId(usuario.getId());
        curso.addCursoUsuario(cursoUsuario);
        repository.save(curso);
        return Optional.of(usuarioMsvc);
    }

    @Override
    @Transactional
    public Optional<Usuario> eliminarUsuario(Usuario usuario, Long cursoId) {
        Optional<Curso> o = repository.findById(cursoId);
        if(o.isEmpty()){
            return Optional.empty();
        }
        Usuario usuarioMsvc = usuarioClientRest.buscarPorId(usuario.getId());
        Curso curso = o.get();
        CursoUsuario cursoUsuario = new CursoUsuario();
        cursoUsuario.setUsuarioId(usuario.getId());
        curso.removerCursoUsuario(cursoUsuario);
        repository.save(curso);
        return Optional.of(usuarioMsvc);
    }
}
