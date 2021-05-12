<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
<title>List users</title>
</head>
<body>
    <table border="2">
        <tr>
            <th>Id</th>
            <th>Name</th>
        </tr>
    <c:forEach items="${users}" var="user" varStatus="loopCounter">
        <tr>
            <td>
                <c:out value="${loopCounter.index + 1}" />
            </td>
            <td>
                <c:out value="${user}" />
            </td>
        </tr>
    </c:forEach>
    </table>

    <form action="./" method="GET">
        <input type="submit" value="Back to form">
    </form>
</body>
</html>