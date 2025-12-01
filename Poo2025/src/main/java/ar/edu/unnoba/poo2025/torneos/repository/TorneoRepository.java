package ar.edu.unnoba.poo2025.torneos.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import ar.edu.unnoba.poo2025.torneos.model.TorneoModel;

public interface TorneoRepository extends JpaRepository<TorneoModel, Long> {

    @Query("SELECT p FROM TorneoModel p WHERE p.nombre = :nombre")
    public Optional<TorneoModel> buscarPorNombre(@Param("nombre") String nombre);

    @Query("SELECT t FROM TorneoModel t WHERE t.publicado = TRUE AND t.fechaFin > :fechaActual")
    public Optional<List<TorneoModel>> getTorneosActivos();
}
