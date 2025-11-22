package ar.edu.unnoba.poo2025.torneos.service;

import ar.edu.unnoba.poo2025.torneos.model.AdministradorModel;
import ar.edu.unnoba.poo2025.torneos.model.ParticipanteModel;

public interface AuthorizationService {

    ParticipanteModel authorize(String token);

    AdministradorModel authorizeAdmin(String token);

    String getSubjectFromToken(String token);
}
