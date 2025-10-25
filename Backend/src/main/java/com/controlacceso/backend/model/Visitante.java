package com.controlacceso.backend.model;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Document(collection = "users")
public class Visitante extends Usuario {
    private String motivoVisita;
    private String personaAVisitar;
    private String torreVisita;
    private String apartamentoVisita;

    public Visitante(String nombre, String identificacion, String correo, String motivoVisita, String personaAVisitar, String torreVisita, String apartamentoVisita) {
        super(nombre, identificacion, correo, "visitante", 1); 
        this.motivoVisita = motivoVisita;
        this.personaAVisitar = personaAVisitar;
        this.torreVisita = torreVisita;
        this.apartamentoVisita = apartamentoVisita;
        setQrToken(generarQRContent()); 
    }

    @Override
    public String generarQRContent() {
        return "Tipo: Visitante | Nombre: " + getNombre() +
               " | ID: " + getIdentificacion() +
               " | Motivo: " + motivoVisita +
               " | Visitando a: " + personaAVisitar +
               " | Torre: " + torreVisita +
               " | Apartamento: " + apartamentoVisita +
               " | QR: " + getQrToken() +
               " | Permiso: " + getPermiso();
    }
}
