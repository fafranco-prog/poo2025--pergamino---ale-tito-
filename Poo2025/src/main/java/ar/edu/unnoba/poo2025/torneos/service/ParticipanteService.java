package ar.edu.unnoba.poo2025.torneos.service;

import java.util.List;
import java.util.Optional;

import ar.edu.unnoba.poo2025.torneos.model.ParticipanteModel;

public interface ParticipanteService {

    List<ParticipanteModel> obtenerParticipantes();

    Optional<ParticipanteModel> obtenerPorId(Long id);

    public void crear(ParticipanteModel participant) throws Exception;

    void eliminar(Long id);

    void actualizar(Long id, ParticipanteModel nuevosDatos);
}
