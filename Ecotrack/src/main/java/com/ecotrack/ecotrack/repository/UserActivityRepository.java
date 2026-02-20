package com.ecotrack.ecotrack.repository;

import com.ecotrack.ecotrack.model.UserActivity;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.time.LocalDate;
import java.util.List;


public interface UserActivityRepository extends MongoRepository<UserActivity, String> {

    List<UserActivity> findByUserId(String userId);

    List<UserActivity> findByUserIdAndDateBetween(
            String userId,
            LocalDate from,
            LocalDate to
    );
}
