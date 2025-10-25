package com.controlacceso.backend.service.impl;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.controlacceso.backend.model.Usuario;
import com.controlacceso.backend.repository.UsuarioRepository;
import com.controlacceso.backend.service.UsuarioService;
import com.controlacceso.backend.util.QRCodeGenerator;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;
    
    @Autowired
    private JavaMailSender mailSender;
    
    @Override
    public Usuario registrarUsuario(Usuario usuario) {
        usuario.setQrToken(generateToken()); // Genera un token único
        Usuario usuarioGuardado = usuarioRepository.save(usuario);
        enviarCodigoQR(usuarioGuardado);
        return usuarioGuardado;
    }

    @Override
    public void enviarCodigoQR(Usuario usuario) {
        String qrCodeBase64 = QRCodeGenerator.generateQRCodeBase64(usuario.getQrToken()); // Usa solo `qrToken`
        
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(usuario.getCorreo());
        message.setSubject("Tu código QR de acceso");
        message.setText("Hola " + usuario.getNombre() + ",\n\nEste es tu código QR de acceso:\n" + qrCodeBase64);
        mailSender.send(message);
    }

    private String generateToken() {
        return UUID.randomUUID().toString(); // Genera un UUID único
    }

    @Override
    public Optional<Usuario> buscarPorToken(String qrToken) {
        return usuarioRepository.findByQrToken(qrToken);
    }
}
