<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>Мой аккаунт</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://bootswatch.com/4/minty/bootstrap.css" media="screen">
    <link rel="stylesheet" href="https://bootswatch.com/_assets/css/custom.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>

    <style>
        body {
            padding-top: 60px;
            padding-bottom: 120px;
        }
    </style>
</head>

<body>
<div class="container">
    <div class="row">
        <div class="col-sm-7 offset-sm-2">
            <h3>Аккаунты</h3>
                <table class="table" id="table">
                    <thead>
                    <tr>
                        <th class="table-active">ID</th>
                        <th class="table-active">Name</th>
                        <th class="table-active">Login</th>
                        <th class="table-active"></th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${requestScope.accounts}" var="account">
                        <tr class="table-light">
                            <td><c:out value="${account.id}"/></td>
                            <td><c:out value="${account.name}"/></td>
                            <td><c:out value="${account.login}"/></td>
                            <td>
                                <form action="${pageContext.request.contextPath}/account-page-admin" method="get">
                                    <button type="submit" class="btn btn-sm btn-info">Перейти</button>
                                    <input type="hidden" name="login" value="<c:out value="${account.login}"/>"/>
                                </form>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
        </div>
    </div>
    <br/>
    <br/>

    <div class="row">
        <div class="col-sm-7 offset-sm-2">
            <div>
                <form action="${pageContext.request.contextPath}/start" method="get">
                    <button type="submit" class="btn btn-sm btn-success">Вернуться на главную</button>
                </form>
            </div>
            <br/>
            <div>
                <form action="${pageContext.request.contextPath}/logout" method="get">
                    <button type="submit" class="btn btn-sm btn-success">Выйти из аккаунта</button>
                </form>
            </div>
        </div>
    </div>
</div>
</body>
</html>