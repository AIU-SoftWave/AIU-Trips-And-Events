package com.aiu.spaceprobe.builder;

import com.aiu.spaceprobe.model.SpaceProbe;

/**
 * Concrete builder for Mars probes
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
        probe.setPropulsionSystem("Chemical Rocket Propulsion");
    }

    @Override
    public void buildPowerSource() {
        probe.setPowerSource("Solar Panels + RTG");
    }

    @Override
    public void buildScientificInstruments() {
        probe.addScientificInstrument("Mars Atmospheric Analyzer");
        probe.addScientificInstrument("High-Resolution Camera");
        probe.addScientificInstrument("Ground-Penetrating Radar");
        probe.addScientificInstrument("Soil Sample Collector");
    }

    @Override
    public void buildMissionTarget() {
        probe.setMissionTarget("Mars");
    }

    @Override
    public void buildPayloadMass() {
        probe.setPayloadMass(850.0);
    }

    @Override
    public SpaceProbe getResult() {
        SpaceProbe result = this.probe;
        this.reset();
        return result;
    }
}
