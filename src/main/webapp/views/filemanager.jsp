<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <style>*, input[type="file"]::-webkit-file-upload-button {
        font-family: monospace
    }</style>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
</head>
<body>
<c:if test="${not empty path_attr}">
    <p>Current directory: ${path_attr} </p>
    <%--<pre>--%>
    <c:if test="${path_attr.getClass().name != 'services.Root'}">
        <c:out value="${path_attr.getClass().name}"/>
        <br/>
        <form method="post">
            <label for="search">Search Files:</label>
            <input type="text" name="search" id="search"
                   value="<c:out value="${not empty param.search ? param.search : ''}" />">
            <button type="submit">Search</button>
        </form>
        <form method="post" enctype="multipart/form-data">
            <label for="upload">Upload Files:</label>
            <button type="submit">Upload</button>
            <button type="submit" name="unzip">Upload & Unzip</button>
            <input type="file" name="upload[]" id="upload" multiple>
        </form>
    </c:if>

    <%--ToDo if  Directory--%>
    <c:if test="${path_attr.getClass().name == 'services.Directory'}">
        <c:out value="${path_attr.getClass().name}"/>
        <br/>
        <c:url value="/" var="url">
            <c:param name="path" value="${path_attr}"/>
        </c:url>
        <a href="${url}">.</a>
        <%--<a href="?path=${URLEncoder.encode(path_attr, ENCODING)}>.</a>--%>
        <%--<c:if test="${file != null}">--%>
            <%--<c:param name="qaz" value="${file}"/>--%>
        <%--</c:if>--%>
    </c:if>

    <%--/ ToDo if  getParentFile()) != null --%>
    <%--<a href="?path=URLEncoder.encode(parent.getAbsolutePath(), ENCODING)">..</a>--%>
    <%--ToDo else--%>
    <%--<a href="?path=">..</a>--%>
    <%--ToDo FOR files.listFiles()--%>
    <%--<%writer.print(child.isDirectory() ? "+ " : "  ");%>--%>
    <%--<a href="?path="URLEncoder.encode(child.getAbsolutePath(), ENCODING)" title="child.getAbsolutePath()"> child.getName() </a>--%>
    <%--ToDo isDirectory--%>
    <%--<a href="?path="URLEncoder.encode(child.getAbsolutePath(), ENCODING)"&zip title="download">&#8681;</a>--%>
    <%--</pre>--%>

</c:if>
</body>
</html>