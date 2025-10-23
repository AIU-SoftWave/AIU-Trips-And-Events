package com.spaceprobe.client;

import com.spaceprobe.SpaceProbe;
import com.spaceprobe.builder.MarsProbeBuilder;
import com.spaceprobe.builder.JupiterProbeBuilder;
import com.spaceprobe.builder.MissionControl;
import com.spaceprobe.builder.SpaceProbeBuilder;
import com.spaceprobe.singleton.ConfigurationManager;

/**
 * Main client application demonstrating the integration of:
 * - Builder Pattern (for constructing complex SpaceProbe objects)
 * - Prototype Pattern (for efficient cloning)
 * - Singleton Pattern (for centralized configuration management)
 */
public class SpaceMissionApp {

    public static void main(String[] args) {
        System.out.println("========================================");
        System.out.println("Space Probe Configuration System");
        System.out.println("========================================\n");

        // === STEP 1: Create Template Probes using Builder Pattern ===
        System.out.println("STEP 1: Building Template Probes using Builder Pattern");
        System.out.println("--------------------------------------------------------\n");

        // Get the Director
        MissionControl missionControl = new MissionControl();

        // Create Mars Probe Template
        SpaceProbeBuilder marsBuilder = new MarsProbeBuilder();
        missionControl.constructStandardProbe(marsBuilder);
        SpaceProbe marsTemplate = marsBuilder.getResult();
        System.out.println("Mars Template Probe created:");
        marsTemplate.describe();

        // Create Jupiter Probe Template
        SpaceProbeBuilder jupiterBuilder = new JupiterProbeBuilder();
        missionControl.constructStandardProbe(jupiterBuilder);
        SpaceProbe jupiterTemplate = jupiterBuilder.getResult();
        System.out.println("Jupiter Template Probe created:");
        jupiterTemplate.describe();

        // === STEP 2: Register Templates in Singleton ConfigurationManager ===
        System.out.println("\nSTEP 2: Registering Templates in ConfigurationManager (Singleton)");
        System.out.println("-------------------------------------------------------------------\n");

        // Get the single instance of ConfigurationManager
        ConfigurationManager manager = ConfigurationManager.getInstance();
        
        // Verify singleton - getting instance again should return the same object
        ConfigurationManager manager2 = ConfigurationManager.getInstance();
        System.out.println("Singleton verification: manager == manager2 ? " + (manager == manager2) + "\n");

        // Register the templates
        manager.addPrototype("MarsTemplate", marsTemplate);
        manager.addPrototype("JupiterTemplate", jupiterTemplate);
        
        System.out.println();
        manager.listPrototypes();
        System.out.println();

        // === STEP 3: Clone Probes using Prototype Pattern ===
        System.out.println("\nSTEP 3: Creating Deployment Probes using Prototype Pattern");
        System.out.println("-----------------------------------------------------------\n");

        // Clone Mars probes (3 times as required)
        SpaceProbe marsProbe1 = (SpaceProbe) manager.getClone("MarsTemplate");
        marsProbe1.setPayloadMass(150.5);
        System.out.println("Mars Probe 1 (Modified payload):");
        marsProbe1.describe();

        SpaceProbe marsProbe2 = (SpaceProbe) manager.getClone("MarsTemplate");
        marsProbe2.setPayloadMass(155.0);
        System.out.println("Mars Probe 2 (Modified payload):");
        marsProbe2.describe();

        SpaceProbe marsProbe3 = (SpaceProbe) manager.getClone("MarsTemplate");
        marsProbe3.setPayloadMass(148.0);
        System.out.println("Mars Probe 3 (Modified payload):");
        marsProbe3.describe();

        // Clone Jupiter probe (once as required)
        SpaceProbe jupiterProbe1 = (SpaceProbe) manager.getClone("JupiterTemplate");
        jupiterProbe1.setPayloadMass(305.5);
        System.out.println("Jupiter Probe 1 (Modified payload):");
        jupiterProbe1.describe();

        // === STEP 4: Verify Independence (Deep Copy) ===
        System.out.println("\nSTEP 4: Verifying Independence of Clones (Deep Copy)");
        System.out.println("-----------------------------------------------------\n");

        System.out.println("Original Mars Template (should remain unchanged at 150.0 kg):");
        marsTemplate.describe();

        System.out.println("Original Jupiter Template (should remain unchanged at 300.0 kg):");
        jupiterTemplate.describe();

        // === STEP 5: Demonstrate Mission Deployment ===
        System.out.println("\nSTEP 5: Mission Deployment Summary");
        System.out.println("-----------------------------------\n");

        System.out.println("Total Templates Registered: " + manager.getPrototypeCount());
        System.out.println("Total Deployment Probes Created: 4 (3 Mars + 1 Jupiter)");
        System.out.println("\nAll probes are independent copies with modified configurations.");
        System.out.println("Original templates remain unchanged and can be reused.\n");

        System.out.println("========================================");
        System.out.println("Mission Deployment Complete!");
        System.out.println("========================================");
    }

    /**
     * Helper method to deploy a mission (can be called from external systems).
     * This demonstrates how the system can be used programmatically.
     */
    public static void deployMission() {
        System.out.println("Deploying mission using ConfigurationManager...");
        ConfigurationManager manager = ConfigurationManager.getInstance();
        SpaceProbe probe = (SpaceProbe) manager.getClone("MarsTemplate");
        if (probe != null) {
            probe.describe();
        }
    }
}
