<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!doctype html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" type="text/css" href="style.css">
        <script src="index.js"></script>
        <script src="register.js"></script>
        <script src="login.js"></script>
        <script src="profile.js"></script>
        <script src="logout.js"></script>
        <script src="schedule.js"></script>
        <script src="schedule-view.js"></script>
        <script src="guest.js"></script>
        <script src="tasks.js" var="tasksScriptUrl"></script>
        <title>ScheduleMaster2000</title>
    </head>
<body>
<div class="container">
    <div id="login-content" class="content">
        <h1>Login</h1><br>
        <form id="login-form" onsubmit="return false;">
            <input type="text" name="username">
            <input type="password" name="password"><br>
            <button id="login-button">Login</button>
            <button id="register-content-button">Register</button><br>
            <p>Try ScheduleMaster2000 without registration!</p>
            <button id="login-as-guest">Try now</button>
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
            <h3>Welcome<span id="user-email"></span>!</h3>
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
                    <p>visibility:</p>
                    <select name="is-public">
                        <option value="true">public</option>
                        <option value="false">private</option>
                    </select>
                    <br>
                    <button onclick="onCreateScheduleButton()" type="submit">create</button>
                    <button onclick="onCancelButtonClicked()">cancel</button>
                </form>
            </div>
            <div id="modify-schedule-div" class="hidden content">
                <form id="modify-schedule" onsubmit="return false;">
                    <p>schedule name:</p>
                    <input type="text" name="schedule-name-update" required>
                    <p>select duration:</p>
                    <select name="schedule-duration-update">
                        <c:forEach var="i" begin="1" end="7" step="1" varStatus ="status">
                            <option value="${i}">"${i}"</option>
                        </c:forEach>
                    </select> days
                    <br>
                    <select name="is-public-update">
                        <option value="true">public</option>
                        <option value="false">private</option>
                    </select>
                    <br>
                    <button onclick="onSubmitModifyScheduleButton()" type="submit">update schedule</button>
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
                    <br>
                    <button id="click-task-button-create" onclick="onCreateTaskButton()" type="submit">create</button>
                    <button id="cancel-create-button" onclick="onCancelButtonClicked()">cancel</button>
                </form>
            </div>
            <div id="modify-task" class="hidden content">
                <form id="modify-task-form" onsubmit="return false;">
                    <p>task name:</p>
                    <input type="text" name="task-name" required>
                    <p>task description:</p>    
                    <input type="text" name="task-description" required>
                    <br>
                    <button onclick="onModifyTaskButton()" type="submit">update task</button>
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
    <div id="schedule_content" class="hidden content">
        <h1 id="schedule-name"></h1>
    </div>
    <div id="schedule" class="hidden content">
    </div>
</div>
</div>
</body>
</html>
