'use client';

interface SingletonIndicatorProps {
  instanceCount: number;
  registeredTemplates: number;
}

export default function SingletonIndicator({ instanceCount, registeredTemplates }: SingletonIndicatorProps) {
  return (
    <div className="bg-gradient-to-r from-green-50 to-emerald-50 rounded-lg p-6 border-2 border-green-300">
      <div className="flex items-center gap-3 mb-4">
        <div className="bg-green-500 text-white rounded-full w-12 h-12 flex items-center justify-center font-bold text-2xl">
          1
        </div>
        <div>
          <h3 className="text-lg font-bold text-gray-800">Singleton Pattern</h3>
          <p className="text-sm text-gray-600">Configuration Manager Instance</p>
        </div>
      </div>

      <div className="space-y-3">
        <div className="bg-white rounded-lg p-4 border border-green-200">
          <div className="flex items-center justify-between">
            <div className="flex items-center gap-2">
              <span className="text-2xl">🏛️</span>
              <span className="font-semibold text-gray-800">Instance Count</span>
            </div>
            <div className={`
              text-2xl font-bold px-4 py-2 rounded-full
              ${instanceCount === 1 
                ? 'bg-green-500 text-white' 
                : 'bg-red-500 text-white'
              }
            `}>
              {instanceCount}
            </div>
          </div>
          <div className="mt-2 text-sm text-gray-600">
            {instanceCount === 1 
              ? '✓ Singleton constraint satisfied - Only one instance exists'
              : '⚠️ Warning: Multiple instances detected!'
            }
          </div>
        </div>

        <div className="bg-white rounded-lg p-4 border border-green-200">
          <div className="flex items-center justify-between mb-2">
            <div className="flex items-center gap-2">
              <span className="text-2xl">📋</span>
              <span className="font-semibold text-gray-800">Registered Templates</span>
            </div>
            <div className="text-2xl font-bold text-green-600">
              {registeredTemplates}
            </div>
          </div>
          <div className="text-sm text-gray-600">
            The singleton instance manages all probe templates
          </div>
        </div>

        <div className="bg-green-100 rounded-lg p-3 border border-green-300">
          <div className="text-sm font-semibold text-green-800 mb-1">
            🔒 Key Features:
          </div>
          <ul className="text-sm text-green-700 space-y-1">
            <li>• Private constructor prevents external instantiation</li>
            <li>• Static getInstance() method for global access</li>
            <li>• Thread-safe implementation (eager initialization)</li>
            <li>• Centralized template registry management</li>
          </ul>
        </div>
      </div>
    </div>
  );
}
