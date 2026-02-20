package com.ecotrack.ecotrack.controller;

import com.ecotrack.ecotrack.model.UserActivity;
import com.ecotrack.ecotrack.service.UserActivityService;
import org.springframework.web.bind.annotation.*;
import com.ecotrack.ecotrack.dto.TotalEmissionResponse;
import com.ecotrack.ecotrack.dto.ApiResponse;


import java.time.LocalDate;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/activities")
public class UserActivityController {

    private final UserActivityService service;

    public UserActivityController(UserActivityService service) {
        this.service = service;
    }

    // ✅ TEST API
    @GetMapping("/test")
    public String test() {
        return "UserActivity Controller Working";
    }

    // ✅ POST: Save activity
    @PostMapping
    public ApiResponse<UserActivity> save(@RequestBody UserActivity activity) {

        UserActivity saved = service.saveActivity(activity);

        return new ApiResponse<>(
                true,
                "Activity saved successfully",
                saved
        );
    }

    // ✅ GET: Fetch all OR by userId
    @GetMapping
    public ApiResponse<List<UserActivity>> getActivities(
            @RequestParam(required = false) String userId) {

        List<UserActivity> activities;

        if (userId != null && !userId.isBlank()) {
            activities = service.getActivitiesByUserId(userId);
        } else {
            activities = service.getAllActivities();
        }

        return new ApiResponse<>(
                true,
                "Activities fetched successfully",
                activities
        );
    }



    @GetMapping("/by-id/{id}")
    public UserActivity getById(@PathVariable String id) {
        return service.getActivityById(id);
    }

    @DeleteMapping("/{id}")
    public String deleteActivity(@PathVariable String id) {
        service.deleteActivityById(id);
        return "Activity deleted successfully";
    }

    @PutMapping("/{id}")
    public UserActivity updateActivity(
            @PathVariable String id,
            @RequestBody UserActivity updatedActivity) {

        return service.updateActivity(id, updatedActivity);
    }

    @GetMapping("/total/{userId}")
    public ApiResponse<TotalEmissionResponse> getTotalCarbonEmission(
            @PathVariable String userId) {

        double total = service.getTotalCarbonEmission(userId);

        TotalEmissionResponse response =
                new TotalEmissionResponse(userId, total, "kg CO2");

        return new ApiResponse<>(
                true,
                "Total carbon emission calculated",
                response
        );
    }

    @GetMapping("/total/{userId}/range")
    public ApiResponse<TotalEmissionResponse> getTotalEmissionByDateRange(
            @PathVariable String userId,
            @RequestParam String from,
            @RequestParam String to
    ) {

        LocalDate fromDate = LocalDate.parse(from);
        LocalDate toDate = LocalDate.parse(to);

        double total = service.getTotalCarbonEmissionByDateRange(
                userId,
                fromDate,
                toDate
        );

        return ApiResponse.success(
                new TotalEmissionResponse(userId, total, "kg CO2")
        );
    }

}
