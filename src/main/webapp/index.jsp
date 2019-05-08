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
        <link rel="stylesheet" type="text/css" href="${styleUrl}">

        <script src="${indexScriptUrl}"></script>
        <script src="${registerScriptUrl}"></script>
        <script src="${loginScriptUrl}"></script>
        <script src="${profileScriptUrl}"></script>
        <script src="${backToProfileScriptUrl}"></script> 
        <script src="${logoutScriptUrl}"></script>
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
            <button id="register-back">Back</button>
        </form>
    </div>
    <div id="profile-content" class="hidden content">
        <h1>Profile</h1>
        <div id="profile-data">
            <p>User name: <span id="user-email"></span></p>
            <p>Password: <span id="user-password"></span></p>
            <button id="create-schedule-button" onclick="onCreateNewSchedule()">New schedule</button>
        </div>
        <h2 class="right-navbar">My schedules</h2>
        <ul class="right-navbar">
        </ul>
        <h2 class="right-navbar">Public schedules</h2>
        <ul class="right-navbar">
        </ul>
    </div>
    <div id="create-new-schedule" class="hidden content">
        <form id="create-schedule" onsubmit="return false;">
            <input type="text" name="schedule-name" placeholder="Schedule name">
            <select>
                <option value="1">1</option>
                <option value="2">2</option>
                <option value="3">3</option>
                <option value="4">4</option>
                <option value="5">5</option>
                <option value="6">6</option>
                <option value="7">7</option>
            </select>
        </form>
    </div>
    <div id="back-to-profile-content" class="hidden content">
        <button onclick="onBackToProfileClicked();">Back to profile</button>
    </div>
    <div id="logout-content" class="hidden content">
        <button id="logout-button">Logout</button>
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
                            <option value="1">1:00</option>
                            <option value="2">2:00</option>
                            <option value="3">3:00</option>
                            <option value="4">4:00</option>
                            <option value="5">5:00</option>
                            <option value="6">6:00</option>
                            <option value="7">7:00</option>
                            <option value="8">8:00</option>
                            <option value="9">9:00</option>
                            <option value="10">10:00</option>
                            <option value="11">11:00</option>
                            <option value="12">12:00</option>
                            <option value="13">13:00</option>
                            <option value="14">14:00</option>
                            <option value="15">15:00</option>
                            <option value="16">16:00</option>
                            <option value="17">17:00</option>
                            <option value="18">18:00</option>
                            <option value="19">19:00</option>
                            <option value="20">20:00</option>
                            <option value="21">21:00</option>
                            <option value="22">22:00</option>
                            <option value="23">23:00</option>
                            <option value="24">24:00</option>
                        </select>
                    </p>
                    <!--displays the possible duration in hrs, consider creating it in js to avoid -->
                    <p>Select duration:
                        <select id="duration-select" name="duration">
                            <option value="1">1</option>
                            <option value="2">2</option>
                            <option value="3">3</option>
                            <option value="4">4</option>
                            <option value="5">5</option>
                            <option value="6">6</option>
                            <option value="7">7</option>
                            <option value="8">8</option>
                            <option value="9">9</option>
                            <option value="10">10</option>
                            <option value="11">11</option>
                            <option value="12">12</option>
                            <option value="13">13</option>
                            <option value="14">14</option>
                            <option value="15">15</option>
                            <option value="16">16</option>
                            <option value="17">17</option>
                            <option value="18">18</option>
                            <option value="19">19</option>
                            <option value="20">20</option>
                            <option value="21">21</option>
                            <option value="22">22</option>
                            <option value="23">23</option>
                            <option value="24">24</option>
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
