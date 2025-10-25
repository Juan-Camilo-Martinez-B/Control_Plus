# 🎓 Despliegue Gratuito


---

## 🎯 Lo que se va a lograr

Tu aplicación estará disponible en:
```
https://controlplus.vercel.app
```

Y funcionará **exactamente igual** que en tu computadora, pero **cualquiera con internet** podrá accederla.

---

## 📦 Servicios Necesarios (TODOS GRATIS)

1. **GitHub** (para guardar tu código) - Gratis ✅
2. **Railway** (para el backend Java) - $5 crédito/mes gratis ✅
3. **Vercel** (para el frontend Next.js) - Gratis ilimitado ✅
4. **MongoDB Atlas** (base de datos) - Ya lo tienes ✅

---

## 🚀 Paso a Paso Ultra Simplificado

### PARTE 1: Subir a GitHub (10 minutos)

#### 1.1 Crear .gitignore

Crea un archivo `.gitignore` en la raíz de tu proyecto:

```gitignore
# No subir contraseñas
Backend/src/main/resources/application.properties
Frontend/.env.local
Frontend/.env

# No subir archivos compilados
Backend/target/
Frontend/node_modules/
Frontend/.next/

# No subir configuraciones del IDE
.vscode/
.idea/
*.iml
```

#### 1.2 Subir a GitHub

```bash
# En la terminal, en la carpeta del proyecto
cd C:\Users\juanc\Desktop\Controlplus

# Inicializar git
git init

# Agregar todos los archivos
git add .

# Hacer commit
git commit -m "Proyecto ControlPlus completo"

# Ir a GitHub.com y crear un repositorio nuevo llamado "controlplus"
# Luego ejecutar:
git remote add origin https://github.com/TU_USUARIO/controlplus.git
git branch -M main
git push -u origin main
```

---

### PARTE 2: Desplegar Backend en Railway (15 minutos)

#### 2.1 Crear cuenta

1. Ve a: https://railway.app/
2. Haz clic en **"Login with GitHub"**
3. Autoriza Railway

#### 2.2 Crear proyecto

1. Haz clic en **"New Project"**
2. Selecciona **"Deploy from GitHub repo"**
3. Busca y selecciona tu repositorio `controlplus`
4. Railway empezará a desplegar automáticamente

#### 2.3 Configurar variables de entorno

1. En Railway, haz clic en tu proyecto
2. Ve a la pestaña **"Variables"**
3. Agrega estas variables (haz clic en "+ New Variable"):

```
MONGODB_URI
mongodb+srv://juanmartinezbed:TU_CONTRASEÑA@controlplus.wr9wpcb.mongodb.net/ControlPlus?retryWrites=true&w=majority&appName=ControlPluss

MAIL_HOST
smtp.gmail.com

MAIL_PORT
587

MAIL_USERNAME
martinez.b.juan.camilo@gmail.com

MAIL_PASSWORD
eahw cidj jfel vowa

MAIL_FROM
martinez.b.juan.camilo@gmail.com

PORT
8081
```

#### 2.4 Actualizar application.properties

Edita `Backend/src/main/resources/application.properties` para usar variables de entorno:

```properties
spring.application.name=backend

server.port=${PORT:8081}
spring.mvc.pathmatch.matching-strategy=path_pattern_parser

# Email configuration
spring.mail.host=${MAIL_HOST:smtp.gmail.com}
spring.mail.port=${MAIL_PORT:587}
spring.mail.username=${MAIL_USERNAME}
spring.mail.password=${MAIL_PASSWORD}
spring.mail.properties.mail.smtp.auth=true
spring.mail.properties.mail.smtp.starttls.enable=true
spring.mail.properties.mail.smtp.starttls.required=true
spring.mail.properties.mail.smtp.ssl.trust=${MAIL_HOST:smtp.gmail.com}
spring.mail.from=${MAIL_FROM}

# MongoDB
spring.data.mongodb.uri=${MONGODB_URI}

# File upload
spring.servlet.multipart.enabled=true
spring.servlet.multipart.max-file-size=5MB
spring.servlet.multipart.max-request-size=5MB
```

#### 2.5 Subir cambios

```bash
git add .
git commit -m "Configurar para Railway"
git push
```

Railway redesplegarán automáticamente (toma ~5 minutos).

#### 2.6 Obtener URL del backend

1. En Railway, haz clic en tu proyecto
2. Ve a **"Settings"** → **"Generate Domain"**
3. Copia la URL (algo como: `controlplus-production.up.railway.app`)

---

### PARTE 3: Desplegar Frontend en Vercel (10 minutos)

#### 3.1 Crear cuenta

1. Ve a: https://vercel.com/
2. Haz clic en **"Sign Up"**
3. Selecciona **"Continue with GitHub"**
4. Autoriza Vercel

#### 3.2 Importar proyecto

1. En Vercel, haz clic en **"Add New"** → **"Project"**
2. Busca tu repositorio `controlplus`
3. Haz clic en **"Import"**

#### 3.3 Configurar el proyecto

En la pantalla de configuración:

1. **Framework Preset**: Next.js (se detecta automáticamente)
2. **Root Directory**: `Frontend` ← **MUY IMPORTANTE**
3. **Build Command**: `npm run build` (ya está por defecto)
4. **Output Directory**: `.next` (ya está por defecto)

#### 3.4 Agregar variable de entorno

En la misma pantalla, baja hasta **"Environment Variables"**:

**Name:**
```
NEXT_PUBLIC_API_URL
```

**Value:** (reemplaza con tu URL de Railway)
```
https://controlplus-production.up.railway.app/api
```

#### 3.5 Desplegar

1. Haz clic en **"Deploy"**
2. Espera 2-3 minutos
3. ¡Listo! 🎉

Vercel te dará una URL como:
```
https://controlplus.vercel.app
```

---

### PARTE 4: Ajustes Finales (5 minutos)

#### 4.1 Actualizar CORS en el backend

Edita `Backend/src/main/java/com/controlacceso/backend/config/SecurityConfig.java`

Busca el método `corsConfigurationSource()` y actualiza:

```java
@Bean
public CorsConfigurationSource corsConfigurationSource() {
    CorsConfiguration configuration = new CorsConfiguration();
    
    // Permitir tu dominio de Vercel
    configuration.setAllowedOrigins(List.of(
            "http://localhost:3000",
            "https://controlplus.vercel.app",  // Tu URL de Vercel
            "https://*.vercel.app"  // Todos los previews de Vercel
    ));
    
    configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
    configuration.setAllowedHeaders(Arrays.asList("*"));
    configuration.setAllowCredentials(true);
    configuration.setMaxAge(3600L);

    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", configuration);
    
    return source;
}
```

#### 4.2 Actualizar enlaces de verificación

Edita `Backend/src/main/java/com/controlacceso/backend/controller/AuthController.java`

Busca la línea ~128 y cambia:

```java
String enlaceVerificacion = String.format(
    "https://controlplus.vercel.app/verificar?correo=%s&codigo=%s",  // Tu URL de Vercel
    usuario.getCorreo(),
    codigoValidacion
);
```

#### 4.3 Crear página de verificación

Crea `Frontend/app/verificar/page.tsx`:

```typescript
'use client';

import { Suspense, useEffect, useState } from 'react';
import { useSearchParams } from 'next/navigation';
import Link from 'next/link';

function VerificarContent() {
  const searchParams = useSearchParams();
  const [estado, setEstado] = useState<'cargando' | 'exito' | 'error'>('cargando');
  const [mensaje, setMensaje] = useState('');

  useEffect(() => {
    const verificar = async () => {
      const correo = searchParams.get('correo');
      const codigo = searchParams.get('codigo');

      if (!correo || !codigo) {
        setEstado('error');
        setMensaje('Enlace de verificación inválido');
        return;
      }

      try {
        const response = await fetch(
          `${process.env.NEXT_PUBLIC_API_URL}/auth/verify-email?correo=${correo}&codigo=${codigo}`
        );

        if (response.ok) {
          setEstado('exito');
          setMensaje('¡Correo verificado exitosamente! Tu código QR ha sido enviado.');
        } else {
          const data = await response.json();
          setEstado('error');
          setMensaje(data.message || 'Error al verificar el correo');
        }
      } catch (error) {
        setEstado('error');
        setMensaje('Error de conexión. Inténtalo de nuevo.');
      }
    };

    verificar();
  }, [searchParams]);

  return (
    <div className="max-w-2xl mx-auto">
      <div className="card text-center">
        {estado === 'cargando' && (
          <>
            <div className="inline-block animate-spin rounded-full h-16 w-16 border-b-2 border-blue-600 mb-6"></div>
            <h1 className="text-2xl font-bold text-gray-900 mb-4">Verificando...</h1>
            <p className="text-gray-600">Por favor espera mientras verificamos tu correo.</p>
          </>
        )}

        {estado === 'exito' && (
          <>
            <div className="bg-green-100 text-green-600 rounded-full w-20 h-20 flex items-center justify-center mx-auto mb-6">
              <svg className="w-12 h-12" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M5 13l4 4L19 7" />
              </svg>
            </div>
            <h1 className="text-3xl font-bold text-gray-900 mb-4">¡Verificación Exitosa!</h1>
            <p className="text-lg text-gray-600 mb-6">{mensaje}</p>
            <div className="bg-blue-50 border border-blue-200 rounded-lg p-4 mb-6">
              <p className="text-sm text-blue-800">
                Revisa tu correo electrónico para encontrar tu código QR de acceso.
              </p>
            </div>
            <Link href="/" className="btn-primary inline-block">
              Volver al Inicio
            </Link>
          </>
        )}

        {estado === 'error' && (
          <>
            <div className="bg-red-100 text-red-600 rounded-full w-20 h-20 flex items-center justify-center mx-auto mb-6">
              <svg className="w-12 h-12" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M6 18L18 6M6 6l12 12" />
              </svg>
            </div>
            <h1 className="text-3xl font-bold text-gray-900 mb-4">Error de Verificación</h1>
            <p className="text-lg text-gray-600 mb-6">{mensaje}</p>
            <Link href="/registro" className="btn-primary inline-block">
              Intentar de Nuevo
            </Link>
          </>
        )}
      </div>
    </div>
  );
}

export default function VerificarPage() {
  return (
    <Suspense fallback={
      <div className="max-w-2xl mx-auto">
        <div className="card text-center">
          <div className="inline-block animate-spin rounded-full h-16 w-16 border-b-2 border-blue-600 mb-6"></div>
          <h1 className="text-2xl font-bold text-gray-900 mb-4">Cargando...</h1>
        </div>
      </div>
    }>
      <VerificarContent />
    </Suspense>
  );
}
```

#### 4.4 Subir cambios finales

```bash
git add .
git commit -m "Configurar para producción"
git push
```

Railway y Vercel redesplegarán automáticamente (2-3 minutos).

---

## ✅ ¡LISTO! Verificación Final

### Prueba tu aplicación:

1. **Abre tu URL de Vercel**: `https://controlplus.vercel.app`
2. **Registra un usuario de prueba**
3. **Revisa tu correo** (puede tardar 1-2 minutos)
4. **Haz clic en el enlace de verificación**
5. **Recibe tu QR por correo**
6. **Prueba el ingreso con QR**

---

## 💡 Respuestas a Preguntas Frecuentes

### ❓ ¿El usuario necesita saber de Railway?
**NO.** El usuario solo necesita tu URL de Vercel.

### ❓ ¿Cómo comparto mi aplicación?
Solo comparte: `https://controlplus.vercel.app`

### ❓ ¿Cuánto tiempo está disponible?
**Para siempre** (mientras no elimines los servicios).

### ❓ ¿Necesito tarjeta de crédito?
**NO.** Todo es 100% gratis con límites generosos.

### ❓ ¿Cuántos usuarios puede tener?
- **Railway**: ~500 horas/mes = ~20,000 solicitudes/mes
- **Vercel**: Ilimitado para uso personal/estudiante
- **MongoDB**: 512 MB = ~1000-5000 usuarios

### ❓ ¿Qué pasa si se me acaba el crédito de Railway?
Puedes migrar a Render (100% gratis pero más lento).

### ❓ ¿Puedo usar esto en mi portafolio?
**¡SÍ!** Es perfecto para tu portafolio como estudiante.

---

## 🎓 Beneficios para Estudiantes

✅ **Portafolio profesional** - URL real que compartir  
✅ **Experiencia práctica** - Deploy real como en empresas  
✅ **CV mejorado** - Proyecto desplegado en producción  
✅ **Demostraciones** - Muéstralo en entrevistas  
✅ **Colaboración** - Compártelo con profesores/compañeros  

---

## 🆘 Si Algo Falla

### Backend no responde
1. Ve a Railway → Tu proyecto → Logs
2. Busca errores en rojo
3. Verifica que las variables de entorno estén bien

### Frontend no carga
1. Ve a Vercel → Tu proyecto → Deployments
2. Haz clic en el último deployment
3. Revisa la pestaña "Build Logs"

### No llegan correos
1. Verifica la contraseña de Gmail en Railway
2. Asegúrate que sea contraseña de aplicación
3. Revisa spam en tu correo

---

## 💰 Costo Real

| Servicio | Costo |
|----------|-------|
| GitHub | $0 |
| Railway | $0 ($5 crédito/mes) |
| Vercel | $0 |
| MongoDB Atlas | $0 |
| **TOTAL** | **$0/mes** |

---

## 🚀 Actualizaciones Futuras

Cuando hagas cambios en tu código:

```bash
git add .
git commit -m "Descripción del cambio"
git push
```

**Railway y Vercel redesplegarán automáticamente** 🎉

---

## 📞 Recursos de Ayuda

- **Railway Docs**: https://docs.railway.app/
- **Vercel Docs**: https://vercel.com/docs
- **MongoDB Atlas**: https://www.mongodb.com/docs/atlas/

---

¡Felicidades! 🎓🎉 

Ahora tienes una aplicación profesional desplegada completamente gratis.

