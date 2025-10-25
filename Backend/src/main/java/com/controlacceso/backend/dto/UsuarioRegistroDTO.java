package com.controlacceso.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioRegistroDTO {
    private String tipo;
    private String nombre;
    private String identificacion;
    private String correo;
    
    // Campos específicos para Residente
    private String torre;
    private String apartamento;
    
    // Campos específicos para Visitante
    private String motivoVisita;
    private String personaAVisitar;
    private String torreVisita;
    private String apartamentoVisita;
}

