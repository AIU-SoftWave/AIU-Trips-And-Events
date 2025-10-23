package com.spaceprobe.builder;

/**
 * Director class that orchestrates the construction of SpaceProbe objects.
 * This class defines the order of construction steps and hides complexity from the client.
 */
public class MissionControl {
    
    /**
     * Constructs a standard probe with all components.
     * 
     * @param builder The builder to use for construction
     */
    public void constructStandardProbe(SpaceProbeBuilder builder) {
        builder.reset();
        builder.buildMissionTarget();
        builder.buildPropulsionSystem();
        builder.buildPowerSource();
        builder.buildScientificInstruments();
        builder.buildPayloadMass();
    }

    /**
     * Constructs a lightweight probe with minimal configuration.
     * 
     * @param builder The builder to use for construction
     */
    public void constructLightweightProbe(SpaceProbeBuilder builder) {
        builder.reset();
        builder.buildMissionTarget();
        builder.buildPropulsionSystem();
        builder.buildPowerSource();
        // Reduced instruments for lightweight version
        builder.buildPayloadMass();
    }
}
