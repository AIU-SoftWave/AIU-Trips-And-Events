'use client';

import { BuildProgress, ProbeBuildStep } from '../types/spaceprobe';

interface BuilderVisualizerProps {
  progress: BuildProgress;
  target: string;
}

const steps: { key: ProbeBuildStep; label: string; icon: string }[] = [
  { key: 'target', label: 'Mission Target', icon: '🎯' },
  { key: 'propulsion', label: 'Propulsion System', icon: '🚀' },
  { key: 'power', label: 'Power Source', icon: '⚡' },
  { key: 'instruments', label: 'Scientific Instruments', icon: '🔬' },
  { key: 'payload', label: 'Payload Mass', icon: '⚖️' },
];

export default function BuilderVisualizer({ progress, target }: BuilderVisualizerProps) {
  const isStepCompleted = (step: ProbeBuildStep) => 
    progress.completedSteps.includes(step);
  
  const isStepCurrent = (step: ProbeBuildStep) => 
    progress.currentStep === step;

  return (
    <div className="bg-gradient-to-br from-blue-50 to-indigo-50 rounded-lg p-6 border border-blue-200">
      <div className="flex items-center gap-3 mb-6">
        <div className="bg-blue-500 text-white rounded-full w-10 h-10 flex items-center justify-center font-bold">
          🏗️
        </div>
        <div>
          <h3 className="text-lg font-bold text-gray-800">Builder Pattern in Action</h3>
          <p className="text-sm text-gray-600">Constructing {target} Probe Template</p>
        </div>
      </div>

      <div className="space-y-3">
        {steps.map((step, index) => (
          <div key={step.key} className="flex items-center gap-3">
            <div className={`
              w-8 h-8 rounded-full flex items-center justify-center font-bold text-sm
              transition-all duration-300
              ${isStepCompleted(step.key) 
                ? 'bg-green-500 text-white' 
                : isStepCurrent(step.key)
                ? 'bg-blue-500 text-white animate-pulse'
                : 'bg-gray-200 text-gray-500'
              }
            `}>
              {isStepCompleted(step.key) ? '✓' : index + 1}
            </div>

            <div className={`
              flex-1 rounded-lg p-3 transition-all duration-300
              ${isStepCompleted(step.key)
                ? 'bg-green-100 border-2 border-green-500'
                : isStepCurrent(step.key)
                ? 'bg-blue-100 border-2 border-blue-500'
                : 'bg-white border border-gray-200'
              }
            `}>
              <div className="flex items-center gap-2">
                <span className="text-xl">{step.icon}</span>
                <span className={`font-semibold ${
                  isStepCompleted(step.key) || isStepCurrent(step.key)
                    ? 'text-gray-800'
                    : 'text-gray-500'
                }`}>
                  {step.label}
                </span>
              </div>
            </div>
          </div>
        ))}
      </div>

      <div className="mt-4 pt-4 border-t border-blue-200">
        <div className="text-sm text-gray-600">
          <strong>Progress:</strong> {progress.completedSteps.length} / {steps.length} steps completed
        </div>
      </div>
    </div>
  );
}
