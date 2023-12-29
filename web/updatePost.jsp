<%-- 
    Document   : updatePost
    Created on : Dec 26, 2023, 1:15:04 PM
    Author     : Dell Latitude 7490
--%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Update Post</title>
        <style>
            body {
                font-family: Arial, sans-serif;
                margin: 0;
                padding: 0;
                background-color: #f0f2f5;
                display: flex;
                justify-content: center;
                align-items: center;
                height: 100vh;
            }

            .signup-container {
                text-align: center;
                background-color: white;
                padding: 40px; /* increased padding for a larger form */
                border-radius: 8px;
                box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            }

            input, select {
                width: 100%;
                padding: 10px;
                margin: 8px 0;
                box-sizing: border-box;
            }

            button {
                background-color: #4267B2;
                color: white;
                padding: 10px 20px;
                border: none;
                border-radius: 4px;
                cursor: pointer;
            }
        </style>
    </head>
    <body> 
        <div class="signup-container">
            <h1>Update Post</h1>
            <c:if test="${requestScope.error != null}">
                <p style="color: red;">${requestScope.error}</p>
            </c:if>
            <form method="post" enctype="multipart/form-data">
                <c:choose>
                    <c:when test="${oldPost.getVisibility() eq 'public'}">
                        <label>
                            <input type="radio" name="visibility" value="public" checked> Public
                        </label>
                        <label>
                            <input type="radio" name="visibility" value="friend"> Friends
                        </label>
                        <label>
                            <input type="radio" name="visibility" value="onlyme"> Only Me
                        </label>
                    </c:when>
                    <c:when test="${oldPost.getVisibility() eq 'friend'}">
                        <label>
                            <input type="radio" name="visibility" value="public"> Public
                        </label>
                        <label>
                            <input type="radio" name="visibility" value="friend" checked> Friends
                        </label>
                        <label>
                            <input type="radio" name="visibility" value="onlyme"> Only Me
                        </label>
                    </c:when>
                    <c:when test="${oldPost.getVisibility() eq 'onlyme'}">
                        <label>
                            <input type="radio" name="visibility" value="public"> Public
                        </label>
                        <label>
                            <input type="radio" name="visibility" value="friend"> Friends
                        </label>
                        <label>
                            <input type="radio" name="visibility" value="onlyme" checked> Only Me
                        </label>
                    </c:when>
                </c:choose>
                <br>
                <br>
                <label for="content">New caption:</label>
                <input type="text" id="content" name="content">
                <br>
                <label for="fileInput">New img or video: </label>
                <input type="file" id="fileInput" name="fileInput" accept="image/*, video/*">
                <br>
                <button type="submit">Update</button>
            </form>
        </div>
    </body>
</html>
