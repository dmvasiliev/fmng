<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <script src="//ajax.googleapis.com/ajax/libs/jquery/3.1.0/jquery.min.js"></script>
    <script type="text/javascript">
        function valid() {
            var state = $("input[name='sex']:checked").val();
            if (!state) return $("#message").html("<font style='color:red'>Укажите ваш пол</font><br />")
            alert(state)
        }
    </script>
</head>
<body>
<h2>Hello World!</h2>
<form name="form" id="form">
    <label for="female">Женский</label>
    <input type="radio" id="female" name="sex" value="female"/>

    <label for="male">Мужской</label>
    <input type="radio" id="male" name="sex" value="male"/>
    <br/><br/>
    <span id="message"></span>
    <br/><br/>
    <input type="button" id="done" onclick="return valid()" value="Готово"/>

    <br/>
    <br/>
    <input type="text" size="40" />

</form>

</body>
</html>
