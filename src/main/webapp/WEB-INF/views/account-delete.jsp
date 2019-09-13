<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>Удаление аккаунта</title>
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
            <form action="${pageContext.request.contextPath}/account-delete" method="post">
                <h3>Удаление аккаунта</h3>
                <br/>
                <p>Внимание! Операция удаления аккаунта является необратимой.</p>
                <p>Все объявления пользователя будут удалены вместе с аккаунтом.</p>
                <br/>
                <button type="submit" class="btn btn-danger">Удаление</button>
            </form>
        </div>
    </div>
    <br/>
    <div class="row">
        <div class="col-sm-5 offset-sm-2">
            <form action="${pageContext.request.contextPath}/account-page-user" method="get">
                <button type="submit" class="btn btn-success">Вернуться назад</button>
            </form>
        </div>
    </div>
</div>
</body>
</html>