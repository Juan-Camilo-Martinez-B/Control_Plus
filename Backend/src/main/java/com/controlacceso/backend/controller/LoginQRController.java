package com.controlacceso.backend.controller;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.controlacceso.backend.dto.ApiResponseDTO;
import com.controlacceso.backend.dto.LoginQRRequestDTO;
import com.controlacceso.backend.exception.BadRequestException;
import com.controlacceso.backend.exception.ResourceNotFoundException;
import com.controlacceso.backend.model.Usuario;
import com.controlacceso.backend.service.UsuarioService;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.ReaderException;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class LoginQRController {

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping(value = "/loginQR-json", consumes = "application/json")
    public ResponseEntity<ApiResponseDTO<String>> validarQRJson(@RequestBody LoginQRRequestDTO request) {
        log.info("Validando QR mediante JSON");
        
        if (request.getQrToken() == null || request.getQrToken().isEmpty()) {
            throw new BadRequestException("QR inválido o faltante");
        }
        
        String mensaje = verificarPermiso(request.getQrToken());
        return ResponseEntity.ok(ApiResponseDTO.success(mensaje));
    }

    @PostMapping(value = "/loginQR-file", consumes = "multipart/form-data")
    public ResponseEntity<ApiResponseDTO<String>> validarQRArchivo(@RequestParam("archivo") MultipartFile archivo) {
        log.info("Validando QR mediante archivo de imagen");
        
        if (archivo == null || archivo.isEmpty()) {
            throw new BadRequestException("Archivo no proporcionado");
        }
        
        String qrToken = procesarQR(archivo);
        
        if (qrToken == null || qrToken.isEmpty()) {
            throw new BadRequestException("QR inválido o no legible");
        }
        
        String mensaje = verificarPermiso(qrToken);
        return ResponseEntity.ok(ApiResponseDTO.success(mensaje));
    }

    private String procesarQR(MultipartFile archivo) {
        try {
            ByteArrayInputStream bis = new ByteArrayInputStream(archivo.getBytes());
            BufferedImage bufferedImage = ImageIO.read(bis);
            
            if (bufferedImage == null) {
                throw new BadRequestException("Archivo de imagen inválido");
            }
            
            LuminanceSource source = new BufferedImageLuminanceSource(bufferedImage);
            BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
            
            return new MultiFormatReader().decode(bitmap).getText();
        } catch (IOException e) {
            log.error("Error al leer el archivo de imagen: {}", e.getMessage());
            throw new BadRequestException("Error al procesar el archivo de imagen");
        } catch (ReaderException e) {
            log.error("Error al decodificar el código QR: {}", e.getMessage());
            throw new BadRequestException("No se pudo leer el código QR de la imagen");
        }
    }

    private String verificarPermiso(String qrToken) {
        Usuario usuario = usuarioService.buscarPorToken(qrToken)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado o QR inválido"));

        if (!usuario.isCorreoVerificado()) {
            return "❌ Acceso denegado: el usuario no ha verificado su correo electrónico";
        }

        int permiso = usuario.getPermiso();
        String nombreUsuario = usuario.getNombre();
        
        log.info("Acceso validado para usuario: {} con permiso nivel {}", nombreUsuario, permiso);

        return obtenerMensajeAcceso(permiso, nombreUsuario);
    }

    private String obtenerMensajeAcceso(int permiso, String nombreUsuario) {
        return switch (permiso) {
            case 3 -> String.format("✅ Bienvenido %s - Acceso completo (todas las puertas)", nombreUsuario);
            case 2 -> String.format("✅ Bienvenido %s - Acceso a puerta 1 y 2", nombreUsuario);
            case 1 -> String.format("✅ Bienvenido %s - Acceso solo a puerta 1", nombreUsuario);
            default -> String.format("❌ Permiso denegado para %s - Nivel de acceso inválido", nombreUsuario);
        };
    }
}

