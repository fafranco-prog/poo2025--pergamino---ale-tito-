package ar.edu.unnoba.poo2025.torneos.model;

import jakarta.persistence.*;
import lombok.Setter;
import lombok.Getter;
  
@Entity
@Table(name = "administrador")
@Getter
@Setter
public class AdministradorModel extends UsuarioModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique=true, nullable=false)
    private Long id;
                              
}
