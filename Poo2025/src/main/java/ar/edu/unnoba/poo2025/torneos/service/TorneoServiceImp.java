package ar.edu.unnoba.poo2025.torneos.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.edu.unnoba.poo2025.torneos.dto.ActualizarTorneoDTO;
import ar.edu.unnoba.poo2025.torneos.dto.TorneoDetalleDTO;
import ar.edu.unnoba.poo2025.torneos.exception.DuplicateResourceException;
import ar.edu.unnoba.poo2025.torneos.exception.ResourceNotFoundException;
import ar.edu.unnoba.poo2025.torneos.model.CompetenciaModel;
import ar.edu.unnoba.poo2025.torneos.model.InscripcionModel;
import ar.edu.unnoba.poo2025.torneos.model.TorneoModel;
import ar.edu.unnoba.poo2025.torneos.repository.TorneoRepository;

@Service
public class TorneoServiceImp implements TorneoService {

    @Autowired
    private TorneoRepository torneoRepository;

    @Override
    public List<TorneoModel> obtenerTorneos() {
        return torneoRepository.findAll();
    }

    private List<TorneoModel> findPublishedAndInProgressTournaments() {
        LocalDate hoy = LocalDate.now();
        return obtenerTorneos().stream()
                .filter(t -> {
                    LocalDate fin = t.getFechaFin();
                    boolean estaPublicado = Boolean.TRUE.equals(t.getPublicado());
                    boolean noHaFinalizado = fin == null || fin.isAfter(hoy);

                    return estaPublicado && noHaFinalizado;
                })
                .collect(Collectors.toList());
    }

    private List<TorneoModel> findPublishedAndFutureTournaments() {
        LocalDate hoy = LocalDate.now();
        return obtenerTorneos().stream()
                .filter(t -> {
                    LocalDate ini = t.getFechaIni();
                    boolean estaPublicado = Boolean.TRUE.equals(t.getPublicado());
                    boolean esFuturo = ini.isAfter(hoy);
                    return estaPublicado && esFuturo;
                })
                .collect(Collectors.toList());
    }

    @Override
    public TorneoModel obtenerPorId(Long id) {
        return torneoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No se encontró un torneo con el ID: " + id));
    }

    @Override
    public void eliminar(Long id) {
        obtenerPorId(id);
        torneoRepository.deleteById(id);
    }

    @Override
    public void actualizar(Long id, ActualizarTorneoDTO nuevosDatos) {
        TorneoModel existente = obtenerPorId(id);

        existente.setNombre(nuevosDatos.getNombre());
        existente.setDescripcion(nuevosDatos.getDescripcion());
        existente.setFechaIni(nuevosDatos.getFechaIni());
        existente.setFechaFin(nuevosDatos.getFechaFin());
        torneoRepository.save(existente);

    }

    @Override
    public List<TorneoModel> getTorneosOrdenadosDesc() {
        List<TorneoModel> aux = new ArrayList<>(obtenerTorneos());
        aux.sort((t1, t2) -> t2.getFechaIni().compareTo(t1.getFechaIni()));
        return aux;
    }

    @Override
    public TorneoDetalleDTO obtenerTorneoDetalleDTO(Long torneoId) {
        TorneoModel torneo = torneoRepository.findById(torneoId)
                .orElseThrow(() -> new ResourceNotFoundException("No se encontró un torneo con el ID: " + torneoId));

        int totalInscripciones = 0;
        BigDecimal montoTotal = BigDecimal.ZERO;
        if (torneo.getCompetencias() != null) {
            for (CompetenciaModel c : torneo.getCompetencias()) {
                if (c.getInscripciones() != null) {
                    totalInscripciones += c.getInscripciones().size();
                    for (InscripcionModel i : c.getInscripciones()) {
                        montoTotal = montoTotal.add(i.getPrecioPagado());
                    }
                }
            }
        }
        TorneoDetalleDTO dto = new TorneoDetalleDTO();
        dto.setId(torneo.getId());
        dto.setNombre(torneo.getNombre());
        dto.setPublicado(torneo.getPublicado());
        dto.setTotalInscripciones(totalInscripciones);
        dto.setMontoTotal(montoTotal);
        dto.setFechaIni(torneo.getFechaIni());
        dto.setFechaFin(torneo.getFechaFin());
        return dto;
    }

    @Override
    public void crear(TorneoModel torneo) {
        if (torneoRepository.buscarPorNombre(torneo.getNombre()).isPresent()) {
            throw new DuplicateResourceException("Ya existe un torneo con el nombre '" + torneo.getNombre() + "'.");
        }
        torneoRepository.save(torneo);
    }

    @Override
    public void cambiarEstadoAPublicado(Long idTorneo) {
        TorneoModel torneo = obtenerPorId(idTorneo);
        torneo.setPublicado(true);
        torneoRepository.save(torneo);
    }

    @Override
    public TorneoModel obtenerTorneoActivoParaInscripcion(Long id) {
        return findPublishedAndFutureTournaments().stream()
                .filter(t -> t.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("No se encontró un torneo publicado y disponible para inscripción con el ID: " + id));
    }

    @Override
    public List<TorneoModel> getTorneosActivos() {
        return findPublishedAndInProgressTournaments();
    }

}
