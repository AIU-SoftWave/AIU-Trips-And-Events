'use client';

import { SpaceProbe } from '../types/spaceprobe';

interface ProbeCardProps {
  probe: SpaceProbe;
  onClone?: (probe: SpaceProbe) => void;
  onModify?: (probe: SpaceProbe) => void;
}

export default function ProbeCard({ probe, onClone, onModify }: ProbeCardProps) {
  const getTargetColor = (target: string) => {
    switch (target.toLowerCase()) {
      case 'mars':
        return 'bg-red-500';
      case 'jupiter':
        return 'bg-orange-500';
      default:
        return 'bg-blue-500';
    }
  };

  return (
    <div className={`rounded-lg shadow-lg p-6 border-2 ${
      probe.isTemplate 
        ? 'border-yellow-400 bg-yellow-50' 
        : 'border-gray-300 bg-white'
    }`}>
      {probe.isTemplate && (
        <div className="mb-2 text-yellow-600 font-semibold text-sm">
          📋 Template Probe
        </div>
      )}
      
      <div className="flex items-center justify-between mb-4">
        <h3 className="text-xl font-bold text-gray-800 flex items-center gap-2">
          <div className={`w-4 h-4 rounded-full ${getTargetColor(probe.missionTarget)}`} />
          {probe.missionTarget} Probe
        </h3>
        <span className="text-xs text-gray-500 bg-gray-100 px-2 py-1 rounded">
          {probe.id}
        </span>
      </div>

      <div className="space-y-3">
        <div className="border-l-4 border-blue-500 pl-3">
          <div className="text-sm text-gray-600">Propulsion System</div>
          <div className="font-semibold text-gray-800">{probe.propulsionSystem}</div>
        </div>

        <div className="border-l-4 border-green-500 pl-3">
          <div className="text-sm text-gray-600">Power Source</div>
          <div className="font-semibold text-gray-800">{probe.powerSource}</div>
        </div>

        <div className="border-l-4 border-purple-500 pl-3">
          <div className="text-sm text-gray-600">Scientific Instruments</div>
          <div className="space-y-1 mt-1">
            {probe.scientificInstruments.map((instrument, idx) => (
              <div key={idx} className="text-sm text-gray-700 flex items-center gap-2">
                <span className="text-purple-500">•</span>
                {instrument}
              </div>
            ))}
          </div>
        </div>

        <div className="border-l-4 border-orange-500 pl-3">
          <div className="text-sm text-gray-600">Payload Mass</div>
          <div className="font-semibold text-gray-800">{probe.payloadMass} kg</div>
        </div>
      </div>

      {(onClone || onModify) && (
        <div className="mt-4 flex gap-2">
          {onClone && (
            <button
              onClick={() => onClone(probe)}
              className="flex-1 bg-blue-500 hover:bg-blue-600 text-white py-2 px-4 rounded transition duration-200 text-sm font-medium"
            >
              🔄 Clone
            </button>
          )}
          {onModify && !probe.isTemplate && (
            <button
              onClick={() => onModify(probe)}
              className="flex-1 bg-gray-500 hover:bg-gray-600 text-white py-2 px-4 rounded transition duration-200 text-sm font-medium"
            >
              ✏️ Modify
            </button>
          )}
        </div>
      )}
    </div>
  );
}
