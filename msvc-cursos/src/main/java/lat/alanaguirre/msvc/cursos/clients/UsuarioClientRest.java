package lat.alanaguirre.msvc.cursos.clients;

import jakarta.validation.Valid;
import lat.alanaguirre.msvc.cursos.models.Usuario;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "msvc-usuarios", url = "localhost:8001")
public interface UsuarioClientRest {

    @GetMapping("/{id}")
    Usuario buscarPorId(@PathVariable Long id);

    @PostMapping("/")
     Usuario registrar(@RequestBody Usuario usuario);
}
