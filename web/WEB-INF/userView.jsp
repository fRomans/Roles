<%--
  Created by IntelliJ IDEA.
  User: Ирина
  Date: 10.05.2020
  Time: 15:12
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>UserView</title>
</head>
<body>
<p>Информация о пользователе:</p><br><br>
<p>Имя: ${user.getName()}</p><br>
<p>Деньги: ${user.getName()}</p><br>
<p>Роль: ${user.getRole()}<br><br>
<a href="<c:url value='/logout' />">Logout</a>

</body>
</html>
