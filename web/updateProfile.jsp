<%-- 
    Document   : updateProfile
    Created on : Dec 19, 2023, 1:36:49 PM
    Author     : Dell Latitude 7490
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Update Profile</title>
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
            <h1>Update Profile</h1>
            <c:if test="${requestScope.error != null}">
                <p style="color: red;">${requestScope.error}</p>
            </c:if>
            <form method="post" enctype="multipart/form-data">
                <label for="email">Email:</label>
                <input type="email" id="email" name="email">
                <br>
                <label for="phone">Phone Number:</label>
                <input type="tel" id="phone" name="phone">
                <br>
                <label for="password">Password:</label>
                <input type="password" id="password" name="password">
                <br>
                <label for="firstName">First Name:</label>
                <input type="text" id="firstName" name="firstName">
                <br>
                <label for="lastName">Last Name:</label>
                <input type="text" id="lastName" name="lastName">
                <br>
                <label for="dob">Date of Birth:</label>
                <input type="date" id="dob" name="dob">
                <br>
                <label for="sex">Sex:</label>
                <select id="sex" name="sex">
                    <option value="Male">Male</option>
                    <option value="Female">Female</option>
                </select>
                <br>
                <label for="country">Country:</label>
                <input type="text" id="country" name="country">
                <br>
                <br>
                <label for="avatar">Avatar:</label>
                <input type="file" id="avatar" name="avatar" accept="image/*">
                <br>
                <button type="submit">Update</button>
            </form>
        </div>
    </body>
</html>

