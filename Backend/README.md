# Backend - Sistema de Control de Acceso

## Descripción
Backend del sistema de control de acceso con Spring Boot, que gestiona usuarios, autenticación mediante códigos QR y verificación de permisos.

## Tecnologías
- Java 21
- Spring Boot 3.5.0
- MongoDB (Base de datos)
- Maven (Gestión de dependencias)
- OAuth 2.0 (Google)
- ZXing (Generación de códigos QR)

## Requisitos Previos
- Java 21 o superior
- Maven 3.8+
- MongoDB Atlas o MongoDB local

## Configuración

### 1. Configurar las credenciales

**IMPORTANTE:** El archivo `application.properties` está en `.gitignore` y NO se versiona por seguridad.

1. Copia el archivo de ejemplo:
```bash
cp src/main/resources/application.properties.example src/main/resources/application.properties
```

2. Edita `application.properties` y configura:

```properties
# Email (Gmail con contraseña de aplicación)
spring.mail.username=tu_correo@gmail.com
spring.mail.password=tu_contraseña_de_aplicacion_de_16_caracteres

# MongoDB (NO CAMBIAR - ya está configurado)
spring.data.mongodb.uri=<mantener_la_uri_actual>
```

**Cómo obtener contraseña de aplicación de Gmail:**
1. Ve a https://myaccount.google.com/security
2. Activa "Verificación en 2 pasos"
3. Busca "Contraseñas de aplicaciones"
4. Genera una para "ControlPlus Backend"
5. Copia la contraseña de 16 caracteres

### 2. Instalar dependencias
```bash
mvn clean install
```

### 3. Ejecutar la aplicación
```bash
mvn spring-boot:run
```

El servidor se iniciará en `http://localhost:8081`

## Endpoints Principales

### Autenticación

#### Registrar Usuario
```http
POST /api/auth/register
Content-Type: application/json

{
  "tipo": "residente|visitante|empleado|administrador",
  "nombre": "Nombre Completo",
  "identificacion": "123456789",
  "correo": "email@ejemplo.com",
  // Campos adicionales según el tipo de usuario
}
```

#### Verificar Correo
```http
GET /api/auth/verify-email?correo=email@ejemplo.com&codigo=UUID
```

### Login con QR

#### Validar QR (JSON)
```http
POST /api/auth/loginQR-json
Content-Type: application/json

{
  "qrToken": "token-del-qr"
}
```

#### Validar QR (Archivo)
```http
POST /api/auth/loginQR-file
Content-Type: multipart/form-data

archivo: [imagen del QR]
```

## Estructura del Proyecto

```
src/main/java/com/controlacceso/backend/
├── config/              # Configuraciones (Seguridad, CORS)
├── controller/          # Controladores REST
├── dto/                 # Data Transfer Objects
├── exception/           # Excepciones personalizadas
├── mapper/              # Mapeo entre entidades y DTOs
├── model/               # Modelos de dominio
├── repository/          # Repositorios de MongoDB
├── service/             # Lógica de negocio
│   └── impl/           # Implementaciones de servicios
└── util/                # Utilidades y helpers
```

## Niveles de Permiso

- **Nivel 1 (Visitante)**: Acceso solo a puerta 1
- **Nivel 2 (Residente)**: Acceso a puertas 1 y 2
- **Nivel 3 (Empleado/Administrador)**: Acceso a todas las puertas

## Seguridad
- OAuth 2.0 con Google
- JWT para autenticación
- CORS configurado para frontend Next.js
- Validación de correo electrónico obligatoria

## Notas Importantes
- ⚠️ **NO modificar** la configuración de MongoDB
- 🔒 **NO subir** `application.properties` a Git (ya está en .gitignore)
- ✅ Solo se versiona `application.properties.example`
- 📧 Usa contraseña de aplicación de Gmail (no expira)
- 🚀 Para producción, ver: [DESPLIEGUE_ESTUDIANTES.md](../DESPLIEGUE_ESTUDIANTES.md)
- 🔓 CSRF está deshabilitado para APIs REST

## Despliegue en Producción

Para desplegar en Railway/Render, consulta la guía completa:
- [Guía de Despliegue para Estudiantes](../DESPLIEGUE_ESTUDIANTES.md)

## Contacto
Para más información sobre el proyecto, consulta la documentación completa.

