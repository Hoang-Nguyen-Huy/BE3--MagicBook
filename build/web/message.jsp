<%-- 
    Document   : message
    Created on : Jan 2, 2024, 11:05:22 AM
    Author     : Dell Latitude 7490
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link
            href="//maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css"
            rel="stylesheet">
        <link rel="stylesheet" href="<c:url value="/static/css/style.css" />">
        <title>MagicBook</title>
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

        <p id="username" style="display: none">${userName}</p>
        <p id="userAvatar" style="display: none">
            <c:url value="${avatar}" />
        </p>

        <div class="container" style="padding-top: 100px">
            <div class="conversation-container">
                <div class="modal-box border" id="add-group">
                    <div class="modal-box-head">
                        <div class="modal-box-title">
                            Add Group
                        </div>
                        <div class="modal-box-close toggle-btn" data-id="add-group" onclick="toggleModal(this, false)">
                            <i class="fa fa-times"></i>
                        </div>
                    </div>
                    <hr>
                    <form action="" onsubmit="return createGroup(event);">
                        <div class="modal-box-body">
                            <input type="text" class="txt-input txt-group-name" placeholder="Group Name...">
                        </div>		
                        <button type="submit" class="btn">Create Group</button>		
                    </form>
                </div>

                <div class="modal-box border" id="add-user">
                    <div class="modal-box-head">
                        <div class="modal-box-title">
                            Add Member
                        </div>
                        <div class="modal-box-close toggle-btn" data-id="add-user" onclick="toggleModal(this, false)">
                            <i class="fa fa-times"></i>
                        </div>
                    </div>
                    <hr>
                    <form action="" onsubmit="return addMember(event);">
                        <div class="modal-box-body add-member-body">
                            <input type="text" class="txt-input txt-group-name" placeholder="Name of member..." onkeyup="searchMemberByKeyword(this)">

                            <div class="list-user">
                                <ul>
                                </ul>
                            </div>
                        </div>		
                        <button type="submit" class="btn">Add Members</button>		
                    </form>
                </div>

                <div class="modal-box border" id="manage-user">
                    <div class="modal-box-head">
                        <div class="modal-box-title">
                            All Member Of Group
                        </div>
                        <div class="modal-box-close toggle-btn" data-id="manage-user" onclick="toggleModal(this, false)">
                            <i class="fa fa-times"></i>
                        </div>
                    </div>
                    <hr>
                    <div class="modal-box-body manage-member-body">
                        <div class="list-user">
                            <ul>
                            </ul>
                        </div>
                    </div>	
                </div>

                <div class="left-side active">
                    <div class="add-group border toggle-btn" data-id="add-group" onclick="toggleModal(this, true)"><i class="fa fa-plus-circle"></i></div>
                    <h2>
                        <a href="<c:url value="/profile?id=${userId}"/>"
                           style="text-decoration: none; color: white;margin-right: 3rem;">Welcome
                            ${userName}</a>
                        :
                        <a href="<c:url value="logout"/>"
                           style="text-decoration: none; color: white; margin-left: 3rem;">Logout</a>
                    </h2>
                    <div class="tab-control">
                        <i class="fa fa-comment active" onclick="chatOne(this)"></i>
                        <i class="fa fa-comments" onclick="chatGroup(this)"></i>
                    </div>
                    <div class="list-user-search">
                        <input type="text" class="txt-input" data-type="user" placeholder="Search..." onkeyup="searchUser(this)">
                    </div>
                    <div class="list-user">
                        <ul>
                            <c:forEach var="friend" items="${friendList}">
                                <li id="<c:out value="${friend.firstName} ${friend.lastName}"/>" onclick="setReceiver(this, '${friend.userId}');">
                                    <div class="user-contain">
                                        <div class="user-img">
                                            <img id="img-<c:out value="${friend.firstName} ${friend.lastName}"/>"
                                                 src="${friend.avatar}"
                                                 alt="Image of user">
                                            <div id="status-<c:out value="${friend.firstName} ${friend.lastName}"/>" class="user-img-dot"></div>
                                        </div>
                                        <div class="user-info">
                                            <span class="user-name"><c:out value="${friend.firstName} ${friend.lastName}"/></span>
                                        </div>
                                    </div>
                                </li>
                            </c:forEach>
                        </ul>
                    </div>
                </div>
                <div class="right-side" id="receiver"></div>
            </div>
        </div>


        <script type="text/javascript" charset="utf-8">
            /* 
             * To change this license header, choose License Headers in Project Properties.
             * To change this template file, choose Tools | Templates
             * and open the template in the editor.
             */


            var username = null;
            var websocket = null;
            var receiver = null;
            var userAvatar = null;
            var receiverAvatar = null;

            var groupName = null;
            var groupId = null

            var back = null;
            var rightSide = null;
            var leftSide = null;
            var conversation = null;

            var attachFile = null;
            var imageFile = null;
            var file = null;
            var listFile = [];
            var typeFile = "image";
            var deleteAttach = null;

            var typeChat = "user";

            var listUserAdd = [];
            var listUserDelete = [];
            var numberMember = 0;

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


            window.onload = function () {
                if ("WebSocket" in window) {
                    username = document.getElementById("username").textContent;
                    userAvatar = document.getElementById("userAvatar").textContent;
                    websocket = new WebSocket('ws://' + window.location.host + '/MagicBook/message/' + username + '/' + '${userId}');

                    websocket.onopen = function () {
                        console.log('Connected');
                    };

                    websocket.onmessage = function (data) {
                        console.log(data.data);
                        setMessage(JSON.parse(data.data));
                        console.log('Saving message...');
                    };

                    websocket.onerror = function () {
                        console.log('An error occured, closing application');
                        cleanUp();
                    };

                    websocket.onclose = function (data) {
                        console.log(data.reason);
                        cleanUp();
                    };
                } else {
                    console.log("Websockets not supported");
                }
            }

            window.onclick = function (e) {
                let modals = document.querySelectorAll(".modal-box");
                let toggleBtns = document.querySelectorAll(".toggle-btn");
                let count = 0;

                modals.forEach(function (modal) {
                    if (modal.contains(e.target))
                        count++;
                });

                toggleBtns.forEach(function (toggleBtn) {
                    if (toggleBtn.contains(e.target))
                        count++;
                });

                if (count !== 1)
                    toggleAllModal();
            }


            function cleanUp() {
                username = null;
                websocket = null;
                receiver = null;
            }

            function setReceiver(element, receiverId) {
                console.log(receiverId);
                groupId = null;
                receiver = element.id;
                console.log(receiver);
                receiverAvatar = document.getElementById('img-' + receiver).src;
                console.log(receiverAvatar);
                var status = '';
                if (document.getElementById('status-' + receiver).classList.contains('online')) {
                    status = 'online';
                }
                var rightSide = '<div class="user-contact">' + '<div class="back">'
                        + '<i class="fa fa-arrow-left"></i>'
                        + '</div>'
                        + '<div class="user-contain">'
                        + '<div class="user-img">'
                        + '<img src="' + receiverAvatar + '" '
                        + 'alt="Image of user">'
                        + '<div class="user-img-dot ' + status + '"></div>'
                        + '</div>'
                        + '<div class="user-info">'
                        + '<span class="user-name">' + receiver + '</span>'
                        + '</div>'
                        + '</div>'
                        + '<div class="setting">'
                        + '<i class="fa fa-cog"></i>'
                        + '</div>'
                        + '</div>'
                        + '<div class="list-messages-contain">'
                        + '<ul id="chat" class="list-messages">'
                        + '</ul>'
                        + '</div>'
                        + '<form class="form-send-message" onsubmit="return sendMessage(event, \'' + receiverId + '\')">'
                        + '<ul class="list-file"></ul> '
                        + '<input type="text" id="message" class="txt-input" placeholder="Type message...">'
                        + '<label class="btn btn-image" for="attach"><i class="fa fa-file"></i></label>'
                        + '<input type="file" multiple id="attach">'
                        + '<label class="btn btn-image" for="image"><i class="fa fa-file-image-o"></i></label>'
                        + '<input type="file" accept="image/*" multiple id="image">'
                        + '<button type="submit" class="btn btn-send">'
                        + '<i class="fa fa-paper-plane"></i>'
                        + '</button>'
                        + '</form>';

                document.getElementById("receiver").innerHTML = rightSide;

                loadMessages(receiverId);

                displayFiles();

//                makeFriend(rightSide);
            }


            function resetChat() {
                let chatBtn = document.querySelectorAll(".tab-control i");
                let searchTxt = document.querySelector(".list-user-search input");
                let addGroupBtn = document.querySelector(".add-group");

                searchTxt.value = "";

                chatBtn.forEach(function (ele) {
                    ele.classList.remove("active");
                });

                if (typeChat == "group") {
                    addGroupBtn.classList.add("active");
                } else {
                    addGroupBtn.classList.remove("active");
                }
            }


            function createGroup(e) {
                e.preventDefault();

                let groupName = document.querySelector(".txt-group-name").value;

                let object = new Object();
                let user = new Object();

                user.username = username;
                user.admin = true;

                object.name = groupName;
                object.users = [];
                object.users.push(user);
                toggleAllModal();

                fetch("http://" + window.location.host + "/conversations-rest-controller", {
                    method: "post",
                    cache: 'no-cache',
                    headers: {
                        'Content-Type': 'application/json;charset=utf-8'
                    },
                    body: JSON.stringify(object)
                })
                        .then(function (data) {
                            return data.json();
                        })
                        .then(function (data) {

                            if (typeChat != "group")
                                return;

                            let numberMember = data.users.length;

                            let imgSrc = ' src="http://' + window.location.host + '/files/group-' + data.id + '/' + data.avatar + '"';
                            let appendUser = '<li id="group-' + data.id + '">'
                                    + '<div class="user-contain" data-id="' + data.id + '" data-number="' + numberMember + '" data-name="' + data.name + '" onclick="setGroup(this);">'
                                    + '<div class="user-img">'
                                    + '<img id="img-group-' + data.id + '"'
                                    + imgSrc
                                    + ' alt="Image of user">'
                                    + '</div>'
                                    + '<div class="user-info" style="flex-grow:1 ;">'
                                    + '<span class="user-name">' + data.name + '</span>'
                                    + '</div>'
                                    + '</div>'
                                    + '<div class="group-delete border" data-id="' + data.id + '" onclick="deleteGroup(this)">Delete</div>'
                                    + '</li>';
                            document.querySelector(".left-side .list-user").innerHTML += appendUser;
                            document.querySelector(".txt-group-name").value = "";
                        });
            }

            function addMember(e) {
                e.preventDefault();

                let object = new Object();
                object.name = groupName;
                object.id = groupId;
                object.users = [];


                listUserAdd.forEach(function (username) {
                    let user = new Object();

                    user.username = username;
                    user.admin = false;
                    user.avatar = null;

                    object.users.push(user);
                });


                fetch("http://" + window.location.host + "/conversations-rest-controller", {
                    method: "post",
                    cache: 'no-cache',
                    headers: {
                        'Content-Type': 'application/json;charset=utf-8'
                    },
                    body: JSON.stringify(object)
                })
                        .then(function (data) {
                            return data.json();
                        })
                        .then(function (data) {
                            numberMember += parseInt(listUserAdd.length);
                            listUserAdd = [];
                            let inviteNumber = document.querySelector(".total-invite-user");
                            if (inviteNumber)
                                inviteNumber.innerHTML = numberMember + " paticipants";

                            document.getElementById("group-" + groupId).querySelector(".user-contain").setAttribute("data-number", numberMember);

                            toggleAllModal();
                        });
            }

            function fetchUser() {

                fetch("http://" + window.location.host + "/conversations-rest-controller?usersConversationId=" + groupId)
                        .then(data => data.json())
                        .then(users => {
                            document.querySelector(".manage-member-body .list-user ul").innerHTML = "";

                            if (users.length == 1) {
                                document.querySelector(".manage-member-body .list-user ul").innerHTML = "No members in group";
                                document.querySelector(".manage-member-body .list-user ul").style = "text-align: center; font-size: 1.8rem;";
                            } else {
                                document.querySelector(".manage-member-body .list-user ul").style = "";
                            }

                            users.forEach(function (data) {
                                if (data.username == username)
                                    return;

                                let appendUser = '<li>'
                                        + '<div class="user-contain">'
                                        + '<div class="user-img">'
                                        + '<img '
                                        + ' src="http://' + window.location.host + '/files/' + data.username + '/' + data.avatar + '"'
                                        + 'alt="Image of user">'
                                        + '</div>'
                                        + '<div class="user-info" style="flex-grow: 1;">'
                                        + '<span class="user-name">' + data.username + '</span>'
                                        + '</div>';

                                if (!data.admin)
                                    appendUser += '<div class="user-delete" style="font-weight: 700;" data-username="' + data.username + '" onclick="deleteMember(this)">Delete</div>'

                                appendUser += '</div></li>';

                                document.querySelector(".manage-member-body .list-user ul").innerHTML += appendUser;
                            });

                        })
                        .catch(ex => console.log(ex));

            }

            function deleteGroup(ele) {
                let grpId = ele.getAttribute("data-id");

                if (grpId == groupId)
                    document.querySelector(".right-side").innerHTML = "";

                fetch("http://" + window.location.host + "/conversations-rest-controller?conversationId=" + grpId, {
                    method: 'delete'
                })
                        .then(function (data) {
                            return data.json();
                        })
                        .then(function (data) {

                            let groupNumber = document.getElementById("group-" + grpId);
                            if (groupNumber)
                                groupNumber.outerHTML = "";

                        })
                        .catch(ex => console.log(ex));
            }

            function deleteMember(ele) {
                let username = ele.getAttribute("data-username");

                fetch("http://" + window.location.host + "/conversations-rest-controller?conversationId=" + groupId + "&username=" + username, {
                    method: 'delete'
                })
                        .then(function (data) {
                            return data.json();
                        })
                        .then(function (data) {

                            numberMember -= 1;

                            let inviteNumber = document.querySelector(".total-invite-user");
                            if (inviteNumber)
                                inviteNumber.innerHTML = numberMember + " paticipants";

                            toggleAllModal();
                        })
                        .catch(ex => console.log(ex));

            }

            function toggleAllModal() {
                let modalBox = document.querySelectorAll(".modal-box");

                modalBox.forEach(function (modal) {
                    modal.classList.remove("active");
                });

            }

            function toggleModal(ele, mode) {
                let modalBox = document.querySelectorAll(".modal-box");
                let id = ele.getAttribute("data-id");

                modalBox.forEach(function (modal) {
                    modal.classList.remove("active");
                });


                if (mode)
                    document.getElementById(id).classList.add("active");
                else
                    document.getElementById(id).classList.remove("active");
            }

            function chatOne(ele) {
                typeChat = "user";
                resetChat();
                ele.classList.add("active");
                searchFriendByKeyword("");
                listFiles = [];
            }

            function chatGroup(ele) {
                typeChat = "group";
                resetChat();
                ele.classList.add("active");
                fetchGroup();
                listFiles = [];
            }

            function addUserChange(e) {
                if (e.checked) {
                    listUserAdd.push(e.value);
                } else {
                    let index = listUserAdd.indexOf(e.value);
                    listUserAdd.splice(index, 1);
                }

            }


            function fetchGroup() {
                fetch("http://" + window.location.host + "/conversations-rest-controller?username=" + username)
                        .then(function (data) {
                            return data.json();
                        })
                        .then(data => {

                            document.querySelector(".left-side .list-user").innerHTML = "";
                            data.forEach(function (data) {
                                let numberMember = data.users ? data.users.length : 0;

                                let findObject = data.users.find(element => element.username == username);
                                let isAdmin = findObject.admin;

                                let imgSrc = ' src="http://' + window.location.host + '/files/group-' + data.id + '/' + data.avatar + '"';
                                let appendUser = '<li id="group-' + data.id + '">'
                                        + '<div class="user-contain" data-id="' + data.id + '" data-number="' + numberMember + '" data-name="' + data.name + '" onclick="setGroup(this);">'
                                        + '<div class="user-img">'
                                        + '<img id="img-group-' + data.id + '"'
                                        + imgSrc
                                        + ' alt="Image of user">'
                                        + '</div>'
                                        + '<div class="user-info" style="flex-grow:1 ;">'
                                        + '<span class="user-name">' + data.name + '</span>'
                                        + '</div>'
                                        + '</div>';
                                if (isAdmin) {
                                    appendUser += '<div class="group-delete border" data-id="' + data.id + '" onclick="deleteGroup(this)">Delete</div>';
                                }
                                appendUser += '</li>';
                                document.querySelector(".left-side .list-user").innerHTML += appendUser;
                            });
                        }).catch(ex => {
                    console.log(ex);
                });
            }


            function handleResponsive() {
                back = document.querySelector(".back");
                rightSide = document.querySelector(".right-side");
                leftSide = document.querySelector(".left-side");

                if (back) {
                    back.addEventListener("click", function () {
                        rightSide.classList.remove("active");
                        leftSide.classList.add("active");
                        listFile = [];
                        renderFile();
                    });
                }

                rightSide.classList.add("active");
                leftSide.classList.remove("active");

            }

            function displayFiles() {
                attachFile = document.getElementById("attach");
                imageFile = document.getElementById("image");
                file = document.querySelector(".list-file");
                deleteAttach = document.querySelectorAll(".delete-attach");

                attachFile.addEventListener("change", function (e) {
                    let filesInput = e.target.files;

                    for (const file of filesInput) {
                        listFile.push(file);
                    }

                    typeFile = "file";
                    renderFile("attach");

                    this.value = null;
                });

                imageFile.addEventListener("change", function (e) {
                    let filesImage = e.target.files;

                    for (const file of filesImage) {
                        listFile.push(file);
                    }

                    typeFile = "image";

                    renderFile("image");

                    this.value = null;
                });



            }

            function deleteFile(idx) {
                if (!isNaN(idx))
                    listFile.splice(idx, 1);

                renderFile(typeFile);
            }

            function renderFile(typeFile) {
                let listFileHTML = "";
                let idx = 0;

                if (typeFile == "image") {
                    for (const file of listFile) {
                        listFileHTML += '<li><img src="' + URL.createObjectURL(file)
                                + '" alt="Image file"><span data-idx="'
                                + (idx) + '" onclick="deleteFile('
                                + idx + ')" class="delete-attach">X</span></li>';
                        idx++;
                    }
                } else {
                    for (const file of listFile) {
                        listFileHTML += '<li><div class="file-input">' + file.name
                                + '</div><span data-idx="'
                                + (idx) + '" onclick="deleteFile('
                                + idx + ')" class="delete-attach">X</span></li>';
                        idx++;
                    }
                }


                if (listFile.length == 0) {
                    file.innerHTML = "";
                    file.classList.remove("active");
                } else {
                    file.innerHTML = listFileHTML;
                    file.classList.add("active");
                }

                deleteAttach = document.querySelectorAll(".delete-attach");
            }

            function sendMessage(e, receiverId) {
                e.preventDefault();
                console.log(receiverId);
                var inputText = document.getElementById("message").value;
                if (inputText != '') {
                    sendText(receiverId);
                } else {
                    sendAttachments();
                }

                return false;
            }

            function sendText(receiverId) {
                var messageContent = document.getElementById("message").value;
                var messageType = "text";
                document.getElementById("message").value = '';

                // Lấy thời gian hiện tại
                var currentDate = new Date();

                // Format ngày và giờ
                var messageSentDate = currentDate.toISOString().split('T')[0]; // Ngày
                var messageSentTime = currentDate.toLocaleTimeString(); // Giờ


                var message = buildMessageToJson(messageContent, messageType, messageSentDate, messageSentTime, receiverId);
                console.log(message);
                setMessage(message);
                websocket.send(JSON.stringify(message));
            }

            function sendAttachments() {
                var messageType = "attachment";
                for (file of listFile) {
                    messageContent = file.name.trim();
                    messageType = file.type;
                    var message = buildMessageToJson(messageContent, messageType);
                    websocket.send(JSON.stringify(message));
                    websocket.send(file);


                    if (messageType.startsWith("audio")) {
                        message.message = '<audio controls>'
                                + '<source src="' + URL.createObjectURL(file) + '" type="' + messageType + '">'
                                + '</audio>';
                    } else if (messageType.startsWith("video")) {
                        message.message = '<video width="400" controls>'
                                + '<source src="' + URL.createObjectURL(file) + '" type="' + messageType + '">'
                                + '</video>';
                    } else if (messageType.startsWith("image")) {
                        message.message = '<img src="' + URL.createObjectURL(file) + '" alt="">';
                    } else {
                        message.message = '<a href= "' + URL.createObjectURL(file) + '">' + messageContent + '</a>'
                    }
                    setMessage(message);
                }
                file = document.querySelector(".list-file");
                file.classList.remove("active");
                file.innerHTML = "";
                listFile = [];
            }

            function buildMessageToJson(message, type, messageSentDate, messageSentTime, receiverId) {
                return {
                    messageId: null,
                    content: message,
                    sentDate: messageSentDate,
                    sentTime: messageSentTime,
                    receiverId: receiverId,
                    userId: '${userId}'
                };
            }

            function setMessage(msg) {
                console.log("online users: " + msg.onlineList);

                if (msg.content !== '[P]open' && msg.content !== '[P]close') {
                    var currentChat = document.getElementById('chat').innerHTML;
                    var newChatMsg = '';
                    if (msg.receiverId !== null) {
                        newChatMsg = customLoadMessage(msg.userId, msg.receiverId, msg.content);

                        // Kiểm tra xem tin nhắn đã tồn tại trong phần tử chat hay chưa
                        if (currentChat.indexOf(newChatMsg) === -1) {
                            document.getElementById('chat').innerHTML = currentChat + newChatMsg;
                            goLastestMsg();
                        }
                    } else {
                        newChatMsg = customLoadMessageGroup(msg.username, msg.groupId, msg.content, msg.avatar);

                        // Kiểm tra xem tin nhắn đã tồn tại trong phần tử chat hay chưa
                        if (currentChat.indexOf(newChatMsg) === -1) {
                            document.getElementById('chat').innerHTML = currentChat + newChatMsg;
                            goLastestMsg();
                        }
                    }
                } else {
                    if (msg.message === '[P]open') {
                        msg.onlineList.forEach(username => {
                            try {
                                setOnline(msg.userId, true);
                            } catch (ex) {
                            }
                        });
                    } else {
                        setOnline(msg.userId, false);
                    }
                }
            }


            function setOnline(username, isOnline) {
                let ele = document.getElementById('status-' + username);

                if (isOnline === true) {
                    ele.classList.add('online');
                } else {
                    ele.classList.remove('online');
                }
            }

            function loadMessagesGroup() {
                var currentChatbox = document.getElementById("chat");
                var xhttp = new XMLHttpRequest();
                xhttp.onreadystatechange = function () {
                    if (this.readyState == 4 && this.status == 200) {
                        var messages = JSON.parse(this.responseText);
                        var chatbox = "";
                        messages.forEach(msg => {
                            try {
                                chatbox += customLoadMessageGroup(msg.username, groupId, msg.message, msg.avatar);
                            } catch (ex) {

                            }
                        });
                        currentChatbox.innerHTML = chatbox;
                        goLastestMsg();
                    }
                };
                xhttp.open("GET", "http://" + window.location.host + "/conversations-rest-controller?messagesConversationId=" + groupId, true);
                xhttp.send();
            }

            function loadMessages(receiverId) {
                var currentChatbox = document.getElementById("chat");
                var xhttp = new XMLHttpRequest();
                xhttp.onreadystatechange = function () {
                    if (this.readyState === 4 && this.status === 200) {
                        var messages = JSON.parse(this.responseText);
                        var chatbox = "";
                        messages.forEach(msg => {
                            try {
                                chatbox += customLoadMessage(msg.userId, msg.receiverId, msg.content);
                            } catch (ex) {

                            }
                        });
                        currentChatbox.innerHTML = chatbox;
                        goLastestMsg();
                    }
                };
                username = document.getElementById("username").textContent;
                xhttp.open("GET", "http://" + window.location.host + "/MagicBook/chat-rest-controller?sender=" + username
                        + "&receiver=" + receiver + "&senderId=" + '${userId}' + "&receiverId=" + receiverId, true);
                xhttp.send();
            }

            function customLoadMessage(sender, receiver, message) {
                var imgSrc = receiverAvatar;
                console.log(sender);
                console.log(receiver);
                console.log(message);
                var msgDisplay = '<li>'
                        + '<div class="message';
                if (`${userId}` !== sender) {
                    msgDisplay += '">';
                } else {
                    imgSrc = userAvatar;
                    console.log(imgSrc);
                    msgDisplay += ' right">';
                }
                return msgDisplay + '<div class="message-img">'
                        + '<img src="' + imgSrc + '" alt="">'
                        + ' </div>'
                        + '<div class="message-text">' + message + '</div>'
                        + '</div>'
                        + '</li>';
            }

            function customLoadMessageGroup(sender, groupIdFromServer, message, avatar) {
                let imgSrc = 'http://' + window.location.host + '/files/' + sender + '/' + avatar;
                var msgDisplay = '<li>'
                        + '<div class="message';
                if (groupIdFromServer != groupId) {
                    return '';
                }
                if (username != sender) {
                    msgDisplay += '">';
                } else {
                    imgSrc = userAvatar;
                    msgDisplay += ' right">';
                }
                return msgDisplay + '<div class="message-img">'
                        + '<img src="' + imgSrc + '" alt="">'
                        + '<div class="sender-name">' + sender + '</div>'
                        + ' </div>'
                        + '<div class="message-text">' + message + '</div>'
                        + '</div>'
                        + '</li>';
            }

            function searchFriendByKeyword(keyword) {
                fetch("http://" + window.location.host + "/users-rest-controller?username=" + username + "&keyword=" + keyword)
                        .then(function (data) {
                            return data.json();
                        })
                        .then(data => {

                            document.querySelector(".left-side .list-user").innerHTML = "";
                            data.forEach(function (data) {
                                if (data.online)
                                    status = "online";
                                else
                                    status = "";

                                let appendUser = '<li id="' + data.username + '" onclick="setReceiver(this);">'
                                        + '<div class="user-contain">'
                                        + '<div class="user-img">'
                                        + '<img id="img-' + data.username + '"'
                                        + ' src="http://' + window.location.host + '/files/' + data.username + '/' + data.avatar + '"'
                                        + 'alt="Image of user">'
                                        + '<div id="status-' + data.username + '" class="user-img-dot ' + status + '"></div>'
                                        + '</div>'
                                        + '<div class="user-info">'
                                        + '<span class="user-name">' + data.username + '</span>'
                                        + '</div>'
                                        + '</div>'
                                        + '</li>';
                                document.querySelector(".left-side .list-user").innerHTML += appendUser;
                            });
                        });
            }

            function searchMemberByKeyword(ele) {
                let keyword = ele.value;
                fetch("http://" + window.location.host + "/users-rest-controller?username=" + username + "&keyword=" + keyword + "&conversationId=" + groupId)
                        .then(function (data) {
                            return data.json();
                        })
                        .then(data => {

                            document.querySelector(".add-member-body .list-user ul").innerHTML = "";
                            data.forEach(function (data) {
                                if (data.online)
                                    status = "online";
                                else
                                    status = "";

                                let check = "";
                                if (listUserAdd.indexOf(data.username) >= 0)
                                    check = "checked";

                                let appendUser = '<li>'
                                        + '<input id="member-' + data.username + '" type="checkbox" ' + check + ' value="' + data.username + '" onchange="addUserChange(this)">'
                                        + '<label for="member-' + data.username + '">'
                                        + '<div class="user-contain">'
                                        + '<div class="user-img">'
                                        + '<img '
                                        + ' src="http://' + window.location.host + '/files/' + data.username + '/' + data.avatar + '"'
                                        + 'alt="Image of user">'
                                        + '</div>'
                                        + '<div class="user-info">'
                                        + '<span class="user-name">' + data.username + '</span>'
                                        + '</div>'
                                        + '</div>'
                                        + '</label>'
                                        + '<div class="user-select-dot"></div>'
                                        + '</li>';
                                document.querySelector(".add-member-body .list-user ul").innerHTML += appendUser;
                            });
                        });
            }

            function searchGroupByKeyword(value) {
                let keyword = value;
                fetch("http://" + window.location.host + "/conversations-rest-controller?username=" + username + "&conversationKeyword=" + keyword)
                        .then(function (data) {
                            return data.json();
                        })
                        .then(data => {

                            document.querySelector(".left-side .list-user").innerHTML = "";
                            data.forEach(function (data) {

                                let numberMember = data.users ? data.users.length : 0;
                                let findObject = data.users.find(element => element.username == username);
                                let isAdmin = false;

                                if (findObject)
                                    isAdmin = findObject.admin;

                                let imgSrc = ' src="http://' + window.location.host + '/files/group-' + data.id + '/' + data.avatar + '"';

                                let appendUser = '<li id="group-' + data.id + '">'
                                        + '<div class="user-contain" data-id="' + data.id + '" data-number="' + numberMember + '" data-name="' + data.name + '" onclick="setGroup(this);">'
                                        + '<div class="user-img">'
                                        + '<img id="img-group-' + data.id + '"'
                                        + imgSrc
                                        + ' alt="Image of user">'
                                        + '</div>'
                                        + '<div class="user-info" style="flex-grow:1 ;">'
                                        + '<span class="user-name">' + data.name + '</span>'
                                        + '</div>'
                                        + '</div>';
                                if (isAdmin)
                                    appendUser += '<div class="group-delete border" data-id="' + data.id + '" onclick="deleteGroup(this)">Delete</div>';

                                appendUser += '</li>';
                                document.querySelector(".left-side .list-user").innerHTML += appendUser;
                            });
                        });
            }

            function searchUser(ele) {
                if (typeChat == "user") {
                    searchFriendByKeyword(ele.value);
                } else {
                    if (ele.value == "") {
                        fetchGroup();
                    } else {
                        searchGroupByKeyword(ele.value);
                    }
                }
            }

            function goLastestMsg() {
                var msgLiTags = document.querySelectorAll(".message");
                var last = msgLiTags[msgLiTags.length - 1];
                try {
                    last.scrollIntoView();
                } catch (ex) {
                }
            }


        </script>

    </body>
</html>