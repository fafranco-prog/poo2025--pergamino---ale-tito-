package ar.edu.unnoba.poo2025;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import ar.edu.unnoba.poo2025.torneos.model.AdministradorModel;
import ar.edu.unnoba.poo2025.torneos.repository.AdministradorRepository;
import ar.edu.unnoba.poo2025.torneos.util.PasswordEncoder;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private AdministradorRepository administradorRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        if (administradorRepository.count() == 0) {
            AdministradorModel admin = new AdministradorModel();
            admin.setEmail("poo@unnoba.edu.ar");
            admin.setContraseña(passwordEncoder.codificar("2025"));
            administradorRepository.save(admin);
            System.out.println("Administrador creado con éxito.");
            System.out.println("Email: " + admin.getEmail());
            System.out.println("Contraseña: " + admin.getContraseña());
        }
    }
}
