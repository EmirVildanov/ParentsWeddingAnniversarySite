<html>
    <head>
        <link rel="stylesheet" href="/static/styles.css">
    </head>
    <span class="homeHeader">To see the info you have to enter password</span>
    <body>
    <#if error??>
        <p class="wrongAnswer">Your password should be :</p>
        <p class="wrongAnswer">${error}</p>
    </#if>
    <form action="/login" method="post" enctype="application/x-www-form-urlencoded">
        <div>Password:</div>
        <div><input type="text" name="password" /></div>
        <div><input type="submit" value="Enter" /></div>
    </form>
    </body>
</html>