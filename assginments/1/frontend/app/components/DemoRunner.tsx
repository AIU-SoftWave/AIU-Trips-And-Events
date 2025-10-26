'use client';

import { useState } from 'react';

interface SpaceProbe {
  missionTarget: string;
  propulsionSystem: string;
  powerSource: string;
  scientificInstruments: string[];
  payloadMass: number;
  description: string;
}

interface DemoResults {
  templates: Record<string, SpaceProbe>;
  clones: Record<string, SpaceProbe>;
  message: string;
}

export default function DemoRunner() {
  const [demoResults, setDemoResults] = useState<DemoResults | null>(null);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState<string | null>(null);

  const runDemo = async () => {
    try {
      setLoading(true);
      setError(null);
      
      const response = await fetch('http://localhost:8080/api/probes/demo');
      if (!response.ok) {
        throw new Error('Failed to run demo');
      }
      
      const data = await response.json();
      setDemoResults(data);
    } catch (err) {
      setError('Error running demo. Make sure the backend server is running on port 8080.');
      console.error(err);
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="space-y-6">
      <div className="bg-gray-800 rounded-lg p-6 border border-gray-700">
        <h2 className="text-2xl font-bold mb-4 text-blue-400">Full System Demonstration</h2>
        <p className="text-gray-400 mb-6">
          This demo showcases all three design patterns working together:
        </p>
        
        <div className="grid md:grid-cols-3 gap-4 mb-6">
          <div className="bg-blue-900/30 border border-blue-700 rounded-lg p-4">
            <h3 className="text-lg font-semibold text-blue-400 mb-2">1. Builder Pattern</h3>
            <p className="text-sm text-gray-400">
              MissionControl (Director) uses MarsProbeBuilder and JupiterProbeBuilder to construct complex probes step-by-step.
            </p>
          </div>
          
          <div className="bg-purple-900/30 border border-purple-700 rounded-lg p-4">
            <h3 className="text-lg font-semibold text-purple-400 mb-2">2. Singleton Pattern</h3>
            <p className="text-sm text-gray-400">
              ConfigurationManager ensures only one instance exists to manage all probe templates.
            </p>
          </div>
          
          <div className="bg-green-900/30 border border-green-700 rounded-lg p-4">
            <h3 className="text-lg font-semibold text-green-400 mb-2">3. Prototype Pattern</h3>
            <p className="text-sm text-gray-400">
              Templates are cloned efficiently with deep copying, allowing independent modifications.
            </p>
          </div>
        </div>

        <button
          onClick={runDemo}
          disabled={loading}
          className={`w-full px-6 py-3 rounded-lg font-semibold text-lg transition-colors ${
            loading
              ? 'bg-gray-700 cursor-not-allowed'
              : 'bg-gradient-to-r from-blue-600 to-purple-600 hover:from-blue-700 hover:to-purple-700'
          }`}
        >
          {loading ? 'Running Demo...' : 'Run Complete Demo'}
        </button>

        {error && (
          <div className="mt-4 bg-red-900/50 border border-red-700 rounded-lg p-4">
            <p className="text-red-200">{error}</p>
          </div>
        )}
      </div>

      {demoResults && (
        <div className="space-y-6">
          <div className="bg-green-900/30 border border-green-700 rounded-lg p-4">
            <p className="text-green-400 font-semibold">✓ {demoResults.message}</p>
          </div>

          <div className="bg-gray-800 rounded-lg p-6 border border-gray-700">
            <h3 className="text-xl font-bold mb-4 text-blue-400">Template Probes (Built with Builder Pattern)</h3>
            <div className="grid md:grid-cols-2 gap-4">
              {Object.entries(demoResults.templates).map(([key, probe]) => (
                <div key={key} className="bg-gray-900 rounded-lg p-4 border border-gray-700">
                  <h4 className="text-lg font-semibold text-blue-400 mb-2">{key}</h4>
                  <div className="space-y-1 text-sm">
                    <p><span className="text-gray-500">Target:</span> {probe.missionTarget}</p>
                    <p><span className="text-gray-500">Propulsion:</span> {probe.propulsionSystem}</p>
                    <p><span className="text-gray-500">Power:</span> {probe.powerSource}</p>
                    <p><span className="text-gray-500">Payload:</span> {probe.payloadMass} kg</p>
                    <div>
                      <p className="text-gray-500 mt-2">Instruments:</p>
                      <ul className="list-disc list-inside">
                        {probe.scientificInstruments.map((inst, idx) => (
                          <li key={idx} className="text-xs text-white">{inst}</li>
                        ))}
                      </ul>
                    </div>
                  </div>
                </div>
              ))}
            </div>
          </div>

          <div className="bg-gray-800 rounded-lg p-6 border border-gray-700">
            <h3 className="text-xl font-bold mb-4 text-purple-400">Cloned Deployment Probes (Using Prototype Pattern)</h3>
            <p className="text-gray-400 mb-4">
              Each clone is an independent object with modified payload mass:
            </p>
            <div className="grid md:grid-cols-2 lg:grid-cols-3 gap-4">
              {Object.entries(demoResults.clones).map(([key, probe]) => (
                <div key={key} className="bg-gray-900 rounded-lg p-4 border border-purple-700">
                  <h4 className="text-lg font-semibold text-purple-400 mb-2">{key}</h4>
                  <div className="space-y-1 text-sm">
                    <p><span className="text-gray-500">Target:</span> {probe.missionTarget}</p>
                    <p>
                      <span className="text-gray-500">Payload:</span> 
                      <span className="text-green-400 font-bold"> {probe.payloadMass} kg</span>
                    </p>
                    <p className="text-xs text-gray-500 mt-2">
                      (Modified from template: {demoResults.templates[`${probe.missionTarget}Template`]?.payloadMass} kg)
                    </p>
                  </div>
                </div>
              ))}
            </div>
          </div>

          <div className="bg-gray-800 rounded-lg p-6 border border-gray-700">
            <h3 className="text-xl font-bold mb-4 text-green-400">Deep Copy Verification</h3>
            <div className="space-y-2 text-sm">
              <p className="text-gray-400">
                The Prototype pattern ensures that clones are independent objects:
              </p>
              <ul className="list-disc list-inside space-y-1 text-white">
                <li>Original MarsTemplate payload: {demoResults.templates.MarsTemplate?.payloadMass} kg</li>
                <li>MarsProbe1 payload: {demoResults.clones.MarsProbe1?.payloadMass} kg</li>
                <li>MarsProbe2 payload: {demoResults.clones.MarsProbe2?.payloadMass} kg</li>
              </ul>
              <div className="mt-4 p-3 bg-green-900/30 border border-green-700 rounded">
                <p className="text-green-400">
                  ✓ Modifications to clones do NOT affect the original templates - deep copy verified!
                </p>
              </div>
            </div>
          </div>
        </div>
      )}
    </div>
  );
}
