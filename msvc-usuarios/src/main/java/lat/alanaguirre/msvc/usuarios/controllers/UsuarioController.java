package lat.alanaguirre.msvc.usuarios.controllers;

import feign.Response;
import jakarta.validation.Valid;
import lat.alanaguirre.msvc.usuarios.models.entity.Usuario;
import lat.alanaguirre.msvc.usuarios.services.UsuarioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
public class UsuarioController {

    private UsuarioService service;

    public UsuarioController(UsuarioService service){
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<Usuario>> listar(){
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Usuario> buscarPorId(@PathVariable Long id){
        var usuario = service.findById(id);
        if(usuario.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(usuario.get());
    }

    @PostMapping
    public ResponseEntity<?> registrar(@Valid @RequestBody Usuario usuario, BindingResult result){

        if(result.hasErrors()){
            return validar(result);
        }
        if(usuario.getEmail()!=null  && !usuario.getEmail().isBlank() &&  service.existsByEmail(usuario.getEmail())){
            return ResponseEntity.badRequest().body(Collections.singletonMap("mensaje", "Ya existe un usuario con ese correo"));
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(usuario));
    }

    @PutMapping("/{id}")
    private ResponseEntity<?> modificar(@PathVariable Long id, @Valid @RequestBody Usuario usuario, BindingResult result){

        if(result.hasErrors()){
            return validar(result);
        }

        var usuarioOptional = service.findById(id);
        if(usuarioOptional.isEmpty()){
            return ResponseEntity.notFound().build();
        }

        var usuarioDb = usuarioOptional.get();

        if(usuario.getEmail()!=null && !usuario.getEmail().isBlank() && !usuario.getEmail().equalsIgnoreCase(usuarioDb.getEmail()) && service.existsByEmail(usuario.getEmail())){
            return ResponseEntity.badRequest().body(Collections.singletonMap("mensaje", "Ya existe un usuario con ese correo"));
        }

        usuarioDb.setEmail(usuario.getEmail());
        usuarioDb.setNombre(usuario.getNombre());
        usuarioDb.setPassword(usuario.getPassword());
        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(usuarioDb));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarPorId(@PathVariable Long id){
        var usuarioOptional = service.findById(id);
        if(usuarioOptional.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        service.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    private static ResponseEntity<Map<String, String>> validar(BindingResult result) {
        Map<String,String> errores = new HashMap<>();
        result.getFieldErrors().forEach(err ->{
            errores.put(err.getField(), "El campo " + err.getField() + " " + err.getDefaultMessage());
        });
        return ResponseEntity.badRequest().body(errores);
    }
}
