// Load users
function loadAndDisplayUsers() {
    const connectedUser = localStorage.getItem('connectedUser');
    if (!connectedUser) { window.location = 'login.html'; return; }

    const userListElement = document.getElementById("userList");
    userListElement.innerHTML = "Loading...";

    fetch('http://localhost:8080/api/v1/users/getAll')
        .then(res => res.json())
        .then(data => displayUsers(data, userListElement))
        .catch(err => console.error(err));
}

function displayUsers(userList, userListElement) {
    userListElement.innerHTML = "";
    userList.forEach(user => {
        const listItem = document.createElement("li");
        listItem.innerHTML = `
            <div>
                <i class="fa fa-user-circle"></i>
                ${user.username} <i class="user-email">(${user.email})</i>
            </div>
            <i class="fa fa-lightbulb-o ${user.status === "online" ? "online" : "offline"}"></i>
        `;
        userListElement.appendChild(listItem);
    });
}

// Logout
function handleLogout() {
    fetch('http://localhost:8080/api/v1/users/logout', {
        method: 'POST',
        headers: {'Content-Type': 'application/json'},
        body: localStorage.getItem('connectedUser')
    })
    .finally(() => {
        localStorage.removeItem('connectedUser');
        window.location.href = "login.html";
    });
}

// New Meeting
function handleNewMeeting() {
    const connectedUser = JSON.parse(localStorage.getItem('connectedUser'));
    window.open(`meet.html?action=create&username=${connectedUser.username}`, "_blank");
}

// Join Meeting
function handleJoinMeeting() {
    const roomId = document.getElementById("meetingName").value.trim();
    if (!roomId) { alert("Enter Meeting ID"); return; }

    const connectedUser = JSON.parse(localStorage.getItem('connectedUser'));
    const url = `meet.html?action=join&roomId=${roomId}&username=${connectedUser.username}`;
    window.open(url, "_blank");
}

// Event listeners
window.addEventListener("load", loadAndDisplayUsers);
document.getElementById("logoutBtn").addEventListener("click", handleLogout);
document.getElementById("newMeetingBtn").addEventListener("click", handleNewMeeting);
document.getElementById("joinMeetingBtn").addEventListener("click", handleJoinMeeting);


//function loadAndDisplayUsers() {
//
//    // check if the user is connected
//    const connectedUser = localStorage.getItem('connectedUser');
//    if (!connectedUser) {
//        window.location = 'login.html';
//        return;
//    }
//    const userListElement = document.getElementById("userList");
//    // Clear any existing content in the userListElement
//    userListElement.innerHTML = "Loading...";
//    // Retrieve the userList from Local Storage
//    fetch('http://localhost:8080/api/v1/users/getAll')
//        .then((response) => {
//            return response.json();
//        })
//        .then((data) => {
//            console.log(data);
//            displayUsers(data, userListElement);
//        });
//}
//
//function displayUsers(userList, userListElement) {
//    userListElement.innerHTML = "";
//
//    // Loop through the userList and create list items to display each user
//    userList.forEach(user => {
//        const listItem = document.createElement("li");
//        listItem.innerHTML = `
//                <div>
//                    <i class="fa fa-user-circle"></i>
//                    ${user.username} <i class="user-email">(${user.email})</i>
//                </div>
//                <i class="fa fa-lightbulb-o ${user.status === "online" ? "online" : "offline"}"></i>
//            `;
//        userListElement.appendChild(listItem);
//    });
//}
//
//// Call the loadAndDisplayUsers function when the page loads
//window.addEventListener("load", loadAndDisplayUsers);
//
//
//
//function handleLogout() {
//    fetch('http://localhost:8080/api/v1/users/logout', {
//        method: 'POST',
//        headers: {
//            'Content-Type': 'application/json'
//        },
//        body: localStorage.getItem('connectedUser')
//    })
//        .then((response) => {
//            return response;
//        })
//        .then((data) => {
//            localStorage.removeItem('connectedUser');
//            window.location.href = "login.html";
//        });
//}
//
//const logoutBtn = document.getElementById("logoutBtn");
//logoutBtn.addEventListener("click", handleLogout);
//
//
//function handleNewMeeting() {
//    const connectedUser = JSON.parse(localStorage.getItem('connectedUser'));
//    window.open(`meet.html?action=create&username=${connectedUser.username}`, "_blank");
//}
//
//function handleJoinMeeting() {
//    const roomId = document.getElementById("meetingName").value;
//    if (!roomId) {
//        alert("Please enter a Meeting ID");
//        return;
//    }
//    const connectedUser = JSON.parse(localStorage.getItem('connectedUser'));
//    window.open(`meet.html?action=join&roomId=${roomId}&username=${connectedUser.username}`, "_blank");
//}
//
//
//const joinMeetingBtn = document.getElementById("joinMeetingBtn");
//joinMeetingBtn.addEventListener("click", handleJoinMeeting);