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
            <h3>Мой аккаунт</h3>
            <br/>
            <table class="table">
                <tbody>
                <tr class="table-light">
                    <th>Имя:</th>
                    <td><c:out value="${sessionScope.account.name}"/></td>
                </tr>
                <tr class="table-light">
                    <th>Логин:</th>
                    <td><c:out value="${sessionScope.account.login}"/></td>
                </tr>
                <tr class="table-light">
                    <th>Email:</th>
                    <td><c:out value="${sessionScope.account.email}"/></td>
                </tr>
                </tbody>
            </table>

            <div class="col-sm-6">
                <form action="${pageContext.request.contextPath}/account-update" method="get">
                    <button type="submit" class="btn btn-sm btn-outline-success">Редактировать аккаунт</button>
                </form>
            </div>
            <br/>
            <div class="col-sm-6">
                <form action="${pageContext.request.contextPath}/account-delete" method="get">
                    <button type="submit" class="btn btn-sm btn-outline-danger">Удалить аккаунт</button>
                </form>
            </div>
        </div>
    </div>
    <br/>

    <div class="row">
        <div class="col-sm-7 offset-sm-2">
            <h3>Мои объявления</h3>
            <c:if test="${sessionScope.applSize == 0}">
                <p>У вас пока нет добавленных объявлений.</p>
                <form action="${pageContext.request.contextPath}/application-create" method="get">
                    <div class="form-group">
                        <div>
                            <button type="submit" class="btn btn-sm btn-outline-success">Добавить объявление</button>
                        </div>
                    </div>
                </form>
            </c:if>

            <c:if test="${sessionScope.applSize > 0}">
                <table class="table" id="table">
                    <tbody id="rows">
                    <c:forEach items="${sessionScope.account.applications}" var="appl">
                        <tr class="table-light">
                            <td width="50%">
                                <b>
                                    <c:out value="${appl.car.carMake.name}"/>
                                    <c:out value="${appl.car.carModel.name}"/>,
                                    <c:out value="${appl.car.year}"/> г.
                                </b>
                            </td>
                            <td>
                                <form action="${pageContext.request.contextPath}/application-update" method="get">
                                    <button type="submit" class="btn btn-sm btn-outline-success">Редактировать</button>
                                    <input type="hidden" name="id" value="<c:out value="${appl.id}"/>"/>
                                </form>
                            </td>
                            <td>
                                <form action="${pageContext.request.contextPath}/application-delete" method="get">
                                    <button type="submit" class="btn btn-sm btn-outline-danger">Удалить</button>
                                    <input type="hidden" name="id" value="<c:out value="${appl.id}"/>"/>
                                </form>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </c:if>
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