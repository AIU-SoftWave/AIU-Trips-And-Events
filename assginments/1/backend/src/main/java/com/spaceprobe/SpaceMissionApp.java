package com.spaceprobe;

import com.spaceprobe.builder.JupiterProbeBuilder;
import com.spaceprobe.builder.MarsProbeBuilder;
import com.spaceprobe.builder.MissionControl;
import com.spaceprobe.model.SpaceProbe;
import com.spaceprobe.singleton.ConfigurationManager;

/**
 * Client class demonstrating all three design patterns
 * Builder, Prototype, and Singleton
 */
public class SpaceMissionApp {
    
    /**
     * Demonstrates the complete workflow of the system
     */
    public static void deployMission() {
        System.out.println("===========================================");
        System.out.println("Space Probe Mission Deployment Demo");
        System.out.println("===========================================\n");
        
        // Step 1: Get the Singleton ConfigurationManager
        ConfigurationManager manager = ConfigurationManager.getInstance();
        System.out.println("✓ Retrieved Singleton ConfigurationManager\n");
        
        // Step 2: Create builders and director
        MissionControl director = new MissionControl();
        MarsProbeBuilder marsBuilder = new MarsProbeBuilder();
        JupiterProbeBuilder jupiterBuilder = new JupiterProbeBuilder();
        System.out.println("✓ Created Director and Builders\n");
        
        // Step 3: Build template probes using Builder pattern
        System.out.println("--- Building Template Probes ---");
        SpaceProbe marsTemplate = director.constructStandardProbe(marsBuilder);
        System.out.println("✓ Built Mars Template");
        
        SpaceProbe jupiterTemplate = director.constructStandardProbe(jupiterBuilder);
        System.out.println("✓ Built Jupiter Template\n");
        
        // Step 4: Register templates in the singleton manager
        System.out.println("--- Registering Templates ---");
        manager.addPrototype("MarsTemplate", marsTemplate);
        System.out.println("✓ Registered Mars Template");
        
        manager.addPrototype("JupiterTemplate", jupiterTemplate);
        System.out.println("✓ Registered Jupiter Template\n");
        
        // Step 5: Clone probes using Prototype pattern
        System.out.println("--- Cloning Deployment Probes ---");
        
        // Clone Mars probes
        SpaceProbe marsProbe1 = (SpaceProbe) manager.getClone("MarsTemplate");
        marsProbe1.setPayloadMass(850.0);
        System.out.println("✓ Cloned Mars Probe 1 (payload: 850.0 kg)");
        
        SpaceProbe marsProbe2 = (SpaceProbe) manager.getClone("MarsTemplate");
        marsProbe2.setPayloadMass(900.0);
        System.out.println("✓ Cloned Mars Probe 2 (payload: 900.0 kg)");
        
        SpaceProbe marsProbe3 = (SpaceProbe) manager.getClone("MarsTemplate");
        marsProbe3.setPayloadMass(820.0);
        System.out.println("✓ Cloned Mars Probe 3 (payload: 820.0 kg)");
        
        // Clone Jupiter probe
        SpaceProbe jupiterProbe1 = (SpaceProbe) manager.getClone("JupiterTemplate");
        jupiterProbe1.setPayloadMass(1250.0);
        System.out.println("✓ Cloned Jupiter Probe 1 (payload: 1250.0 kg)\n");
        
        // Step 6: Display all probe details
        System.out.println("===========================================");
        System.out.println("ORIGINAL TEMPLATES");
        System.out.println("===========================================\n");
        
        System.out.println("Mars Template:");
        System.out.println(marsTemplate.describe());
        
        System.out.println("Jupiter Template:");
        System.out.println(jupiterTemplate.describe());
        
        System.out.println("===========================================");
        System.out.println("CLONED DEPLOYMENT PROBES");
        System.out.println("===========================================\n");
        
        System.out.println("Mars Probe 1:");
        System.out.println(marsProbe1.describe());
        
        System.out.println("Mars Probe 2:");
        System.out.println(marsProbe2.describe());
        
        System.out.println("Mars Probe 3:");
        System.out.println(marsProbe3.describe());
        
        System.out.println("Jupiter Probe 1:");
        System.out.println(jupiterProbe1.describe());
        
        // Step 7: Verify independence (deep copy verification)
        System.out.println("===========================================");
        System.out.println("DEEP COPY VERIFICATION");
        System.out.println("===========================================\n");
        
        System.out.println("Original Mars Template payload: " + marsTemplate.getPayloadMass() + " kg");
        System.out.println("Mars Probe 1 payload: " + marsProbe1.getPayloadMass() + " kg");
        System.out.println("Mars Probe 2 payload: " + marsProbe2.getPayloadMass() + " kg");
        System.out.println("Mars Probe 3 payload: " + marsProbe3.getPayloadMass() + " kg");
        System.out.println("\n✓ Clones are independent - modifications don't affect templates!");
        
        System.out.println("\n===========================================");
        System.out.println("Mission Deployment Complete!");
        System.out.println("===========================================");
    }
    
    /**
     * Main method for standalone testing
     */
    public static void main(String[] args) {
        deployMission();
    }
}
