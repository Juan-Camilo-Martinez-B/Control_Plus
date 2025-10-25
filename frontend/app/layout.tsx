import type { Metadata } from "next";
import { Inter } from "next/font/google";
import "./globals.css";

const inter = Inter({ subsets: ["latin"] });

export const metadata: Metadata = {
  title: "ControlPlus - Sistema de Control de Acceso",
  description: "Sistema de gestión de acceso mediante códigos QR",
};

export default function RootLayout({
  children,
}: {
  children: React.ReactNode;
}) {
  return (
    <html lang="es">
      <body className={inter.className}>
        <div className="min-h-screen flex flex-col">
          <nav className="bg-gradient-to-r from-blue-600 to-blue-700 text-white shadow-xl">
            <div className="container mx-auto px-6 py-5">
              <div className="flex items-center justify-between">
                <div className="flex items-center space-x-3">
                  <div className="bg-white text-blue-600 rounded-lg p-2">
                    <svg className="w-8 h-8" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                      <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M12 15v2m-6 4h12a2 2 0 002-2v-6a2 2 0 00-2-2H6a2 2 0 00-2 2v6a2 2 0 002 2zm10-10V7a4 4 0 00-8 0v4h8z" />
                    </svg>
                  </div>
                  <div>
                    <h1 className="text-2xl font-bold">ControlPlus</h1>
                    <p className="text-xs text-blue-100">Sistema de Control de Acceso</p>
                  </div>
                </div>
                <div className="hidden md:block text-sm text-blue-100">
                  Gestión Inteligente de Accesos
                </div>
              </div>
            </div>
          </nav>
          
          <main className="flex-grow container mx-auto px-4 py-8">
            {children}
          </main>
          
          <footer className="bg-gray-900 text-white py-8 mt-auto">
            <div className="container mx-auto px-4">
              <div className="grid grid-cols-1 md:grid-cols-3 gap-8">
                <div>
                  <h3 className="text-lg font-bold mb-4">ControlPlus</h3>
                  <p className="text-gray-400 text-sm">
                    Sistema seguro y eficiente para el control de acceso mediante códigos QR.
                  </p>
                </div>
                <div>
                  <h3 className="text-lg font-bold mb-4">Características</h3>
                  <ul className="text-gray-400 text-sm space-y-2">
                    <li>• Registro rápido de usuarios</li>
                    <li>• Verificación por correo</li>
                    <li>• Códigos QR seguros</li>
                    <li>• Múltiples niveles de acceso</li>
                  </ul>
                </div>
                <div>
                  <h3 className="text-lg font-bold mb-4">Contacto</h3>
                  <p className="text-gray-400 text-sm">
                    © 2025 ControlPlus<br />
                    Todos los derechos reservados.
                  </p>
                </div>
              </div>
            </div>
          </footer>
        </div>
      </body>
    </html>
  );
}

