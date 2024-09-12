<%--
  Created by IntelliJ IDEA.
  User: Windows 10
  Date: 23-12-2023
  Time: 6:48 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>

    <style>
        body{
            text-align: center;
        }
        h1{
        position:relative;
            top:35%;

        }
        .backLogin{
        position:relative;
            top:40%;
            padding:20px;
            width:100px;
            height:70px;
            background-color:#255ed1;
            font-size:18px;
            border:0px;
        }
        .backLogin a {
            color:white;
        }
    </style>
</head>
<body>
    <h1>THANKS FOR YOUR JOIN</h1>
    <% session.removeAttribute("errorRegis"); %>
    <button class="backLogin"><a href="login" style="text-decoration: none">Login</a></button>
</body>
</html>
