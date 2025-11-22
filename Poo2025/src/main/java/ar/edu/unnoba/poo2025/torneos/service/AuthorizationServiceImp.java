package ar.edu.unnoba.poo2025.torneos.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.edu.unnoba.poo2025.torneos.model.AdministradorModel;
import ar.edu.unnoba.poo2025.torneos.model.ParticipanteModel;
import ar.edu.unnoba.poo2025.torneos.util.JwtTokenUtil;

@Service
public class AuthorizationServiceImp implements AuthorizationService {

    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private ParticipanteService participanteService;
    @Autowired
    private AdministradorServiceImp adminService;

    @Override
    public ParticipanteModel authorize(String token) {

        jwtTokenUtil.validarToken(token);
        String email = jwtTokenUtil.getSubject(token);
        ParticipanteModel participante = participanteService.obtenerPorEmail(email);
        return participante;
    }

    @Override
    public AdministradorModel authorizeAdmin(String token) {

        jwtTokenUtil.validarToken(token);
        String email = jwtTokenUtil.getSubject(token);
        AdministradorModel adminModel = adminService.obtenerPorEmail(email);
        return adminModel;
    }

    @Override
    public String getSubjectFromToken(String token) {
        return jwtTokenUtil.getSubject(token);
    }
}
