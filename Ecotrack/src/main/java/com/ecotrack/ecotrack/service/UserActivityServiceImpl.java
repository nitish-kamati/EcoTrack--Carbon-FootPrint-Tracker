package com.ecotrack.ecotrack.service;

import com.ecotrack.ecotrack.model.ActivityType;
import com.ecotrack.ecotrack.model.UserActivity;
import com.ecotrack.ecotrack.repository.UserActivityRepository;
import org.springframework.stereotype.Service;
import com.ecotrack.ecotrack.exception.ResourceNotFoundException;


import java.time.LocalDate;
import java.util.List;

@Service
public class UserActivityServiceImpl implements UserActivityService {

    private final UserActivityRepository repository;

    public UserActivityServiceImpl(UserActivityRepository repository) {
        this.repository = repository;
    }

    // ================= SAVE ACTIVITY =================
    @Override
    public UserActivity saveActivity(UserActivity activity) {

        // ✅ UserId validation
        if (activity.getUserId() == null || activity.getUserId().isBlank()) {
            throw new IllegalArgumentException("UserId cannot be empty");
        }

        // ✅ Quantity validation
        if (activity.getQuantity() <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than 0");
        }

        // ✅ Activity type validation
        if (activity.getActivityType() == null) {
            throw new IllegalArgumentException("Activity type is required");
        }

        double emission;

        // ✅ Carbon calculation (ENUM BASED)
        switch (activity.getActivityType()) {
            case ELECTRICITY:
                emission = activity.getQuantity() * 0.82;
                break;

            case TRAVEL:
                emission = activity.getQuantity() * 0.21;
                break;

            case FOOD:
                emission = activity.getQuantity() * 2.5;
                break;

            default:
                throw new IllegalArgumentException("Invalid activity type");
        }

        activity.setCarbonEmission(emission);
        return repository.save(activity);
    }

    // ================= GET ALL =================
    @Override
    public List<UserActivity> getAllActivities() {
        return repository.findAll();
    }

    // ================= GET BY ID =================
    @Override
    public UserActivity getActivityById(String id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Activity not found with id: " + id));
    }

    // ================= DELETE =================
    @Override
    public void deleteActivityById(String id) {
        repository.deleteById(id);
    }

    // ================= UPDATE =================
    @Override
    public UserActivity updateActivity(String id, UserActivity updatedActivity) {

        UserActivity existingActivity = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Activity not found with id: " + id));

        existingActivity.setUserId(updatedActivity.getUserId());
        existingActivity.setActivityType(updatedActivity.getActivityType());
        existingActivity.setQuantity(updatedActivity.getQuantity());

        // 🔁 reuse save logic (BEST PRACTICE)
        return saveActivity(existingActivity);
    }

    // ================= TOTAL EMISSION =================
    @Override
    public double getTotalCarbonEmission(String userId) {

        List<UserActivity> activities = repository.findByUserId(userId);

        // ✅ ADD THIS CHECK
        if (activities.isEmpty()) {
            throw new ResourceNotFoundException(
                    "No activities found for userId: " + userId
            );
        }

        double total = 0.0;
        for (UserActivity activity : activities) {
            total += activity.getCarbonEmission();
        }


        return total;
    }

    @Override
    public double getTotalCarbonEmissionByDateRange(
            String userId,
            LocalDate from,
            LocalDate to
    ) {

        List<UserActivity> activities =
                repository.findByUserIdAndDateBetween(userId, from, to);

        double total = 0.0;
        for (UserActivity activity : activities) {
            total += activity.getCarbonEmission();
        }

        return total;
    }

    @Override
    public List<UserActivity> getActivitiesByUserId(String userId) {
        return repository.findByUserId(userId);
    }



}
