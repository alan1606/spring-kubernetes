package lat.alanaguirre.msvc.cursos.controllers;

import feign.FeignException;
import feign.Response;
import jakarta.validation.Valid;
import lat.alanaguirre.msvc.cursos.models.Usuario;
import lat.alanaguirre.msvc.cursos.models.entity.Curso;
import lat.alanaguirre.msvc.cursos.services.CursoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
public class CursoController {
    private CursoService service;

    public CursoController(CursoService service){
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<Curso>> findAll(){
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Curso> findById(@PathVariable Long id){
        Optional<Curso> cursoOptional = service.findById(id);
        if(cursoOptional.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(cursoOptional.get());
    }

    @PostMapping
    public ResponseEntity<?> guardar(@Valid @RequestBody Curso curso, BindingResult result){
        if(result.hasErrors()){
            return validar(result);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(curso));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> modificar(@PathVariable Long id, @Valid @RequestBody Curso curso, BindingResult result){
        if(result.hasErrors()){
            return validar(result);
        }

        Optional<Curso> cursoOptional = service.findById(id);
        if(cursoOptional.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        Curso cursoDb = cursoOptional.get();
        cursoDb.setNombre(curso.getNombre());
        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(cursoDb));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        Optional<Curso> cursoOptional = service.findById(id);
        if(cursoOptional.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        service.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }


    @PutMapping("/asignar-usuario/{cursoId}")
    public ResponseEntity<?> asignarUsuario(@PathVariable Long cursoId, @RequestBody Usuario usuario){
        Optional<Usuario> o;

        try{
            o = service.asignarUsuario(usuario, cursoId);
        }
        catch (FeignException ex){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.singletonMap("mensaje", "No existe el usuario o error en la comunicación: " + ex.getMessage()));
        }

        if(o.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(o.get());
    }


    @PostMapping("/crear-usuario/{cursoId}")
    public ResponseEntity<?> crearUsuario(@PathVariable Long cursoId, @RequestBody Usuario usuario){
        Optional<Usuario> o;

        try{
            o = service.crearUsuario(usuario, cursoId);
        }
        catch (FeignException ex){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.singletonMap("mensaje", "No se pudo crear el usuario, o error en comunicación: " + ex.getMessage()));
        }

        if(o.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(o.get());
    }

    @DeleteMapping("/eliminar-usuario/{cursoId}")
    public ResponseEntity<?> eliminarUsuario(@PathVariable Long cursoId, @RequestBody Usuario usuario){
        Optional<Usuario> o;

        try{
            o = service.eliminarUsuario(usuario, cursoId);
        }
        catch (FeignException ex){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.singletonMap("mensaje", "No existe el usuario, o error en comunicación: " + ex.getMessage()));
        }

        if(o.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(o.get());
    }


    private ResponseEntity<Map<String, String>> validar(BindingResult result) {
        Map<String,String> errores = new HashMap<>();
        result.getFieldErrors().forEach(err ->{
            errores.put(err.getField(), "El campo " + err.getField() + " " + err.getDefaultMessage());
        });
        return ResponseEntity.badRequest().body(errores);
    }

    
}
