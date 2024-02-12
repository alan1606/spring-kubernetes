package lat.alanaguirre.msvc.cursos.clients;

import lat.alanaguirre.msvc.cursos.models.Usuario;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "msvc-usuarios", url = "localhost:8001")
public interface UsuarioClientRest {

    @GetMapping("/{id}")
    Usuario buscarPorId(@PathVariable Long id);

    @PostMapping("/")
     Usuario registrar(@RequestBody Usuario usuario);

    @GetMapping("/usuarios")
    public List<Usuario> buscarPorIds(@RequestParam Iterable<Long> ids);
}
