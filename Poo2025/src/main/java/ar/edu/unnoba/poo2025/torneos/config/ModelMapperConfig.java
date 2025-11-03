package ar.edu.unnoba.poo2025.torneos.config;

import ar.edu.unnoba.poo2025.torneos.dto.CrearParticipanteRequestDTO;
import ar.edu.unnoba.poo2025.torneos.model.ParticipanteModel;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import ar.edu.unnoba.poo2025.torneos.dto.AuthenticationRequestDTO;

@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();

        // Configuración para mapear de CrearParticipanteRequestDTO a ParticipanteModel
        TypeMap<CrearParticipanteRequestDTO, ParticipanteModel> typeMapParticipanteFromCrearParticipanteDTO = modelMapper.createTypeMap(CrearParticipanteRequestDTO.class, ParticipanteModel.class);
        typeMapParticipanteFromCrearParticipanteDTO.addMappings(mapper
                -> mapper.map(CrearParticipanteRequestDTO::getPassword, ParticipanteModel::setContraseña)
        );

        TypeMap<AuthenticationRequestDTO, ParticipanteModel> typeMapParticipanteFromAuthenticationDTO = modelMapper.createTypeMap(AuthenticationRequestDTO.class, ParticipanteModel.class);
        typeMapParticipanteFromAuthenticationDTO.addMappings(mapper
                -> mapper.map(AuthenticationRequestDTO::getPassword, ParticipanteModel::setContraseña));

        return modelMapper;
    }
}
