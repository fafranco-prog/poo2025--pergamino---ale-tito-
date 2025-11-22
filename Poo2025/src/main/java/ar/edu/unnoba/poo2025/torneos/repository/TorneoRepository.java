package ar.edu.unnoba.poo2025.torneos.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import ar.edu.unnoba.poo2025.torneos.model.TorneoModel;

public interface TorneoRepository extends JpaRepository<TorneoModel, Long> {

    @Query("SELECT p FROM TorneoModel p WHERE p.nombre = :nombre")
    public Optional<TorneoModel> buscarPorNombre(@Param("nombre") String nombre);
}
