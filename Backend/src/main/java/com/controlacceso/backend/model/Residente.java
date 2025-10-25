package com.controlacceso.backend.model;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Document(collection = "users")
public class Residente extends Usuario {
    private String torre;
    private String apartamento;

    public Residente(String nombre, String identificacion, String correo, String torre, String apartamento) {
        super(nombre, identificacion, correo, "residente", 2); // Se agrega el tipo explícitamente
        this.torre = torre;
        this.apartamento = apartamento;
        setQrToken(generarQRContent()); // Se genera el QR correctamente
    }

    @Override
    public String generarQRContent() {
        return "Tipo: Residente | Nombre: " + getNombre() +
               " | ID: " + getIdentificacion() +
               " | Torre: " + torre +
               " | Apartamento: " + apartamento +
               " | QR: " + getQrToken() +
               " | Permiso: " + getPermiso();
    }
}
