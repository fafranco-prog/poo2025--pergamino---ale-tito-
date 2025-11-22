package ar.edu.unnoba.poo2025.torneos.service;

import java.util.List;

import ar.edu.unnoba.poo2025.torneos.model.ParticipanteModel;

public interface ParticipanteService {

    List<ParticipanteModel> obtenerParticipantes();

    ParticipanteModel obtenerPorId(Long id);

    public void crear(ParticipanteModel participant);

    public ParticipanteModel obtenerPorEmail(String email);

    void eliminar(Long id);

    void actualizar(Long id, ParticipanteModel nuevosDatos);
}
