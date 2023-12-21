<%-- 
    Document   : searchPeople
    Created on : Dec 20, 2023, 1:05:58 PM
    Author     : Dell Latitude 7490
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Simple Social Network</title>
        <style>
            body {
                font-family: "Segoe UI", Tahoma, Geneva, Verdana, sans-serif;
                margin: 0;
                padding: 0;
                background-color: #f0f2f5;
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

            .logo {
                font-size: 28px;
                font-weight: bold;
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

            main {
                display: flex;
                flex-direction: column;
                align-items: center;
                padding: 20px;
                height: 100vh; /* Để main chiếm toàn bộ chiều cao của trang */
                position: relative;
                top: 70px;
            }

            #postsContainer {
                width: 60%;
                position: relative;
                display: flex;
                flex-direction: column;
            }

            .user {
                width: 40%;
                background-color: white;
                padding: 20px;
                margin-bottom: 20px;
                border-radius: 8px;
                box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
                display: flex;
                flex-direction: row;
                align-items: center;
                justify-content: space-between;
            }

            .user img {
                width: 70px;
                height: 80px;
                border-radius: 50%;
                object-fit: cover;
                margin-bottom: 10px;
            }

            .user a {
                font-size: 16px;
                font-weight: bold;
                color: #4267b2;
                text-decoration: none;
                margin-bottom: 5px;
            }

            .user p {
                font-size: 14px;
                color: #666;
            }

            .user button {
                background-color: #4267b2;
                color: white;
                padding: 8px;
                border: none;
                border-radius: 4px;
                cursor: pointer;
                transition: background-color 0.3s ease;
            }

            .user button:hover {
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

            .user-menu {
                position: relative;
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
                    <img class="dropimg" src="${avatar}" width="20px" height="20px" alt="Avatar">
                </button>
                <div id="myDropdown" class="dropdown-content">
                    <a href="profile?id=${userId}"><img src="${avatar}" width="20px" height="20px"> ${userName}</a>
                    <a href="update-profile">Update profile</a>
                    <a href="logout">Log out</a>
                </div>
            </div>
        </header>

        <main>
            
            <c:forEach var="foundUser" items="${found}">
                <div class="user">
                    <img src="${foundUser.avatar}" width="70" heigth="80" alt="Avatar">
                    <a href="profile?id=${foundUser.userId}"><c:out value="${foundUser.firstName} ${foundUser.lastName}"/></a>
                    <p>${foundUser.country}</p>
                </div>
            </c:forEach>
            
        </main>

        <script>
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
