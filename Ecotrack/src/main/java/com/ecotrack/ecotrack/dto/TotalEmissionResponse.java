package com.ecotrack.ecotrack.dto;

public class TotalEmissionResponse {

    private String userId;
    private double totalCarbonEmission;
    private String unit;

    public TotalEmissionResponse(String userId, double totalCarbonEmission, String unit) {
        this.userId = userId;
        this.totalCarbonEmission = totalCarbonEmission;
        this.unit = unit;
    }

    public String getUserId() {
        return userId;
    }

    public double getTotalCarbonEmission() {
        return totalCarbonEmission;
    }

    public String getUnit() {
        return unit;
    }
}
