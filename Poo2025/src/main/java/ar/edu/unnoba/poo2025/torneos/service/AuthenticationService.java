package ar.edu.unnoba.poo2025.torneos.service;

import ar.edu.unnoba.poo2025.torneos.model.AdministradorModel;
import ar.edu.unnoba.poo2025.torneos.model.ParticipanteModel;

public interface AuthenticationService {

    String authenticate(ParticipanteModel participante);

    String authenticateAdmin(AdministradorModel admin);

    String encodePassword(String rawPassword);
}
