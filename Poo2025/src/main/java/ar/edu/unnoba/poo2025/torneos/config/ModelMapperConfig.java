package ar.edu.unnoba.poo2025.torneos.config;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import ar.edu.unnoba.poo2025.torneos.dto.AdminResponseDTO;
import ar.edu.unnoba.poo2025.torneos.dto.AuthenticationRequestDTO;
import ar.edu.unnoba.poo2025.torneos.dto.CompetenciaResponseDTO;
import ar.edu.unnoba.poo2025.torneos.dto.CrearAdminRequestDTO;
import ar.edu.unnoba.poo2025.torneos.dto.CrearCompetenciaDTO;
import ar.edu.unnoba.poo2025.torneos.dto.CrearParticipanteRequestDTO;
import ar.edu.unnoba.poo2025.torneos.dto.CrearTorneoDTO;
import ar.edu.unnoba.poo2025.torneos.dto.InscripcionDetalleDTO;
import ar.edu.unnoba.poo2025.torneos.dto.InscripcionResponseDTO;
import ar.edu.unnoba.poo2025.torneos.dto.ParticipanteRequestDTO;
import ar.edu.unnoba.poo2025.torneos.dto.TorneoActivoResponseDTO;
import ar.edu.unnoba.poo2025.torneos.dto.TorneoResponse2DTO;
import ar.edu.unnoba.poo2025.torneos.dto.TorneoResponseDTO;
import ar.edu.unnoba.poo2025.torneos.model.AdministradorModel;
import ar.edu.unnoba.poo2025.torneos.model.CompetenciaModel;
import ar.edu.unnoba.poo2025.torneos.model.InscripcionModel;
import ar.edu.unnoba.poo2025.torneos.model.ParticipanteModel;
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
        typeMapTorneoFromCrearTorneoDTO.addMappings(mapper -> {
            mapper.map(CrearTorneoDTO::getNombre, TorneoModel::setNombre);
            mapper.map(CrearTorneoDTO::getDescripcion, TorneoModel::setDescripcion);
            mapper.map(CrearTorneoDTO::getFechaFin, TorneoModel::setFechaFin);
            mapper.map(CrearTorneoDTO::getFechaIni, TorneoModel::setFechaIni);
        });
        TypeMap<CrearCompetenciaDTO, CompetenciaModel> typeMapCompetenciaFromCrearCompetenciaDTO = modelMapper.createTypeMap(CrearCompetenciaDTO.class, CompetenciaModel.class);
        typeMapCompetenciaFromCrearCompetenciaDTO.addMappings(mapper -> {
            mapper.map(CrearCompetenciaDTO::getNombre, CompetenciaModel::setNombre);
            mapper.map(CrearCompetenciaDTO::getCupos, CompetenciaModel::setCupos);
            mapper.map(CrearCompetenciaDTO::getPrecio, CompetenciaModel::setPrecioBase);
        });

        TypeMap<CrearAdminRequestDTO, AdministradorModel> typeMapAdminFromCrearAdminDTO = modelMapper.createTypeMap(CrearAdminRequestDTO.class, AdministradorModel.class);
        typeMapAdminFromCrearAdminDTO.addMappings(mapper -> {
            mapper.map(CrearAdminRequestDTO::getPassword, AdministradorModel::setContraseña);
            mapper.map(CrearAdminRequestDTO::getEmail, AdministradorModel::setEmail);
        });

        TypeMap<AuthenticationRequestDTO, ParticipanteModel> typeMapParticipanteFromAuthenticationDTO
                = modelMapper.createTypeMap(AuthenticationRequestDTO.class, ParticipanteModel.class);
        typeMapParticipanteFromAuthenticationDTO.addMappings(mapper
                -> mapper.map(AuthenticationRequestDTO::getPassword, ParticipanteModel::setContraseña));

        TypeMap<AuthenticationRequestDTO, AdministradorModel> typeMapAdminFromAuthenticationDTO
                = modelMapper.createTypeMap(AuthenticationRequestDTO.class, AdministradorModel.class);
        typeMapAdminFromAuthenticationDTO.addMappings(mapper
                -> mapper.map(AuthenticationRequestDTO::getPassword, AdministradorModel::setContraseña));

        TypeMap<TorneoModel, TorneoResponseDTO> typeMapTorneoFromDTO
                = modelMapper.createTypeMap(TorneoModel.class, TorneoResponseDTO.class);

        typeMapTorneoFromDTO.addMappings(mapper -> {
            mapper.map(TorneoModel::getId, TorneoResponseDTO::setId);
            mapper.map(TorneoModel::getNombre, TorneoResponseDTO::setNombre);
            mapper.map(TorneoModel::getDescripcion, TorneoResponseDTO::setDescripcion);
        });

        TypeMap<TorneoModel, TorneoResponse2DTO> typeMapTorneoFromDTO2
                = modelMapper.createTypeMap(TorneoModel.class, TorneoResponse2DTO.class);
        typeMapTorneoFromDTO2.addMappings(mapper -> {
            mapper.map(TorneoModel::getId, TorneoResponse2DTO::setId);
            mapper.map(TorneoModel::getNombre, TorneoResponse2DTO::setNombre);
            mapper.map(TorneoModel::getFechaFin, TorneoResponse2DTO::setFechaFin);
            mapper.map(TorneoModel::getFechaIni, TorneoResponse2DTO::setFechaIni);
            mapper.map(TorneoModel::getPublicado, TorneoResponse2DTO::setPublicado);
        });

        TypeMap<AdministradorModel, AdminResponseDTO> typeMapAdminDTO
                = modelMapper.createTypeMap(AdministradorModel.class, AdminResponseDTO.class);

        typeMapAdminDTO.addMappings(mapper -> {
            mapper.map(AdministradorModel::getId, AdminResponseDTO::setId);
            mapper.map(AdministradorModel::getEmail, AdminResponseDTO::setEmail);
        });

        TypeMap<ParticipanteRequestDTO, ParticipanteModel> typeMapParticipanteFromParticipanteRequestDTO
                = modelMapper.createTypeMap(ParticipanteRequestDTO.class, ParticipanteModel.class);

        typeMapParticipanteFromParticipanteRequestDTO.addMappings(mapper -> {
            mapper.map(ParticipanteRequestDTO::getEmail, ParticipanteModel::setEmail);
            mapper.map(ParticipanteRequestDTO::getPassword, ParticipanteModel::setContraseña);
            mapper.map(ParticipanteRequestDTO::getFirstName, ParticipanteModel::setNombre);
            mapper.map(ParticipanteRequestDTO::getLastName, ParticipanteModel::setApellido);
            mapper.map(ParticipanteRequestDTO::getDocumentType, ParticipanteModel::setTipoDni);
            mapper.map(ParticipanteRequestDTO::getDocumentNumber, ParticipanteModel::setNumeroDni);
        });

        TypeMap<CompetenciaModel, CompetenciaResponseDTO> typeMapCompetenciaFromCompetenciaResponseDTO
                = modelMapper.createTypeMap(CompetenciaModel.class, CompetenciaResponseDTO.class);

        typeMapCompetenciaFromCompetenciaResponseDTO.addMappings(mapper -> {
            mapper.map(CompetenciaModel::getId, CompetenciaResponseDTO::setId);
            mapper.map(CompetenciaModel::getNombre, CompetenciaResponseDTO::setNombre);
            mapper.map(CompetenciaModel::getCupos, CompetenciaResponseDTO::setCupos);
            mapper.map(CompetenciaModel::getPrecioBase, CompetenciaResponseDTO::setPrecioBase);
        });

        TypeMap<InscripcionModel, InscripcionResponseDTO> typeMapInscripcionFromInscripcionResponseDTO
                = modelMapper.createTypeMap(InscripcionModel.class, InscripcionResponseDTO.class);

        typeMapInscripcionFromInscripcionResponseDTO.addMappings(mapper -> {
            mapper.map(InscripcionModel::getId, InscripcionResponseDTO::setId);
            mapper.map(InscripcionModel::getFechaInscripcion, InscripcionResponseDTO::setFecha);
            mapper.map(InscripcionModel::getPrecioPagado, InscripcionResponseDTO::setValor);
            mapper.map(i -> i.getCompetencia().getId(), InscripcionResponseDTO::setCompetenciaId);
            mapper.map(i -> i.getCompetencia().getNombre(), InscripcionResponseDTO::setCompetenciaNombre);
            mapper.map(i -> i.getCompetencia().getTorneo().getId(), InscripcionResponseDTO::setTorneoId);
            mapper.map(i -> i.getCompetencia().getTorneo().getNombre(), InscripcionResponseDTO::setTorneoNombre);
        });

        TypeMap<InscripcionModel, InscripcionDetalleDTO> typeMapInscripcionFromInscripcionDetalleDTO
                = modelMapper.createTypeMap(InscripcionModel.class, InscripcionDetalleDTO.class);

        typeMapInscripcionFromInscripcionDetalleDTO.addMappings(mapper -> {
            mapper.map(InscripcionModel::getId, InscripcionDetalleDTO::setId);
            mapper.map(InscripcionModel::getFechaInscripcion, InscripcionDetalleDTO::setFecha);
            mapper.map(InscripcionModel::getPrecioPagado, InscripcionDetalleDTO::setValor);
            mapper.map(i -> i.getCompetencia().getId(), InscripcionDetalleDTO::setCompetenciaId);
            mapper.map(i -> i.getCompetencia().getNombre(), InscripcionDetalleDTO::setCompetenciaNombre);
            mapper.map(i -> i.getCompetencia().getTorneo().getId(), InscripcionDetalleDTO::setTorneoId);
            mapper.map(i -> i.getCompetencia().getTorneo().getNombre(), InscripcionDetalleDTO::setTorneoNombre);
            mapper.map(i -> i.getCompetencia().getTorneo().getDescripcion(), InscripcionDetalleDTO::setTorneoDescripcion);
            mapper.map(i -> i.getCompetencia().getTorneo().getFechaIni(), InscripcionDetalleDTO::setTorneoFechaInicio);
            mapper.map(i -> i.getCompetencia().getTorneo().getFechaFin(), InscripcionDetalleDTO::setTorneoFechaFin);
        });

        TypeMap<TorneoModel, TorneoActivoResponseDTO> typeMapTorneoActivoFromDTO
                = modelMapper.createTypeMap(TorneoModel.class, TorneoActivoResponseDTO.class);

        typeMapTorneoActivoFromDTO.addMappings(mapper -> {
            mapper.map(TorneoModel::getNombre, TorneoActivoResponseDTO::setNombre);
            mapper.map(TorneoModel::getDescripcion, TorneoActivoResponseDTO::setDescripcion);
            mapper.map(TorneoModel::getFechaIni, TorneoActivoResponseDTO::setFechaInicio);
            mapper.map(TorneoModel::getFechaFin, TorneoActivoResponseDTO::setFechaFin);
        });

        return modelMapper;
    }
}
