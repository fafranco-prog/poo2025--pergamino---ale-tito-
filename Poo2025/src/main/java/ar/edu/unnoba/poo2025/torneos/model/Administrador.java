package ar.edu.unnoba.poo2025.torneos.model;

import jakarta.persistence.*;
import lombok.Setter;
import lombok.Getter;

@Entity
@Table(name = "administradores")
@PrimaryKeyJoinColumn(name = "id") // Usa Usuario.id como id primario
@Getter
@Setter
public class Administrador extends Usuario {
}
