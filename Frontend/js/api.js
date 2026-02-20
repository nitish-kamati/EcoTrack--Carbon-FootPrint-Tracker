const BASE_URL = "http://localhost:8080/activities";

// ---------- SAVE ACTIVITY ----------
function saveActivity(activity) {
    return fetch(BASE_URL, {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(activity)
    });
}

// ---------- GET ALL ACTIVITIES ----------
function getAllActivities() {
    return fetch(BASE_URL)
        .then(response => {
            if (!response.ok) {
                throw new Error("Failed to fetch activities");
            }
            return response.json();
        })
        .then(apiResponse => apiResponse.data); // ✅ unwrap ApiResponse
}

// ---------- GET TOTAL EMISSION ----------
function getTotalEmission(userId) {
    return fetch(`${BASE_URL}/total/${userId}`)
        .then(response => {
            if (!response.ok) {
                throw new Error("Failed to fetch total emission");
            }
            return response.json();
        })
        .then(apiResponse => apiResponse.data); // ✅ unwrap ApiResponse
}

// DELETE API
function deleteActivityById(id) {
    return fetch(`${BASE_URL}/${id}`, {
        method: "DELETE"
    }).then(response => {
        if (!response.ok) {
            throw new Error("Failed to delete activity");
        }
        return response.text();
    });
}

