package ar.edu.unnoba.poo2025.torneos.resource;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import ar.edu.unnoba.poo2025.torneos.dto.AdminResponseDTO;
import ar.edu.unnoba.poo2025.torneos.dto.AuthenticationRequestDTO;
import ar.edu.unnoba.poo2025.torneos.dto.CrearAdminRequestDTO;
import ar.edu.unnoba.poo2025.torneos.dto.TorneoDetalleDTO;
import ar.edu.unnoba.poo2025.torneos.dto.TorneoResponse2DTO;
import ar.edu.unnoba.poo2025.torneos.model.AdministradorModel;
import ar.edu.unnoba.poo2025.torneos.model.TorneoModel;
import ar.edu.unnoba.poo2025.torneos.service.AdministradorServiceImp;
import ar.edu.unnoba.poo2025.torneos.service.AuthenticationServiceImp;
import ar.edu.unnoba.poo2025.torneos.service.AuthorizationServiceImp;
import ar.edu.unnoba.poo2025.torneos.service.TorneoServiceImp;
import ar.edu.unnoba.poo2025.torneos.util.JwtTokenUtil;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import ar.edu.unnoba.poo2025.torneos.dto.CrearTorneoDTO;
import ar.edu.unnoba.poo2025.torneos.service.AdministradorService;


@RestController
@RequestMapping("/admin")
public class AdministradorResource {

    @Autowired
    private AdministradorServiceImp adminService;
    @Autowired
    private AuthorizationServiceImp authorizationService;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
   


    @GetMapping("/getAdminPorId/{id}")
    public Optional<AdministradorModel> getAdminPorId(@PathVariable Long id) {
        return adminService.obtenerPorId(id);
    }
   @PutMapping("/updateAdmin/{id}")
    public void updateAdmin(@PathVariable Long id, @RequestBody AdministradorModel adminActualizado) {
        adminService.actualizar(id, adminActualizado);
    }

    //TP6
    @GetMapping("/cuentas")
    public ResponseEntity<?> getAdministradores(@RequestHeader("Authorization")String token){
        try {
            adminService.autorizar(token);
            List<AdminResponseDTO> response = adminService.obtenerAdministrador();
            
            return ResponseEntity.ok(response);
        }  
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("no autorizado");
        }
    } 
    @PostMapping("/cuentas")
    public ResponseEntity<?> addAdmin(@RequestHeader("Authorization")String token, @RequestBody CrearAdminRequestDTO adminDTO) {
        try {
            adminService.autorizar(token);
            adminService.crear(adminDTO);
            return new ResponseEntity<>(HttpStatus.CREATED);
        }   
        catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        }
    }      

    @DeleteMapping("/cuentas/{id}")
    public ResponseEntity<?> delAdmin(@RequestHeader("Authorization")String token, @PathVariable Long id) {
        try {
            adminService.autorizar(token);

            String emailAuth = jwtTokenUtil.getSubject(token);

            if(emailAuth == null){
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body("token invalido");
            }
            AdministradorModel adminAuth = adminService.obtenerPorEmail(emailAuth);
            if(adminAuth == null){
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body("administrador autenticado no encontrado");
            }
            if (adminAuth.getId().equals(id)){
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body("un administrador no puedo eliminarse a si mismo");
            }
            if (adminService.obtenerPorId(id).isEmpty()){
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("la cuenta de administrador no existe");
            }

            adminService.eliminar(id);
            return ResponseEntity.ok("administrador eliminado");
        } 
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body("No autorizado:");
        }

    }

    @GetMapping("torneos")
    public ResponseEntity<?> getTorneos(@RequestHeader("Authorization") String token){
        try { 
            adminService.autorizar(token);
            List<TorneoResponse2DTO> response = adminService.getTorneosOrdenadosDesc();
            return ResponseEntity.ok(response);
        
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
            .body("no autorizado: token invalido o expirado");
        } 
    }

    @GetMapping("/torneos/:id")
    public ResponseEntity<?> getTorneoPorId(@RequestHeader("Authorization") String token, @PathVariable Long id){
        try {
            adminService.autorizar(token);
            TorneoDetalleDTO response = adminService.getTorneoPorId(id);
            return ResponseEntity.ok(response);
        } 
        catch (SecurityException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("no autorizado");
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.singletonMap("error", e.getMessage()));
        }
        
    }
     
 
    @PostMapping("/torneos")
    public ResponseEntity<?> crearTorneo(@RequestHeader("Authorization") String token,@RequestBody CrearTorneoDTO torneoDTO){
        try {
            authorizationService.authorizeAdmin(token);
            adminService.crearTorneo(torneoDTO);
            return ResponseEntity.ok("se agrego correctamente");
        } 
        catch (SecurityException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("no autorizado");
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.singletonMap("error", e.getMessage()));
        }
    }
                         


    //crea el token a usar en el resto de endpoints del controlador
    @PostMapping(path = "/auth", produces = "application/json")
    public ResponseEntity<?> authentication(@RequestBody AuthenticationRequestDTO authenticationRequestDTO) {
        try {

            String token = adminService.generarToken(authenticationRequestDTO);
            Map<String, String> response = Collections.singletonMap("token", token);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
        }
    } 

}
