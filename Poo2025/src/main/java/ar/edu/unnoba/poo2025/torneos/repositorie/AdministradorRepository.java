package ar.edu.unnoba.poo2025.torneos.repositorie;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import ar.edu.unnoba.poo2025.torneos.model.AdministradorModel;

@Repository
public interface  AdministradorRepository extends  CrudRepository<AdministradorModel, Long>{
    
} 
