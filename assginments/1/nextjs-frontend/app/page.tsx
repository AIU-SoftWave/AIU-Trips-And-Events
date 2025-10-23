'use client';

import { useState } from 'react';
import ProbeCard from './components/ProbeCard';
import BuilderVisualizer from './components/BuilderVisualizer';
import SingletonIndicator from './components/SingletonIndicator';
import PrototypeVisualizer from './components/PrototypeVisualizer';
import { SpaceProbe, BuildProgress, ProbeBuildStep } from './types/spaceprobe';

export default function Home() {
  const [templates, setTemplates] = useState<SpaceProbe[]>([]);
  const [deploymentProbes, setDeploymentProbes] = useState<SpaceProbe[]>([]);
  const [buildingProbe, setBuildingProbe] = useState<string | null>(null);
  const [buildProgress, setBuildProgress] = useState<BuildProgress>({
    currentStep: null,
    completedSteps: []
  });
  const [selectedTemplate, setSelectedTemplate] = useState<SpaceProbe | null>(null);
  const [modifyingProbe, setModifyingProbe] = useState<SpaceProbe | null>(null);
  const [newPayloadMass, setNewPayloadMass] = useState<number>(0);

  // Simulate building a probe with the Builder pattern
  const buildProbe = async (type: 'Mars' | 'Jupiter') => {
    setBuildingProbe(type);
    setBuildProgress({ currentStep: null, completedSteps: [] });

    const steps: ProbeBuildStep[] = ['target', 'propulsion', 'power', 'instruments', 'payload'];
    
    for (const step of steps) {
      setBuildProgress(prev => ({ ...prev, currentStep: step }));
      await new Promise(resolve => setTimeout(resolve, 800));
      setBuildProgress(prev => ({
        currentStep: step,
        completedSteps: [...prev.completedSteps, step]
      }));
    }

    // Create the probe based on type
    const probe: SpaceProbe = type === 'Mars' 
      ? {
          id: `mars-template-${Date.now()}`,
          propulsionSystem: 'Chemical Rocket',
          powerSource: 'Solar Panels',
          scientificInstruments: [
            'High-Resolution Camera',
            'Spectrometer',
            'Soil Analysis Kit',
            'Weather Station'
          ],
          missionTarget: 'Mars',
          payloadMass: 150.0,
          isTemplate: true
        }
      : {
          id: `jupiter-template-${Date.now()}`,
          propulsionSystem: 'Ion Thruster',
          powerSource: 'Radioisotope Thermoelectric Generator (RTG)',
          scientificInstruments: [
            'Magnetometer',
            'Ultraviolet Spectrometer',
            'Plasma Wave Detector',
            'Infrared Camera'
          ],
          missionTarget: 'Jupiter',
          payloadMass: 300.0,
          isTemplate: true
        };

    setTemplates(prev => [...prev, probe]);
    setBuildingProbe(null);
    setBuildProgress({ currentStep: null, completedSteps: [] });
  };

  // Clone a probe using the Prototype pattern
  const cloneProbe = (template: SpaceProbe) => {
    const clone: SpaceProbe = {
      ...template,
      id: `${template.missionTarget.toLowerCase()}-clone-${Date.now()}`,
      scientificInstruments: [...template.scientificInstruments], // Deep copy
      isTemplate: false
    };
    
    setDeploymentProbes(prev => [...prev, clone]);
    setSelectedTemplate(template);
  };

  // Modify a cloned probe
  const modifyProbe = (probe: SpaceProbe) => {
    setModifyingProbe(probe);
    setNewPayloadMass(probe.payloadMass);
  };

  const saveModification = () => {
    if (modifyingProbe) {
      setDeploymentProbes(prev =>
        prev.map(p =>
          p.id === modifyingProbe.id
            ? { ...p, payloadMass: newPayloadMass }
            : p
        )
      );
      setModifyingProbe(null);
    }
  };

  // Get clones for the selected template
  const getClonesForTemplate = (template: SpaceProbe | null) => {
    if (!template) return [];
    return deploymentProbes.filter(p => 
      p.missionTarget === template.missionTarget && !p.isTemplate
    );
  };

  return (
    <div className="min-h-screen bg-gradient-to-br from-gray-50 to-gray-100">
      {/* Header */}
      <header className="bg-gradient-to-r from-blue-600 to-indigo-700 text-white shadow-lg">
        <div className="container mx-auto px-4 py-6">
          <h1 className="text-4xl font-bold mb-2">🚀 Space Probe Configuration System</h1>
          <p className="text-blue-100">
            Demonstrating Builder, Prototype, and Singleton Design Patterns
          </p>
        </div>
      </header>

      <main className="container mx-auto px-4 py-8">
        {/* Singleton Indicator */}
        <div className="mb-8">
          <SingletonIndicator 
            instanceCount={1} 
            registeredTemplates={templates.length}
          />
        </div>

        {/* Control Panel */}
        <div className="bg-white rounded-lg shadow-lg p-6 mb-8">
          <h2 className="text-2xl font-bold text-gray-800 mb-4">🎮 Control Panel</h2>
          <div className="flex flex-wrap gap-4">
            <button
              onClick={() => buildProbe('Mars')}
              disabled={buildingProbe !== null}
              className="bg-red-500 hover:bg-red-600 disabled:bg-gray-400 text-white py-3 px-6 rounded-lg font-semibold transition duration-200 shadow-md"
            >
              🏗️ Build Mars Probe Template
            </button>
            <button
              onClick={() => buildProbe('Jupiter')}
              disabled={buildingProbe !== null}
              className="bg-orange-500 hover:bg-orange-600 disabled:bg-gray-400 text-white py-3 px-6 rounded-lg font-semibold transition duration-200 shadow-md"
            >
              🏗️ Build Jupiter Probe Template
            </button>
            <button
              onClick={() => {
                setTemplates([]);
                setDeploymentProbes([]);
                setSelectedTemplate(null);
              }}
              className="bg-gray-500 hover:bg-gray-600 text-white py-3 px-6 rounded-lg font-semibold transition duration-200 shadow-md"
            >
              🗑️ Reset All
            </button>
          </div>
        </div>

        {/* Builder Visualizer */}
        {buildingProbe && (
          <div className="mb-8">
            <BuilderVisualizer progress={buildProgress} target={buildingProbe} />
          </div>
        )}

        {/* Templates Section */}
        <div className="mb-8">
          <h2 className="text-2xl font-bold text-gray-800 mb-4">📋 Template Probes (Registered in ConfigurationManager)</h2>
          {templates.length === 0 ? (
            <div className="bg-white rounded-lg shadow p-8 text-center text-gray-500">
              No templates created yet. Use the control panel to build probe templates.
            </div>
          ) : (
            <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
              {templates.map(template => (
                <ProbeCard
                  key={template.id}
                  probe={template}
                  onClone={cloneProbe}
                />
              ))}
            </div>
          )}
        </div>

        {/* Prototype Visualizer */}
        {selectedTemplate && (
          <div className="mb-8">
            <PrototypeVisualizer
              template={selectedTemplate}
              clones={getClonesForTemplate(selectedTemplate)}
            />
          </div>
        )}

        {/* Deployment Probes Section */}
        <div className="mb-8">
          <h2 className="text-2xl font-bold text-gray-800 mb-4">🚀 Deployment Probes (Cloned)</h2>
          {deploymentProbes.length === 0 ? (
            <div className="bg-white rounded-lg shadow p-8 text-center text-gray-500">
              No deployment probes created yet. Clone a template to create deployment probes.
            </div>
          ) : (
            <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
              {deploymentProbes.map(probe => (
                <ProbeCard
                  key={probe.id}
                  probe={probe}
                  onModify={modifyProbe}
                />
              ))}
            </div>
          )}
        </div>

        {/* Pattern Summary */}
        <div className="bg-gradient-to-r from-indigo-50 to-purple-50 rounded-lg shadow-lg p-6">
          <h2 className="text-2xl font-bold text-gray-800 mb-4">📚 Design Patterns Summary</h2>
          <div className="grid md:grid-cols-3 gap-4">
            <div className="bg-white rounded-lg p-4 border-l-4 border-blue-500">
              <h3 className="font-bold text-blue-600 mb-2">🏗️ Builder Pattern</h3>
              <p className="text-sm text-gray-600">
                Constructs complex SpaceProbe objects step-by-step using MissionControl (Director) 
                and specific builders (MarsProbeBuilder, JupiterProbeBuilder).
              </p>
            </div>
            <div className="bg-white rounded-lg p-4 border-l-4 border-purple-500">
              <h3 className="font-bold text-purple-600 mb-2">🔄 Prototype Pattern</h3>
              <p className="text-sm text-gray-600">
                Enables efficient creation of deployment probes by deep cloning template probes. 
                Modifications to clones don't affect the original templates.
              </p>
            </div>
            <div className="bg-white rounded-lg p-4 border-l-4 border-green-500">
              <h3 className="font-bold text-green-600 mb-2">🏛️ Singleton Pattern</h3>
              <p className="text-sm text-gray-600">
                ConfigurationManager ensures only one instance exists to manage all probe templates, 
                providing centralized access via getInstance().
              </p>
            </div>
          </div>
        </div>
      </main>

      {/* Modify Modal */}
      {modifyingProbe && (
        <div className="fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center z-50">
          <div className="bg-white rounded-lg p-6 max-w-md w-full mx-4">
            <h3 className="text-xl font-bold text-gray-800 mb-4">
              Modify {modifyingProbe.missionTarget} Probe
            </h3>
            <div className="mb-4">
              <label className="block text-sm font-semibold text-gray-700 mb-2">
                Payload Mass (kg)
              </label>
              <input
                type="number"
                value={newPayloadMass}
                onChange={(e) => setNewPayloadMass(parseFloat(e.target.value))}
                className="w-full px-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-transparent"
                step="0.1"
              />
            </div>
            <div className="flex gap-3">
              <button
                onClick={saveModification}
                className="flex-1 bg-blue-500 hover:bg-blue-600 text-white py-2 px-4 rounded-lg font-semibold transition duration-200"
              >
                Save
              </button>
              <button
                onClick={() => setModifyingProbe(null)}
                className="flex-1 bg-gray-500 hover:bg-gray-600 text-white py-2 px-4 rounded-lg font-semibold transition duration-200"
              >
                Cancel
              </button>
            </div>
          </div>
        </div>
      )}

      {/* Footer */}
      <footer className="bg-gray-800 text-white py-6 mt-12">
        <div className="container mx-auto px-4 text-center">
          <p className="text-gray-300">
            Space Probe Configuration System - Software Engineering Assignment
          </p>
          <p className="text-gray-400 text-sm mt-2">
            Demonstrating Builder, Prototype, and Singleton design patterns
          </p>
        </div>
      </footer>
    </div>
  );
}
