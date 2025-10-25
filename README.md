# ControlPlus - Sistema de Control de Acceso

Sistema completo de control de acceso mediante códigos QR, con verificación por correo electrónico y múltiples niveles de permisos.

## 🏗️ Arquitectura del Proyecto

El proyecto está dividido en dos partes principales:

### Backend (Spring Boot + Java 21)
- API REST con Spring Boot 3.5.0
- Base de datos MongoDB
- Autenticación OAuth 2.0 con Google
- Generación de códigos QR con ZXing
- Envío de correos electrónicos con Gmail API

### Frontend (Next.js + React + TypeScript)
- Next.js 16 con App Router
- React 19 con TypeScript
- Tailwind CSS 4 para estilos
- Interfaz moderna y responsive
- Integración completa con la API del backend

## 🚀 Inicio Rápido

### Prerrequisitos
- Java 21+
- Node.js 18+
- MongoDB (Atlas o local)
- Maven 3.8+
- Cuenta de Gmail con contraseña de aplicación (o cualquier SMTP)

### 1. Configurar el Backend

```bash
cd Backend

# Copiar y configurar las credenciales
cp src/main/resources/application.properties.example src/main/resources/application.properties

# Editar application.properties con:
# - Credenciales SMTP (Gmail con contraseña de aplicación)
# - MongoDB (NO modificar)

# Instalar dependencias y ejecutar
mvn clean install
mvn spring-boot:run
```

El backend estará disponible en `http://localhost:8081`

### 2. Configurar el Frontend

```bash
cd Frontend

# Instalar dependencias
npm install

# Configurar variables de entorno (opcional)
echo "NEXT_PUBLIC_API_URL=http://localhost:8081/api" > .env.local

# Ejecutar en modo desarrollo
npm run dev
```

El frontend estará disponible en `http://localhost:3000`

## 📚 Documentación

- [🎓 Guía de Despliegue para Estudiantes](DESPLIEGUE_ESTUDIANTES.md) ⭐ **PARA PRODUCCIÓN**
- [📖 Documentación del Backend](Backend/README.md)
- [📖 Documentación del Frontend](Frontend/README.md)

## 🎯 Funcionalidades Principales

### Sistema de Registro
- **4 tipos de usuarios**: Visitante, Residente, Empleado, Administrador
- **Verificación por correo**: Link de activación enviado automáticamente
- **Generación automática de QR**: Enviado por correo tras la verificación
- **Formularios dinámicos**: Adaptados según el tipo de usuario

### Sistema de Ingreso
- **3 niveles de puertas**: Con diferentes requisitos de acceso
- **Validación de QR**: Por imagen o JSON
- **Verificación instantánea**: Respuesta en tiempo real
- **Retroalimentación visual**: Indicadores claros de acceso permitido/denegado

### Niveles de Acceso

| Tipo de Usuario | Nivel | Puertas Permitidas |
|-----------------|-------|-------------------|
| Visitante | 1 | Solo Puerta 1 |
| Residente | 2 | Puertas 1 y 2 |
| Empleado | 3 | Todas las puertas |
| Administrador | 3 | Todas las puertas |

## 🛠️ Tecnologías Utilizadas

### Backend
- **Framework**: Spring Boot 3.5.0
- **Lenguaje**: Java 21
- **Base de Datos**: MongoDB
- **Seguridad**: Spring Security
- **Email**: Spring Mail (SMTP simple - sin OAuth)
- **Librerías**: 
  - ZXing (Generación de QR)
  - Lombok (Reducción de boilerplate)
  - Jackson (Serialización JSON)
  - JavaMailSender (Envío de correos)

### Frontend
- **Framework**: Next.js 16
- **Librería UI**: React 19
- **Lenguaje**: TypeScript 5
- **Estilos**: Tailwind CSS 4
- **HTTP Client**: Fetch API

## 📁 Estructura del Proyecto

```
Controlplus/
├── Backend/                    # Backend Spring Boot
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/
│   │   │   │   └── com/controlacceso/backend/
│   │   │   │       ├── config/           # Configuraciones
│   │   │   │       ├── controller/       # Controladores REST
│   │   │   │       ├── dto/              # Data Transfer Objects
│   │   │   │       ├── exception/        # Manejo de excepciones
│   │   │   │       ├── mapper/           # Mappers
│   │   │   │       ├── model/            # Modelos de dominio
│   │   │   │       ├── repository/       # Repositorios MongoDB
│   │   │   │       ├── service/          # Lógica de negocio
│   │   │   │       └── util/             # Utilidades
│   │   │   └── resources/
│   │   │       └── application.properties
│   │   └── test/
│   ├── pom.xml
│   └── README.md
│
├── Frontend/                   # Frontend Next.js
│   ├── app/                   # App Router
│   │   ├── registro/         # Página de registro
│   │   ├── ingreso/          # Página de ingreso
│   │   ├── layout.tsx        # Layout principal
│   │   ├── page.tsx          # Página de inicio
│   │   └── globals.css       # Estilos globales
│   ├── lib/                  # Servicios y utilidades
│   │   └── api.ts           # Cliente API
│   ├── package.json
│   └── README.md
│
└── README.md                  # Este archivo
```

## 🔒 Seguridad

- **Spring Security**: Autenticación y autorización
- **CORS**: Configurado para desarrollo y producción
- **Verificación de correo**: Obligatoria para activar cuenta
- **Códigos QR únicos**: Generados individualmente para cada usuario
- **Contraseñas de aplicación**: Para SMTP seguro sin OAuth
- **HTTPS**: Recomendado para producción

## 🧪 Testing

### Backend
```bash
cd Backend
mvn test
```

### Frontend
```bash
cd Frontend
npm test
```

## 📦 Despliegue

### Backend
1. Configurar `application.properties` para producción
2. Compilar: `mvn clean package`
3. Ejecutar: `java -jar target/backend-0.0.1-SNAPSHOT.war`

### Frontend
1. Compilar: `npm run build`
2. Ejecutar: `npm run start`
3. O desplegar en Vercel/Netlify

## 🤝 Contribuciones

Las contribuciones son bienvenidas. Por favor:

1. Fork el proyecto
2. Crea una rama para tu feature (`git checkout -b feature/AmazingFeature`)
3. Commit tus cambios (`git commit -m 'Add some AmazingFeature'`)
4. Push a la rama (`git push origin feature/AmazingFeature`)
5. Abre un Pull Request

## ⚠️ Notas Importantes

- **NO modificar** la configuración de MongoDB en `application.properties`
- **Genera una contraseña de aplicación de Gmail:**
  1. Ve a https://myaccount.google.com/security
  2. Activa verificación en 2 pasos
  3. Genera contraseña de aplicación
  4. Úsala en `application.properties`
- Las contraseñas de aplicación NO expiran
- Para producción, usar variables de entorno (ver DESPLIEGUE_ESTUDIANTES.md)
- `application.properties` está en .gitignore (NO se sube a Git)
- Solo se versiona `application.properties.example`

## 📞 Soporte

Para reportar bugs o solicitar nuevas funcionalidades, por favor abre un issue en el repositorio.

## 📄 Licencia

Este proyecto es privado y está protegido por derechos de autor.

---

**ControlPlus** - Sistema de Control de Acceso con Códigos QR

