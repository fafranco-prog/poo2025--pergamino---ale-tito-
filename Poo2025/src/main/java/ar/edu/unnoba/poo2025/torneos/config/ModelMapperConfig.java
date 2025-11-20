package ar.edu.unnoba.poo2025.torneos.config;

import ar.edu.unnoba.poo2025.torneos.dto.CrearParticipanteRequestDTO;
import ar.edu.unnoba.poo2025.torneos.model.ParticipanteModel;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import ar.edu.unnoba.poo2025.torneos.dto.AdminResponseDTO;
import ar.edu.unnoba.poo2025.torneos.dto.AuthenticationRequestDTO;
import ar.edu.unnoba.poo2025.torneos.dto.CrearAdminRequestDTO;
import ar.edu.unnoba.poo2025.torneos.dto.CrearTorneoDTO;
import ar.edu.unnoba.poo2025.torneos.dto.TorneoResponse2DTO;
import ar.edu.unnoba.poo2025.torneos.dto.TorneoResponseDTO;
import ar.edu.unnoba.poo2025.torneos.model.AdministradorModel;
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
        TypeMap<CrearTorneoDTO, TorneoModel> typeMapTorneoFromCrearTorneoDTO = modelMapper.createTypeMap(CrearTorneoDTO.class, TorneoModel.class);
        typeMapTorneoFromCrearTorneoDTO.addMappings(mapper-> {
                mapper.map(CrearTorneoDTO::getNombre, TorneoModel::setNombre);
                mapper.map(CrearTorneoDTO::getDescription, TorneoModel::setDescripcion);
                mapper.map(CrearTorneoDTO::getFechaFin, TorneoModel::setFechaFin);
                mapper.map(CrearTorneoDTO::getFechaIni, TorneoModel::setFechaIni);
        });
   
        
        TypeMap<CrearAdminRequestDTO, AdministradorModel> typeMapAdminFromCrearAdminDTO = modelMapper.createTypeMap(CrearAdminRequestDTO.class, AdministradorModel.class);
        typeMapAdminFromCrearAdminDTO.addMappings(mapper-> {
            mapper.map(CrearAdminRequestDTO::getPassword, AdministradorModel::setContraseña);
            mapper.map(CrearAdminRequestDTO::getEmail, AdministradorModel::setEmail);
            });

        TypeMap<AuthenticationRequestDTO, ParticipanteModel> typeMapParticipanteFromAuthenticationDTO = 
        modelMapper.createTypeMap(AuthenticationRequestDTO.class, ParticipanteModel.class);
        typeMapParticipanteFromAuthenticationDTO.addMappings(mapper
                -> mapper.map(AuthenticationRequestDTO::getPassword, ParticipanteModel::setContraseña));

        TypeMap<AuthenticationRequestDTO, AdministradorModel> typeMapAdminFromAuthenticationDTO =
        modelMapper.createTypeMap(AuthenticationRequestDTO.class, AdministradorModel.class);
        typeMapAdminFromAuthenticationDTO.addMappings(mapper 
                ->mapper.map(AuthenticationRequestDTO::getPassword, AdministradorModel::setContraseña));




        TypeMap<TorneoModel, TorneoResponseDTO> typeMapTorneoFromDTO =
                modelMapper.createTypeMap(TorneoModel.class, TorneoResponseDTO.class);
           
        typeMapTorneoFromDTO.addMappings(mapper ->{
            mapper.map(TorneoModel::getId, TorneoResponseDTO::setId);
            mapper.map(TorneoModel::getNombre, TorneoResponseDTO::setNombre);
            mapper.map(TorneoModel::getDescripcion, TorneoResponseDTO::setDescripcion);
        });
        
        TypeMap<TorneoModel, TorneoResponse2DTO> typeMapTorneoFromDTO2 =
                modelMapper.createTypeMap(TorneoModel.class, TorneoResponse2DTO.class);
        typeMapTorneoFromDTO2.addMappings(mapper -> {
            mapper.map(TorneoModel::getId, TorneoResponse2DTO::setId);
            mapper.map(TorneoModel::getNombre, TorneoResponse2DTO::setNombre);
            mapper.map(TorneoModel::getFechaFin, TorneoResponse2DTO::setFechaFin);
            mapper.map(TorneoModel::getFechaIni, TorneoResponse2DTO::setFechaIni);
            mapper.map(TorneoModel::getPublicado, TorneoResponse2DTO::setPublicado);
        });
    
        TypeMap<AdministradorModel, AdminResponseDTO> typeMapAdminDTO   
                = modelMapper.createTypeMap(AdministradorModel.class, AdminResponseDTO.class);
        
        typeMapAdminDTO.addMappings(mapper ->{
            mapper.map(AdministradorModel::getId, AdminResponseDTO::setId);
            mapper.map(AdministradorModel::getEmail, AdminResponseDTO::setEmail);
        });


        
        return modelMapper;
    }
}
