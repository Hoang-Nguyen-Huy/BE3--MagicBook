<%-- 
    Document   : group
    Created on : Dec 22, 2023, 1:37:18 PM
    Author     : Dell Latitude 7490
--%>

<%@ page import="com.model.dao.GroupDAO" %>
<%@ page import="com.model.dao.FriendshipDAO" %>
<%@ page import="com.model.dao.LikeDAO" %>
<%@ page import="com.model.dao.CommentDAO" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
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
                width: 40%;
                margin: auto;
                background-color: white;
                padding: 20px;
                border-radius: 8px;
                box-shadow: 0 0 20px rgba(0, 0, 0, 0.1);
                position: relative; /* Để có thể định vị phần info */
                display: flex;
                flex-direction: column;
                align-items: flex-start;
            }

            .post-avatar {
                display: flex;
                align-items: flex-start;
                position: relative;
            }
            
            .avatar-wrapper {
                display: flex;
                justify-content: space-between;
                align-items: center;
                margin-right: 10px; /* Tùy chỉnh khoảng cách giữa avatar và nút */
            }
            
            .post-actions {
                position: relative;
            }
            
            .edit-post-btn,
            .delete-post-btn {
                background-color: #4267b2;
                color: white;
                padding: 6px;
                font-size: 12px;
                border: none;
                border-radius: 4px;
                cursor: pointer;
                margin-right: 5px;
                transition: background-color 0.3s ease;
            }
            
            .edit-post-btn:hover,
            .delete-post-btn:hover {
                background-color: #345291;
            }
            
            .post-info {
                position: absolute;
                top: 0;
                right: 0;
                text-align: right;
            }
            
            .post-header {
                width: 100%;
                display: flex;
                justify-content: space-between;
                align-items: center;
                margin-bottom: 10px;
            }
            
            .post-info {
                text-align: right;
            }

            .post-date, .post-time, .post-visibility {
                font-size: 12px;
                color: #888;
                margin: 0;
            }
            
            .post-form {
                width: 100%; /* Tăng độ rộng của post form */
                margin: auto; /* Để căn giữa post form */
                background-color: #fff; /* Màu nền của post form */
                padding: 20px; /* Khoảng cách giữa các phần tử trong post form */
                border-radius: 8px; /* Bo tròn góc */
                box-shadow: 0 0 20px rgba(0, 0, 0, 0.1); /* Đổ bóng */
                position: relative;
            }

            .post-form label {
                display: flex;
                align-items: center;
                margin-bottom: 10px;
            }
            
            .post-form textarea {
                width: 100%;
                margin-bottom: 10px;
                padding: 10px;
                resize: vertical;
                border: 1px solid #ccc;
                border-radius: 4px;
                transition: border-color 0.3s ease;
            }
            
            .post-form input[type="file"] {
                margin-bottom: 10px;
            }

            .post-form button {
                background-color: #4267b2;
                color: white;
                padding: 12px;
                font-size: 16px;
                border: none;
                border-radius: 4px;
                cursor: pointer;
                transition: background-color 0.3s ease;
                position: absolute; /* Đặt vị trí tuyệt đối */
                bottom: 10px; /* Duyệt phần dưới cùng của post form */
                right: 10px; /* Đặt nút "Post" về phía bên phải */
            }
            
            .post-form button:hover {
                background-color: #345291;
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
                    </div> 
                    <form action="group?groupid=${groupId}" method="post" enctype="multipart/form-data">
                        <div class="post-form">
                            <textarea id="postContent" name="postContent" placeholder="What's on your mind?" rows="6"></textarea>
                            <input type="file" id="fileInput" name="fileInput" accept="image/*, video/*">

                            <button type="submit">Post</button>
                        </div>
                    </form>
                    <div id="postsContainer">
                        <h2>Recent Posts</h2>
                        <c:forEach var="entry" items="${post}">
                            <div class="post">
                                    <div class="post-avatar">
                                        <div class="avatar-wrapper">
                                            <img src="${entry.key.getAvatar()}" width="35" height="40" alt="Avatar">
                                            <!-- Thêm nút Edit và Delete -->
                                            <c:if test="${accountId eq entry.key.getUserId()}">
                                                <div class="post-actions"> 
                                                    <a href="update-post?postid=${entry.value.getPostId()}">Edit</a>
                                                </div>         
                                            </c:if> 
                                            <div class="post-actions">
                                                <form action="group?groupid=${groupId}" method="post">                                                                          
                                                    <button class="delete-post-btn" onclick="deletePost('${entry.value.getPostId()}')" type="button">Delete</button>
                                                </form>
                                            </div>                                                          
                                        </div>
                                    </div>

                                   <div class="post-header">
                                        <h3>
                                            <a href="profile?id=${entry.key.getUserId()}">
                                                <c:out value="${entry.key.getFirstName()} ${entry.key.getLastName()}"/>
                                            </a>
                                        </h3>

                                         <!-- Hiển thị thông tin ngày và giờ bài đăng -->
                                        <div class="post-info">
                                            <p class="post-date">${entry.value.getPostDate()}</p>
                                            <p class="post-time">${entry.value.getPostTime()}</p>
                                            <p class="post-visibility">${GroupDAO.getInstance().selectNameById(groupId)}</p>
                                        </div>
                                    </div>

                                    <p><c:out value="${entry.value.getContent()}"/></p>

                                    <c:choose>
                                        <c:when test="${entry.value.getFile() != null}">
                                            <c:set var="fileName" value="${entry.value.getFile()}" />
                                            <c:set var="lowercaseFileName" value="${fn:toLowerCase(fileName)}" />

                                            <c:choose>
                                                <c:when test="${lowercaseFileName.endsWith('.mp4') or lowercaseFileName.endsWith('.mp3') or lowercaseFileName.endsWith('.mov')}">
                                                    <!-- Nếu là video -->
                                                    <video width="320" height="400" controls>
                                                        <source src="${entry.value.getFile()}" type="video/${lowercaseFileName.substring(lowercaseFileName.lastIndexOf('.') + 1)}">
                                                        Your browser does not support the video tag.
                                                    </video>
                                                </c:when>
                                                <c:otherwise>
                                                    <!-- Nếu là hình ảnh -->
                                                    <img src="${entry.value.getFile()}" alt="Image Description" width="320" height="400">
                                                </c:otherwise>
                                            </c:choose>
                                        </c:when>
                                    </c:choose>
                                    <div class="post-actions">
                                        <!-- Nút Like -->
                                        <button class="like-btn" onclick="likePost('${entry.value.getPostId()}')">
                                            Like <span class="like-count"><c:out value="${LikeDAO.getInstance().countLike(entry.value.getPostId())}" /></span>
                                        </button>

                                        <!-- Nút Comment -->
                                        <a class="comment-btn" href="comment-post?postid=${entry.value.getPostId()}">
                                            Comment <span class="comment-count"><c:out value="${CommentDAO.getInstance().countComment(entry.value.getPostId())}" /></span>
                                        </a>

                                    </div>
                                </div>
                                        <hr>    
                        </c:forEach>
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
                            <button class="grant-admin-button">
                                <a href="member-list?groupid=${groupId}">
                                    Member List
                                </a>
                            </button> 
                            <form action="group?groupid=${groupId}" method="post" enctype="multipart/form-data">
                                <div class="post-form">
                                    <textarea id="postContent" name="postContent" placeholder="What's on your mind?" rows="6"></textarea>
                                    <input type="file" id="fileInput" name="fileInput" accept="image/*, video/*">

                                    <button type="submit">Post</button>
                                </div>
                            </form>
                            <div id="postsContainer">
                                <h2>Recent Posts</h2>
                                <c:forEach var="entry" items="${post}">
                                    <div class="post">
                                            <div class="post-avatar">
                                                <div class="avatar-wrapper">
                                                    <img src="${entry.key.getAvatar()}" width="35" height="40" alt="Avatar">
                                                    <!-- Thêm nút Edit và Delete -->
                                                    <c:if test="${accountId eq entry.key.getUserId()}">
                                                        <div class="post-actions">
                                                            <form action="group?groupid=${groupId}" method="post">     
                                                                <a href="update-post?postid=${entry.value.getPostId()}">Edit</a>                                                                     
                                                                <button class="delete-post-btn" onclick="deletePost('${entry.value.getPostId()}')" type="button">Delete</button>
                                                            </form>
                                                        </div>         
                                                    </c:if>                                                                             
                                                </div>
                                            </div>

                                           <div class="post-header">
                                                <h3>
                                                    <a href="profile?id=${entry.key.getUserId()}">
                                                        <c:out value="${entry.key.getFirstName()} ${entry.key.getLastName()}"/>
                                                    </a>
                                                </h3>

                                                 <!-- Hiển thị thông tin ngày và giờ bài đăng -->
                                                <div class="post-info">
                                                    <p class="post-date">${entry.value.getPostDate()}</p>
                                                    <p class="post-time">${entry.value.getPostTime()}</p>
                                                    <p class="post-visibility">${GroupDAO.getInstance().selectNameById(groupId)}</p>
                                                </div>
                                            </div>

                                            <p><c:out value="${entry.value.getContent()}"/></p>

                                            <c:choose>
                                                <c:when test="${entry.value.getFile() != null}">
                                                    <c:set var="fileName" value="${entry.value.getFile()}" />
                                                    <c:set var="lowercaseFileName" value="${fn:toLowerCase(fileName)}" />

                                                    <c:choose>
                                                        <c:when test="${lowercaseFileName.endsWith('.mp4') or lowercaseFileName.endsWith('.mp3') or lowercaseFileName.endsWith('.mov')}">
                                                            <!-- Nếu là video -->
                                                            <video width="320" height="400" controls>
                                                                <source src="${entry.value.getFile()}" type="video/${lowercaseFileName.substring(lowercaseFileName.lastIndexOf('.') + 1)}">
                                                                Your browser does not support the video tag.
                                                            </video>
                                                        </c:when>
                                                        <c:otherwise>
                                                            <!-- Nếu là hình ảnh -->
                                                            <img src="${entry.value.getFile()}" alt="Image Description" width="320" height="400">
                                                        </c:otherwise>
                                                    </c:choose>
                                                </c:when>
                                            </c:choose>
                                            <div class="post-actions">
                                                <!-- Nút Like -->
                                                <button class="like-btn" onclick="likePost('${entry.value.getPostId()}')">
                                                    Like <span class="like-count"><c:out value="${LikeDAO.getInstance().countLike(entry.value.getPostId())}" /></span>
                                                </button>

                                                <!-- Nút Comment -->
                                                <a class="comment-btn" href="comment-post?postid=${entry.value.getPostId()}">
                                                    Comment <span class="comment-count"><c:out value="${CommentDAO.getInstance().countComment(entry.value.getPostId())}" /></span>
                                                </a>

                                            </div>
                                        </div>
                                                <hr>    
                                </c:forEach>
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

        </main>

                    <script>
                         // Hàm để xử lý sự kiện khi click nút Like
                        function likePost(postId) {
                            var xhr = new XMLHttpRequest();
                            xhr.open("POST", "home", true);
                            xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");

                            // Tạo dữ liệu cần gửi đi
                            var data = "likedpostId=" + encodeURIComponent(postId);

                            xhr.onreadystatechange = function () {
                                if (xhr.readyState == 4 && xhr.status == 200) {
                                    // Xử lý phản hồi từ máy chủ nếu cần
                                    console.log(xhr.responseText);
                                    // Thêm logic để cập nhật trạng thái trên giao diện (nếu cần)
                                }
                            };

                            // Gửi yêu cầu
                            xhr.send(data);
                        }


                        // Hàm để xử lý sự kiện khi click nút Comment
                        function commentOnPost(postId) {
                            alert("Commented on Post with ID: " + postId);
                            // Thêm logic để chuyển người dùng đến trang comment tương ứng hoặc hiển thị form comment ngay trên giao diện
                        }

                          // Hàm để xử lý sự kiện khi click nút Edit Post
                        function editPost(postId) {
                            alert("Edit Post with ID: " + postId);
                            // Thêm logic để chuyển người dùng đến trang edit post tương ứng
                        }

                        // Hàm để xử lý sự kiện khi click nút Delete Post
                        // Hàm để xử lý sự kiện khi click nút Delete Post
                        function deletePost(postId) {
                            var confirmDelete = confirm("Are you sure you want to delete this post?");
                            if (confirmDelete) {
                                // Thực hiện xóa
                                performDelete(postId, 'delete');
                            } else {
                                // Nếu người dùng hủy delete, thì gửi action là 'cancelDelete'
                                performDelete(postId, 'cancelDelete');
                            }
                        }

                        // Hàm để thực hiện xóa với action được truyền vào
                        function performDelete(postId, action) {
                            console.log("Deleting Post with ID: " + postId);

                            var form = document.createElement("form");
                            form.method = "post";
                            form.action = "group?groupid=${groupId}";

                            var postIdInput = document.createElement("input");
                            postIdInput.type = "hidden";
                            postIdInput.name = "postId";
                            postIdInput.value = postId;

                            var actionInput = document.createElement("input");
                            actionInput.type = "hidden";
                            actionInput.name = "actionPost";
                            actionInput.value = action;

                            form.appendChild(postIdInput);
                            form.appendChild(actionInput);

                            document.body.appendChild(form);
                            form.submit();
                        }
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
