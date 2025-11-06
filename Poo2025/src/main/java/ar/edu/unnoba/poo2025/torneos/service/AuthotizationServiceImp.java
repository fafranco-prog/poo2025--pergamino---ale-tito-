package ar.edu.unnoba.poo2025.torneos.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.edu.unnoba.poo2025.torneos.model.ParticipanteModel;
import ar.edu.unnoba.poo2025.torneos.util.JwtTokenUtil;

@Service
public class AuthotizationServiceImp implements AuthorizationService {

    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private ParticipanteService participanteService;

    @Override
    public ParticipanteModel authorize(String token) throws Exception {

        if (!jwtTokenUtil.validarToken(token)) {
            throw new Exception("El token no es valido");
        }
        String email = jwtTokenUtil.getSubject(token);
        ParticipanteModel participante = participanteService.obtenerPorEmail(email);
        if (participante == null) {
            throw new Exception("El email no existe.");
        }
        return participante;
    }
}
 