package ar.edu.unnoba.poo2025.torneos.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.edu.unnoba.poo2025.torneos.exception.InvalidCredentialsException;
import ar.edu.unnoba.poo2025.torneos.model.AdministradorModel;
import ar.edu.unnoba.poo2025.torneos.model.ParticipanteModel;
import ar.edu.unnoba.poo2025.torneos.util.JwtTokenUtil;
import ar.edu.unnoba.poo2025.torneos.util.PasswordEncoder;

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
    public String authenticate(ParticipanteModel participante) {
        try {
            ParticipanteModel participanteGuardado = participanteService.obtenerPorEmail(participante.getEmail());
            if (!passwordEncoder.verificar(participante.getContraseña(), participanteGuardado.getContraseña())) {
                throw new InvalidCredentialsException("Email o contraseña incorrectos.");
            }
            return jwtTokenUtil.generarToken(participanteGuardado.getEmail(), participanteGuardado);
        } catch (RuntimeException e) {
            throw new InvalidCredentialsException("Email o contraseña incorrectos.");
        }
    }

    @Override
    public String authenticateAdmin(AdministradorModel admin) {
        try {
            AdministradorModel adminGuardado = adminService.obtenerPorEmail(admin.getEmail());
            if (!passwordEncoder.verificar(admin.getContraseña(), adminGuardado.getContraseña())) {
                throw new InvalidCredentialsException("Email o contraseña incorrectos.");
            }
            return jwtTokenUtil.generarToken(adminGuardado.getEmail());
        } catch (RuntimeException e) {
            throw new InvalidCredentialsException("Email o contraseña incorrectos.");
        }
    }

    @Override
    public String encodePassword(String rawPassword) {
        return passwordEncoder.codificar(rawPassword);
    }
}
