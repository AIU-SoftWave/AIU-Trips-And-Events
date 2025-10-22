package com.aiu.spaceprobe.model;

import com.aiu.spaceprobe.prototype.IPrototype;
import java.util.ArrayList;
import java.util.List;

/**
 * Product class representing a Space Probe
 * Implements Prototype pattern for efficient cloning
 */
public class SpaceProbe implements IPrototype {
    private String propulsionSystem;
    private String powerSource;
    private List<String> scientificInstruments;
    private String missionTarget;
    private double payloadMass;

    /**
     * Private constructor to prevent direct instantiation
     */
    private SpaceProbe() {
        this.scientificInstruments = new ArrayList<>();
    }

    /**
     * Factory method to create a new instance
     * @return A new SpaceProbe instance
     */
    public static SpaceProbe createNewInstance() {
        return new SpaceProbe();
    }

    /**
     * Deep clone implementation
     * @return A deep copy of this SpaceProbe
     */
    @Override
    public IPrototype deepClone() {
        SpaceProbe clone = new SpaceProbe();
        clone.propulsionSystem = this.propulsionSystem;
        clone.powerSource = this.powerSource;
        clone.scientificInstruments = new ArrayList<>(this.scientificInstruments);
        clone.missionTarget = this.missionTarget;
        clone.payloadMass = this.payloadMass;
        return clone;
    }

    /**
     * Display probe information
     */
    public void describe() {
        System.out.println("=== Space Probe Description ===");
        System.out.println("Mission Target: " + missionTarget);
        System.out.println("Propulsion System: " + propulsionSystem);
        System.out.println("Power Source: " + powerSource);
        System.out.println("Scientific Instruments: " + scientificInstruments);
        System.out.println("Payload Mass: " + payloadMass + " kg");
        System.out.println("================================");
    }

    // Getters and Setters
    public String getPropulsionSystem() {
        return propulsionSystem;
    }

    public void setPropulsionSystem(String propulsionSystem) {
        this.propulsionSystem = propulsionSystem;
    }

    public String getPowerSource() {
        return powerSource;
    }

    public void setPowerSource(String powerSource) {
        this.powerSource = powerSource;
    }

    public List<String> getScientificInstruments() {
        return scientificInstruments;
    }

    public void setScientificInstruments(List<String> scientificInstruments) {
        this.scientificInstruments = scientificInstruments;
    }

    public void addScientificInstrument(String instrument) {
        this.scientificInstruments.add(instrument);
    }

    public String getMissionTarget() {
        return missionTarget;
    }

    public void setMissionTarget(String missionTarget) {
        this.missionTarget = missionTarget;
    }

    public double getPayloadMass() {
        return payloadMass;
    }

    public void setPayloadMass(double payloadMass) {
        this.payloadMass = payloadMass;
    }
}
