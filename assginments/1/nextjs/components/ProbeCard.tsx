import { SpaceProbe } from '@/lib/spaceProbe';

interface ProbeCardProps {
  probe: SpaceProbe;
  title: string;
  onDelete?: () => void;
}

export default function ProbeCard({ probe, title, onDelete }: ProbeCardProps) {
  return (
    <div className="bg-gradient-to-br from-slate-800 to-slate-900 rounded-lg p-6 border border-slate-700 shadow-xl">
      <div className="flex justify-between items-start mb-4">
        <h3 className="text-xl font-bold text-blue-400">{title}</h3>
        {onDelete && (
          <button
            onClick={onDelete}
            className="text-red-400 hover:text-red-300 transition-colors"
            title="Delete probe"
          >
            ✕
          </button>
        )}
      </div>
      
      <div className="space-y-3 text-sm">
        <div>
          <span className="text-slate-400 font-semibold">Mission Target:</span>
          <p className="text-white mt-1">{probe.missionTarget}</p>
        </div>
        
        <div>
          <span className="text-slate-400 font-semibold">Propulsion System:</span>
          <p className="text-white mt-1">{probe.propulsionSystem}</p>
        </div>
        
        <div>
          <span className="text-slate-400 font-semibold">Power Source:</span>
          <p className="text-white mt-1">{probe.powerSource}</p>
        </div>
        
        <div>
          <span className="text-slate-400 font-semibold">Payload Mass:</span>
          <p className="text-white mt-1">{probe.payloadMass} kg</p>
        </div>
        
        {probe.scientificInstruments.length > 0 && (
          <div>
            <span className="text-slate-400 font-semibold">Scientific Instruments:</span>
            <ul className="mt-1 space-y-1">
              {probe.scientificInstruments.map((instrument, idx) => (
                <li key={idx} className="text-white text-xs ml-2">
                  • {instrument}
                </li>
              ))}
            </ul>
          </div>
        )}
      </div>
    </div>
  );
}
