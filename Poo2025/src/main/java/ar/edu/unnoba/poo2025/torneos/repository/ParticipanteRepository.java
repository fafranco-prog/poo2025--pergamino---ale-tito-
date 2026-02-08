package ar.edu.unnoba.poo2025.torneos.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import ar.edu.unnoba.poo2025.torneos.model.ParticipanteModel;

public interface ParticipanteRepository extends JpaRepository<ParticipanteModel, Long> {

    @Query("SELECT p FROM ParticipanteModel p WHERE p.email = :email")
    public Optional<ParticipanteModel> buscarPorEmail(@Param("email") String email);

    public Optional<ParticipanteModel> findByTipoDniAndNumeroDni(String tipoDni, String numeroDni);
                  
}
  