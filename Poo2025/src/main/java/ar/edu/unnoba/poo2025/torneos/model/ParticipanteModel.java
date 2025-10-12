package ar.edu.unnoba.poo2025.torneos.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "participante")
@Setter
@Getter
public class ParticipanteModel extends UsuarioModel{
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(unique=true, nullable=false)
     
    private Long id;
    private String nombre;
    private String apellido;
    private String tipo_dni;
    private int numero_dni;
    
   
   
}
