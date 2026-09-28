const API = "/api";
let currentUser = null;

async function request(url, options = {}) {
    const response = await fetch(API + url, {
        headers: {"Content-Type": "application/json"},
        ...options
    });

    const data = await response.json().catch(() => ({}));

    if (!response.ok) {
        throw new Error(data.message || Object.values(data)[0] || "Request failed");
    }
    return data;
}

async function register() {
    try {
        const data = await request("/auth/register", {
            method: "POST",
            body: JSON.stringify({
                name: regName.value,
                email: regEmail.value,
                password: regPassword.value,
                role: regRole.value
            })
        });
        alert("Registered successfully. User ID: " + data.id);
    } catch (e) {
        alert(e.message);
    }
}

async function login() {
    try {
        currentUser = await request("/auth/login", {
            method: "POST",
            body: JSON.stringify({
                email: loginEmail.value,
                password: loginPassword.value
            })
        });

        auth.classList.add("hidden");
        app.classList.remove("hidden");
        welcome.textContent =
            "Welcome, " + currentUser.name + " (" + currentUser.role + ")";

        if (currentUser.role === "STAFF" || currentUser.role === "ADMIN")
            staffBox.classList.remove("hidden");

        if (currentUser.role === "ADMIN") {
            adminBox.classList.remove("hidden");
            loadDashboard();
        }

        await loadCategories();
        await loadLost();
        await loadFound();
    } catch (e) {
        alert(e.message);
    }
}

function logout() {
    currentUser = null;
    app.classList.add("hidden");
    auth.classList.remove("hidden");
    location.reload();
}

async function loadCategories() {
    const categories = await request("/categories");

    lostCategory.innerHTML = "";
    foundCategory.innerHTML = "";

    categories.forEach(c => {
        const option1 = new Option(c.name, c.id);
        const option2 = new Option(c.name, c.id);
        lostCategory.add(option1);
        foundCategory.add(option2);
    });
}

async function reportLost() {
    try {
        await request("/lost", {
            method: "POST",
            body: JSON.stringify({
                description: lostDescription.value,
                location: lostLocation.value,
                lostDate: lostDate.value,
                categoryId: Number(lostCategory.value),
                userId: currentUser.id
            })
        });

        alert("Lost item reported successfully");
        await loadLost();
    } catch (e) {
        alert(e.message);
    }
}

async function reportFound() {
    try {
        await request("/found", {
            method: "POST",
            body: JSON.stringify({
                description: foundDescription.value,
                location: foundLocation.value,
                foundDate: foundDate.value,
                categoryId: Number(foundCategory.value),
                staffId: currentUser.id
            })
        });

        alert("Found item added successfully");
        await loadFound();
    } catch (e) {
        alert(e.message);
    }
}

async function loadLost() {
    try {
        const items = await request("/lost");
        lostList.innerHTML = items.map(x => `
            <div class="item">
                <b>ID:</b> ${x.id}<br>
                <b>Description:</b> ${x.description}<br>
                <b>Category:</b> ${x.category.name}<br>
                <b>Location:</b> ${x.location}<br>
                <b>Date:</b> ${x.lostDate}<br>
                <b>Status:</b> ${x.status}
                ${x.status === "OPEN" ? `<br><button onclick="findMatches(${x.id})">Find Matches</button>` : ""}
            </div>
        `).join("");
    } catch (e) {
        alert(e.message);
    }
}

async function loadFound() {
    try {
        const items = await request("/found");
        foundList.innerHTML = items.map(x => `
            <div class="item">
                <b>ID:</b> ${x.id}<br>
                <b>Description:</b> ${x.description}<br>
                <b>Category:</b> ${x.category.name}<br>
                <b>Location:</b> ${x.location}<br>
                <b>Date:</b> ${x.foundDate}<br>
                <b>Status:</b> ${x.status}
                ${canChangeStatus(x) ? `
                    <br>
                    <button onclick="changeStatus(${x.id}, 'CLAIMED')">Mark Claimed</button>
                    <button onclick="changeStatus(${x.id}, 'RETURNED')">Mark Returned</button>
                ` : ""}
            </div>
        `).join("");
    } catch (e) {
        alert(e.message);
    }
}

function canChangeStatus(item) {
    return currentUser.role === "ADMIN" ||
           (currentUser.role === "STAFF" && currentUser.id === item.reportedBy.id);
}

async function changeStatus(id, status) {
    try {
        await request("/found/" + id + "/status", {
            method: "PUT",
            body: JSON.stringify({
                status: status,
                userId: currentUser.id
            })
        });

        alert("Status updated");
        await loadFound();
    } catch (e) {
        alert(e.message);
    }
}

async function findMatches(lostId) {
    try {
        const matches = await request("/matches/" + lostId);

        if (matches.length === 0) {
            alert("No possible matches found");
            return;
        }

        alert(matches.map(x =>
            "Found ID: " + x.id +
            "\nDescription: " + x.description +
            "\nLocation: " + x.location +
            "\nStatus: " + x.status
        ).join("\n\n"));
    } catch (e) {
        alert(e.message);
    }
}

async function loadDashboard() {
    try {
        const d = await request("/admin/dashboard");
        dashboard.innerHTML = `
            <p><b>Total Users:</b> ${d.users}</p>
            <p><b>Pending:</b> ${d.pending}</p>
            <p><b>Matched:</b> ${d.matched}</p>
            <p><b>Returned:</b> ${d.returned}</p>
        `;
    } catch (e) {
        alert(e.message);
    }
}
