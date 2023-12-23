<%-- 
    Document   : group
    Created on : Dec 22, 2023, 1:37:18 PM
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

            /* Thêm kiểu dáng cho phần profile-container */
            .profile-container {
                display: flex;
                justify-content: space-between;
                align-items: center;
                margin-bottom: 20px;
            }

            /* Cập nhật kiểu dáng cho friend-list-button */
            .friend-list-button {
                background-color: #3498db;
                color: #fff;
                padding: 10px 15px;
                font-size: 16px;
                border: none;
                border-radius: 5px;
                cursor: pointer;
                transition: background-color 0.3s ease;
            }

            .friend-list-button:hover {
                background-color: #2980b9;
            }

            .group-buttons {
                display: flex;
                gap: 400px; /* Khoảng cách giữa các nút */
                align-items: center;
            }

            .create-group-button,
            .delete-group-button,
            .grant-admin-button {
                background-color: #3498db;
                color: #fff;
                padding: 10px 15px;
                font-size: 16px;
                border: none;
                border-radius: 5px;
                cursor: pointer;
                transition: background-color 0.3s ease;
            }

            .create-group-button:hover {
                background-color: green;
            }

            .delete-group-button:hover {
                background-color: red;
            }
   
            .grant-admin-button:hover {
                background-color: orange; /* Màu khi hover */
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
            <div class="profile-container">
                <div class="profile">
                    <img src="${avatar}" alt="Profile Picture">
                    <div class="profile-info">
                        <h2>${name}</h2>
                    </div>
                </div>   
            </div>

            <c:choose>
                <c:when test="${account}">
                    <div class="group-buttons">
                        <button class="create-group-button">
                            <a href="update-group?groupid=${groupId}">
                                Update Group
                            </a> 
                        </button>   
                        
                        <button class="grant-admin-button">
                            <a href="member-list?groupid=${groupId}">
                                Member List
                            </a>
                        </button>  
                        
                        <button class="delete-group-button">
                            <a href="delete-group?groupid=${groupId}">
                                Delete Group
                            </a> 
                        </button> 
                    </div> 
                    <div class="post-form">
                        <textarea id="postContent" placeholder="What's on your mind?" rows="4"></textarea>
                        <input type="file" id="fileInput" accept="image/*, video/*">
                        <button onclick="submitPost()">Post</button>
                    </div>
                </c:when>
                <c:otherwise>
                    <c:choose>
                        <c:when test="${admin && member}">
                            <form action="group?groupid=${groupId}" method="post" onsubmit="submitForm(event);">
                                <input type="hidden" name="action" value="joinGroup">
                                <input type="hidden" id="memberRequestId" name="memberRequestId" value="">
                                <button type="button" id="joinGroupButton" style="background-color: red; color: white;" onclick="toggleMemberRequest(event, '${groupId}', 'leaveGroup')">Leave Group</button>
                            </form>
                        </c:when>
                        <c:when test="${member && !admin}">
                            <form action="group?groupid=${groupId}" method="post" onsubmit="submitForm(event);">
                                <input type="hidden" name="action" value="joinGroup">
                                <input type="hidden" id="memberRequestId" name="memberRequestId" value="">
                                <button type="button" id="joinGroupButton" style="background-color: red; color: white;" onclick="toggleMemberRequest(event, '${groupId}', 'leaveGroup')">Leave Group</button>
                            </form>
                            <div class="post-form">
                                <textarea id="postContent" placeholder="What's on your mind?" rows="4"></textarea>
                                <input type="file" id="fileInput" accept="image/*, video/*">
                                <button onclick="submitPost()">Post</button>
                            </div>
                        </c:when>
                        <c:when test="${tuesday && !member}">
                            <form action="group?groupid=${groupId}" method="post" onsubmit="submitForm(event);">
                                <input type="hidden" name="action" value="joinGroup">
                                <input type="hidden" id="memberRequestId" name="memberRequestId" value="">
                                <button type="button" id="joinGroupButton" style="background-color: #4267b2; color: white;" onclick="toggleMemberRequest(event, '${groupId}', 'joinGroup')">Join Group</button>
                            </form>
                        </c:when>
                    </c:choose>
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
                        <c:choose>
                            <c:when test="${!account}">
                                function submitForm(event) {
                                    event.preventDefault();
                                    
                                    document.forms[1].submit();
                                }
                                let memberRequestSent = ${member};
                                let memberRequestIdInput = document.getElementById('memberRequestId');
                                let joinButton = document.getElementById('joinGroupButton');
                                
                                function toggleMemberRequest(event, groupId, action) {
                                    event.preventDefault();
                                    event.stopPropagation();
                                    
                                    $.ajax({
                                        type: "POST",
                                        url: "group",
                                        data: { action: action, memberRequestId: action, groupid: groupId},
                                        success: function (response) {
                                            console.log(response);
                                            
                                            refreshPage();
                                        },
                                        error: function(error) {
                                            console.error("Error: ", error);
                                        }   
                                    });
                                    
                                    if(memberRequestSent) {
                                        joinButton.innerText = 'Leave Group';
                                        joinButton.style.backgroundColor = 'red';
                                        memberRequestIdInput.value = 'leaveGroup';
                                    } else {
                                        joinButton.innerText = 'Join Group';
                                        joinButton.style.backgroundColor = '#4267b2';
                                        memberRequestIdInput.value = 'joinGroup';
                                    }
                                    
                                    memberRequestSent = !memberRequestSent;
                                    
                                }
                            </c:when>
                        </c:choose>
                        
                        
                        function submitPost() {
                            const postContent = document.getElementById('postContent').value;
                            if (postContent.trim() !== '') {
                                const postContainer = document.getElementById('postsContainer');
                                const newPost = document.createElement('div');
                                newPost.className = 'post';
                                newPost.innerHTML = "<h3>User Name</h3><p>" + postContent + "</p>";
                                console.log(newPost);
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
                        };
                    </script>

    </body>
</html>
