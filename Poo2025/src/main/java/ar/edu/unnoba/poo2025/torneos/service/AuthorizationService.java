package ar.edu.unnoba.poo2025.torneos.service;

import ar.edu.unnoba.poo2025.torneos.model.ParticipanteModel;

public interface AuthorizationService {

    public ParticipanteModel authorize(String token) throws Exception;
}
