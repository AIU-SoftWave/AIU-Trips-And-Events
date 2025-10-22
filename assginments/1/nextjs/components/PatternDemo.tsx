'use client';

import { useState, useEffect } from 'react';
import {
  SpaceProbe,
  MarsProbeBuilder,
  JupiterProbeBuilder,
  MissionControl,
  ConfigurationManager,
} from '@/lib/spaceProbe';
import ProbeCard from './ProbeCard';

interface DeployedProbe {
  id: number;
  probe: SpaceProbe;
  name: string;
}

export default function PatternDemo() {
  const [templates, setTemplates] = useState<{ mars: SpaceProbe | null; jupiter: SpaceProbe | null }>({
    mars: null,
    jupiter: null,
  });
  const [deployedProbes, setDeployedProbes] = useState<DeployedProbe[]>([]);
  const [nextId, setNextId] = useState(1);
  const [isInitialized, setIsInitialized] = useState(false);

  // Initialize templates using Builder Pattern and register with Singleton
  const initializeTemplates = () => {
    console.log('🚀 Initializing Space Probe System...');
    
    // Step 1: Create templates using Builder Pattern
    const director = new MissionControl();
    
    const marsBuilder = new MarsProbeBuilder();
    director.constructStandardProbe(marsBuilder);
    const marsTemplate = marsBuilder.getResult();
    
    const jupiterBuilder = new JupiterProbeBuilder();
    director.constructStandardProbe(jupiterBuilder);
    const jupiterTemplate = jupiterBuilder.getResult();
    
    // Step 2: Register templates with Singleton
    const manager = ConfigurationManager.getInstance();
    manager.addPrototype('MarsTemplate', marsTemplate);
    manager.addPrototype('JupiterTemplate', jupiterTemplate);
    
    setTemplates({ mars: marsTemplate, jupiter: jupiterTemplate });
    setIsInitialized(true);
    
    console.log('✅ Templates created and registered');
  };

  // Deploy a probe using Prototype Pattern
  const deployProbe = (templateName: 'MarsTemplate' | 'JupiterTemplate', customMass?: number) => {
    const manager = ConfigurationManager.getInstance();
    const clonedProbe = manager.getClone(templateName) as SpaceProbe;
    
    if (clonedProbe) {
      // Post-cloning modification
      if (customMass !== undefined) {
        clonedProbe.payloadMass = customMass;
      }
      
      const probeName = `${clonedProbe.missionTarget} Probe #${nextId}`;
      setDeployedProbes([...deployedProbes, { id: nextId, probe: clonedProbe, name: probeName }]);
      setNextId(nextId + 1);
      
      console.log(`🛰️ Deployed: ${probeName}`);
    }
  };

  const deleteProbe = (id: number) => {
    setDeployedProbes(deployedProbes.filter(p => p.id !== id));
  };

  return (
    <div className="min-h-screen p-8">
      <div className="max-w-7xl mx-auto">
        {/* Header */}
        <div className="text-center mb-12">
          <h1 className="text-5xl font-bold bg-gradient-to-r from-blue-400 to-purple-500 bg-clip-text text-transparent mb-4">
            Space Probe Mission Control
          </h1>
          <p className="text-slate-400 text-lg">
            Demonstrating Builder, Prototype & Singleton Design Patterns
          </p>
        </div>

        {/* Initialize Button */}
        {!isInitialized && (
          <div className="text-center mb-8">
            <button
              onClick={initializeTemplates}
              className="bg-gradient-to-r from-blue-600 to-purple-600 hover:from-blue-500 hover:to-purple-500 text-white px-8 py-4 rounded-lg text-lg font-semibold transition-all transform hover:scale-105 shadow-lg"
            >
              🚀 Initialize Mission Control System
            </button>
          </div>
        )}

        {isInitialized && (
          <>
            {/* Design Patterns Info */}
            <div className="bg-slate-800 rounded-lg p-6 mb-8 border border-slate-700">
              <h2 className="text-2xl font-bold text-green-400 mb-4">✨ Design Patterns in Action</h2>
              <div className="grid grid-cols-1 md:grid-cols-3 gap-4 text-sm">
                <div className="bg-slate-900 p-4 rounded-lg">
                  <h3 className="font-bold text-yellow-400 mb-2">🏗️ Builder Pattern</h3>
                  <p className="text-slate-300">
                    Templates are constructed step-by-step using MarsProbeBuilder and JupiterProbeBuilder with MissionControl as the director.
                  </p>
                </div>
                <div className="bg-slate-900 p-4 rounded-lg">
                  <h3 className="font-bold text-purple-400 mb-2">🔄 Prototype Pattern</h3>
                  <p className="text-slate-300">
                    Deploy probes by cloning templates efficiently using deepClone() method instead of rebuilding from scratch.
                  </p>
                </div>
                <div className="bg-slate-900 p-4 rounded-lg">
                  <h3 className="font-bold text-green-400 mb-2">⭐ Singleton Pattern</h3>
                  <p className="text-slate-300">
                    ConfigurationManager ensures single instance manages all templates globally using getInstance().
                  </p>
                </div>
              </div>
            </div>

            {/* Templates Section */}
            <div className="mb-8">
              <h2 className="text-3xl font-bold text-blue-400 mb-4">📋 Templates (Built with Builder Pattern)</h2>
              <div className="grid grid-cols-1 md:grid-cols-2 gap-6">
                {templates.mars && (
                  <ProbeCard probe={templates.mars} title="🔴 Mars Template" />
                )}
                {templates.jupiter && (
                  <ProbeCard probe={templates.jupiter} title="🟠 Jupiter Template" />
                )}
              </div>
            </div>

            {/* Deploy Controls */}
            <div className="bg-slate-800 rounded-lg p-6 mb-8 border border-slate-700">
              <h2 className="text-2xl font-bold text-purple-400 mb-4">🛰️ Deploy Probes (Using Prototype Pattern)</h2>
              <div className="flex flex-wrap gap-4">
                <button
                  onClick={() => deployProbe('MarsTemplate')}
                  className="bg-red-600 hover:bg-red-500 text-white px-6 py-3 rounded-lg font-semibold transition-all transform hover:scale-105"
                >
                  Deploy Mars Probe
                </button>
                <button
                  onClick={() => deployProbe('MarsTemplate', 850 + Math.random() * 100)}
                  className="bg-red-700 hover:bg-red-600 text-white px-6 py-3 rounded-lg font-semibold transition-all transform hover:scale-105"
                >
                  Deploy Mars Probe (Custom Mass)
                </button>
                <button
                  onClick={() => deployProbe('JupiterTemplate')}
                  className="bg-orange-600 hover:bg-orange-500 text-white px-6 py-3 rounded-lg font-semibold transition-all transform hover:scale-105"
                >
                  Deploy Jupiter Probe
                </button>
                <button
                  onClick={() => deployProbe('JupiterTemplate', 1200 + Math.random() * 100)}
                  className="bg-orange-700 hover:bg-orange-600 text-white px-6 py-3 rounded-lg font-semibold transition-all transform hover:scale-105"
                >
                  Deploy Jupiter Probe (Custom Mass)
                </button>
              </div>
            </div>

            {/* Deployed Probes Section */}
            <div>
              <h2 className="text-3xl font-bold text-green-400 mb-4">
                🚀 Deployed Probes ({deployedProbes.length})
              </h2>
              {deployedProbes.length === 0 ? (
                <div className="text-center py-12 bg-slate-800 rounded-lg border border-slate-700">
                  <p className="text-slate-400 text-lg">No probes deployed yet. Click the deploy buttons above!</p>
                </div>
              ) : (
                <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
                  {deployedProbes.map((deployed) => (
                    <ProbeCard
                      key={deployed.id}
                      probe={deployed.probe}
                      title={deployed.name}
                      onDelete={() => deleteProbe(deployed.id)}
                    />
                  ))}
                </div>
              )}
            </div>
          </>
        )}
      </div>
    </div>
  );
}
