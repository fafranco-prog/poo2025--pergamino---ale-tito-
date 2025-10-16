package ar.edu.unnoba.poo2025.torneos.service;

import ar.edu.unnoba.poo2025.torneos.model.AdministradorModel;
import ar.edu.unnoba.poo2025.torneos.repository.AdministradorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AdministradorService {

    @Autowired
    private AdministradorRepository administradorRepository;

    public List<AdministradorModel> obtenerAdministrador() {
        return administradorRepository.findAll();
    }
    public Optional<AdministradorModel> obtenerPorId(Long id){
        return administradorRepository.findById(id);
    }

    public AdministradorModel crear(AdministradorModel administrador) {
        return administradorRepository.save(administrador);
    }

    public void eliminar(Long id) {
        administradorRepository.deleteById(id);
    }

    public void actualizar(Long id, AdministradorModel nuevosDatos) {
        AdministradorModel existente = administradorRepository.findById(id).orElse(null);
        if (existente != null) {
            existente.setEmail(nuevosDatos.getEmail());
            existente.setContraseña(nuevosDatos.getContraseña());
            administradorRepository.save(existente);
        }
    }
}
