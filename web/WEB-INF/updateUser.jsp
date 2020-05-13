<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Изменить данные пользователя</title>
</head>
<body>

Редактировать пользователя

<form action="/admin/update" method="post">
    <input type="hidden" name = "id" value="${user.id}">
    <input type="text" name="name" value="${user.name}" placeholder=${user.name}>
    <input type="text" name="password" value="${user.password}" placeholder=${user.password}>
    <input type="text" name="money" value="${user.money}" placeholder=${user.money}>
    <select  name="role"  size="1">
        <option value="admin">admin</option>
        <option  value="user">user</option>
    <option selected="selected">${user.role}</option>

    </select>

    <input type="submit" value="Обновить">
</form>

<p> ${UpdateUserException}</p>

</body>
</html>
