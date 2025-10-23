package com.spaceprobe.builder;

import com.spaceprobe.SpaceProbe;

/**
 * Concrete builder for creating Mars-specific Space Probes.
 * Configures the probe with components suitable for Mars exploration.
 */
public class MarsProbeBuilder implements SpaceProbeBuilder {
    private SpaceProbe probe;

    public MarsProbeBuilder() {
        this.reset();
    }

    @Override
    public void reset() {
        this.probe = SpaceProbe.createNewInstance();
    }

    @Override
    public void buildPropulsionSystem() {
        probe.setPropulsionSystem("Chemical Rocket");
    }

    @Override
    public void buildPowerSource() {
        probe.setPowerSource("Solar Panels");
    }

    @Override
    public void buildScientificInstruments() {
        probe.addScientificInstrument("High-Resolution Camera");
        probe.addScientificInstrument("Spectrometer");
        probe.addScientificInstrument("Soil Analysis Kit");
        probe.addScientificInstrument("Weather Station");
    }

    @Override
    public void buildMissionTarget() {
        probe.setMissionTarget("Mars");
    }

    @Override
    public void buildPayloadMass() {
        probe.setPayloadMass(150.0);
    }

    @Override
    public SpaceProbe getResult() {
        SpaceProbe result = this.probe;
        this.reset(); // Prepare for next build
        return result;
    }
}
