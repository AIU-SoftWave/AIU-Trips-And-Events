package com.spaceprobe.builder;

import com.spaceprobe.model.SpaceProbe;

/**
 * Concrete Builder for Mars Probes
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
        probe.addScientificInstrument("Ground-Penetrating Radar");
        probe.addScientificInstrument("Atmospheric Analyzer");
        probe.addScientificInstrument("Sample Collection Arm");
    }
    
    @Override
    public void buildMissionTarget() {
        probe.setMissionTarget("Mars");
    }
    
    @Override
    public void buildPayloadMass() {
        probe.setPayloadMass(800.0);
    }
    
    @Override
    public SpaceProbe getResult() {
        SpaceProbe result = this.probe;
        this.reset(); // Prepare for next build
        return result;
    }
}
