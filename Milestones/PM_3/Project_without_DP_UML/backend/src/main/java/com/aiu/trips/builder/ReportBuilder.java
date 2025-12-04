package com.aiu.trips.builder;

import com.aiu.trips.model.Report;

/**
 * Builder Pattern - Abstract builder for Report objects
 * Provides template for building different report formats
 */
public abstract class ReportBuilder {
    
    protected Report report;
    
    public ReportBuilder() {
        this.report = new Report();
    }
    
    public abstract void setReportId(String reportId);
    public abstract void setTitle(String title);
    public abstract void buildContent(String data);
    public abstract void setFormat();
    
    public Report getReport() {
        return this.report;
    }
    
    public void reset() {
        this.report = new Report();
    }
}
