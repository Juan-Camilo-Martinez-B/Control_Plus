'use client';

import { useState } from 'react';
import Link from 'next/link';
import { apiService } from '@/lib/api';

type NivelPuerta = 1 | 2 | 3;

export default function IngresoPage() {
  const [puertaSeleccionada, setPuertaSeleccionada] = useState<NivelPuerta | null>(null);
  const [loading, setLoading] = useState(false);
  const [mensaje, setMensaje] = useState('');
  const [error, setError] = useState('');
  const [tipoIngreso, setTipoIngreso] = useState<'archivo' | null>(null);

  const handleSeleccionPuerta = (nivel: NivelPuerta) => {
    setPuertaSeleccionada(nivel);
    setMensaje('');
    setError('');
  };

  const handleFileUpload = async (e: React.ChangeEvent<HTMLInputElement>) => {
    const file = e.target.files?.[0];
    if (!file || !puertaSeleccionada) return;

    setLoading(true);
    setError('');
    setMensaje('');

    try {
      const response = await apiService.validarQRArchivo(file);
      
      // Determinar si tiene acceso según el nivel de puerta
      const mensajeRespuesta = response.message || response.data;
      const tieneAcceso = verificarAcceso(mensajeRespuesta, puertaSeleccionada);
      
      if (tieneAcceso) {
        setMensaje(`✅ ${mensajeRespuesta}`);
      } else {
        setError(`❌ Acceso denegado. Tu nivel de acceso no te permite ingresar por la Puerta ${puertaSeleccionada}.`);
      }
    } catch (err: any) {
      setError(err.message || 'Error al validar el código QR');
    } finally {
      setLoading(false);
    }
  };

  const verificarAcceso = (mensaje: string, puerta: NivelPuerta): boolean => {
    const mensajeLower = mensaje.toLowerCase();
    
    if (mensajeLower.includes('todas las puertas') || mensajeLower.includes('acceso completo')) {
      return true; // Nivel 3: acceso a todas las puertas
    }
    
    if (mensajeLower.includes('puerta 1 y 2')) {
      return puerta <= 2; // Nivel 2: acceso a puertas 1 y 2
    }
    
    if (mensajeLower.includes('solo a puerta 1') || mensajeLower.includes('puerta 1')) {
      return puerta === 1; // Nivel 1: solo puerta 1
    }
    
    // Si el mensaje indica acceso (contiene "bienvenido" o "✅"), asumimos que tiene permiso
    if (mensajeLower.includes('bienvenido') || mensajeLower.includes('✅')) {
      return true;
    }
    
    return false;
  };

  const renderSeleccionPuerta = () => (
    <div className="max-w-5xl mx-auto">
      <div className="text-center mb-12">
        <h1 className="text-4xl font-bold text-gray-900 mb-4">Ingreso al Sistema</h1>
        <p className="text-lg text-gray-600">Selecciona la puerta por la que deseas ingresar</p>
      </div>

      <div className="grid md:grid-cols-3 gap-8">
        <button
          onClick={() => handleSeleccionPuerta(1)}
          className={`card group cursor-pointer transform hover:scale-105 transition-all duration-300 ${
            puertaSeleccionada === 1 ? 'border-4 border-green-500' : ''
          }`}
        >
          <div className="flex flex-col items-center text-center">
            <div className="bg-green-100 text-green-600 rounded-full p-6 mb-6 group-hover:bg-green-600 group-hover:text-white transition-colors">
              <svg className="w-16 h-16" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M8 11V7a4 4 0 118 0m-4 8v2m-6 4h12a2 2 0 002-2v-6a2 2 0 00-2-2H6a2 2 0 00-2 2v6a2 2 0 002 2z" />
              </svg>
            </div>
            <h2 className="text-2xl font-bold text-gray-900 mb-3">Puerta 1</h2>
            <p className="text-gray-600 mb-2">Nivel Básico</p>
            <p className="text-sm text-gray-500">Todos los usuarios</p>
          </div>
        </button>

        <button
          onClick={() => handleSeleccionPuerta(2)}
          className={`card group cursor-pointer transform hover:scale-105 transition-all duration-300 ${
            puertaSeleccionada === 2 ? 'border-4 border-blue-500' : ''
          }`}
        >
          <div className="flex flex-col items-center text-center">
            <div className="bg-blue-100 text-blue-600 rounded-full p-6 mb-6 group-hover:bg-blue-600 group-hover:text-white transition-colors">
              <svg className="w-16 h-16" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M12 15v2m-6 4h12a2 2 0 002-2v-6a2 2 0 00-2-2H6a2 2 0 00-2 2v6a2 2 0 002 2zm10-10V7a4 4 0 00-8 0v4h8z" />
              </svg>
            </div>
            <h2 className="text-2xl font-bold text-gray-900 mb-3">Puerta 2</h2>
            <p className="text-gray-600 mb-2">Nivel Intermedio</p>
            <p className="text-sm text-gray-500">Residentes y superiores</p>
          </div>
        </button>

        <button
          onClick={() => handleSeleccionPuerta(3)}
          className={`card group cursor-pointer transform hover:scale-105 transition-all duration-300 ${
            puertaSeleccionada === 3 ? 'border-4 border-purple-500' : ''
          }`}
        >
          <div className="flex flex-col items-center text-center">
            <div className="bg-purple-100 text-purple-600 rounded-full p-6 mb-6 group-hover:bg-purple-600 group-hover:text-white transition-colors">
              <svg className="w-16 h-16" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M9 12l2 2 4-4m5.618-4.016A11.955 11.955 0 0112 2.944a11.955 11.955 0 01-8.618 3.04A12.02 12.02 0 003 9c0 5.591 3.824 10.29 9 11.622 5.176-1.332 9-6.03 9-11.622 0-1.042-.133-2.052-.382-3.016z" />
              </svg>
            </div>
            <h2 className="text-2xl font-bold text-gray-900 mb-3">Puerta 3</h2>
            <p className="text-gray-600 mb-2">Nivel Premium</p>
            <p className="text-sm text-gray-500">Empleados y administradores</p>
          </div>
        </button>
      </div>

      {puertaSeleccionada && (
        <div className="mt-12 max-w-2xl mx-auto">
          <div className="card">
            <h3 className="text-2xl font-bold text-gray-900 mb-6 text-center">
              Escanea tu Código QR
            </h3>

            <div className="border-4 border-dashed border-gray-300 rounded-xl p-12 text-center hover:border-blue-500 transition-colors">
              <input
                type="file"
                accept="image/*"
                onChange={handleFileUpload}
                className="hidden"
                id="qr-upload"
                disabled={loading}
              />
              <label
                htmlFor="qr-upload"
                className="cursor-pointer flex flex-col items-center"
              >
                <div className="bg-blue-100 text-blue-600 rounded-full p-6 mb-4">
                  <svg className="w-16 h-16" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                    <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M12 4v1m6 11h2m-6 0h-2v4m0-11v3m0 0h.01M12 12h4.01M16 20h4M4 12h4m12 0h.01M5 8h2a1 1 0 001-1V5a1 1 0 00-1-1H5a1 1 0 00-1 1v2a1 1 0 001 1zm12 0h2a1 1 0 001-1V5a1 1 0 00-1-1h-2a1 1 0 00-1 1v2a1 1 0 001 1zM5 20h2a1 1 0 001-1v-2a1 1 0 00-1-1H5a1 1 0 00-1 1v2a1 1 0 001 1z" />
                  </svg>
                </div>
                <p className="text-lg font-semibold text-gray-900 mb-2">
                  Haz clic para subir tu código QR
                </p>
                <p className="text-sm text-gray-600">
                  Soporta: PNG, JPG, JPEG
                </p>
              </label>
            </div>

            {loading && (
              <div className="mt-6 text-center">
                <div className="inline-block animate-spin rounded-full h-12 w-12 border-b-2 border-blue-600"></div>
                <p className="mt-4 text-gray-600">Validando código QR...</p>
              </div>
            )}

            {mensaje && (
              <div className="mt-6 bg-green-50 border border-green-200 text-green-700 px-6 py-4 rounded-lg">
                <p className="font-semibold text-lg">{mensaje}</p>
              </div>
            )}

            {error && (
              <div className="mt-6 bg-red-50 border border-red-200 text-red-700 px-6 py-4 rounded-lg">
                <p className="font-semibold text-lg">{error}</p>
              </div>
            )}

            <div className="mt-6 text-center">
              <button
                onClick={() => {
                  setPuertaSeleccionada(null);
                  setMensaje('');
                  setError('');
                }}
                className="text-blue-600 hover:text-blue-700 font-semibold"
              >
                ← Cambiar puerta
              </button>
            </div>
          </div>
        </div>
      )}

      <div className="text-center mt-8">
        <Link href="/" className="text-blue-600 hover:text-blue-700 font-semibold">
          ← Volver al inicio
        </Link>
      </div>
    </div>
  );

  return <>{renderSeleccionPuerta()}</>;
}

