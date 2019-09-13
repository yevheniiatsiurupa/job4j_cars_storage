<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>Вход на сайт</title>
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
            <c:if test="${error != ''}">
                <div style="background-color: lightpink">
                    <c:out value="${error}"/>
                </div>
            </c:if>
            <form action="${pageContext.request.contextPath}/login" method="post">
                <legend>Вход на сайт</legend>
                <div class="form-group">
                    <label for="login">Логин</label>
                    <input type="text" class="form-control" id="login" name="login" placeholder="Введите логин">
                </div>
                <div class="form-group">
                    <label for="password">Пароль</label>
                    <input type="password" class="form-control" id="password" name="password" placeholder="Введите пароль">
                </div>
                <button type="submit" class="btn btn-success" onclick="return validate()">Войти</button>
            </form>
        </div>
    </div>
    <br/>
    <br/>

    <div class="row">
        <div class="col-sm-5 offset-sm-2">
            <form action="${pageContext.request.contextPath}/account-create" method="get">
                <div class="form-group">
                    <button type="submit" class="btn btn-primary">Создать аккаунт</button>
                </div>
            </form>
        </div>
    </div>

    <div class="row">
        <div class="col-sm-5 offset-sm-2">
            <form action="${pageContext.request.contextPath}/start" method="get">
                <div class="form-group">
                    <button type="submit" class="btn btn-primary">На главную</button>
                </div>
            </form>
        </div>
    </div>
</div>
</body>
</html>