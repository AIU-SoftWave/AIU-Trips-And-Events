/**
 * Space Probe Types and Classes
 * Implementing Builder, Prototype, and Singleton patterns in TypeScript
 */

// === Prototype Pattern ===
export interface IPrototype {
  deepClone(): IPrototype;
}

// === Product (Space Probe) ===
export class SpaceProbe implements IPrototype {
  propulsionSystem: string = '';
  powerSource: string = '';
  scientificInstruments: string[] = [];
  missionTarget: string = '';
  payloadMass: number = 0;

  private constructor() {}

  static createNewInstance(): SpaceProbe {
    return new SpaceProbe();
  }

  deepClone(): IPrototype {
    const clone = new SpaceProbe();
    clone.propulsionSystem = this.propulsionSystem;
    clone.powerSource = this.powerSource;
    clone.scientificInstruments = [...this.scientificInstruments];
    clone.missionTarget = this.missionTarget;
    clone.payloadMass = this.payloadMass;
    return clone;
  }
}

// === Builder Pattern ===
export interface SpaceProbeBuilder {
  reset(): void;
  buildPropulsionSystem(): void;
  buildPowerSource(): void;
  buildScientificInstruments(): void;
  buildMissionTarget(): void;
  buildPayloadMass(): void;
  getResult(): SpaceProbe;
}

export class MarsProbeBuilder implements SpaceProbeBuilder {
  private probe: SpaceProbe;

  constructor() {
    this.probe = SpaceProbe.createNewInstance();
  }

  reset(): void {
    this.probe = SpaceProbe.createNewInstance();
  }

  buildPropulsionSystem(): void {
    this.probe.propulsionSystem = "Chemical Rocket Propulsion";
  }

  buildPowerSource(): void {
    this.probe.powerSource = "Solar Panels + RTG";
  }

  buildScientificInstruments(): void {
    this.probe.scientificInstruments = [
      "Mars Atmospheric Analyzer",
      "High-Resolution Camera",
      "Ground-Penetrating Radar",
      "Soil Sample Collector"
    ];
  }

  buildMissionTarget(): void {
    this.probe.missionTarget = "Mars";
  }

  buildPayloadMass(): void {
    this.probe.payloadMass = 850.0;
  }

  getResult(): SpaceProbe {
    const result = this.probe;
    this.reset();
    return result;
  }
}

export class JupiterProbeBuilder implements SpaceProbeBuilder {
  private probe: SpaceProbe;

  constructor() {
    this.probe = SpaceProbe.createNewInstance();
  }

  reset(): void {
    this.probe = SpaceProbe.createNewInstance();
  }

  buildPropulsionSystem(): void {
    this.probe.propulsionSystem = "Ion Drive Propulsion";
  }

  buildPowerSource(): void {
    this.probe.powerSource = "Nuclear RTG";
  }

  buildScientificInstruments(): void {
    this.probe.scientificInstruments = [
      "Magnetometer",
      "Plasma Wave Sensor",
      "Infrared Spectrometer",
      "Radiation Detector",
      "Jupiter Moon Scanner"
    ];
  }

  buildMissionTarget(): void {
    this.probe.missionTarget = "Jupiter";
  }

  buildPayloadMass(): void {
    this.probe.payloadMass = 1200.0;
  }

  getResult(): SpaceProbe {
    const result = this.probe;
    this.reset();
    return result;
  }
}

// === Director ===
export class MissionControl {
  constructStandardProbe(builder: SpaceProbeBuilder): void {
    builder.reset();
    builder.buildPropulsionSystem();
    builder.buildPowerSource();
    builder.buildScientificInstruments();
    builder.buildMissionTarget();
    builder.buildPayloadMass();
  }

  constructLightweightProbe(builder: SpaceProbeBuilder): void {
    builder.reset();
    builder.buildPropulsionSystem();
    builder.buildPowerSource();
    builder.buildMissionTarget();
    builder.buildPayloadMass();
  }
}

// === Singleton Pattern ===
export class ConfigurationManager {
  private static instance: ConfigurationManager;
  private prototypes: Map<string, IPrototype>;

  private constructor() {
    this.prototypes = new Map();
  }

  static getInstance(): ConfigurationManager {
    if (!ConfigurationManager.instance) {
      ConfigurationManager.instance = new ConfigurationManager();
    }
    return ConfigurationManager.instance;
  }

  addPrototype(key: string, prototype: IPrototype): void {
    this.prototypes.set(key, prototype);
  }

  getClone(key: string): IPrototype | null {
    const prototype = this.prototypes.get(key);
    return prototype ? prototype.deepClone() : null;
  }

  hasPrototype(key: string): boolean {
    return this.prototypes.has(key);
  }

  getRegisteredKeys(): string[] {
    return Array.from(this.prototypes.keys());
  }
}
