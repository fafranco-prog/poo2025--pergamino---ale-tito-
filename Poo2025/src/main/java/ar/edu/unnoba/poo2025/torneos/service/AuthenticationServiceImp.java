package ar.edu.unnoba.poo2025.torneos.service;

import ar.edu.unnoba.poo2025.torneos.model.AdministradorModel;
import ar.edu.unnoba.poo2025.torneos.model.ParticipanteModel;
import ar.edu.unnoba.poo2025.torneos.util.JwtTokenUtil;
import ar.edu.unnoba.poo2025.torneos.util.PasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationServiceImp implements AuthenticationService {

    @Autowired
    private ParticipanteService participanteService;
    @Autowired 
    private AdministradorServiceImp adminService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Override
    public String authenticate(ParticipanteModel participante) throws Exception {
        ParticipanteModel participanteGuardado = participanteService.obtenerPorEmail(participante.getEmail());
        if (participanteGuardado == null) {
            throw new Exception("El email no existe.");
        }
        if (!passwordEncoder.verificar(participante.getContraseña(), participanteGuardado.getContraseña())) {
            throw new Exception("Contraseña incorrecta.");
        }

        return jwtTokenUtil.generarToken(participanteGuardado.getEmail());
    }

    @Override
    public String authenticateAdmin(AdministradorModel admin) throws Exception {
        AdministradorModel adminGuardado = adminService.obtenerPorEmail(admin.getEmail());
        if (adminGuardado == null) {
            throw new Exception("El email no existe.");
        } 
        if (!passwordEncoder.verificar(admin.getContraseña(), adminGuardado.getContraseña())) {
            throw new Exception("Contraseña incorrecta.");
        }

        return jwtTokenUtil.generarToken(adminGuardado.getEmail());
    }
}
