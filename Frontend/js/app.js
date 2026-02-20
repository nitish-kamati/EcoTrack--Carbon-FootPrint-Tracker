console.log("app.js loaded");

// ---------- ADD ACTIVITY ----------
const activityForm = document.getElementById("activityForm");

if (activityForm) {
    activityForm.addEventListener("submit", function (e) {
        e.preventDefault();

        const activity = {
            userId: document.getElementById("userId").value,
            activityType: document.getElementById("activityType").value,
            quantity: Number(document.getElementById("quantity").value)
        };

        saveActivity(activity)
            .then(res => {
                if (!res.ok) {
                    return res.json().then(err => {
                        throw new Error(err.error);
                    });
                }
                return res.json();
            })
            .then(() => {
                document.getElementById("message").innerText =
                    "Activity saved successfully ✅";
                activityForm.reset();
            })
            .catch(err => {
                document.getElementById("message").innerText =
                    "Error: " + err.message;
            });
    });
}

// ---------- LOAD ACTIVITIES ----------
document.addEventListener("DOMContentLoaded", () => {
    loadAllActivities();
    loadTotalEmission("u1"); // default user
});

// 🔹 Common function to render table
function renderTable(activities) {
    const tableBody = document.querySelector("#activitiesTable tbody");
    if (!tableBody) return;

    tableBody.innerHTML = "";

    activities.forEach(activity => {
        const row = document.createElement("tr");

        row.innerHTML = `
            <td>${activity.userId}</td>
            <td>${activity.activityType}</td>
            <td>${activity.carbonEmission}</td>
            <td>
                <button onclick="deleteActivity('${activity.id}')">
                    ❌ Delete
                </button>
            </td>
        `;

        tableBody.appendChild(row);
    });
}

// 🔹 Load all
function loadAllActivities() {
    getAllActivities()
        .then(activities => {
            renderTable(activities);
        })
        .catch(err => console.error(err));
}

// 🔹 Load total
function loadTotalEmission(userId) {
    const totalEl = document.getElementById("totalEmission");
    if (!totalEl) return;

    getTotalEmission(userId)
        .then(data => {
            totalEl.innerText =
                `Total Carbon Emission: ${data.totalCarbonEmission} ${data.unit}`;
        })
        .catch(error => {
            totalEl.innerText = "Error loading total emission";
            console.error(error);
        });
}

// 🔎 Filter dashboard by user
function filterByUser() {

    const userId = document.getElementById("searchUserId").value;

    if (!userId) {
        alert("Please enter a User ID");
        return;
    }

    fetch(`http://localhost:8080/activities?userId=${userId}`)
        .then(res => res.json())
        .then(json => {

            renderTable(json.data);
            loadTotalEmission(userId);

        })
        .catch(err => {
            console.error(err);
        });
}

// ---------- DELETE ----------
function deleteActivity(id) {

    if (!confirm("Are you sure you want to delete this activity?"))
        return;

    fetch(`${BASE_URL}/${id}`, {
        method: "DELETE"
    })
    .then(response => {
        if (!response.ok) {
            throw new Error("Failed to delete activity");
        }
        return response.text();
    })
    .then(() => {
        alert("Activity deleted successfully");
        loadAllActivities(); // reload table without full refresh
    })
    .catch(error => {
        alert("Error deleting activity");
        console.error(error);
    });
}
