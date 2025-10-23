package com.spaceprobe.controller;

import com.spaceprobe.builder.JupiterProbeBuilder;
import com.spaceprobe.builder.MarsProbeBuilder;
import com.spaceprobe.builder.MissionControl;
import com.spaceprobe.model.SpaceProbe;
import com.spaceprobe.singleton.ConfigurationManager;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * REST Controller for Space Probe operations
 * Provides API endpoints for probe management
 */
@RestController
@RequestMapping("/api/probes")
@CrossOrigin(origins = "*")
public class SpaceProbeController {
    
    private final ConfigurationManager manager;
    private final MissionControl director;
    
    public SpaceProbeController() {
        this.manager = ConfigurationManager.getInstance();
        this.director = new MissionControl();
        initializeTemplates();
    }
    
    /**
     * Initialize template probes on startup
     */
    private void initializeTemplates() {
        if (!manager.hasPrototype("MarsTemplate")) {
            MarsProbeBuilder marsBuilder = new MarsProbeBuilder();
            SpaceProbe marsTemplate = director.constructStandardProbe(marsBuilder);
            manager.addPrototype("MarsTemplate", marsTemplate);
        }
        
        if (!manager.hasPrototype("JupiterTemplate")) {
            JupiterProbeBuilder jupiterBuilder = new JupiterProbeBuilder();
            SpaceProbe jupiterTemplate = director.constructStandardProbe(jupiterBuilder);
            manager.addPrototype("JupiterTemplate", jupiterTemplate);
        }
    }
    
    /**
     * Get all available probe templates
     */
    @GetMapping("/templates")
    public ResponseEntity<Map<String, Object>> getTemplates() {
        Map<String, Object> response = new HashMap<>();
        
        for (String key : manager.getPrototypeKeys()) {
            SpaceProbe probe = (SpaceProbe) manager.getClone(key);
            response.put(key, convertProbeToMap(probe));
        }
        
        return ResponseEntity.ok(response);
    }
    
    /**
     * Get a specific template by key
     */
    @GetMapping("/templates/{key}")
    public ResponseEntity<Map<String, Object>> getTemplate(@PathVariable String key) {
        if (!manager.hasPrototype(key)) {
            return ResponseEntity.notFound().build();
        }
        
        SpaceProbe probe = (SpaceProbe) manager.getClone(key);
        return ResponseEntity.ok(convertProbeToMap(probe));
    }
    
    /**
     * Clone a probe from a template
     */
    @PostMapping("/clone/{templateKey}")
    public ResponseEntity<Map<String, Object>> cloneProbe(
            @PathVariable String templateKey,
            @RequestBody(required = false) Map<String, Object> modifications) {
        
        if (!manager.hasPrototype(templateKey)) {
            return ResponseEntity.notFound().build();
        }
        
        SpaceProbe clone = (SpaceProbe) manager.getClone(templateKey);
        
        // Apply modifications if provided
        if (modifications != null && modifications.containsKey("payloadMass")) {
            double newPayloadMass = Double.parseDouble(modifications.get("payloadMass").toString());
            clone.setPayloadMass(newPayloadMass);
        }
        
        return ResponseEntity.ok(convertProbeToMap(clone));
    }
    
    /**
     * Create a new probe using builder pattern
     */
    @PostMapping("/build/{probeType}")
    public ResponseEntity<Map<String, Object>> buildProbe(@PathVariable String probeType) {
        SpaceProbe probe;
        
        switch (probeType.toLowerCase()) {
            case "mars":
                MarsProbeBuilder marsBuilder = new MarsProbeBuilder();
                probe = director.constructStandardProbe(marsBuilder);
                break;
            case "jupiter":
                JupiterProbeBuilder jupiterBuilder = new JupiterProbeBuilder();
                probe = director.constructStandardProbe(jupiterBuilder);
                break;
            default:
                return ResponseEntity.badRequest().build();
        }
        
        return ResponseEntity.ok(convertProbeToMap(probe));
    }
    
    /**
     * Run the complete demonstration
     */
    @GetMapping("/demo")
    public ResponseEntity<Map<String, Object>> runDemo() {
        Map<String, Object> response = new HashMap<>();
        
        // Build templates
        MarsProbeBuilder marsBuilder = new MarsProbeBuilder();
        JupiterProbeBuilder jupiterBuilder = new JupiterProbeBuilder();
        
        SpaceProbe marsTemplate = director.constructStandardProbe(marsBuilder);
        SpaceProbe jupiterTemplate = director.constructStandardProbe(jupiterBuilder);
        
        // Clone probes
        SpaceProbe marsProbe1 = (SpaceProbe) manager.getClone("MarsTemplate");
        marsProbe1.setPayloadMass(850.0);
        
        SpaceProbe marsProbe2 = (SpaceProbe) manager.getClone("MarsTemplate");
        marsProbe2.setPayloadMass(900.0);
        
        SpaceProbe jupiterProbe1 = (SpaceProbe) manager.getClone("JupiterTemplate");
        jupiterProbe1.setPayloadMass(1250.0);
        
        // Build response
        Map<String, Object> templates = new HashMap<>();
        templates.put("MarsTemplate", convertProbeToMap(marsTemplate));
        templates.put("JupiterTemplate", convertProbeToMap(jupiterTemplate));
        
        Map<String, Object> clones = new HashMap<>();
        clones.put("MarsProbe1", convertProbeToMap(marsProbe1));
        clones.put("MarsProbe2", convertProbeToMap(marsProbe2));
        clones.put("JupiterProbe1", convertProbeToMap(jupiterProbe1));
        
        response.put("templates", templates);
        response.put("clones", clones);
        response.put("message", "Demo executed successfully");
        
        return ResponseEntity.ok(response);
    }
    
    /**
     * Helper method to convert SpaceProbe to Map
     */
    private Map<String, Object> convertProbeToMap(SpaceProbe probe) {
        Map<String, Object> map = new HashMap<>();
        map.put("missionTarget", probe.getMissionTarget());
        map.put("propulsionSystem", probe.getPropulsionSystem());
        map.put("powerSource", probe.getPowerSource());
        map.put("scientificInstruments", probe.getScientificInstruments());
        map.put("payloadMass", probe.getPayloadMass());
        map.put("description", probe.describe());
        return map;
    }
}
