package ar.edu.unnoba.poo2025.torneos.controller;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import ar.edu.unnoba.poo2025.torneos.service.AdministradorService;
import ar.edu.unnoba.poo2025.torneos.model.AdministradorModel;


                
                          
@RestController
@RequestMapping("/admins")
public class AdministradorController {

    @Autowired
    private AdministradorService adminService;

    @GetMapping("/getAdministrador")
    public List<AdministradorModel> getAdmins() {
        return adminService.obtenerAdministrador();
    }
    @GetMapping("/getAdminPorId/{id}")
    public Optional<AdministradorModel> getAdminPorId(@PathVariable Long id) {
        return adminService.obtenerPorId(id);
    }

    @PostMapping("/addAdmin")
    public AdministradorModel addAdmin(@RequestBody AdministradorModel admin) {
        return adminService.crear(admin);
    }

    @DeleteMapping("/delAdmin/{id}")
    public void delAdmin(@PathVariable Long id) {
        adminService.eliminar(id);
    }

    @PutMapping("/updateAdmin/{id}")
    public void updateAdmin(@PathVariable Long id, @RequestBody AdministradorModel adminActualizado) {
        adminService.actualizar(id, adminActualizado);
    }
} 

