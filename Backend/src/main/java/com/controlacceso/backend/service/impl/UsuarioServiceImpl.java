package com.controlacceso.backend.service.impl;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.controlacceso.backend.model.Usuario;
import com.controlacceso.backend.repository.UsuarioRepository;
import com.controlacceso.backend.service.EmailService;
import com.controlacceso.backend.service.UsuarioService;
import com.controlacceso.backend.util.QRCodeGenerator;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;
    
    @Autowired
    private EmailService emailService;
    
    @Override
    public Usuario registrarUsuario(Usuario usuario) {
        usuario.setQrToken(generateToken()); // Genera un token único
        Usuario usuarioGuardado = usuarioRepository.save(usuario);
        enviarCodigoQR(usuarioGuardado);
        return usuarioGuardado;
    }

    @Override
    public void enviarCodigoQR(Usuario usuario) {
        String qrCodeBase64 = QRCodeGenerator.generateQRCodeBase64(usuario.getQrToken());
        
        String asunto = "Tu código QR de acceso - ControlPlus";
        String contenido = "Hola " + usuario.getNombre() + ",\n\n"
                + "Gracias por registrarte en ControlPlus.\n\n"
                + "A continuación encontrarás tu código QR de acceso.\n\n"
                + "Guarda este correo para acceder al sistema.";
        
        emailService.enviarCorreoConQR(usuario.getCorreo(), asunto, contenido, qrCodeBase64);
    }

    private String generateToken() {
        return UUID.randomUUID().toString(); // Genera un UUID único
    }

    @Override
    public Optional<Usuario> buscarPorToken(String qrToken) {
        return usuarioRepository.findByQrToken(qrToken);
    }
}
