<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>Добавить объявление</title>
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
            <form action="${pageContext.request.contextPath}/application-create" enctype="multipart/form-data" method="post">
                <legend>Создаем объявление</legend>
                <div class="form-group">
                    <label for="carMake">Марка</label>
                    <select class="form-control col-sm-6" id="carMake" name="carMake" onchange="getModels()">
                        <option value="" disabled selected>Выберите марку</option>
                        <c:forEach items="${applicationScope.carMakes}" var="carMake">
                            <option value="<c:out value="${carMake.id}"/>"><c:out value="${carMake.name}"/></option>
                        </c:forEach>
                    </select>
                </div>

                <div class="form-group">
                    <label for="carModel">Модель</label>
                    <select class="form-control  col-sm-6" id="carModel" name="carModel">
                        <option value="" disabled selected>Выберите модель</option>
                    </select>
                </div>

                <div class="form-group">
                    <label for="year">Год выпуска</label>
                    <input type="text" class="form-control  col-sm-6" id="year" name="year" placeholder="Введите год">
                </div>

                <div class="form-group">
                    <label for="year">Цена (руб.)</label>
                    <input type="text" class="form-control  col-sm-6" id="price" name="price" placeholder="Введите цену">
                </div>

                <div class="form-group">
                    <label for="carBody">Кузов</label>
                    <select class="form-control  col-sm-6" id="carBody" name="carBody">
                        <option value="" disabled selected>Выберите тип кузова</option>
                        <c:forEach items="${applicationScope.carBodies}" var="carBody">
                            <option value="<c:out value="${carBody.id}"/>"><c:out value="${carBody.name}"/></option>
                        </c:forEach>
                    </select>
                </div>

                <div class="form-group">
                    <label for="engine">Двигатель</label>
                    <select class="form-control  col-sm-6" id="engine" name="engine">
                        <option value="" disabled selected>Выберите тип двигателя</option>
                        <c:forEach items="${applicationScope.engines}" var="eng">
                            <option value="<c:out value="${eng.id}"/>"><c:out value="${eng.name}"/></option>
                        </c:forEach>
                    </select>
                </div>

                <div class="form-group">
                    <label for="transmission">Коробка передач</label>
                    <select class="form-control  col-sm-6" id="transmission" name="transmission">
                        <option value="" disabled selected>Выберите тип коробки передач</option>
                        <c:forEach items="${applicationScope.transmissions}" var="tr">
                            <option value="<c:out value="${tr.id}"/>"><c:out value="${tr.name}"/></option>
                        </c:forEach>
                    </select>
                </div>

                <div class="form-group">
                    <label class="control-label" for="desc">Текст</label>
                    <textarea class="form-control" id="desc" name="desc" rows="4" placeholder="Добавьте текст объявления"></textarea>
                </div>

                <div class="form-group">
                    <label class="control-label" for="photo">Фото</label>
                    <br/>
                    <input type="file" name="photo" id="photo"/>
                </div>

                <br/>
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