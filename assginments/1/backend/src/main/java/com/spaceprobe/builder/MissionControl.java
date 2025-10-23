package com.spaceprobe.builder;

import com.spaceprobe.model.SpaceProbe;

/**
 * Director class for building SpaceProbes
 * Orchestrates the building process
 */
public class MissionControl {
    
    /**
     * Constructs a standard probe using the provided builder
     * @param builder The builder to use
     * @return The constructed SpaceProbe
     */
    public SpaceProbe constructStandardProbe(SpaceProbeBuilder builder) {
        builder.reset();
        builder.buildPropulsionSystem();
        builder.buildPowerSource();
        builder.buildScientificInstruments();
        builder.buildMissionTarget();
        builder.buildPayloadMass();
        return builder.getResult();
    }
    
    /**
     * Constructs a lightweight probe using the provided builder
     * @param builder The builder to use
     * @return The constructed SpaceProbe
     */
    public SpaceProbe constructLightweightProbe(SpaceProbeBuilder builder) {
        builder.reset();
        builder.buildPropulsionSystem();
        builder.buildPowerSource();
        builder.buildMissionTarget();
        // Skip some instruments for lightweight version
        builder.buildPayloadMass();
        return builder.getResult();
    }
}
