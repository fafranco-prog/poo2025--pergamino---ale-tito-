package ar.edu.unnoba.poo2025.torneos.model;

public abstract class Usuario {
    private String email;
    private String contraseña;
    
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getContraseña() {
        return contraseña;
    }
    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }
    
} 
