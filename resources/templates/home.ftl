<html>
<head>
    <link rel="stylesheet" href="/static/styles.css">
</head>
    <body>
        <div class="homeContainer">
            <span class="homeHeader">Привет! Введи пароль, чтобы войти в комнату ожидания:</span>
        </div>
        <#if notification??>
            <p class="wrongAnswer">Нужно, чтобы подключились оба. Подождём</p>
            <p class="wrongAnswer">${notification}</p>
        </#if>
        <form action="/" method="post" enctype="application/x-www-form-urlencoded">
            <div>Password:</div>
            <div><input type="text" name="password" /></div>
            <div><input type="submit" value="Enter" /></div>
        </form>
    </body>
</html>