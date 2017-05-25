<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <link rel="stylesheet" type="text/css" href="./css/default.css"/>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
    <script type="text/javascript" src="./js/main.js"></script>
</head>
<body>
<%--<c:if test="${not empty path}">
    <table>
        <caption>Current directory: ${path}</caption>
        <tr>
            <th class="col_type">Type</th>
            <th class="col_name">Name</th>
            <th class="col_size">Size(bytes)</th>
            <th class="col_size">Download</th>
        </tr>
        <tr>
            <c:if test="${isChild}">
        <tr>
            <td class="col_type"></td>
            <td class="col_name">
                <a href="/fmanager?path=${parentPath}">..</a>
            </td>
            <td class="col_size"></td>
            <td class="col_download"></td>
        </tr>
        </c:if>

        <c:if test="${not empty contents}">
            <c:forEach items="${contents}" var="file">
                <tr>
                    <td class="col_type"><c:out value="${file.isDirectory() ? '+': '-'}"/></td>
                    <td class="col_name">
                        <c:if test="${file.isDirectory() == true}">
                            <c:url value="/fmanager" var="url">
                                <c:param name="path"
                                         value="${file.getRelativePath()}${separator}${file.getName()}"/>
                            </c:url>
                            <a href="${url}"
                               title="${file.getRelativePath()}${file.getName()}">${file.getName()}</a>
                        </c:if>
                        <c:if test="${file.isFile() == true}">
                            <c:out value="${file.getName()}"/>
                        </c:if>
                    </td>
                    <td class="col_size"><c:out value="${file.getSize()}"/></td>
                    <td class="col_download">
                        <c:if test="${file.isFile() == true}">
                            <form method="POST" action="/download" class="download">
                                <input type="hidden" value="${path}" name="path"/><br/>
                                <input type="hidden" value="${file.getName()}" name="fileName"/><br/>
                                <input type="submit" value="Download" name="download" id="download"/>
                            </form>
                        </c:if>
                    </td>
                </tr>
            </c:forEach>
        </c:if>
        </tr>
    </table>
    <br/>
    <br/>
    <form method="POST" action="/upload" enctype="multipart/form-data">
        File: <input type="file" name="file" id="file"/> <br/>
        <input type="hidden" value="${path}" name="path"/><br/>
        <input type="submit" value="Upload" name="upload" id="upload"/>
    </form>
</c:if>--%>
</body>
</html>