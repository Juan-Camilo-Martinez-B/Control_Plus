package com.controlacceso.backend.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import lombok.Data;

@Data
@Document(collection = "users")
@JsonTypeInfo(
    use = JsonTypeInfo.Id.NAME, 
    include = JsonTypeInfo.As.PROPERTY, 
    property = "tipo"
)
@JsonSubTypes({
    @JsonSubTypes.Type(value = Residente.class, name = "residente"),
    @JsonSubTypes.Type(value = Empleado.class, name = "empleado"),
    @JsonSubTypes.Type(value = Administrador.class, name = "administrador"),
    @JsonSubTypes.Type(value = Visitante.class, name = "visitante")
})

public abstract class Usuario {
    @Id
    private String id;
    
    @Field("nombre")
    private String nombre;
    
    @Field("identificacion")
    private String identificacion;
    
    @Field("correo")
    private String correo;
    
    @Field("tipo") 
    private String tipo;

    @Field("qrToken") 
    private String qrToken;

    @Field("permiso")
    private int permiso;

    @Field("correoVerificado")
    private boolean correoVerificado;

    public Usuario(String nombre, String identificacion, String correo, String tipo, int permiso) {
        this.nombre = nombre;
        this.identificacion = identificacion;
        this.correo = correo;
        this.tipo = tipo;
        this.permiso = permiso;
        this.correoVerificado = false;
        this.qrToken = generarQRContent();
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String generarQRContent() {
        return "Usuario: " + nombre + " | Tipo: " + tipo + " | ID: " + identificacion + " | Permiso: " + permiso;
    }
}
