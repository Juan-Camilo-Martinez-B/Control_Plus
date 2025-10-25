// Configuración de la API
const API_BASE_URL = process.env.NEXT_PUBLIC_API_URL || 'http://localhost:8081/api';

// Tipos de datos
export interface UsuarioRegistroDTO {
  tipo: 'residente' | 'visitante' | 'empleado' | 'administrador';
  nombre: string;
  identificacion: string;
  correo: string;
  // Campos para residente
  torre?: string;
  apartamento?: string;
  // Campos para visitante
  motivoVisita?: string;
  personaAVisitar?: string;
  torreVisita?: string;
  apartamentoVisita?: string;
}

export interface ApiResponse<T = any> {
  status: string;
  message: string;
  data?: T;
}

// Servicio de API
class ApiService {
  private baseUrl: string;

  constructor(baseUrl: string) {
    this.baseUrl = baseUrl;
  }

  async registrarUsuario(usuario: UsuarioRegistroDTO): Promise<ApiResponse> {
    const response = await fetch(`${this.baseUrl}/auth/register`, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify(usuario),
    });

    if (!response.ok) {
      const error = await response.json();
      throw new Error(error.message || 'Error al registrar usuario');
    }

    return response.json();
  }

  async validarQRJson(qrToken: string): Promise<ApiResponse> {
    const response = await fetch(`${this.baseUrl}/auth/loginQR-json`, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify({ qrToken }),
    });

    if (!response.ok) {
      const error = await response.json();
      throw new Error(error.message || 'Error al validar QR');
    }

    return response.json();
  }

  async validarQRArchivo(archivo: File): Promise<ApiResponse> {
    const formData = new FormData();
    formData.append('archivo', archivo);

    const response = await fetch(`${this.baseUrl}/auth/loginQR-file`, {
      method: 'POST',
      body: formData,
    });

    if (!response.ok) {
      const error = await response.json();
      throw new Error(error.message || 'Error al validar QR');
    }

    return response.json();
  }
}

export const apiService = new ApiService(API_BASE_URL);

