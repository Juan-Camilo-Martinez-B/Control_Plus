package com.controlacceso.backend.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.controlacceso.backend.model.Usuario;
import com.controlacceso.backend.repository.UsuarioRepository;

@Service
public class VerificacionCorreoService {

    private final UsuarioRepository usuarioRepository;

    public VerificacionCorreoService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public boolean verificarCorreo(String correo) {
        Optional<Usuario> usuario = usuarioRepository.findByCorreo(correo);
        
        if (usuario.isPresent()) {
            usuario.get().setCorreoVerificado(true); // Marca el correo como verificado
            usuarioRepository.save(usuario.get()); // Guarda el cambio en la base de datos
            return true;
        }
        
        return false;
    }
}
