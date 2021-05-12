<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
<title>Hello world!</title>
</head>
<body>
    Greeting: ${greeting}

    <form action="./users" method="POST">
        Username:
        <input type="text" name="username"><br>
        <input type="submit" value="Add user">
    </form>
</body>
</html>