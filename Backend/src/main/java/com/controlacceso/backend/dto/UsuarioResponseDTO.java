package com.controlacceso.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioResponseDTO {
    private String id;
    private String nombre;
    private String identificacion;
    private String correo;
    private String tipo;
    private int permiso;
    private boolean correoVerificado;
}

