`<%--
  Created by IntelliJ IDEA.
  User: 84828
  Date: 4/21/2024
  Time: 1:01 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page import="bean.*" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="description" content="Ogani Template">
    <meta name="keywords" content="Ogani, unica, creative, html">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <link rel="icon" type="image/x-icon" href="assets/img/logo.png">
    <title>Ký đơn hàng</title>
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

</head>
<body>
<%--<jsp:include page="layout/header.jsp"/>--%>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>

<!-- jQuery -->
<script src="https://code.jquery.com/jquery-3.3.1.min.js"></script>
<div style="margin-top: 10%" class="container w-50 p-4 border rounded shadow">
    <!-- Tiêu đề -->
    <h3 class="text-center text mb-4">Đây là mã Hash đơn hàng của bạn:</h3>
    <div class="d-flex align-items-center justify-content-center gap-3 mt-5">
        <!-- Mã Hash -->
        <p class="fs-5 fw-semibold bg-light text rounded px-3 py-2 shadow-sm">
            <%= request.getAttribute("hash") %>
        </p>
        <!-- Nút Copy -->
        <button style="margin-top: -13px;margin-left: 20px"
                class="  btn btn-outline-secondary d-flex align-items-center gap-2" id="copyButton">
            <svg viewBox="0 0 512 512" class="svgIcon" height="1em">
                <path d="M288 448H64V224h64V160H64c-35.3 0-64 28.7-64 64V448c0 35.3 28.7 64 64 64H288c35.3 0 64-28.7 64-64V384H288v64zm-64-96H448c35.3 0 64-28.7 64-64V64c0-35.3-28.7-64-64-64H224c-35.3 0-64 28.7-64 64V288c0 35.3 28.7 64 64 64z"></path>
            </svg>
            <span>COPY</span>
        </button>
    </div>

    <!-- Chữ ký -->
    <h2 class="text-center text-success mt-5">Chữ ký của bạn</h2>
    <div class="input-group mt-5 mb-5 w-50" style="margin-left: 250px">
        <div class="input-group-prepend">
            <button id="runTool" class="btn btn-outline-danger" type="button">Tool</button>
        </div>
        <input type="text" class="form-control" placeholder="Dùng tool để lấy chữ kí và nhập vào đây " aria-label=""
               aria-describedby="basic-addon1">
    </div>

    <!-- Lỗi và Nút Xác Nhận -->
    <div class="text-center mt-4">
        <span id="errorSign" class="text-danger d-block mb-2"></span>
        <button class="btn btn-success px-4">Xác nhận</button>
    </div>
</div>

<script>
    $(document).ready(function () {
        $("#copyButton").click(function () {
            var copyText = document.getElementById("copyButton");
            var textArea = document.createElement("textarea");
            textArea.value = copyText.previousElementSibling.innerText;
            document.body.appendChild(textArea);
            textArea.select();
            document.execCommand("Copy");
            // textArea.remove();
        });

        $("#runTool").click(function () {
            $.ajax({
                url: 'Tool',
                type: 'GET',
                success: function (data) {
                    console.log(data);
                }
            });
        });
    });
</script>
<%--<jsp:include page="layout/footer.jsp"/>--%>
<!-- popper -->
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js"></script>
<!-- Bootstrap JS -->
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
<!-- DataTables JS with Bootstrap -->
<script src="https://cdn.datatables.net/1.10.24/js/jquery.dataTables.min.js"></script>
<script src="https://cdn.datatables.net/1.10.24/js/dataTables.bootstrap4.min.js"></script>
</body>

</html>
`