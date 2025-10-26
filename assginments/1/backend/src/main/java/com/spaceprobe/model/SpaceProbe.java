package com.spaceprobe.model;

import com.spaceprobe.prototype.IPrototype;
import java.util.ArrayList;
import java.util.List;

/**
 * SpaceProbe - The complex Product
 * Implements Prototype pattern for efficient cloning
 */
public class SpaceProbe implements IPrototype {
    private String propulsionSystem;
    private String powerSource;
    private List<String> scientificInstruments;
    private String missionTarget;
    private double payloadMass;
    
    /**
     * Private constructor to enforce creation through builder
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
     * Creates an independent copy of the probe
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
     * Describes the probe configuration in human-readable format
     */
    public String describe() {
        StringBuilder description = new StringBuilder();
        description.append("=== Space Probe Configuration ===\n");
        description.append("Mission Target: ").append(missionTarget).append("\n");
        description.append("Propulsion System: ").append(propulsionSystem).append("\n");
        description.append("Power Source: ").append(powerSource).append("\n");
        description.append("Payload Mass: ").append(payloadMass).append(" kg\n");
        description.append("Scientific Instruments:\n");
        for (String instrument : scientificInstruments) {
            description.append("  - ").append(instrument).append("\n");
        }
        description.append("================================\n");
        return description.toString();
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
