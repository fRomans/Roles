<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core_1_1" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>UserView</title>
</head>
<body>
<p>Информация о пользователе:</p><br>


<p> ПАРОЛЬ:${password}</p>
<p> ИМЯ:${login}</p>
<p> ПРАВА:${role}</p>

<a href="<c:url value='/logout' />">Logout</a>

</body>
</html>
