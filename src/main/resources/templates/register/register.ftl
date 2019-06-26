<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>核销员注册</title>
    <link rel="stylesheet" href="/css/index.css">
    <style>
        *{
            margin: 0;
            padding: 0;
        }
    </style>
</head>

<body>
<form action="/scan/register/index" method="post">
    <di class="forms">
        <h3>欢迎注册</h3><p>核销员</p>
        请输入邀请码：
        <input type="text" name="authority_id">
        <input type="hidden" name="openid" value="${openid}">
        <input type="hidden" name="oAuth2AccessToken" value="${oAuth2AccessToken}">

        <div class="submission">
            <input type="submit">
        </div>
</form>
</body>

</html>