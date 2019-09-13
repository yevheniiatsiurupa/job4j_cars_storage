<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>Car Market</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://bootswatch.com/4/minty/bootstrap.css" media="screen">
    <link rel="stylesheet" href="https://bootswatch.com/_assets/css/custom.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <style>
        body {
            padding-top: 20px;
            padding-bottom: 100px;
        }
    </style>

    <%--    Скрипт для получения моделей машин соответствующих марке и обновления выпадающего списка--%>
    <script>
        function deleteOptions() {
            var parent = document.getElementById('carModel');
            var nodes = parent.childNodes;
            for (var i = 0; i < nodes.length; i++) {
                var elem = nodes[i];
                if (elem.value !== '') {
                    parent.removeChild(elem);
                    i--;
                }
            }
        }

        function getModels() {
            deleteOptions();
            var carMake = $('#carMake').val();
            $.ajax({
                type: 'POST',
                url:'./car-models',
                data: carMake,
                datatype: 'json',
                success: function (data, status, settings) {
                    var arr = data['list'];
                    for (var i = 0; i < arr.length; i++) {
                        var model = arr[i];
                        var opt = document.createElement('option');
                        opt.innerHTML = model['name'];
                        opt.value = model['id'];
                        document.getElementById('carModel').appendChild(opt);
                    }
                }
            })
        }
    </script>

    <%--    Скрипт для обновления таблицы с объявлениями на странице --%>
    <script>
        function deleteApplications() {
            var parent = document.getElementById('rows');
            var nodes = parent.childNodes;
            for (var i = 0; i < nodes.length; i++) {
                var elem = nodes[i];
                parent.removeChild(elem);
                i--;
            }
        }

        function refreshApplications (data) {
            deleteApplications();
            var arr = data['list'];
            if (arr.length !== 0) {
                for (i = 0; i < arr.length; i++) {
                    var applId = arr[i]['id'];
                    var carMake = arr[i]['car']['carMake']['name'];
                    var carModel = arr[i]['car']['carModel']['name'];
                    var carBody = arr[i]['car']['carBody']['name'];
                    var engine = arr[i]['car']['engine']['name'];
                    var transmission = arr[i]['car']['transmission']['name'];
                    var price = arr[i]['car']['price'];
                    var year = arr[i]['car']['year'];
                    var created = new Date(arr[i]['created']);

                    var fullDate =created.getFullYear() + '-' + ('0' + (created.getMonth()+1)).slice(-2)  + '-' + ('0' + created.getDate()).slice(-2);

                    $("#rows").append('<tr class="table-light"><td rowspan="4" width="35%"><img alt="Фото" width="100%"></td><td><a><b><h4></h4></b></a></td></tr>');

                    var row1 = carMake + ' ' + carModel + ', ' + year + ' г.';
                    var ref = 'application-page?applicationId=' + applId;
                    var src = 'photo?applId=' + applId;
                    $("#rows h4:last").text(row1);
                    $("#rows a:last").attr('href', ref);
                    $("#rows img:last").attr('src', src);

                    $("#rows").append('<tr><td><b></b></td></tr>');
                    var row2 = price + ' руб.';
                    $("#rows b:last").text(row2);

                    $("#rows").append('<tr><td></td></tr>');
                    var row3 = carBody + ', ' + engine + ', ' + transmission;
                    $("#rows td:last").text(row3);

                    $("#rows").append('<tr><td></td></tr>');

                    var row4 = 'Объявление добавлено ' + fullDate;
                    $("#rows td:last").text(row4);
                }
            } else {
                $("#rows").append('<tr><td></td></tr>');
                var row = 'По заданному фильтру ничего не найдено';
                $("#rows td:last").text(row);
            }
        }
    </script>

    <%--    Скрипт фильтрует объявления на странице, отправляет запрос сервлету FilterApplicationServlet,
    обновляет данные на странице--%>
    <script>
        function filterAll() {
            var carMake = $('#carMake').val();
            var carModel = $('#carModel').val();
            var carBody= $('#carBody').val();
            var engine= $('#engine').val();
            var transmission= $('#transmission').val();
            var filterType= $('#filterType').val();
            var forSend = {
                carMake: carMake,
                carModel: carModel,
                carBody: carBody,
                engine: engine,
                transmission: transmission,
                filterType: filterType
            };
            $.ajax({
                type: 'POST',
                url:'./filter',
                data: JSON.stringify(forSend),
                datatype: 'json',
                success: function (data, status, settings) {
                    console.log(data);
                    refreshApplications(data);
                }
            })
        }
    </script>
</head>

<body>
<div class="container">
    <div>
        <h1>CarMarket</h1>
        <br/>
        <div class="row">
            <div class="col-sm-3">
                <form action="${pageContext.request.contextPath}/application-create" method="get">
                    <div class="form-group">
                        <div>
                            <button type="submit" class="btn btn-outline-primary">Добавить объявление</button>
                        </div>
                    </div>
                </form>
            </div>
            <c:if test="${sessionScope.account == null}">
                <div class="col-sm-7"></div>
                <div class="col-sm-2">
                    <form action="${pageContext.request.contextPath}/login" method="get">
                        <div class="form-group">
                            <div>
                                <button type="submit" class="btn btn-primary">Войти</button>
                            </div>
                        </div>
                    </form>
                </div>
            </c:if>
            <c:if test="${sessionScope.account != null}">
                <div class="col-sm-6"></div>
                <div class="col-sm-2">
                    <c:if test="${sessionScope.account.role == 'user'}">
                        <form action="${pageContext.request.contextPath}/account-page-user" method="get">
                            <div class="form-group">
                                <div>
                                    <button type="submit" class="btn btn-info">Мой аккаунт</button>
                                </div>
                            </div>
                        </form>
                    </c:if>
                    <c:if test="${sessionScope.account.role == 'admin'}">
                        <form action="${pageContext.request.contextPath}/account-list" method="get">
                            <div class="form-group">
                                <div>
                                    <button type="submit" class="btn btn-info">Администратор</button>
                                </div>
                            </div>
                        </form>
                    </c:if>
                </div>
                <div class="col-sm-1">
                    <form action="${pageContext.request.contextPath}/logout" method="get">
                        <div class="form-group">
                            <div>
                                <button type="submit" class="btn btn-info">Выйти</button>
                            </div>
                        </div>
                    </form>
                </div>
            </c:if>
        </div>
    </div>
    <br/>
    <br/>
    <div class="col-sm-offset-1">
        <div class="row">
            <div class="col-sm-3">
                <div class="card text-white bg-primary">
                    <div class="card-header">
                        <b>Фильтры</b>
                    </div>
                    <div class="card-body">
                        <div class="form-group">
                            <select class="form-control-sm" id="filterType">
                                <option value="showAll">Показать все</option>
                                <option value="showToday">Показать за сегодня</option>
                                <option value="showPhoto">Показать с фото</option>
                            </select>
                        </div>
                        <br/>

                        <div class="form-group">
                            <select class="form-control-sm" id="carMake" name="carMake" onchange="getModels()">
                                <option value="" selected>Выбрать марку</option>
                                <c:forEach items="${applicationScope.carMakes}" var="carMake">
                                    <option value="<c:out value="${carMake.id}"/>"><c:out value="${carMake.name}"/></option>
                                </c:forEach>
                            </select>
                        </div>

                        <div class="form-group">
                            <select class="form-control-sm" id="carModel" name="carModel">
                                <option value="" selected>Выберите модель</option>
                            </select>
                        </div>

                        <div class="form-group">
                            <select class="form-control-sm" id="carBody" name="carBody">
                                <option value="" selected>Выбрать кузов</option>
                                <c:forEach items="${applicationScope.carBodies}" var="carBody">
                                    <option value="<c:out value="${carBody.id}"/>"><c:out value="${carBody.name}"/></option>
                                </c:forEach>
                            </select>
                        </div>

                        <div class="form-group">
                            <select class="form-control-sm" id="engine" name="engine">
                                <option value="" selected>Выбрать двигатель</option>
                                <c:forEach items="${applicationScope.engines}" var="eng">
                                    <option value="<c:out value="${eng.id}"/>"><c:out value="${eng.name}"/></option>
                                </c:forEach>
                            </select>
                        </div>

                        <div class="form-group">
                            <select class="form-control-sm" id="transmission" name="transmission">
                                <option value="" selected>Выбрать коробку передач</option>
                                <c:forEach items="${applicationScope.transmissions}" var="tr">
                                    <option value="<c:out value="${tr.id}"/>"><c:out value="${tr.name}"/></option>
                                </c:forEach>
                            </select>
                        </div>
                        <br/>

                        <button type="submit" class="btn btn-sm btn-dark" onclick="filterAll()">Отфильтровать</button>
                    </div>
                </div>
            </div>

            <div class="col-sm-9">
                <table class="table" id="table" style="table-layout: fixed">
                    <tbody id="rows">
                    <c:forEach items="${requestScope.applications}" var="appl">
                        <tr class="table-light">
                            <td width="35%" rowspan="4" >
                                <img src="${pageContext.request.contextPath}/photo?applId=<c:out value="${appl.id}"/>"
                                     alt="Фото" width="100%"></td>
                            <td>
                                <a href="application-page?applicationId=<c:out value="${appl.id}"/>">
                                    <b>
                                        <h4>
                                            <c:out value="${appl.car.carMake.name}"/>
                                            <c:out value="${appl.car.carModel.name}"/>,
                                            <c:out value="${appl.car.year}"/> г.
                                        </h4>
                                    </b>
                                </a>
                            </td>
                        </tr>
                        <tr><td><b><c:out value="${appl.car.price}"/> руб.</b></td></tr>
                        <tr><td>
                            <c:out value="${appl.car.carBody.name}"/>,
                            <c:out value="${appl.car.engine.name}"/>,
                            <c:out value="${appl.car.transmission.name}"/>
                        </td></tr>
                        <tr><td>Объявление добавлено <c:out value="${appl.created}"/></td></tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</div>
</body>
</html>