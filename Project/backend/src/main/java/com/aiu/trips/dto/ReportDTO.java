package com.aiu.trips.dto;

import com.aiu.trips.enums.ExportFormat;
import com.aiu.trips.enums.ReportType;

/**
 * ReportDTO for transferring report data
 */
public class ReportDTO {
    private Long reportId;
    private ReportType type;
    private String filePath;
    private String description;
    private ExportFormat format;
    private byte[] data;

    public ReportDTO() {}

    // Getters and Setters
    public Long getReportId() { return reportId; }
    public void setReportId(Long reportId) { this.reportId = reportId; }

    public ReportType getType() { return type; }
    public void setType(ReportType type) { this.type = type; }

    public String getFilePath() { return filePath; }
    public void setFilePath(String filePath) { this.filePath = filePath; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public ExportFormat getFormat() { return format; }
    public void setFormat(ExportFormat format) { this.format = format; }

    public byte[] getData() { return data; }
    public void setData(byte[] data) { this.data = data; }
}
