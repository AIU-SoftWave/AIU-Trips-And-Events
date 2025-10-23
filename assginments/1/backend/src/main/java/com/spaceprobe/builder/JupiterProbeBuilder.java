package com.spaceprobe.builder;

import com.spaceprobe.model.SpaceProbe;

/**
 * Concrete Builder for Jupiter Probes
 */
public class JupiterProbeBuilder implements SpaceProbeBuilder {
    private SpaceProbe probe;
    
    public JupiterProbeBuilder() {
        this.reset();
    }
    
    @Override
    public void reset() {
        this.probe = SpaceProbe.createNewInstance();
    }
    
    @Override
    public void buildPropulsionSystem() {
        probe.setPropulsionSystem("Ion Thruster");
    }
    
    @Override
    public void buildPowerSource() {
        probe.setPowerSource("Radioisotope Thermoelectric Generator (RTG)");
    }
    
    @Override
    public void buildScientificInstruments() {
        probe.addScientificInstrument("Magnetometer");
        probe.addScientificInstrument("Infrared Spectrometer");
        probe.addScientificInstrument("Plasma Wave Detector");
        probe.addScientificInstrument("Ultraviolet Imaging System");
    }
    
    @Override
    public void buildMissionTarget() {
        probe.setMissionTarget("Jupiter");
    }
    
    @Override
    public void buildPayloadMass() {
        probe.setPayloadMass(1200.0);
    }
    
    @Override
    public SpaceProbe getResult() {
        SpaceProbe result = this.probe;
        this.reset(); // Prepare for next build
        return result;
    }
}
