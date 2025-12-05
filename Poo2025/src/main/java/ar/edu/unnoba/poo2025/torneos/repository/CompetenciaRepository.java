package ar.edu.unnoba.poo2025.torneos.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import ar.edu.unnoba.poo2025.torneos.model.CompetenciaModel;

public interface CompetenciaRepository extends JpaRepository<CompetenciaModel, Long> {

    @Query("SELECT p FROM CompetenciaModel p WHERE p.nombre = :nombre")
    public Optional<CompetenciaModel> buscarPorNombre(@Param("nombre") String nombre);

}
