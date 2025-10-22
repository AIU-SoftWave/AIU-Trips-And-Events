package com.aiu.spaceprobe.builder;

/**
 * Director class that defines the construction steps
 */
public class MissionControl {
    
    /**
     * Construct a standard probe with all features
     * @param builder The builder to use
     */
    public void constructStandardProbe(SpaceProbeBuilder builder) {
        builder.reset();
        builder.buildPropulsionSystem();
        builder.buildPowerSource();
        builder.buildScientificInstruments();
        builder.buildMissionTarget();
        builder.buildPayloadMass();
    }

    /**
     * Construct a lightweight probe with minimal features
     * @param builder The builder to use
     */
    public void constructLightweightProbe(SpaceProbeBuilder builder) {
        builder.reset();
        builder.buildPropulsionSystem();
        builder.buildPowerSource();
        builder.buildMissionTarget();
        // Lightweight version has reduced payload mass
        builder.buildPayloadMass();
    }
}
