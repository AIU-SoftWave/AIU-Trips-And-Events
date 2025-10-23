package com.spaceprobe.builder;

import com.spaceprobe.SpaceProbe;

/**
 * Builder interface for constructing SpaceProbe objects step by step.
 * This pattern separates the construction of a complex object from its representation.
 */
public interface SpaceProbeBuilder {
    /**
     * Resets the builder to create a new SpaceProbe.
     */
    void reset();

    /**
     * Builds the propulsion system component.
     */
    void buildPropulsionSystem();

    /**
     * Builds the power source component.
     */
    void buildPowerSource();

    /**
     * Builds the scientific instruments component.
     */
    void buildScientificInstruments();

    /**
     * Builds the mission target component.
     */
    void buildMissionTarget();

    /**
     * Builds the payload mass component.
     */
    void buildPayloadMass();

    /**
     * Returns the constructed SpaceProbe.
     * 
     * @return The fully constructed SpaceProbe
     */
    SpaceProbe getResult();
}
