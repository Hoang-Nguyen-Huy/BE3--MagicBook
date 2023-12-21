<%-- 
    Document   : profile
    Created on : Dec 18, 2023, 1:46:10 PM
    Author     : Dell Latitude 7490
--%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>My Profile</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f4f4f4;
            margin: 0;
            padding: 0;
        }

        header {
            background-color: #4267b2;
                color: white;
                padding: 10px;
                display: flex;
                position: fixed;
                width: 100%;
                justify-content: space-between;
                align-items: center;
                box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
                z-index: 100; 
        }

        main {  
                display: flex;
                flex-direction: column;
                align-items: center;
                padding: 20px;
                height: 100vh; /* Để main chiếm toàn bộ chiều cao của trang */
                position: relative;
                top: 70px;
            }

        .nav-buttons,
        .search-bar,
        .post-form {
            display: flex;
            align-items: center;
        }

        nav button,
            .post-form button {
                background-color: #4267b2;
                color: white;
                padding: 10px;
                border: none;
                border-radius: 4px;
                cursor: pointer;
                margin-right: 10px;
                transition: background-color 0.3s ease;
        }

        nav button:hover,
            .post-form button:hover {
                background-color: #345291;
            }

            .search-bar input,
            .post-form textarea {
                padding: 10px;
                margin-right: 10px;
                border-radius: 4px;
                border: 1px solid #ccc;
                transition: border-color 0.3s ease;
            }

            .search-bar input:focus,
            .post-form textarea:focus {
                border-color: #4267b2;
            }

        .profile {
            display: flex;
            justify-content: space-around;
            align-items: center;
        }

        .profile img {
            border-radius: 50%;
            width: 150px;
            height: 150px;
            object-fit: cover;
        }

        .profile-info {
            max-width: 400px;
        }

        #postsContainer {
                width: 60%;
                position: relative;
                display: flex;
                flex-direction: column;
            }

            .post {
                background-color: white;
                padding: 20px;
                margin-bottom: 20px;
                border-radius: 8px;
                box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            }

            .post-form {
                width: 30%;
                display: flex;
                flex-direction: column;
                align-items: flex-start;
            }

            .post-form textarea {
                width: 100%;
                margin-bottom: 10px;
            }

            .post-form button {
                align-self: flex-end;
            }

            .side-buttons {
                width: 20%;
                display: flex;
                flex-direction: column;
                align-items: center;
            }

            .side-buttons button {
                margin-bottom: 10px;
                display: flex;
                align-items: center;
            }

            .side-buttons button img {
                width: 20px; /* Kích thước mong muốn cho ảnh */
                height: 20px; /* Kích thước mong muốn cho ảnh */
                margin-right: 8px;
            }

            .dropbtn {
                background-color: #3498db;
                color: white;
                width: 40px;
                height: 40px;
                border-radius: 50%;
                cursor: pointer;
                display: flex;
                align-items: center;
                justify-content: center;
                border: none; /* Thêm dòng này để loại bỏ đường viền */
                margin-right: 20px;
            }

            .dropbtn img {
                width: 20px;
                height: 20px;
            }


            .dropbtn:hover,
            .dropbtn:focus {
                background-color: #2980b9;
            }

            .dropdown {
                position: relative;
                display: inline-block;
            }

            .dropdown-content {
                display: none;
                position: absolute;
                background-color: #4267b2;
                min-width: 160px;
                box-shadow: 0px 8px 16px 0px rgba(0, 0, 0, 0.2);
                z-index: 1;
                left: -200%;
            }

            .dropdown-content a {
                color: black;
                padding: 12px 16px;
                text-decoration: none;
                display: block;
            }

            .dropdown-content a:hover {
                background-color: #ddd;
            }

            .show {
                display: block;
            }
    </style>
</head>
<body>

    <header class="navbar">
        <div class="logo" onclick="refreshPage()">MagicBook</div>
        <div class="search-bar">
            <form action="search" method="get">
                <input type="text" name="u" placeholder="Search...">
                <input type="submit" value="Search">
            </form>
        </div>

        <div class="nav-buttons">
            <button onclick="refreshPage()">
                <a href="home">
                    <img src="DefaultImg\home-icon.png" width="20px" height="20px" alt="Home">
                </a>
            </button>
            <button onclick="openMessages()">
                <a href="https://www.facebook.com/">
                    <img src="DefaultImg\message-icon.png" width="20px" height="20px" alt="Messages">
                </a>
            </button>
            <button onclick="openNotifications()"><img src="DefaultImg\notification-icon.png" width="20px" height="20px" alt="Notifications"></button>
        </div>

        <div class="dropdown" >
            <button   class="dropbtn" onclick="myFunction()">
                <img class="dropimg" src="${accountAvatar}" width="20px" height="20px" alt="Avatar">
            </button>
            <div id="myDropdown" class="dropdown-content">
                <a href="profile?id=${accountId}"><img src="${accountAvatar}" width="20px" height="20px"> ${accountName}</a>
                <a href="update-profile">Update profile</a>
                <a href="logout">Log out</a>
            </div>
        </div>
    </header>

    <main>
        <div class="profile">
            <img src="${avatar}" alt="Profile Picture">
            <div class="profile-info">
                <h2>${userName}</h2>
                <p>Gender: ${gender}</p>
                <p>Location: ${country}</p>
            </div>
        </div>
          
        <c:choose>
            <c:when test="${account}">
                <div class="post-form">
                    <textarea id="postContent" placeholder="What's on your mind?" rows="4"></textarea>
                    <input type="file" id="fileInput" accept="image/*, video/*">
                    <button onclick="submitPost()">Post</button>
                </div>
            </c:when>
            <c:otherwise>
                <form action="profile?id=${userId}" method="post" onsubmit="submitForm(event);">
                    <input type="hidden" name="action" value="addFriend">
                    <input type="hidden" id="friendRequestId" name="friendRequestId" value="">
                    <button type="button" id="addFriendButton" style="background-color: #4267b2; color: white;" onclick="toggleFriendRequest(event, '${userId}')">Add Friend</button>
                </form>
            </c:otherwise>
        </c:choose>
       
        <div id="postsContainer">
            <h2>Recent Posts</h2>
            <div class="post">
                <h3>User Name</h3>
                <p>This is a sample post....</p>
            <div>
            <!-- Additional posts go here -->
        </div>
    </main>

    <script>
        function submitForm(event) {
            // Xử lý logic của bạn ở đây

            // Ngăn chặn hành vi mặc định của form (chuyển hướng)
            event.preventDefault();

            // Gọi hàm submit của form
            document.forms[1].submit();
        }
        let friendRequestSent = false; // Giá trị mặc định: không có yêu cầu bạn bè
        let friendRequestIdInput = document.getElementById('friendRequestId');
        let addButton = document.getElementById('addFriendButton');

        // Lấy giá trị từ localStorage khi trang được tải
        document.addEventListener("DOMContentLoaded", function () {
            let storedFriendRequestSent = localStorage.getItem('friendRequestSent');
            let storedFriendRequestId = localStorage.getItem('friendRequestId');

            if (storedFriendRequestSent && storedFriendRequestId) {
                friendRequestSent = JSON.parse(storedFriendRequestSent);
                friendRequestIdInput.value = storedFriendRequestId;

                // Cập nhật giao diện tùy thuộc vào trạng thái lưu trữ
                if (friendRequestSent) {
                    addButton.innerText = 'Cancel Request';
                    addButton.style.backgroundColor = 'red';
                } else {
                    addButton.innerText = 'Add Friend';
                    addButton.style.backgroundColor = '#4267b2';
                }
            }
        });

        function toggleFriendRequest(event, userId) {
            event.preventDefault();
            event.stopPropagation();

            var friendRequestId = friendRequestIdInput.value;

            if (!friendRequestId) {
                friendRequestId = 'addFriend'; // Thiết lập giá trị mặc định khi không có giá trị
            }

            $.ajax({
                type: "POST",
                url: "profile", // Đổi URL thành servlet hoặc controller chính xác
                data: { action: friendRequestId, friendRequestId: friendRequestId, id: userId},
                success: function (response) {
                    console.log(response);
                    // Cập nhật giao diện người dùng tại đây nếu cần
                },
                error: function (error) {
                    console.error("Error:", error);
                }
            });

            if (friendRequestSent) {
                addButton.innerText = 'Add Friend';
                addButton.style.backgroundColor = '#4267b2';
                friendRequestIdInput.value = 'addFriend';
            } else {
                addButton.innerText = 'Cancel Request';
                addButton.style.backgroundColor = 'red';
                friendRequestIdInput.value = 'cancelRequest';
            }

            friendRequestSent = !friendRequestSent;

            // Lưu trạng thái và action vào localStorage
            localStorage.setItem('friendRequestSent', JSON.stringify(friendRequestSent));
            localStorage.setItem('friendRequestId', friendRequestIdInput.value);
        }


        function submitPost() {
            const postContent = document.getElementById('postContent').value;
            if (postContent.trim() !== '') {
                const postContainer = document.getElementById('postsContainer');
                const newPost = document.createElement('div');
                newPost.className = 'post';
                newPost.innerHTML = "<h3>User Name</h3><p>"+postContent+"</p>";
                console.log(newPost)
                postContainer.append(newPost);
                document.getElementById('postContent').value = '';
            }
        }


        function openMessages() {
            alert("Redirect to Messages Page");
        }

        function openNotifications() {
            alert("Redirect to Notifications Page");
        }

        function refreshPage() {
            location.reload();
        }

        /* When the user clicks on the button,
         toggle between hiding and showing the dropdown content */
        function myFunction() {
            document.getElementById("myDropdown").classList.toggle("show");
        }

        // Close the dropdown menu if the user clicks outside of it
        window.onclick = function (event) {
            if (!event.target.matches('.dropbtn') && !event.target.matches('.dropimg')) {
                var dropdowns = document.getElementsByClassName("dropdown-content");
                var i;
                for (i = 0; i < dropdowns.length; i++) {
                    var openDropdown = dropdowns[i];
                    if (openDropdown.classList.contains('show')) {
                        openDropdown.classList.remove('show');
                    }
                }
            }
        }
    </script>

</body>
</html>