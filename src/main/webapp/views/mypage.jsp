<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Initialization Page</title>
</head>
<body>

<form method="Post" action="/fm">
    Default path: ${path}
    <br/>
    <b>Enter path:</b>
    <input type="text" name="path"/>
    <input type='submit'/>
</form>

<form method="Post" action="/fmanager">
    Default path: ${path}
    <br/>
    <b>Enter path:</b>
    <input type="text" name="path"/>
    <input type='submit'/>
</form>

</body>
</html>


