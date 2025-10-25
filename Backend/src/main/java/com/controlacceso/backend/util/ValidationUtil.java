package com.controlacceso.backend.util;

import com.controlacceso.backend.dto.UsuarioRegistroDTO;
import com.controlacceso.backend.exception.BadRequestException;

public class ValidationUtil {

    public static void validateRegistroDTO(UsuarioRegistroDTO dto) {
        if (dto.getTipo() == null || dto.getTipo().trim().isEmpty()) {
            throw new BadRequestException("El tipo de usuario es obligatorio");
        }
        
        if (dto.getNombre() == null || dto.getNombre().trim().isEmpty()) {
            throw new BadRequestException("El nombre es obligatorio");
        }
        
        if (dto.getIdentificacion() == null || dto.getIdentificacion().trim().isEmpty()) {
            throw new BadRequestException("La identificación es obligatoria");
        }
        
        if (dto.getCorreo() == null || !dto.getCorreo().matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            throw new BadRequestException("El correo electrónico no es válido");
        }
        
        String tipo = dto.getTipo().toLowerCase();
        
        // Validaciones específicas por tipo de usuario
        if ("residente".equals(tipo)) {
            if (dto.getTorre() == null || dto.getTorre().trim().isEmpty()) {
                throw new BadRequestException("La torre es obligatoria para residentes");
            }
            if (dto.getApartamento() == null || dto.getApartamento().trim().isEmpty()) {
                throw new BadRequestException("El apartamento es obligatorio para residentes");
            }
        } else if ("visitante".equals(tipo)) {
            if (dto.getMotivoVisita() == null || dto.getMotivoVisita().trim().isEmpty()) {
                throw new BadRequestException("El motivo de visita es obligatorio para visitantes");
            }
            if (dto.getPersonaAVisitar() == null || dto.getPersonaAVisitar().trim().isEmpty()) {
                throw new BadRequestException("La persona a visitar es obligatoria para visitantes");
            }
            if (dto.getTorreVisita() == null || dto.getTorreVisita().trim().isEmpty()) {
                throw new BadRequestException("La torre de visita es obligatoria para visitantes");
            }
            if (dto.getApartamentoVisita() == null || dto.getApartamentoVisita().trim().isEmpty()) {
                throw new BadRequestException("El apartamento de visita es obligatorio para visitantes");
            }
        }
    }
    
    public static boolean isValidUserType(String tipo) {
        return tipo != null && 
               ("residente".equalsIgnoreCase(tipo) || 
                "visitante".equalsIgnoreCase(tipo) || 
                "empleado".equalsIgnoreCase(tipo) || 
                "administrador".equalsIgnoreCase(tipo));
    }
}

