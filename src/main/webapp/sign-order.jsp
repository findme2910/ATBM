<%--
  Created by IntelliJ IDEA.
  User: ngoctaiphan
  Date: 19/12/2024
  Time: 21:30
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="description" content="Ogani Template">
    <meta name="keywords" content="Ogani, unica, creative, html">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <link rel="icon" type="image/x-icon" href="assets/img/logo.png">
    <title>Vườn phố</title>
    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
    <%--    Database css boostrap--%>
    <link href="https://cdn.datatables.net/1.10.24/css/dataTables.bootstrap4.min.css" rel="stylesheet">
    <!-- Font Awesome CSS -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.2/css/all.min.css">
    <!-- Google Fonts -->
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;500;600&display=swap" rel="stylesheet">
    <link href="https://fonts.googleapis.com/css2?family=Material+Icons" rel="stylesheet">

    <!-- Css Styles -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.2/css/all.min.css"
          integrity="sha512-z3gLpd7yknf1YoNbCzqRKc4qyor8gaKU1qmn+CShxbuBusANI9QpRohGBreCFkKxLhei6S9CQXFEbbKuqLg0DA=="
          crossorigin="anonymous" referrerpolicy="no-referrer"/>
    <link rel="stylesheet" href="assets/css/bootstrap.min.css" type="text/css">
    <link rel="stylesheet" href="assets/css/font-awesome.min.css" type="text/css">
    <link rel="stylesheet" href="assets/css/elegant-icons.css" type="text/css">
    <link rel="stylesheet" href="assets/css/nice-select.css" type="text/css">
    <link rel="stylesheet" href="assets/css/jquery-ui.min.css" type="text/css">
    <link rel="stylesheet" href="assets/css/owl.carousel.min.css" type="text/css">
    <link rel="stylesheet" href="assets/css/slicknav.min.css" type="text/css">
    <link rel="stylesheet" href="assets/css/style.css" type="text/css">
    <link rel="stylesheet" href="assets/css/Log_Regis.css">
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

        .dataTables_wrapper .dataTables_paginate .paginate_button {
            padding: 0.5rem 0.75rem;
            margin-left: -1px;
            border: 1px solid #dee2e6;
            border-radius: 0.25rem;
            color: #007bff;
            background-color: #fff;
            text-decoration: none;
            cursor: pointer;
        }

        .dataTables_wrapper .dataTables_paginate .paginate_button:hover {
            color: #0056b3;
            background-color: #e9ecef;
            border-color: #dee2e6;
        }

        .dataTables_wrapper .dataTables_filter input {
            border: 1px solid #dee2e6;
            border-radius: 0.25rem;
            padding: 0.375rem 0.75rem;
        }

        input[type="search"] {
            background-color: #fff;
        }

        .modal .modal-footer {
            background-color: #fff;

        }

        table.table td:last-child {
            font-size: 14px;
        }

        a {
            text-decoration: none !important;
        }

        .navbar {
            background-color: #fff;
            border-bottom: 1px solid #ccc;
            width: 100%;
            margin: auto;
            overflow: hidden;
        }

        .navbar ul {
            list-style: none;
            margin: 0;
            display: flex;
            justify-content: center;
            align-items: center;
            padding: 0;
            width: 100%;
        }

        .navbar ul li {
            flex: 1;
            border-right: 1px solid white;
        }

        .navbar ul li a {
            text-decoration: none;
            color: #000;
            padding: 10px;
            display: block;
            width: 100%;
            text-align: center;
        }

        .navbar a.active {
            background-color: #7fad39;
            color: white;
        }

        .navbar li:hover {
            background-color: #7fad39;
        }

        .btn-view {
            color: #ff6347;
            background-color: #ffe4e1;
        }

        .btn-view:hover {
            background-color: #ffcccb;
            color: #dc3545;
        }

        body {
            padding-right: 0 !important;
        }

        .btn-review {
            background-color: #ff9800;
            color: white;
            border: none;
            padding: 10px 20px;
            font-size: 16px;
            cursor: pointer;
            border-radius: 5px;
        }

        .btn-review:hover {
            background-color: #cc7900;
        }

        .alertWidth {
            width: 200px !important;
        }

        .aleHeight {
            height: 100px !important;
        }
        .main-div{
            background-color: #f5f5f5
        ;
        }
    </style>
</head>
<body>
<jsp:include page="layout/header.jsp"/>
<div class="container mb-lg-5 main-div" style="width:max-content;margin-top:100px">
    <div class="">
        <h5 class="mb-2" id="signOrderModalLabel"> Kí đơn hàng</h5>
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"></button>
    </div>
    <div class="">
        <table class="table table-bordered table-hover">
            <tbody>
            <tr>
                <td>
                    <div class="mb-3">
                        <label for="privateKeyTextArea" class="form-label">Nhập private key của
                            bạn</label>
                        <textarea class="form-control" id="privateKeyTextArea" rows="3"></textarea>
                    </div>
                </td>
                <td>
                    <div class="input-group mt-5">Hoặc</div>
                </td>
                <td>
                    <div class="input-group mt-5">
                        <input type="file" class="custom-file-input" onchange="handleFile(event)" id="privateKeyFile">
                        <label class="custom-file-label" for="privateKeyFile">Chọn file</label>
                        <div class="input-group-append" id="iconContainer"></div>
                    </div>
                </td>
            </tr>
            </tbody>
        </table>
    </div>
    <div class="modal-footer">
        <button class="btn btn-custom" id="cofirmSignOrder">Xác thực</button>
        <button type="button" class="btn btn-secondary" data-dismiss="modal">Đóng</button>
    </div>
</div >
<jsp:include page="layout/footer.jsp"/>
</body>
</html>
