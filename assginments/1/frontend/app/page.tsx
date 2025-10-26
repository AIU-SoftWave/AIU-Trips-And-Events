'use client';

import { useState } from 'react';
import ProbeDisplay from './components/ProbeDisplay';
import DemoRunner from './components/DemoRunner';

export default function Home() {
  const [activeTab, setActiveTab] = useState<'templates' | 'demo'>('templates');

  return (
    <div className="min-h-screen bg-gradient-to-b from-gray-900 to-gray-800 text-white">
      <header className="bg-gray-900 border-b border-gray-700 shadow-lg">
        <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-6">
          <h1 className="text-4xl font-bold text-center bg-gradient-to-r from-blue-400 to-purple-600 bg-clip-text text-transparent">
            Space Probe Configuration System
          </h1>
          <p className="text-center text-gray-400 mt-2">
            Demonstrating Builder, Prototype, and Singleton Design Patterns
          </p>
        </div>
      </header>

      <main className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-8">
        <div className="mb-6">
          <div className="flex space-x-4 border-b border-gray-700">
            <button
              onClick={() => setActiveTab('templates')}
              className={`px-6 py-3 font-medium transition-colors ${
                activeTab === 'templates'
                  ? 'border-b-2 border-blue-500 text-blue-400'
                  : 'text-gray-400 hover:text-gray-300'
              }`}
            >
              View Templates
            </button>
            <button
              onClick={() => setActiveTab('demo')}
              className={`px-6 py-3 font-medium transition-colors ${
                activeTab === 'demo'
                  ? 'border-b-2 border-blue-500 text-blue-400'
                  : 'text-gray-400 hover:text-gray-300'
              }`}
            >
              Run Demo
            </button>
          </div>
        </div>

        {activeTab === 'templates' && <ProbeDisplay />}
        {activeTab === 'demo' && <DemoRunner />}
      </main>

      <footer className="bg-gray-900 border-t border-gray-700 mt-12">
        <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-6">
          <p className="text-center text-gray-500">
            Software Engineering Assignment - Design Patterns Implementation
          </p>
        </div>
      </footer>
    </div>
  );
}
