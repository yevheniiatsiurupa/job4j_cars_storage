<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>Создать аккаунт</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://bootswatch.com/4/minty/bootstrap.css" media="screen">
    <link rel="stylesheet" href="https://bootswatch.com/_assets/css/custom.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>

    <style>
        body {
            padding-top: 60px;
        }
    </style>
    <script>
        function validate() {
            var result = true;
            if ($('#name').val() === '') {
                alert($('#name').attr('placeholder'));
                result = false;
            }
            if ($('#email').val() === '') {
                alert($('#email').attr('placeholder'));
                result = false;
            }
            if ($('#login').val() === '') {
                alert($('#login').attr('placeholder'));
                result = false;
            }
            if ($('#password').val() === '') {
                alert($('#password').attr('placeholder'));
                result = false;
            }
            return result;
        }
    </script>
</head>

<body>
<div class="container">
    <div class="row">
        <div class="col-sm-5 offset-sm-2">
            <form action="${pageContext.request.contextPath}/account-create" method="post">
                <legend>Регистрация</legend>
                <div class="form-group">
                    <label for="name">ФИО</label>
                    <input type="text" class="form-control" id="name" name="name" placeholder="Введите ФИО">
                </div>
                <div class="form-group">
                    <label for="email">Email</label>
                    <input type="text" class="form-control" id="email" name="email" placeholder="Введите email">
                </div>
                <div class="form-group">
                    <label for="login">Логин</label>
                    <input type="text" class="form-control" id="login" name="login" placeholder="Введите логин">
                </div>
                <div class="form-group">
                    <label for="password">Пароль</label>
                    <input type="password" class="form-control" id="password" name="password" placeholder="Введите пароль">
                </div>
                <c:if test="${sessionScope.account.role == 'admin'}">
                    <div class="form-group">
                        <label class="control-label" for="role">Role:</label>
                        <div>
                            <select class="form-control" id="role" name="role">
                                <option value="user">user</option>
                                <option value="admin">admin</option>
                            </select>
                        </div>
                    </div>
                </c:if>
                <c:if test="${sessionScope.account.role != 'admin'}">
                    <input type="hidden" name="role" value="user"/>
                </c:if>
                <button type="submit" class="btn btn-success" onclick="return validate()">Регистрация</button>
            </form>
        </div>
    </div>
</div>
</body>
</html>