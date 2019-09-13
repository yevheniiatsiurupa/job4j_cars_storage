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
</head>

<body>
<div class="container">
    <div class="row">
        <div class="col-sm-5 offset-sm-2">
            <form action="${pageContext.request.contextPath}/start" method="get">
                <h3>${requestScope.answer}</h3>
                <br/>
                <button type="submit" class="btn btn-success">Вернуться на главную</button>
            </form>
        </div>
    </div>
    <br/>
    <div class="row">
        <div class="col-sm-5 offset-sm-2">
            <form action="${pageContext.request.contextPath}/login" method="get">
                <button type="submit" class="btn btn-success">Войти в аккаунт</button>
            </form>
        </div>
    </div>
</div>
</body>
</html>