'use client';

import Link from 'next/link';

export default function Home() {
  return (
    <div className="max-w-6xl mx-auto">
      {/* Hero Section */}
      <div className="text-center mb-16 animate-fade-in">
        <h1 className="text-5xl md:text-6xl font-bold text-gray-900 mb-4">
          Bienvenido a <span className="text-blue-600">ControlPlus</span>
        </h1>
        <p className="text-xl text-gray-600 max-w-2xl mx-auto">
          Sistema inteligente de control de acceso mediante códigos QR. 
          Seguro, rápido y eficiente.
        </p>
      </div>

      {/* Main Actions */}
      <div className="grid md:grid-cols-2 gap-8 mb-16">
        <Link href="/registro">
          <div className="card group cursor-pointer transform hover:scale-105 transition-all duration-300">
            <div className="flex flex-col items-center text-center">
              <div className="bg-blue-100 text-blue-600 rounded-full p-6 mb-6 group-hover:bg-blue-600 group-hover:text-white transition-colors duration-300">
                <svg className="w-16 h-16" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                  <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M18 9v3m0 0v3m0-3h3m-3 0h-3m-2-5a4 4 0 11-8 0 4 4 0 018 0zM3 20a6 6 0 0112 0v1H3v-1z" />
                </svg>
              </div>
              <h2 className="text-2xl font-bold text-gray-900 mb-3">Registro de Usuario</h2>
              <p className="text-gray-600 mb-4">
                Regístrate en el sistema como residente, visitante, empleado o administrador
              </p>
              <span className="text-blue-600 font-semibold group-hover:translate-x-2 transition-transform duration-300 inline-flex items-center">
                Comenzar →
              </span>
            </div>
          </div>
        </Link>

        <Link href="/ingreso">
          <div className="card group cursor-pointer transform hover:scale-105 transition-all duration-300">
            <div className="flex flex-col items-center text-center">
              <div className="bg-green-100 text-green-600 rounded-full p-6 mb-6 group-hover:bg-green-600 group-hover:text-white transition-colors duration-300">
                <svg className="w-16 h-16" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                  <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M12 4v1m6 11h2m-6 0h-2v4m0-11v3m0 0h.01M12 12h4.01M16 20h4M4 12h4m12 0h.01M5 8h2a1 1 0 001-1V5a1 1 0 00-1-1H5a1 1 0 00-1 1v2a1 1 0 001 1zm12 0h2a1 1 0 001-1V5a1 1 0 00-1-1h-2a1 1 0 00-1 1v2a1 1 0 001 1zM5 20h2a1 1 0 001-1v-2a1 1 0 00-1-1H5a1 1 0 00-1 1v2a1 1 0 001 1z" />
                </svg>
              </div>
              <h2 className="text-2xl font-bold text-gray-900 mb-3">Ingreso al Sistema</h2>
              <p className="text-gray-600 mb-4">
                Accede al edificio escaneando tu código QR personal
              </p>
              <span className="text-green-600 font-semibold group-hover:translate-x-2 transition-transform duration-300 inline-flex items-center">
                Ingresar →
              </span>
            </div>
          </div>
        </Link>
      </div>

      {/* Features Section */}
      <div className="bg-white rounded-2xl shadow-xl p-8 md:p-12">
        <h2 className="text-3xl font-bold text-gray-900 mb-8 text-center">
          Características del Sistema
        </h2>
        <div className="grid md:grid-cols-3 gap-8">
          <div className="text-center">
            <div className="bg-purple-100 text-purple-600 rounded-full w-16 h-16 flex items-center justify-center mx-auto mb-4">
              <svg className="w-8 h-8" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M9 12l2 2 4-4m5.618-4.016A11.955 11.955 0 0112 2.944a11.955 11.955 0 01-8.618 3.04A12.02 12.02 0 003 9c0 5.591 3.824 10.29 9 11.622 5.176-1.332 9-6.03 9-11.622 0-1.042-.133-2.052-.382-3.016z" />
              </svg>
            </div>
            <h3 className="text-xl font-bold text-gray-900 mb-2">Seguridad</h3>
            <p className="text-gray-600">
              Códigos QR únicos y verificación de correo electrónico
            </p>
          </div>
          
          <div className="text-center">
            <div className="bg-yellow-100 text-yellow-600 rounded-full w-16 h-16 flex items-center justify-center mx-auto mb-4">
              <svg className="w-8 h-8" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M13 10V3L4 14h7v7l9-11h-7z" />
              </svg>
            </div>
            <h3 className="text-xl font-bold text-gray-900 mb-2">Rapidez</h3>
            <p className="text-gray-600">
              Acceso instantáneo mediante escaneo de código QR
            </p>
          </div>
          
          <div className="text-center">
            <div className="bg-indigo-100 text-indigo-600 rounded-full w-16 h-16 flex items-center justify-center mx-auto mb-4">
              <svg className="w-8 h-8" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M12 6V4m0 2a2 2 0 100 4m0-4a2 2 0 110 4m-6 8a2 2 0 100-4m0 4a2 2 0 110-4m0 4v2m0-6V4m6 6v10m6-2a2 2 0 100-4m0 4a2 2 0 110-4m0 4v2m0-6V4" />
              </svg>
            </div>
            <h3 className="text-xl font-bold text-gray-900 mb-2">Niveles de Acceso</h3>
            <p className="text-gray-600">
              3 niveles configurables según el tipo de usuario
            </p>
          </div>
        </div>
      </div>
    </div>
  );
}

