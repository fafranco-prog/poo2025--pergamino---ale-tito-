package ar.edu.unnoba.poo2025.torneos.config;

import ar.edu.unnoba.poo2025.torneos.dto.CrearParticipanteRequestDTO;
import ar.edu.unnoba.poo2025.torneos.model.ParticipanteModel;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import ar.edu.unnoba.poo2025.torneos.dto.AuthenticationRequestDTO;
import ar.edu.unnoba.poo2025.torneos.dto.TorneoResponseDTO;
import ar.edu.unnoba.poo2025.torneos.model.TorneoModel;

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


        TypeMap<TorneoModel, TorneoResponseDTO> torneoMap =
                modelMapper.createTypeMap(TorneoModel.class, TorneoResponseDTO.class);
           
        
        torneoMap.addMappings(mapper ->{
            mapper.map(TorneoModel::getId, TorneoResponseDTO::setId);
            mapper.map(TorneoModel::getNombre, TorneoResponseDTO::setNombre);
            mapper.map(TorneoModel::getDescripcion, TorneoResponseDTO::setDescripcion);
        });    
        return modelMapper;
    }
}
