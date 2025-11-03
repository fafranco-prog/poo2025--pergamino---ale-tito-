package ar.edu.unnoba.poo2025.torneos.service;

import ar.edu.unnoba.poo2025.torneos.model.ParticipanteModel;

public interface AuthenticationService {

    public String authenticate(ParticipanteModel participant) throws Exception;

}
