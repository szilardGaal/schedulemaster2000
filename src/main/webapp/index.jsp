<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!doctype html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <c:url value="/style.css" var="styleUrl"/>
        <c:url value="/index.js" var="indexScriptUrl"/>
        <c:url value="/register.js" var="registerScriptUrl"/>
        <c:url value="/login.js" var="loginScriptUrl"/>
        <c:url value="/profile.js" var="profileScriptUrl"/>
        <c:url value="/back-to-profile.js" var="backToProfileScriptUrl"/>
        <c:url value="/logout.js" var="logoutScriptUrl"/>
        <c:url value="/schedule.js" var="scheduleScriptUrl"/>
        <link rel="stylesheet" type="text/css" href="${styleUrl}">

        <script src="${indexScriptUrl}"></script>
        <script src="${registerScriptUrl}"></script>
        <script src="${loginScriptUrl}"></script>
        <script src="${profileScriptUrl}"></script>
        <script src="${backToProfileScriptUrl}"></script> 
        <script src="${logoutScriptUrl}"></script>
        <script src="${scheduleScriptUrl}"></script>
        <title>ScheduleMaster2000</title>
    </head>
<body>
<div class="container">
    <div id="login-content" class="content">
        <h1>Login</h1>
        <form id="login-form" onsubmit="return false;">
            <input type="text" name="username">
            <input type="password" name="password"><br>
            <button id="login-button">Login</button>
            <button id="register-content-button">Register</button>
        </form>
    </div>
    <div id="register-content" class="hidden content">
        <h1>Register</h1>
        <form id="register-form" onsubmit="return false;">
            <p>Username:</p>
            <input type="text" name="username" placeholder="username" required>
            <p>Password:</p>
            <input type="password" name="password" placeholder="password" onkeyup="validatePassword()" required><br>
            <input type="password" name="confirm_password" placeholder="password again" onkeyup="validatePassword()" required><br>
            <p id="validate_status"><br></p>
            <p>register as:
            <select name="role">
                <option value="user">User</option>
                <option value="admin">Admin</option>
            </select>
            </p>
            <br>
            <button id="register-button">Register</button>
            <button id="register-back-button">Back</button>
        </form>
    </div>
    <div id="profile-content" class="hidden content">
        <div id="profile-data">
            <h3>Welcome <span id="user-email"></span>!</h3>
            <div id="list-my-schedules">
            </div>
            <p><button id="create-schedule-button" onclick="onCreateNewSchedule()">New schedule</button></p>
            <div id="create-new-schedule" class="hidden content">
                <form id="create-schedule" onsubmit="return false;">
                    <p>schedule name:</p>
                    <input type="text" name="schedule-name" placeholder="Schedule name" required>
                    <p>select duration:</p>
                    <select name="schedule-duration">
                        <c:forEach var="i" begin="1" end="7" step="1" varStatus ="status">
                            <option value="${i}">"${i}"</option>
                        </c:forEach>
                    </select> days
                    <br>
                    <button onclick="onCreateScheduleButton()" type="submit">create</button>
                    <button onclick="onCancelButtonClicked()">cancel</button>
                </form>
            </div>
            <div id="list-my-tasks">
            </div>
            <br>
            <p><button id="create-task-button" onclick="onCreateNewTask()">New task</button></p>
            <div id="create-new-task" class="hidden content">
                <form id="create-task" onsubmit="return false;">
                    <p>task name:</p>
                    <input type="text" name="task-name" placeholder="task name" required>
                    <p>task description:</p>
                    <input type="text" name="task-description" placeholder="task description" required>
                    <p>task duration:</p>
                    <select name="task-duration">
                        <c:forEach var="i" begin="1" end="24" step="1" varStatus ="status">
                            <option value="${i}">"${i}"</option>
                        </c:forEach>
                    </select> hours
                    <br>
                    <button onclick="onCreateTaskButton()" type="submit">create</button>
                    <button onclick="onCancelButtonClicked()">cancel</button>
                </form>
            </div>
            <br>
            <div id="list-public-schedules">
            </div>
            <br>
            <div id="logout-content" class="hidden content">
                    <button id="logout-button">Logout</button>
            </div>
        </div>
    </div>
    <div id="back-to-profile-content" class="hidden content">
        <button onclick="onBackToProfileClicked();">Back to profile</button>
    </div>
    <div id="schedule_content" class="hidden content">
        <h1 id="schedule-name"></h1>
        <div class="left-navbar">
            <div id="add-task" class="hidden content">
                <form id="add-task-form" onsubmit="return false;">
                    <!--get tasklist from db and list it in the dropdown menu below, format: <option value="sample1">sample1</option>-->
                    <p>Select task:
                        <select id="tasklist" name="tasks">
                        </select>
                    </p>
                    <!-- javascript below displays the new task creator form-->
                    <p>Select starting time:
                        <select name="begins">
                            <c:forEach var="i" begin="1" end="24" step="1" varStatus ="status">
                                <option value="${i}">"${i}":00</option>
                            </c:forEach>
                        </select>
                    </p>
                    <!--displays the possible duration in hrs, consider creating it in js to avoid -->
                    <p>Select duration:
                        <select id="duration-select" name="duration">
                            <c:forEach var="i" begin="1" end="24" step="1" varStatus ="status">
                                <option value="${i}">"${i}"</option>
                            </c:forEach>
                        </select>
                    </p>

                    <p>Select days:</p>
                    <div id="checkbox-for-days">
                        <!-- displays the columns in the current schedule below, input fields has to be created in js according to the number of columns, format: <input type="checkbox" name="column1" value="1">Day 1<br> -->
                    </div>
                    <button onclick="onTaskAddClick();">Add task to schedule</button>
                </form>
                <!-- js to create publish/unpublish button according to current schedule state-->
                <button id="change-schedule-state" class="hidden content" onclick="onPublushScheduleClick()"></button>
            </div>
            <div id="display-create-task" class="hidden content">
                <p>Cannot find the task you are looking for?</p>
                <button onclick="createNewTaskClick()">Create new task!</button>
            </div>
            <div id="create-task" class="hidden content">
                <form id="create-task-form" onsubmit="return false;">
                    <input type="text" name="title" placeholder="Add title here..."><br>
                    <input type="text" name="content" placeholder="Add details here"><br>
                    <!-- javascript below creates new task and adds it to the db-->
                    <button onclick="onTaskCreateClick();">Add new task</button>
                </form>
            </div>
        </div>
        <div id="schedule" class="">
            <!--here table has to be created in js based on the datas stored in db, first td in each row contains time e.g.: 1:00 and has a class "line-highlight" for css-->
            <!--td max width and height has to be restricted accordingly in js, along with overflow-->
        </div>
    </div>
</div>
</body>
</html>
