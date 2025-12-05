package com.aiu.trips.builder;

import com.aiu.trips.dto.ReportDTO;
import com.aiu.trips.factory.IModelFactory;

import java.util.List;

/**
 * Builder Pattern for Reports
 * Abstract builder for creating reports
 */
public abstract class ReportBuilder {
    protected IModelFactory modelFactory;
    
    /**
     * Resets the builder
     */
    public abstract void reset();
    
    /**
     * Builds report header
     */
    public abstract void buildHeader();
    
    /**
     * Builds report body with data
     * @param data Report data
     */
    public abstract void buildBody(List<?> data);
    
    /**
     * Builds report footer
     */
    public abstract void buildFooter();
    
    /**
     * Returns the built report
     * @return Report DTO
     */
    public abstract ReportDTO getProduct();
}
