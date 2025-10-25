'use client';

import { useState } from 'react';
import Link from 'next/link';
import { apiService, UsuarioRegistroDTO } from '@/lib/api';

type TipoUsuario = 'residente' | 'visitante' | 'empleado' | 'administrador';

export default function RegistroPage() {
  const [paso, setPaso] = useState<'seleccion' | 'formulario' | 'exito'  >('seleccion');
  const [tipoUsuario, setTipoUsuario] = useState<TipoUsuario | null>(null);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState('');
  const [mensaje, setMensaje] = useState('');

  const [formData, setFormData] = useState({
    nombre: '',
    identificacion: '',
    correo: '',
    torre: '',
    apartamento: '',
    motivoVisita: '',
    personaAVisitar: '',
    torreVisita: '',
    apartamentoVisita: '',
  });

  const handleTipoSelect = (tipo: TipoUsuario) => {
    setTipoUsuario(tipo);
    setPaso('formulario');
    setError('');
  };

  const handleInputChange = (e: React.ChangeEvent<HTMLInputElement | HTMLTextAreaElement>) => {
    setFormData({
      ...formData,
      [e.target.name]: e.target.value,
    });
  };

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    setLoading(true);
    setError('');

    try {
      const usuario: UsuarioRegistroDTO = {
        tipo: tipoUsuario!,
        nombre: formData.nombre,
        identificacion: formData.identificacion,
        correo: formData.correo,
      };

      if (tipoUsuario === 'residente') {
        usuario.torre = formData.torre;
        usuario.apartamento = formData.apartamento;
      } else if (tipoUsuario === 'visitante') {
        usuario.motivoVisita = formData.motivoVisita;
        usuario.personaAVisitar = formData.personaAVisitar;
        usuario.torreVisita = formData.torreVisita;
        usuario.apartamentoVisita = formData.apartamentoVisita;
      }

      const response = await apiService.registrarUsuario(usuario);
      setMensaje(response.message);
      setPaso('exito');
    } catch (err: any) {
      setError(err.message || 'Error al registrar usuario');
    } finally {
      setLoading(false);
    }
  };

  const renderSeleccionTipo = () => (
    <div className="max-w-5xl mx-auto">
      <div className="text-center mb-12">
        <h1 className="text-4xl font-bold text-gray-900 mb-4">Registro de Usuario</h1>
        <p className="text-lg text-gray-600">Selecciona tu tipo de usuario</p>
      </div>

      <div className="grid md:grid-cols-2 lg:grid-cols-4 gap-6">
        <button
          onClick={() => handleTipoSelect('residente')}
          className="card-sm group hover:border-2 hover:border-blue-500 transition-all duration-300"
        >
          <div className="flex flex-col items-center text-center">
            <div className="bg-blue-100 text-blue-600 rounded-full p-4 mb-4 group-hover:bg-blue-600 group-hover:text-white transition-colors">
              <svg className="w-10 h-10" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M3 12l2-2m0 0l7-7 7 7M5 10v10a1 1 0 001 1h3m10-11l2 2m-2-2v10a1 1 0 01-1 1h-3m-6 0a1 1 0 001-1v-4a1 1 0 011-1h2a1 1 0 011 1v4a1 1 0 001 1m-6 0h6" />
              </svg>
            </div>
            <h3 className="text-xl font-bold text-gray-900 mb-2">Residente</h3>
            <p className="text-sm text-gray-600">Acceso a puertas 1 y 2</p>
          </div>
        </button>

        <button
          onClick={() => handleTipoSelect('visitante')}
          className="card-sm group hover:border-2 hover:border-green-500 transition-all duration-300"
        >
          <div className="flex flex-col items-center text-center">
            <div className="bg-green-100 text-green-600 rounded-full p-4 mb-4 group-hover:bg-green-600 group-hover:text-white transition-colors">
              <svg className="w-10 h-10" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M16 7a4 4 0 11-8 0 4 4 0 018 0zM12 14a7 7 0 00-7 7h14a7 7 0 00-7-7z" />
              </svg>
            </div>
            <h3 className="text-xl font-bold text-gray-900 mb-2">Visitante</h3>
            <p className="text-sm text-gray-600">Acceso solo a puerta 1</p>
          </div>
        </button>

        <button
          onClick={() => handleTipoSelect('empleado')}
          className="card-sm group hover:border-2 hover:border-purple-500 transition-all duration-300"
        >
          <div className="flex flex-col items-center text-center">
            <div className="bg-purple-100 text-purple-600 rounded-full p-4 mb-4 group-hover:bg-purple-600 group-hover:text-white transition-colors">
              <svg className="w-10 h-10" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M21 13.255A23.931 23.931 0 0112 15c-3.183 0-6.22-.62-9-1.745M16 6V4a2 2 0 00-2-2h-4a2 2 0 00-2 2v2m4 6h.01M5 20h14a2 2 0 002-2V8a2 2 0 00-2-2H5a2 2 0 00-2 2v10a2 2 0 002 2z" />
              </svg>
            </div>
            <h3 className="text-xl font-bold text-gray-900 mb-2">Empleado</h3>
            <p className="text-sm text-gray-600">Acceso a todas las puertas</p>
          </div>
        </button>

        <button
          onClick={() => handleTipoSelect('administrador')}
          className="card-sm group hover:border-2 hover:border-red-500 transition-all duration-300"
        >
          <div className="flex flex-col items-center text-center">
            <div className="bg-red-100 text-red-600 rounded-full p-4 mb-4 group-hover:bg-red-600 group-hover:text-white transition-colors">
              <svg className="w-10 h-10" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M9 12l2 2 4-4m5.618-4.016A11.955 11.955 0 0112 2.944a11.955 11.955 0 01-8.618 3.04A12.02 12.02 0 003 9c0 5.591 3.824 10.29 9 11.622 5.176-1.332 9-6.03 9-11.622 0-1.042-.133-2.052-.382-3.016z" />
              </svg>
            </div>
            <h3 className="text-xl font-bold text-gray-900 mb-2">Administrador</h3>
            <p className="text-sm text-gray-600">Acceso completo</p>
          </div>
        </button>
      </div>

      <div className="text-center mt-8">
        <Link href="/" className="text-blue-600 hover:text-blue-700 font-semibold">
          ← Volver al inicio
        </Link>
      </div>
    </div>
  );

  const renderFormulario = () => (
    <div className="max-w-2xl mx-auto">
      <div className="card">
        <div className="text-center mb-8">
          <h1 className="text-3xl font-bold text-gray-900 mb-2">
            Registro como {tipoUsuario?.charAt(0).toUpperCase()}{tipoUsuario?.slice(1)}
          </h1>
          <p className="text-gray-600">Completa tus datos para registrarte</p>
        </div>

        {error && (
          <div className="bg-red-50 border border-red-200 text-red-700 px-4 py-3 rounded-lg mb-6">
            {error}
          </div>
        )}

        <form onSubmit={handleSubmit} className="space-y-6">
          {/* Campos comunes */}
          <div>
            <label className="label-text">Nombre Completo</label>
            <input
              type="text"
              name="nombre"
              value={formData.nombre}
              onChange={handleInputChange}
              className="input-field"
              required
            />
          </div>

          <div>
            <label className="label-text">Identificación</label>
            <input
              type="text"
              name="identificacion"
              value={formData.identificacion}
              onChange={handleInputChange}
              className="input-field"
              required
            />
          </div>

          <div>
            <label className="label-text">Correo Electrónico</label>
            <input
              type="email"
              name="correo"
              value={formData.correo}
              onChange={handleInputChange}
              className="input-field"
              required
            />
          </div>

          {/* Campos específicos para Residente */}
          {tipoUsuario === 'residente' && (
            <>
              <div>
                <label className="label-text">Torre</label>
                <input
                  type="text"
                  name="torre"
                  value={formData.torre}
                  onChange={handleInputChange}
                  className="input-field"
                  required
                />
              </div>
              <div>
                <label className="label-text">Apartamento</label>
                <input
                  type="text"
                  name="apartamento"
                  value={formData.apartamento}
                  onChange={handleInputChange}
                  className="input-field"
                  required
                />
              </div>
            </>
          )}

          {/* Campos específicos para Visitante */}
          {tipoUsuario === 'visitante' && (
            <>
              <div>
                <label className="label-text">Motivo de Visita</label>
                <textarea
                  name="motivoVisita"
                  value={formData.motivoVisita}
                  onChange={handleInputChange}
                  className="input-field"
                  rows={3}
                  required
                />
              </div>
              <div>
                <label className="label-text">Persona a Visitar</label>
                <input
                  type="text"
                  name="personaAVisitar"
                  value={formData.personaAVisitar}
                  onChange={handleInputChange}
                  className="input-field"
                  required
                />
              </div>
              <div className="grid grid-cols-2 gap-4">
                <div>
                  <label className="label-text">Torre de Visita</label>
                  <input
                    type="text"
                    name="torreVisita"
                    value={formData.torreVisita}
                    onChange={handleInputChange}
                    className="input-field"
                    required
                  />
                </div>
                <div>
                  <label className="label-text">Apartamento de Visita</label>
                  <input
                    type="text"
                    name="apartamentoVisita"
                    value={formData.apartamentoVisita}
                    onChange={handleInputChange}
                    className="input-field"
                    required
                  />
                </div>
              </div>
            </>
          )}

          <div className="flex gap-4 pt-4">
            <button
              type="button"
              onClick={() => setPaso('seleccion')}
              className="btn-secondary flex-1"
              disabled={loading}
            >
              Atrás
            </button>
            <button
              type="submit"
              className="btn-primary flex-1"
              disabled={loading}
            >
              {loading ? 'Registrando...' : 'Registrar'}
            </button>
          </div>
        </form>
      </div>
    </div>
  );

  const renderExito = () => (
    <div className="max-w-2xl mx-auto">
      <div className="card text-center">
        <div className="bg-green-100 text-green-600 rounded-full w-20 h-20 flex items-center justify-center mx-auto mb-6">
          <svg className="w-12 h-12" fill="none" stroke="currentColor" viewBox="0 0 24 24">
            <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M5 13l4 4L19 7" />
          </svg>
        </div>
        <h1 className="text-3xl font-bold text-gray-900 mb-4">¡Registro Exitoso!</h1>
        <p className="text-lg text-gray-600 mb-6">
          {mensaje}
        </p>
        <div className="bg-blue-50 border border-blue-200 rounded-lg p-4 mb-6">
          <p className="text-sm text-blue-800">
            <strong>Importante:</strong> Revisa tu correo electrónico y haz clic en el enlace de verificación.
            Una vez verificado, recibirás tu código QR de acceso.
          </p>
        </div>
        <Link href="/" className="btn-primary inline-block">
          Volver al Inicio
        </Link>
      </div>
    </div>
  );

  return (
    <>
      {paso === 'seleccion' && renderSeleccionTipo()}
      {paso === 'formulario' && renderFormulario()}
      {paso === 'exito' && renderExito()}
    </>
  );
}

