package lat.alanaguirre.msvc.usuarios.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "msvc-cursos", url = "host.docker.internal:8002")
public interface CursoClienteRest {

    @DeleteMapping("/eliminar-curso-usuario/{usuarioId}")
    public void eliminarUsuario(@PathVariable Long usuarioId);
}
