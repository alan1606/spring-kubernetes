package lat.alanaguirre.msvc.cursos.services;

import lat.alanaguirre.msvc.cursos.entity.Curso;
import lat.alanaguirre.msvc.cursos.repositories.CursoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CursoServiceImpl implements CursoService{

    private CursoRepository repository;
    public CursoServiceImpl(CursoRepository repository){
        this.repository = repository;
    }
    @Override
    public List<Curso> findAll() {
        return (List<Curso>) repository.findAll();
    }

    @Override
    public Optional<Curso> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public Curso save(Curso curso) {
        return repository.save(curso);
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }
}
