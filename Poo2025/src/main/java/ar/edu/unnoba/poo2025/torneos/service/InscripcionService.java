package ar.edu.unnoba.poo2025.torneos.service;

import java.util.List;
import java.util.Optional;


import ar.edu.unnoba.poo2025.torneos.dto.CompetenciaDetalleOutDTO;
import ar.edu.unnoba.poo2025.torneos.model.InscripcionModel;


public interface InscripcionService {
 

    public List<InscripcionModel> obtenerInscripciones();

    public Optional<InscripcionModel> obtenerPorId(Long id);

    //public InscripcionModel crear(InscripcionModel inscripcion);

    public void eliminar(Long id);

    //public void actualizar(Long id, InscripcionModel nuevosDatos);

    public CompetenciaDetalleOutDTO obtenerEstadisticasPorCompetencia(Long idCompetencia);
}
