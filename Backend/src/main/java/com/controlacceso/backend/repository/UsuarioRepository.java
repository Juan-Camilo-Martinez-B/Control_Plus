package com.controlacceso.backend.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.controlacceso.backend.model.Usuario;

@Repository
public interface UsuarioRepository extends MongoRepository<Usuario, String> {

    // Método para verificar si un correo ya está registrado
    boolean existsByCorreo(String correo);

    // Método optimizado para obtener un usuario por su correo
    @Query("{ 'correo' : ?0 }") // Consulta en MongoDB
    Optional<Usuario> findByCorreo(String correo); // Devuelve `Optional<Usuario>`
    Optional<Usuario> findByQrToken(String qrToken);
}
