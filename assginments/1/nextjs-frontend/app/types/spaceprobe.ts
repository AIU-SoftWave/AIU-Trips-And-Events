// Type definitions for the Space Probe Configuration System

export interface SpaceProbe {
  id: string;
  propulsionSystem: string;
  powerSource: string;
  scientificInstruments: string[];
  missionTarget: string;
  payloadMass: number;
  isTemplate?: boolean;
}

export interface ProbeTemplate {
  key: string;
  probe: SpaceProbe;
}

export type ProbeBuildStep = 
  | 'propulsion'
  | 'power'
  | 'instruments'
  | 'target'
  | 'payload';

export interface BuildProgress {
  currentStep: ProbeBuildStep | null;
  completedSteps: ProbeBuildStep[];
}
