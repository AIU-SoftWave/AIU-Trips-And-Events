package com.aiu.spaceprobe.builder;

import com.aiu.spaceprobe.model.SpaceProbe;

/**
 * Concrete builder for Jupiter probes
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
        probe.setPropulsionSystem("Ion Drive Propulsion");
    }

    @Override
    public void buildPowerSource() {
        probe.setPowerSource("Nuclear RTG");
    }

    @Override
    public void buildScientificInstruments() {
        probe.addScientificInstrument("Magnetometer");
        probe.addScientificInstrument("Plasma Wave Sensor");
        probe.addScientificInstrument("Infrared Spectrometer");
        probe.addScientificInstrument("Radiation Detector");
        probe.addScientificInstrument("Jupiter Moon Scanner");
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
        this.reset();
        return result;
    }
}
