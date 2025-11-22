package ar.edu.unnoba.poo2025.torneos.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity
//@Table(name = "administrador")
@DiscriminatorValue("ADMIN") // discriminante para eleccion de clase
@Getter
@Setter
public class AdministradorModel extends UsuarioModel {
    //nada de momento  
}
