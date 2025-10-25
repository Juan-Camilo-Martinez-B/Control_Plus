package com.controlacceso.backend.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.controlacceso.backend.dto.ApiResponseDTO;
import com.controlacceso.backend.dto.UsuarioRegistroDTO;
import com.controlacceso.backend.dto.UsuarioResponseDTO;
import com.controlacceso.backend.exception.BadRequestException;
import com.controlacceso.backend.exception.EmailSendingException;
import com.controlacceso.backend.exception.ResourceNotFoundException;
import com.controlacceso.backend.mapper.UsuarioMapper;
import com.controlacceso.backend.model.Usuario;
import com.controlacceso.backend.repository.UsuarioRepository;
import com.controlacceso.backend.service.EmailService;
import com.controlacceso.backend.service.QrService;
import com.controlacceso.backend.util.ValidationUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private QrService qrService;

    @Autowired
    private EmailService emailService;
    
    @Value("${app.base.url}")
    private String appBaseUrl;

    private final Map<String, Usuario> usuariosPendientes = new HashMap<>();
    private final Map<String, String> codigosValidacion = new HashMap<>();

    @PostMapping("/register")
    public ResponseEntity<ApiResponseDTO<UsuarioResponseDTO>> registrarUsuario(@RequestBody UsuarioRegistroDTO registroDTO) {
        log.info("Iniciando registro de usuario: {}", registroDTO.getCorreo());
        
        // Validar datos de entrada
        ValidationUtil.validateRegistroDTO(registroDTO);
        
        // Verificar si el correo ya está registrado
        if (usuarioRepository.existsByCorreo(registroDTO.getCorreo())) {
            throw new BadRequestException("Este correo ya está registrado");
        }
        
        // Crear entidad de usuario según el tipo
        Usuario usuario = UsuarioMapper.toEntity(registroDTO);
        usuario.setCorreoVerificado(false);
        
        // Guardar temporalmente el usuario
        usuariosPendientes.put(usuario.getCorreo(), usuario);
        
        // Generar código de validación
        String codigoValidacion = UUID.randomUUID().toString();
        codigosValidacion.put(usuario.getCorreo(), codigoValidacion);
        
        // Enviar correo de validación
        enviarCorreoValidacion(usuario, codigoValidacion);
        
        log.info("Usuario registrado exitosamente (pendiente de verificación): {}", usuario.getCorreo());
        
        UsuarioResponseDTO responseDTO = UsuarioMapper.toResponseDTO(usuario);
        return ResponseEntity.ok(ApiResponseDTO.success(
                "Registro iniciado. Revisa tu correo para validarlo.",
                responseDTO
        ));
    }

    @GetMapping("/verify-email")
    public ResponseEntity<ApiResponseDTO<String>> verificarCorreo(
            @RequestParam String correo,
            @RequestParam String codigo) {
        
        log.info("Verificando correo: {}", correo);
        
        Usuario usuario = usuariosPendientes.get(correo);
        
        if (usuario == null) {
            throw new ResourceNotFoundException("Usuario no encontrado o ya verificado");
        }
        
        String codigoEsperado = codigosValidacion.get(correo);
        if (codigoEsperado == null || !codigoEsperado.equals(codigo)) {
            throw new BadRequestException("Código de validación incorrecto o expirado");
        }
        
        // Marcar correo como verificado y guardar en la base de datos
        usuario.setCorreoVerificado(true);
        usuarioRepository.save(usuario);
        
        // Limpiar datos temporales
        usuariosPendientes.remove(correo);
        codigosValidacion.remove(correo);
        
        // Generar y enviar QR
        enviarQRPorCorreo(usuario);
        
        log.info("Correo verificado exitosamente: {}", correo);
        
        return ResponseEntity.ok(ApiResponseDTO.success(
                "Correo verificado. QR enviado.",
                "Tu código QR ha sido enviado a tu correo electrónico"
        ));
    }
    
    private void enviarCorreoValidacion(Usuario usuario, String codigoValidacion) {
        try {
            String enlaceVerificacion = String.format(
                    "%s/api/auth/verify-email?correo=%s&codigo=%s",
                    appBaseUrl,
                    usuario.getCorreo(),
                    codigoValidacion
            );
            
            String contenido = String.format(
                    "Hola %s,\n\n" +
                    "Por favor, haz clic en el siguiente enlace para validar tu correo:\n\n" +
                    "%s\n\n" +
                    "Este enlace expirará en 10 minutos.\n\n" +
                    "Saludos,\nEquipo ControlPlus",
                    usuario.getNombre(),
                    enlaceVerificacion
            );
            
            emailService.enviarCorreo(
                    usuario.getCorreo(),
                    "Validación de correo - ControlPlus",
                    contenido
            );
        } catch (Exception e) {
            log.error("Error al enviar correo de validación: {}", e.getMessage());
            throw new EmailSendingException("Error enviando correo de validación", e);
        }
    }
    
    private void enviarQRPorCorreo(Usuario usuario) {
        try {
            log.info("Intentando enviar QR a: {}", usuario.getCorreo());
            log.info("QR Token del usuario: {}", usuario.getQrToken());
            
            if (usuario.getQrToken() == null || usuario.getQrToken().isEmpty()) {
                log.error("El usuario no tiene QR Token asignado");
                throw new IllegalStateException("El usuario no tiene QR Token asignado");
            }
            
            String qrBase64 = qrService.generarQrBase64(usuario.getQrToken());
            log.info("QR generado correctamente, tamaño Base64: {}", qrBase64.length());
            
            String contenido = String.format(
                    "Hola %s,\n\n" +
                    "Tu registro ha sido completado exitosamente.\n\n" +
                    "Adjunto encontrarás tu código QR de acceso.\n" +
                    "Nivel de permiso: %d\n\n" +
                    "Saludos,\nEquipo ControlPlus",
                    usuario.getNombre(),
                    usuario.getPermiso()
            );
            
            boolean enviado = emailService.enviarCorreoConQR(
                    usuario.getCorreo(),
                    "Código QR de Acceso - ControlPlus",
                    contenido,
                    qrBase64
            );
            
            if (enviado) {
                log.info("Correo con QR enviado exitosamente a: {}", usuario.getCorreo());
            } else {
                log.error("El correo con QR no pudo ser enviado");
            }
        } catch (Exception e) {
            log.error("Error al enviar QR por correo a {}: {}", usuario.getCorreo(), e.getMessage(), e);
            throw new EmailSendingException("Error enviando QR por correo", e);
        }
    }
}

