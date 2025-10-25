package com.controlacceso.backend.mapper;

import com.controlacceso.backend.dto.UsuarioRegistroDTO;
import com.controlacceso.backend.dto.UsuarioResponseDTO;
import com.controlacceso.backend.exception.BadRequestException;
import com.controlacceso.backend.model.Administrador;
import com.controlacceso.backend.model.Empleado;
import com.controlacceso.backend.model.Residente;
import com.controlacceso.backend.model.Usuario;
import com.controlacceso.backend.model.Visitante;

public class UsuarioMapper {

    public static Usuario toEntity(UsuarioRegistroDTO dto) {
        String tipo = dto.getTipo().toLowerCase();
        
        return switch (tipo) {
            case "residente" -> new Residente(
                    dto.getNombre(),
                    dto.getIdentificacion(),
                    dto.getCorreo(),
                    dto.getTorre(),
                    dto.getApartamento()
            );
            case "visitante" -> new Visitante(
                    dto.getNombre(),
                    dto.getIdentificacion(),
                    dto.getCorreo(),
                    dto.getMotivoVisita(),
                    dto.getPersonaAVisitar(),
                    dto.getTorreVisita(),
                    dto.getApartamentoVisita()
            );
            case "empleado" -> new Empleado(
                    dto.getNombre(),
                    dto.getIdentificacion(),
                    dto.getCorreo()
            );
            case "administrador" -> new Administrador(
                    dto.getNombre(),
                    dto.getIdentificacion(),
                    dto.getCorreo()
            );
            default -> throw new BadRequestException("Tipo de usuario inválido: " + tipo);
        };
    }

    public static UsuarioResponseDTO toResponseDTO(Usuario usuario) {
        return UsuarioResponseDTO.builder()
                .id(usuario.getId())
                .nombre(usuario.getNombre())
                .identificacion(usuario.getIdentificacion())
                .correo(usuario.getCorreo())
                .tipo(usuario.getTipo())
                .permiso(usuario.getPermiso())
                .correoVerificado(usuario.isCorreoVerificado())
                .build();
    }
}

