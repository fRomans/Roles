<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Удалить пользователя</title>
</head>
<body>

Вы действительно хотите удалить пользователя ${param.id}?
<form action="/admin/delete" method="post">
<input type="hidden" name="id" value="${param.id}">
<input type="submit" value="Удалить">
</form>

<p> ${DeleteUserException}</p>

</body>
</html>
