# Frontend - Sistema de Control de Acceso ControlPlus

## Descripción
Frontend moderno y responsive del sistema de control de acceso, desarrollado con Next.js 16, React 19 y Tailwind CSS 4.

## Tecnologías
- **Next.js 16** - Framework de React con App Router
- **React 19** - Biblioteca de UI
- **TypeScript** - Tipado estático
- **Tailwind CSS 4** - Framework de estilos
- **Fetch API** - Cliente HTTP para comunicación con el backend

## Requisitos Previos
- Node.js 18+ o superior
- npm, yarn o pnpm

## Instalación

### 1. Instalar dependencias
```bash
npm install
# o
yarn install
# o
pnpm install
```

### 2. Configurar variables de entorno (OPCIONAL para desarrollo local)

**IMPORTANTE:** El archivo `.env.local` está en `.gitignore` y NO se versiona.

Para desarrollo local, crea `.env.local`:

```bash
echo "NEXT_PUBLIC_API_URL=http://localhost:8081/api" > .env.local
```

Si no creas este archivo, la app usará `http://localhost:8081/api` por defecto.

### 3. Ejecutar en modo desarrollo
```bash
npm run dev
# o
yarn dev
# o
pnpm dev
```

La aplicación estará disponible en `http://localhost:3000`

### 4. Compilar para producción
```bash
npm run build
npm run start
```

## Estructura del Proyecto

```
Frontend/
├── app/                    # App Router de Next.js
│   ├── registro/          # Página de registro de usuarios
│   ├── ingreso/           # Página de ingreso con QR
│   ├── verificar/         # Página de verificación de correo
│   ├── layout.tsx         # Layout principal
│   ├── page.tsx           # Página de inicio
│   └── globals.css        # Estilos globales
├── src/
│   └── lib/               # Utilidades y servicios
│       └── api.ts        # Servicios de API
├── public/                # Archivos estáticos
└── package.json          # Dependencias y scripts
```

## Características

### 🎨 Diseño Moderno
- Interfaz limpia y profesional
- Diseño responsive para todos los dispositivos
- Animaciones suaves y transiciones
- Esquema de colores coherente

### 📱 Responsive
- Optimizado para móviles, tablets y desktop
- Grid adaptativo
- Menú hamburguesa en móviles

### 🔐 Funcionalidades

#### Página de Inicio
- Vista general del sistema
- Acceso directo a registro e ingreso
- Sección de características

#### Registro de Usuarios
- Selección visual de tipo de usuario
- Formularios dinámicos según el tipo
- Validación de datos
- Notificación de registro exitoso

#### Ingreso con QR
- Selección de puerta (1, 2 o 3)
- Carga de imagen QR
- Validación en tiempo real
- Retroalimentación visual clara

## Tipos de Usuario

### 1. Visitante (Nivel 1)
- Acceso solo a Puerta 1
- Requiere: motivo de visita, persona a visitar, torre y apartamento

### 2. Residente (Nivel 2)
- Acceso a Puertas 1 y 2
- Requiere: torre y apartamento

### 3. Empleado (Nivel 3)
- Acceso a todas las puertas
- Requiere: información básica

### 4. Administrador (Nivel 3)
- Acceso completo a todas las puertas
- Requiere: información básica

## API Endpoints

El frontend se comunica con los siguientes endpoints del backend:

- `POST /api/auth/register` - Registro de usuario
- `POST /api/auth/loginQR-file` - Validación de QR por archivo
- `POST /api/auth/loginQR-json` - Validación de QR por JSON

## Personalización

### Colores
Los colores principales se pueden modificar en `app/globals.css`:
```css
@layer components {
  .btn-primary {
    @apply bg-blue-600 hover:bg-blue-700 ...
  }
}
```

### Estilos
El proyecto usa Tailwind CSS 4. Para personalizar, edita:
- `tailwind.config.ts` - Configuración de Tailwind
- `app/globals.css` - Estilos globales y componentes

## Scripts Disponibles

```bash
npm run dev      # Ejecutar en modo desarrollo
npm run build    # Compilar para producción
npm run start    # Iniciar servidor de producción
npm run lint     # Ejecutar linter
```

## Navegación

- `/` - Página de inicio
- `/registro` - Registro de usuarios
- `/ingreso` - Ingreso con código QR

## Notas Importantes

1. **Backend requerido**: El frontend necesita que el backend esté corriendo en el puerto 8081
2. **CORS**: El backend debe tener CORS configurado para aceptar peticiones desde `localhost:3000`
3. **Imágenes QR**: El sistema acepta archivos PNG, JPG y JPEG
4. **Variables de entorno**: `.env.local` está en `.gitignore` (NO se sube a Git)
5. **Despliegue**: Ver [DESPLIEGUE_ESTUDIANTES.md](../DESPLIEGUE_ESTUDIANTES.md) para Vercel

## Despliegue en Producción

Para desplegar en Vercel (gratis), consulta la guía completa:
- [Guía de Despliegue para Estudiantes](../DESPLIEGUE_ESTUDIANTES.md)

## Mejoras Futuras

- [ ] Agregar panel de administración
- [ ] Implementar historial de accesos
- [ ] Agregar notificaciones en tiempo real
- [ ] Implementar modo oscuro
- [ ] Agregar internacionalización (i18n)

## Contacto

Para más información o soporte, consulta la documentación del proyecto principal.
