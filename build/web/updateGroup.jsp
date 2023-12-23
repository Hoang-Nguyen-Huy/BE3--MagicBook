<%-- 
    Document   : updateGroup
    Created on : Dec 23, 2023, 12:49:23 PM
    Author     : Dell Latitude 7490
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Create Group</title>
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
            <h1>Update Group</h1>
            <c:if test="${requestScope.error != null}">
                <p style="color: red;">${requestScope.error}</p>
            </c:if>
            <form method="post" enctype="multipart/form-data">
                <label for="groupName">New Group Name:</label>
                <input type="text" id="groupName" name="groupName">
                <br>      
                <label for="avatar">New Group Avatar:</label>
                <input type="file" id="avatar" name="avatar" accept="image/*">
                <br>
                <button type="submit">Update Group</button>
            </form>
        </div>
    </body>
</html>
