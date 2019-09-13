<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>Редактировать объявление</title>
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

    <script>
        function validate() {
            var result = true;
            if ($('#carMake').val() === ''
                || $('#carModel').val() === ''
                || $('#year').val() === ''
                || $('#price').val() === ''
                || $('#carBody').val() === ''
                || $('#engine').val() === ''
                || $('#transmission').val() === ''
                ||$('#desc').val() === '') {
                alert('Заполните все поля.');
                result = false;
            }
            if ($('#year').val() > new Date().getFullYear && $('#year').val() < 1900) {
                alert('Введите год с 1900 до текущего.');
                result = false;
            }
            return result;
        }
    </script>
</head>

<body>
<div class="container">
    <div class="row">
        <div class="col-sm-8 offset-sm-1">
            <form action="${pageContext.request.contextPath}/application-update" enctype="multipart/form-data" method="post">
                <legend>Редактируем объявление</legend>
                <div class="form-group">
                    <label for="status">Статус</label>
                    <c:if test="${!requestScope.application.sold}">
                        <select class="form-control col-sm-6" id="status" name="status">
                            <option value="false" selected>Не продано</option>
                            <option value="true">Продано</option>
                        </select>
                    </c:if>
                    <c:if test="${requestScope.application.sold}">
                        <select class="form-control col-sm-6" id="status" name="status">
                            <option value="false">Не продано</option>
                            <option value="true" selected>Продано</option>
                        </select>
                    </c:if>
                </div>

                <div class="form-group">
                    <label for="carMake">Марка</label>
                    <select class="form-control col-sm-6" id="carMake" name="carMake" onchange="getModels()">
                        <c:forEach items="${applicationScope.carMakes}" var="carMake">
                            <c:if test="${requestScope.application.car.carMake.id == carMake.id}">
                                <option value="<c:out value="${carMake.id}"/>" selected><c:out value="${carMake.name}"/></option>
                            </c:if>
                            <c:if test="${requestScope.application.car.carMake.id != carMake.id}">
                                <option value="<c:out value="${carMake.id}"/>"><c:out value="${carMake.name}"/></option>
                            </c:if>
                        </c:forEach>
                    </select>
                </div>

                <div class="form-group">
                    <label for="carModel">Модель</label>
                    <select class="form-control  col-sm-6" id="carModel" name="carModel">
                        <option value="<c:out value="${requestScope.application.car.carModel.id}"/>" selected>
                            <c:out value="${requestScope.application.car.carModel.name}"/>
                        </option>
                    </select>
                </div>

                <div class="form-group">
                    <label for="year">Год выпуска</label>
                    <input type="text" class="form-control  col-sm-6" id="year" name="year" value="<c:out value="${requestScope.application.car.year}"/>"/>
                </div>

                <div class="form-group">
                    <label for="year">Цена (руб.)</label>
                    <input type="text" class="form-control  col-sm-6" id="price" name="price" value="<c:out value="${requestScope.application.car.price}"/>"/>
                </div>

                <div class="form-group">
                    <label for="carBody">Кузов</label>
                    <select class="form-control  col-sm-6" id="carBody" name="carBody">
                        <c:forEach items="${applicationScope.carBodies}" var="carBody">
                            <c:if test="${requestScope.application.car.carBody.id == carBody.id}">
                                <option value="<c:out value="${carBody.id}"/>" selected><c:out value="${carBody.name}"/></option>
                            </c:if>
                            <c:if test="${requestScope.application.car.carBody.id != carBody.id}">
                                <option value="<c:out value="${carBody.id}"/>"><c:out value="${carBody.name}"/></option>
                            </c:if>
                        </c:forEach>
                    </select>
                </div>

                <div class="form-group">
                    <label for="engine">Двигатель</label>
                    <select class="form-control  col-sm-6" id="engine" name="engine">
                        <c:forEach items="${applicationScope.engines}" var="eng">
                            <c:if test="${requestScope.application.car.engine.id == eng.id}">
                                <option value="<c:out value="${eng.id}"/>" selected><c:out value="${eng.name}"/></option>
                            </c:if>
                            <c:if test="${requestScope.application.car.engine.id != eng.id}">
                                <option value="<c:out value="${eng.id}"/>"><c:out value="${eng.name}"/></option>
                            </c:if>
                        </c:forEach>
                    </select>
                </div>

                <div class="form-group">
                    <label for="transmission">Коробка передач</label>
                    <select class="form-control  col-sm-6" id="transmission" name="transmission">
                        <c:forEach items="${applicationScope.transmissions}" var="tr">
                            <c:if test="${requestScope.application.car.transmission.id == tr.id}">
                                <option value="<c:out value="${tr.id}"/>" selected><c:out value="${tr.name}"/></option>
                            </c:if>
                            <c:if test="${requestScope.application.car.transmission.id != tr.id}">
                                <option value="<c:out value="${tr.id}"/>"><c:out value="${tr.name}"/></option>
                            </c:if>
                        </c:forEach>
                    </select>
                </div>

                <div class="form-group">
                    <label class="control-label" for="desc">Текст</label>
                    <textarea class="form-control" id="desc" name="desc" rows="4"><c:out value="${requestScope.application.description}"/></textarea>
                </div>

                <div class="form-group">
                    <label class="control-label" for="photo">Новое фото</label>
                    <br/>
                    <input type="file" name="photo" id="photo"/>
                </div>

                <br/>

                <input type="hidden" id="applicationId" name="applicationId" value="<c:out value="${requestScope.application.id}"/>"/>
                <input type="hidden" id="carId" name="carId" value="<c:out value="${requestScope.application.car.id}"/>"/>

                <button type="submit" class="btn btn-success" onclick="return validate()">Сохранить объявление</button>
            </form>
            <br/>
            <br/>

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