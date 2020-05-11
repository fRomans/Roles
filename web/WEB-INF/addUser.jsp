<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Добавить нового пользователя</title>
</head>
<body>
<form action = "/admin/add" method="post">
    <input required type="text" name="name" placeholder="имя">
    <input required type="text" name="password" placeholder="пароль">
    <input required type="text" name="money" placeholder="деньги">
    <input required type="text" name="role"  placeholder="доступ">

    <input type="submit" value="Сохранить">
</form>

<p> ${AddUserException}</p>
<p> ${NumberFormatException}</p>
</body>
</html>