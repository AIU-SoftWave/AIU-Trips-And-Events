package com.aiu.trips.command;

import com.aiu.trips.dto.ActivityDTO;
import com.aiu.trips.enums.ActivityType;
import com.aiu.trips.service.interfaces.IActivityManagement;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.util.Map;

public class CreateEventCommand implements IControllerCommand {
    private final IActivityManagement activityService;

    public CreateEventCommand(IActivityManagement activityService) {
        this.activityService = activityService;
    }

    @Override
    public ResponseEntity<?> execute(Map<String, Object> requestData) {
        try {
            ActivityDTO activityDTO = mapToActivityDTO(requestData);
            ActivityType type = ActivityType.valueOf((String) requestData.getOrDefault("type", "EVENT"));
            ActivityDTO result = activityService.createActivity(activityDTO, type);
            return ResponseEntity.status(HttpStatus.CREATED).body(result);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    private ActivityDTO mapToActivityDTO(Map<String, Object> data) {
        ActivityDTO dto = new ActivityDTO();
        dto.setName((String) data.get("name"));
        dto.setDescription((String) data.get("description"));
        dto.setLocation((String) data.get("location"));
        return dto;
    }
}
