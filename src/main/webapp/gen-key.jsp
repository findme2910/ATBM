<%@ page import="java.util.List" %>
<%@ page import="bean.*" %>
<%@ page import="dao.OrdersDAO" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="description" content="Ogani Template">
    <meta name="keywords" content="Ogani, unica, creative, html">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <link rel="icon" type="image/x-icon" href="assets/img/logo.png">
    <title>Vườn phố</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
    <link rel="stylesheet" href="assets/css/style.css" type="text/css">
    <style>
        .btn-custom {
            background-color: #7FAD39;
            color: white;
        }

        .btn-custom:hover {
            background-color: white;
            color: #7FAD39;
            border: 1px solid #7FAD39;
        }

        .key {
            margin: 20px auto;
            width: 100%;
            max-width: 600px;
            position: relative;
        }

        textarea {
            width: 100%;
            height: 150px;
            resize: none;
            padding-right: 40px;
        }

        .icon {
            position: absolute;
            top: 40px;
            right: 10px;
            cursor: pointer;
        }

        .text-center {
            padding-top: 20px;
        }
        .container.mt-lg-5{
            margin-top: 0px !important;
            padding-top: 50px;
            background-color: #78ff78;
            height: 100%;
            max-width: 800px !important;
        }
        body {
            /*background-color: #78ff78;*/
        }
        .btn-block {
            display: block;
             width: 50%;
        }

    </style>
</head>
<%
    User user = (User) session.getAttribute("user");
%>
<body>
<%--<jsp:include page="layout/header.jsp"/>--%>
<div class="container mt-lg-5 ">
    <div class="text-center">
        <h4 class="mb-0">Genkey User: <%= user.getEmail() %></h4>
    </div>
    <div class="key">
        <label for="publicKey">Khóa công khai:</label>
        <div>
            <textarea name="publicKey" id="publicKey" readonly></textarea>
            <div>

                <i style="margin-right: 50px" class="btn btn-secondary fas fa-copy icon" title="Sao chép"
                   ></i>
                <i class="btn btn-primary  fas fa-save icon" title="Lưu"></i>
            </div>
        </div>
    </div>

    <div class="key">
        <label for="privatekey">Khóa riêng tư:</label>
        <div>
            <textarea name="privatekey" id="privatekey" readonly></textarea>
            <div>

                <i style="margin-right: 50px" class="btn btn-secondary fas fa-copy icon" title="Sao chép"
                  ></i>
                <i class="btn btn-primary  fas fa-save icon" title="Lưu"></i>
            </div>
        </div>
    </div>
    <div class="key">
    <div class="d-flex justify-content-sm-between">
        <button class="btn btn-block btn-secondary" style="background-color: #7fad39; border: #7fad39;">
            <i class="fa fa-sign-out"></i>
            <a href="user-profile.jsp" style="text-decoration: none; text-underline: none; color: white;">Thoát</a>
        </button>
        <div>

        <div class="d-flex justify-content-end">
            <button type="button" class="btn btn-primary" id="btnGenKey">
                <i class="fa fa-key"></i>
                Tạo khóa
            </button>
        </div>

</div>

<script>


    $(document).ready(function () {
        document.getElementById('btnGenKey').addEventListener('click', function () {
            document.getElementById('publicKey').textContent = 'Public key';
            document.getElementById('privatekey').textContent = 'Private key';
        });

    });

</script>

<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.5.2/dist/umd/popper.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.2/js/all.min.js"></script>
</body>
</html>