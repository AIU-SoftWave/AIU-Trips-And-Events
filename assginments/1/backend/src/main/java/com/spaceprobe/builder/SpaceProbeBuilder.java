package com.spaceprobe.builder;

import com.spaceprobe.model.SpaceProbe;

/**
 * Builder Pattern Interface
 * Defines the steps to build a SpaceProbe
 */
public interface SpaceProbeBuilder {
    /**
     * Resets the builder to start building a new probe
     */
    void reset();
    
    /**
     * Builds the propulsion system
     */
    void buildPropulsionSystem();
    
    /**
     * Builds the power source
     */
    void buildPowerSource();
    
    /**
     * Builds the scientific instruments
     */
    void buildScientificInstruments();
    
    /**
     * Sets the mission target
     */
    void buildMissionTarget();
    
    /**
     * Sets the payload mass
     */
    void buildPayloadMass();
    
    /**
     * Returns the constructed SpaceProbe
     * @return The built SpaceProbe
     */
    SpaceProbe getResult();
}
