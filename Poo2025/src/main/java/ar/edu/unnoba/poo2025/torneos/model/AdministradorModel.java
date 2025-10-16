package ar.edu.unnoba.poo2025.torneos.model;

import jakarta.persistence.*;
import lombok.Setter;
import lombok.Getter;
  
@Entity
//@Table(name = "administrador")
@DiscriminatorValue("ADMIN") // discriminante para eleccion de clase
@Getter
@Setter
public class AdministradorModel extends UsuarioModel {
        //nada de momento  
}
