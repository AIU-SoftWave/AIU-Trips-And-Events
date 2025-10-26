'use client';

import { useState, useEffect } from 'react';

interface SpaceProbe {
  missionTarget: string;
  propulsionSystem: string;
  powerSource: string;
  scientificInstruments: string[];
  payloadMass: number;
  description: string;
}

export default function ProbeDisplay() {
  const [templates, setTemplates] = useState<Record<string, SpaceProbe>>({});
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);
  const [selectedTemplate, setSelectedTemplate] = useState<string>('');
  const [clonedProbe, setClonedProbe] = useState<SpaceProbe | null>(null);
  const [payloadMass, setPayloadMass] = useState<number>(0);

  useEffect(() => {
    fetchTemplates();
  }, []);

  const fetchTemplates = async () => {
    try {
      setLoading(true);
      const response = await fetch('http://localhost:8080/api/probes/templates');
      if (!response.ok) {
        throw new Error('Failed to fetch templates');
      }
      const data = await response.json();
      setTemplates(data);
      setError(null);
    } catch (err) {
      setError('Error fetching templates. Make sure the backend server is running on port 8080.');
      console.error(err);
    } finally {
      setLoading(false);
    }
  };

  const cloneProbe = async (templateKey: string) => {
    try {
      const response = await fetch(`http://localhost:8080/api/probes/clone/${templateKey}`, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({ payloadMass }),
      });
      
      if (!response.ok) {
        throw new Error('Failed to clone probe');
      }
      
      const data = await response.json();
      setClonedProbe(data);
      setError(null);
    } catch (err) {
      setError('Error cloning probe. Make sure the backend server is running.');
      console.error(err);
    }
  };

  if (loading) {
    return (
      <div className="flex justify-center items-center py-12">
        <div className="animate-spin rounded-full h-12 w-12 border-b-2 border-blue-500"></div>
      </div>
    );
  }

  if (error) {
    return (
      <div className="bg-red-900/50 border border-red-700 rounded-lg p-6 text-center">
        <p className="text-red-200">{error}</p>
        <button
          onClick={fetchTemplates}
          className="mt-4 px-4 py-2 bg-red-700 hover:bg-red-600 rounded-lg transition-colors"
        >
          Retry
        </button>
      </div>
    );
  }

  return (
    <div className="space-y-6">
      <div className="bg-gray-800 rounded-lg p-6 border border-gray-700">
        <h2 className="text-2xl font-bold mb-4 text-blue-400">Probe Templates</h2>
        <p className="text-gray-400 mb-6">
          These templates are stored in the Singleton ConfigurationManager and built using the Builder pattern.
        </p>
        
        <div className="grid md:grid-cols-2 gap-6">
          {Object.entries(templates).map(([key, probe]) => (
            <div
              key={key}
              className="bg-gray-900 rounded-lg p-5 border border-gray-700 hover:border-blue-500 transition-colors"
            >
              <h3 className="text-xl font-semibold text-blue-400 mb-3">{key}</h3>
              <div className="space-y-2 text-sm">
                <p><span className="text-gray-500">Target:</span> <span className="text-white">{probe.missionTarget}</span></p>
                <p><span className="text-gray-500">Propulsion:</span> <span className="text-white">{probe.propulsionSystem}</span></p>
                <p><span className="text-gray-500">Power:</span> <span className="text-white">{probe.powerSource}</span></p>
                <p><span className="text-gray-500">Payload Mass:</span> <span className="text-white">{probe.payloadMass} kg</span></p>
                <div>
                  <p className="text-gray-500 mb-1">Instruments:</p>
                  <ul className="list-disc list-inside space-y-1">
                    {probe.scientificInstruments.map((instrument, idx) => (
                      <li key={idx} className="text-white text-xs">{instrument}</li>
                    ))}
                  </ul>
                </div>
              </div>
              <button
                onClick={() => {
                  setSelectedTemplate(key);
                  setPayloadMass(probe.payloadMass);
                  setClonedProbe(null);
                }}
                className="mt-4 w-full px-4 py-2 bg-blue-600 hover:bg-blue-700 rounded-lg transition-colors font-medium"
              >
                Clone This Template
              </button>
            </div>
          ))}
        </div>
      </div>

      {selectedTemplate && (
        <div className="bg-gray-800 rounded-lg p-6 border border-gray-700">
          <h2 className="text-2xl font-bold mb-4 text-purple-400">Clone Template: {selectedTemplate}</h2>
          <p className="text-gray-400 mb-4">
            Using the Prototype pattern to create a deep copy. Modify the payload mass:
          </p>
          
          <div className="flex items-center space-x-4 mb-4">
            <label className="text-white font-medium">Payload Mass (kg):</label>
            <input
              type="number"
              value={payloadMass}
              onChange={(e) => setPayloadMass(parseFloat(e.target.value))}
              className="bg-gray-900 border border-gray-700 rounded-lg px-4 py-2 text-white focus:outline-none focus:border-blue-500"
              step="0.1"
            />
            <button
              onClick={() => cloneProbe(selectedTemplate)}
              className="px-6 py-2 bg-purple-600 hover:bg-purple-700 rounded-lg transition-colors font-medium"
            >
              Clone Probe
            </button>
          </div>

          {clonedProbe && (
            <div className="mt-6 bg-gray-900 rounded-lg p-5 border border-purple-500">
              <h3 className="text-lg font-semibold text-purple-400 mb-3">Cloned Probe (Independent Copy)</h3>
              <div className="space-y-2 text-sm">
                <p><span className="text-gray-500">Target:</span> <span className="text-white">{clonedProbe.missionTarget}</span></p>
                <p><span className="text-gray-500">Propulsion:</span> <span className="text-white">{clonedProbe.propulsionSystem}</span></p>
                <p><span className="text-gray-500">Power:</span> <span className="text-white">{clonedProbe.powerSource}</span></p>
                <p>
                  <span className="text-gray-500">Payload Mass:</span> 
                  <span className="text-green-400 font-bold"> {clonedProbe.payloadMass} kg</span>
                  <span className="text-gray-500 ml-2">(Modified from {templates[selectedTemplate]?.payloadMass} kg)</span>
                </p>
                <div>
                  <p className="text-gray-500 mb-1">Instruments:</p>
                  <ul className="list-disc list-inside space-y-1">
                    {clonedProbe.scientificInstruments.map((instrument, idx) => (
                      <li key={idx} className="text-white text-xs">{instrument}</li>
                    ))}
                  </ul>
                </div>
              </div>
              <div className="mt-4 p-3 bg-green-900/30 border border-green-700 rounded">
                <p className="text-green-400 text-sm">
                  ✓ Clone created successfully! This is an independent object - modifications don't affect the original template.
                </p>
              </div>
            </div>
          )}
        </div>
      )}
    </div>
  );
}
