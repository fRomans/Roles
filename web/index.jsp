<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page session="false" %>
<html>
<head>
  <title>Здравствуйте!</title>
</head>
<body>
Если вы хотите начать работу введите имя и пароль:<br>

<form action = "/admin" method="post">
  <input type="text" name="name" required placeholder="login"><br>
  <input type="text" name="password" required placeholder="password"><br><br>
  <input type="submit" value="ВОЙТИ">
</form>

<p> ${nodata}</p>
</body>
</html>