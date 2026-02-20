package com.ecotrack.ecotrack.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import com.ecotrack.ecotrack.model.ActivityType;
import java.time.LocalDate;





@Document(collection = "activities")
public class UserActivity {

    private LocalDate date;
    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }


    @Id
    private String id;

    private String userId;
    private ActivityType activityType;
    private double quantity;
    private double carbonEmission;

    // ✅ empty constructor (VERY IMPORTANT)
    public UserActivity() {}

    // getters & setters
    public String getId() { return id; }

    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }


    public ActivityType getActivityType() {
        return activityType;
    }
    public void setActivityType(ActivityType activityType) {
        this.activityType = activityType;
    }


    public double getQuantity() { return quantity; }
    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public double getCarbonEmission() { return carbonEmission; }
    public void setCarbonEmission(double carbonEmission) {
        this.carbonEmission = carbonEmission;
    }
}
