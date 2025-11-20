package ar.edu.unnoba.poo2025.torneos.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import ar.edu.unnoba.poo2025.torneos.model.AdministradorModel;


@Repository
public interface AdministradorRepository extends JpaRepository<AdministradorModel, Long> {

    @Query("SELECT p FROM AdministradorModel p WHERE p.email = :email")
    public Optional<AdministradorModel> buscarPorEmail(@Param("email") String email);
}

