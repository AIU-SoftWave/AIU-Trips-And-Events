package com.aiu.spaceprobe.app;

import com.aiu.spaceprobe.builder.*;
import com.aiu.spaceprobe.model.SpaceProbe;
import com.aiu.spaceprobe.singleton.ConfigurationManager;

/**
 * Main application demonstrating all three design patterns:
 * 1. Builder Pattern - for creating complex SpaceProbe objects
 * 2. Prototype Pattern - for efficient cloning of probes
 * 3. Singleton Pattern - for managing prototype registry
 */
public class SpaceMissionApp {

    public static void main(String[] args) {
        System.out.println("╔════════════════════════════════════════════════════════════╗");
        System.out.println("║    SPACE PROBE MISSION CONTROL SYSTEM                      ║");
        System.out.println("║    Demonstrating: Builder, Prototype & Singleton Patterns ║");
        System.out.println("╚════════════════════════════════════════════════════════════╝");
        System.out.println();

        // Step 1: Create Template Probes using Builder Pattern
        System.out.println(">>> STEP 1: Creating Template Probes using Builder Pattern");
        System.out.println("────────────────────────────────────────────────────────────");
        
        MissionControl director = new MissionControl();
        
        // Build Mars Template
        MarsProbeBuilder marsBuilder = new MarsProbeBuilder();
        director.constructStandardProbe(marsBuilder);
        SpaceProbe marsTemplate = marsBuilder.getResult();
        System.out.println("✓ Mars Template Created");
        
        // Build Jupiter Template
        JupiterProbeBuilder jupiterBuilder = new JupiterProbeBuilder();
        director.constructStandardProbe(jupiterBuilder);
        SpaceProbe jupiterTemplate = jupiterBuilder.getResult();
        System.out.println("✓ Jupiter Template Created");
        System.out.println();

        // Step 2: Register Templates using Singleton Pattern
        System.out.println(">>> STEP 2: Registering Templates in Configuration Manager (Singleton)");
        System.out.println("────────────────────────────────────────────────────────────");
        
        ConfigurationManager manager = ConfigurationManager.getInstance();
        manager.addPrototype("MarsTemplate", marsTemplate);
        manager.addPrototype("JupiterTemplate", jupiterTemplate);
        System.out.println();

        // Step 3: Deploy probes using Prototype Pattern
        System.out.println(">>> STEP 3: Deploying Probes using Prototype Pattern");
        System.out.println("────────────────────────────────────────────────────────────");
        
        // Deploy multiple Mars probes
        System.out.println("\n--- Deploying Mars Mission Fleet ---");
        SpaceProbe marsProbe1 = (SpaceProbe) manager.getClone("MarsTemplate");
        marsProbe1.setPayloadMass(850.5);
        System.out.println("Mars Probe #1 deployed:");
        marsProbe1.describe();

        SpaceProbe marsProbe2 = (SpaceProbe) manager.getClone("MarsTemplate");
        marsProbe2.setPayloadMass(875.0);
        System.out.println("\nMars Probe #2 deployed:");
        marsProbe2.describe();

        // Deploy Jupiter probe
        System.out.println("\n--- Deploying Jupiter Mission ---");
        SpaceProbe jupiterProbe1 = (SpaceProbe) manager.getClone("JupiterTemplate");
        jupiterProbe1.setPayloadMass(1250.0);
        System.out.println("Jupiter Probe #1 deployed:");
        jupiterProbe1.describe();

        // Step 4: Demonstrate template immutability
        System.out.println("\n>>> STEP 4: Verifying Template Integrity");
        System.out.println("────────────────────────────────────────────────────────────");
        System.out.println("Original Mars Template (unchanged by deployments):");
        marsTemplate.describe();

        // Additional demonstration
        System.out.println("\n>>> ADDITIONAL: Creating Lightweight Probe");
        System.out.println("────────────────────────────────────────────────────────────");
        MarsProbeBuilder lightweightBuilder = new MarsProbeBuilder();
        director.constructLightweightProbe(lightweightBuilder);
        SpaceProbe lightweightProbe = lightweightBuilder.getResult();
        System.out.println("Lightweight Mars Probe created:");
        lightweightProbe.describe();

        System.out.println("\n╔════════════════════════════════════════════════════════════╗");
        System.out.println("║    MISSION DEPLOYMENT COMPLETE                             ║");
        System.out.println("╚════════════════════════════════════════════════════════════╝");
    }

    /**
     * Deploy a mission (can be called from other parts of the application)
     */
    public static void deployMission() {
        ConfigurationManager manager = ConfigurationManager.getInstance();
        SpaceProbe probe = (SpaceProbe) manager.getClone("MarsTemplate");
        if (probe != null) {
            probe.describe();
        }
    }
}
