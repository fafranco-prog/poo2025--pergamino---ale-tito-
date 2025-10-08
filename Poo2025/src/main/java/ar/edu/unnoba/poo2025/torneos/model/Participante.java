package ar.edu.unnoba.poo2025.torneos.model;

import jakarta.persistence.*;

@Entity
@Table(name = "participante")
public class Participante extends Usuario{
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(unique=true, nullable=false)
     
    private Long idParticipante;
    private String nombre;
    private String apellido;
    private String tipo_dni;
    private int numero_dni;
    
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public String getApellido() {
        return apellido;
    }
    public void setApellido(String apellido) {
        this.apellido = apellido;
    }
    public String getTipo_dni() {
        return tipo_dni;
    }
    public void setTipo_dni(String tipo_dni) {
        this.tipo_dni = tipo_dni;
    }
    public int getNumero_dni() {
        return numero_dni;
    }
    public void setNumero_dni(int numero_dni) {
        this.numero_dni = numero_dni;
    }
    
   
}
