'use client';

import { SpaceProbe } from '../types/spaceprobe';

interface PrototypeVisualizerProps {
  template: SpaceProbe | null;
  clones: SpaceProbe[];
}

export default function PrototypeVisualizer({ template, clones }: PrototypeVisualizerProps) {
  return (
    <div className="bg-gradient-to-br from-purple-50 to-pink-50 rounded-lg p-6 border-2 border-purple-300">
      <div className="flex items-center gap-3 mb-4">
        <div className="bg-purple-500 text-white rounded-full w-12 h-12 flex items-center justify-center font-bold text-xl">
          🔄
        </div>
        <div>
          <h3 className="text-lg font-bold text-gray-800">Prototype Pattern</h3>
          <p className="text-sm text-gray-600">Deep Cloning Mechanism</p>
        </div>
      </div>

      {template ? (
        <div className="space-y-4">
          <div className="bg-white rounded-lg p-4 border-2 border-yellow-400">
            <div className="flex items-center gap-2 mb-2">
              <span className="text-2xl">📋</span>
              <span className="font-semibold text-gray-800">Template: {template.missionTarget}</span>
            </div>
            <div className="text-sm text-gray-600">
              Original payload: <strong>{template.payloadMass} kg</strong>
            </div>
          </div>

          <div className="flex items-center justify-center py-2">
            <div className="flex items-center gap-2 text-purple-600">
              <div className="w-8 border-t-2 border-purple-400 border-dashed"></div>
              <span className="text-sm font-semibold">deepClone()</span>
              <div className="w-8 border-t-2 border-purple-400 border-dashed"></div>
            </div>
          </div>

          <div className="grid gap-3">
            {clones.map((clone, idx) => (
              <div 
                key={clone.id} 
                className="bg-white rounded-lg p-4 border border-purple-300 hover:shadow-md transition-shadow"
              >
                <div className="flex items-center justify-between">
                  <div className="flex items-center gap-2">
                    <span className="text-xl">🚀</span>
                    <div>
                      <div className="font-semibold text-gray-800">Clone #{idx + 1}</div>
                      <div className="text-xs text-gray-500">{clone.id}</div>
                    </div>
                  </div>
                  <div className={`
                    px-3 py-1 rounded-full text-sm font-semibold
                    ${clone.payloadMass !== template.payloadMass
                      ? 'bg-purple-100 text-purple-700'
                      : 'bg-gray-100 text-gray-700'
                    }
                  `}>
                    {clone.payloadMass} kg
                    {clone.payloadMass !== template.payloadMass && ' ✓'}
                  </div>
                </div>
              </div>
            ))}
          </div>

          {clones.length === 0 && (
            <div className="text-center py-6 text-gray-500 bg-white rounded-lg border border-dashed border-gray-300">
              No clones created yet. Click "Clone" to create deployment probes.
            </div>
          )}

          <div className="bg-purple-100 rounded-lg p-3 border border-purple-300">
            <div className="text-sm font-semibold text-purple-800 mb-1">
              🔍 Deep Copy Verification:
            </div>
            <ul className="text-sm text-purple-700 space-y-1">
              <li>• Each clone is an independent object</li>
              <li>• Modifications to clones don't affect template</li>
              <li>• List objects are properly deep copied</li>
              <li>• Template remains at original mass: {template.payloadMass} kg</li>
            </ul>
          </div>
        </div>
      ) : (
        <div className="text-center py-8 text-gray-500">
          Build a template probe first to enable cloning
        </div>
      )}
    </div>
  );
}
