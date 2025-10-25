package com.controlacceso.backend.model;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Document(collection = "users")
public class Empleado extends Usuario {

    public Empleado(String nombre, String identificacion, String correo) {
        super(nombre, identificacion, correo, "empleado", 3); // Se agrega el tipo explícitamente
        setQrToken(generarQRContent()); // Se genera el QR correctamente
    }

    @Override
    public String generarQRContent() {
        return "Tipo: Empleado | Nombre: " + getNombre() +
               " | ID: " + getIdentificacion() +
               " | QR: " + getQrToken() +
               " | Permiso: " + getPermiso();
    }
}
