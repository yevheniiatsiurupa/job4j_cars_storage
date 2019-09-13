<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>Объявление</title>
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
        <div class="col-sm-7 offset-sm-1">
            <h2>
                <c:out value="${requestScope.application.car.carMake.name}"/>
                <c:out value="${requestScope.application.car.carModel.name}"/>,
                <c:out value="${requestScope.application.car.year}"/> г.
            </h2>
        </div>
        <div class="col-sm-3">
            <h2>
                <c:out value="${requestScope.application.car.price}"/> руб.
            </h2>
        </div>
    </div>
    <br/>

    <div class="row">
        <div class="col-sm-7 offset-sm-1">
            <img src="${pageContext.request.contextPath}/photo?applId=<c:out value="${requestScope.application.id}"/>"
                    alt="Фото" width="100%">
        </div>
        <div class="col-sm-4">
            <c:if test="${requestScope.application.sold}">
                <p><b>Статус:</b> Продано</p>
            </c:if>
            <c:if test="${!requestScope.application.sold}">
                <p><b>Статус:</b> Не продано</p>
            </c:if>

            <p><b>Дата добавления:</b> <c:out value="${requestScope.application.created}"/></p>
            <br/>

            <p><c:out value="${requestScope.application.description}"/></p>
        </div>
    </div>
    <br/>

    <div class="row">
        <div class="col-sm-7 offset-sm-1">
            <table class="table table-hover">
                <thead>
                <tr class="table-active">
                    <th scope="col" style="padding: 8px">Характеристики</th>
                </tr>
                </thead>
                <tbody>
                <tr class="table-light">
                    <td style="padding: 5px"><b>Марка: </b><c:out value="${requestScope.application.car.carMake.name}"/></td>
                </tr>
                <tr class="table-light">
                    <td style="padding: 5px"><b>Модель: </b><c:out value="${requestScope.application.car.carModel.name}"/></td>
                </tr>
                <tr class="table-light">
                    <td style="padding: 5px"><b>Кузов: </b><c:out value="${requestScope.application.car.carBody.name}"/></td>
                </tr>
                <tr class="table-light">
                    <td style="padding: 5px"><b>Двигатель: </b><c:out value="${requestScope.application.car.engine.name}"/></td>
                </tr>
                <tr class="table-light">
                    <td style="padding: 5px"><b>Коробка передач: </b><c:out value="${requestScope.application.car.transmission.name}"/></td>
                </tr>
                <tr class="table-light">
                    <td style="padding: 5px"><b>Год выпуска: </b><c:out value="${requestScope.application.car.year}"/></td>
                </tr>
                </tbody>
            </table>
        </div>

    </div>
    <div class="row">
            <div class="col-sm-7 offset-sm-1">
                <form action="${pageContext.request.contextPath}/start" method="get">
                    <h3>${requestScope.answer}</h3>
                    <br/>
                    <button type="submit" class="btn btn-secondary">Вернуться на главную</button>
                </form>
            </div>
    </div>
</div>
</body>
</html>