package com.spaceprobe;

import com.spaceprobe.prototype.IPrototype;
import java.util.ArrayList;
import java.util.List;

/**
 * SpaceProbe represents a complex object with multiple configuration components.
 * This class implements the Prototype pattern to support efficient cloning.
 * It is constructed using the Builder pattern.
 */
public class SpaceProbe implements IPrototype {
    private String propulsionSystem;
    private String powerSource;
    private List<String> scientificInstruments;
    private String missionTarget;
    private double payloadMass;

    /**
     * Private constructor to ensure SpaceProbe is only created via factory method or builder.
     */
    private SpaceProbe() {
        this.scientificInstruments = new ArrayList<>();
    }

    /**
     * Factory method to create a new SpaceProbe instance.
     * 
     * @return A new SpaceProbe instance
     */
    public static SpaceProbe createNewInstance() {
        return new SpaceProbe();
    }

    /**
     * Deep clone implementation.
     * Creates a complete independent copy of the SpaceProbe.
     * 
     * @return A new SpaceProbe instance with the same configuration
     */
    @Override
    public IPrototype deepClone() {
        SpaceProbe clone = new SpaceProbe();
        clone.propulsionSystem = this.propulsionSystem;
        clone.powerSource = this.powerSource;
        // Deep copy the list to ensure independence
        clone.scientificInstruments = new ArrayList<>(this.scientificInstruments);
        clone.missionTarget = this.missionTarget;
        clone.payloadMass = this.payloadMass;
        return clone;
    }

    /**
     * Prints all configuration details in a human-readable format.
     */
    public void describe() {
        System.out.println("=== Space Probe Configuration ===");
        System.out.println("Mission Target: " + missionTarget);
        System.out.println("Propulsion System: " + propulsionSystem);
        System.out.println("Power Source: " + powerSource);
        System.out.println("Scientific Instruments:");
        for (String instrument : scientificInstruments) {
            System.out.println("  - " + instrument);
        }
        System.out.println("Payload Mass: " + payloadMass + " kg");
        System.out.println("================================\n");
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
