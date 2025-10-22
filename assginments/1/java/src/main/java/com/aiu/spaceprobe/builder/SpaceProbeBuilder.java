package com.aiu.spaceprobe.builder;

import com.aiu.spaceprobe.model.SpaceProbe;

/**
 * Builder interface for constructing SpaceProbe objects
 */
public interface SpaceProbeBuilder {
    /**
     * Reset the builder to start fresh
     */
    void reset();

    /**
     * Build the propulsion system
     */
    void buildPropulsionSystem();

    /**
     * Build the power source
     */
    void buildPowerSource();

    /**
     * Build scientific instruments
     */
    void buildScientificInstruments();

    /**
     * Build mission target
     */
    void buildMissionTarget();

    /**
     * Build payload mass
     */
    void buildPayloadMass();

    /**
     * Get the constructed SpaceProbe
     * @return The built SpaceProbe
     */
    SpaceProbe getResult();
}
