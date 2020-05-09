<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Изменить данные пользователя</title>
</head>
<body>

Редактировать пользователя

<form action="/users/update" method="post">
    <input type="hidden" name = "id" value="${user.id}">
    <input type="text" name="name" value="${user.name}" placeholder=${user.name}>
    <input type="text" name="password" value="${user.password}" placeholder=${user.password}>
    <input type="text" name="money" value="${user.money}" placeholder=${user.money}>
    <input type="submit" value="Обновить">
</form>

<p> ${UpdateUserException}</p>

</body>
</html>
