package com.ecotrack.ecotrack.service;

import com.ecotrack.ecotrack.model.UserActivity;

import java.time.LocalDate;
import java.util.List;

public interface UserActivityService {

    UserActivity saveActivity(UserActivity activity);

    List<UserActivity> getAllActivities();

    UserActivity getActivityById(String id);

    void deleteActivityById(String id);

    UserActivity updateActivity(String id, UserActivity updatedActivity);

    double getTotalCarbonEmission(String userId);

    double getTotalCarbonEmissionByDateRange(
            String userId,
            LocalDate from,
            LocalDate to
    );
    List<UserActivity> getActivitiesByUserId(String userId);


}
