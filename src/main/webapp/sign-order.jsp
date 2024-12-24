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
<style>
    .button {

        --border_radius: 9999px;
        --transtion: 0.3s ease-in-out;
        --offset: 2px;

        cursor: pointer;
        position: relative;

        display: flex;
        align-items: center;
        gap: 0.5rem;
        font-size:19px;
        transform-origin: center;

        padding: 0.5rem 1rem;

        border: none;
        border-radius: var(--border_radius);
        transform: scale(calc(1 + (var(--active, 0) * 0.1)));

        transition: transform var(--transtion);
    }

    .button::before {
        content: "";
        position: absolute;
        top: 50%;
        left: 50%;
        transform: translate(-50%, -50%);
        width: 100%;
        height: 100%;

        border-radius: var(--border_radius);
        box-shadow: inset 0 0.5px hsl(0, 0%, 100%), inset 0 -1px 2px 0 hsl(0, 0%, 0%),
        0px 4px 10px -4px hsla(0 0% 0% / calc(1 - var(--active, 0))),
        0 0 0 calc(var(--active, 0) * 0.375rem) hsl(53.13deg 5.89% 2.97% / 75%);

        transition: all var(--transtion);
        z-index: 0;
    }

    .button::after {
        content: "";
        position: absolute;
        top: 50%;
        left: 50%;
        transform: translate(-50%, -50%);

        width: 100%;
        height: 100%;


    radial-gradient(at 100% 100%, hsla(266, 36%, 60%, 1) 0px, transparent 50%),
    radial-gradient(at 22% 91%, hsla(266, 36%, 60%, 1) 0px, transparent 50%);
        background-position: top;

        opacity: var(--active, 0);
        border-radius: var(--border_radius);
        transition: opacity var(--transtion);
        z-index: 2;
    }

    .button:is(:hover, :focus-visible) {
        --active: 1;
    }
    .button:active {
        transform: scale(1);
    }

    .button .dots_border {
        --size_border: calc(100% + 2px);

        overflow: hidden;

        position: absolute;
        top: 50%;
        left: 50%;
        transform: translate(-50%, -50%);

        width: var(--size_border);
        height: var(--size_border);
        background-color: transparent;

        border-radius: var(--border_radius);
        z-index: -10;
    }

    .button .dots_border::before {
        content: "";
        position: absolute;
        top: 30%;
        left: 50%;
        transform: translate(-50%, -50%);
        transform-origin: left;
        transform: rotate(0deg);

        width: 100%;
        height: 2rem;
        background-color: white;

        mask: linear-gradient(transparent 0%, white 120%);
        animation: rotate 2s linear infinite;
    }

    @keyframes rotate {
        to {
            transform: rotate(360deg);
        }
    }

    .button .sparkle {
        position: relative;
        z-index: 10;

        width: 1.75rem;
    }

    .button .sparkle .path {
        fill: currentColor;
        stroke: currentColor;

        transform-origin: center;

        color: hsl(0, 0%, 100%);
    }

    .button:is(:hover, :focus) .sparkle .path {
        animation: path 1.5s linear 0.5s infinite;
    }

    .button .sparkle .path:nth-child(1) {
        --scale_path_1: 1.2;
    }
    .button .sparkle .path:nth-child(2) {
        --scale_path_2: 1.2;
    }
    .button .sparkle .path:nth-child(3) {
        --scale_path_3: 1.2;
    }

    @keyframes path {
        0%,
        34%,
        71%,
        100% {
            transform: scale(1);
        }
        17% {
            transform: scale(var(--scale_path_1, 1));
        }
        49% {
            transform: scale(var(--scale_path_2, 1));
        }
        83% {
            transform: scale(var(--scale_path_3, 1));
        }
    }
    #runTool{
        margin:auto;
    }
</style>
<body>
<%--<jsp:include page="layout/header.jsp"/>--%>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>

<!-- jQuery -->
<script src="https://code.jquery.com/jquery-3.3.1.min.js"></script>
<div style="margin-top: 10%" class="container w-50 p-4 border rounded shadow">
    <!-- Tiêu đề -->
    <h3 class="text-center text mb-4">Mã Hash đơn hàng của bạn</h3>
    <div class="d-flex align-items-center justify-content-center gap-3 mt-5">
        <!-- Mã Hash -->
        <p class="fs-5 fw-semibold bg-light text rounded px-3 py-2 shadow-sm">
            <%= (String) session.getAttribute("orderHashed") %>
        </p>
        <!-- Nút Copy -->
        <button style="margin-top: -13px;margin-left: 20px"
                class="button" id="copyButton">
            <svg viewBox="0 0 512 512" class="svgIcon" height="1em">
                <path d="M288 448H64V224h64V160H64c-35.3 0-64 28.7-64 64V448c0 35.3 28.7 64 64 64H288c35.3 0 64-28.7 64-64V384H288v64zm-64-96H448c35.3 0 64-28.7 64-64V64c0-35.3-28.7-64-64-64H224c-35.3 0-64 28.7-64 64V288c0 35.3 28.7 64 64 64z"></path>
            </svg>
            <span>COPY</span>
        </button>
    </div>

    <!-- Chữ ký -->
    <div class="tool">
        <button id="runTool" class="button btn-primary" type="button">Tool tạo chữ ký</button>
    </div>
    <div class="input-group mt-2 mb-2 w-50" style="margin: 23%">
        <div style="margin-bottom: 10px; font-size: 19px;">
            <label for="signalInput">Chữ ký</label>
            <textarea id="signalInput"  name="signalKey" placeholder="Nhập chữ ký ở đây" style="width: 160%; height: 100px;font-size: 18px"></textarea>
        </div>
    </div>

    <!-- Lỗi và Nút Xác Nhận -->
    <div class="text-center mt-4" style="margin-left:42%;">
        <span id="errorSign" class="text-danger d-block mb-2"></span>
        <button class="btn-success button">Xác nhận</button>
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
            textArea.remove();
            // alert("Đã copy");
            var originalText = $("#copyButton").html();
            var originalClass = $("#copyButton").attr("class");
            $("#copyButton").html("Đã copy mã hash");
            $("#copyButton").removeClass(originalClass).addClass("btn btn-secondary");
            setTimeout(function () {
                $("#copyButton").html(originalText);
                $("#copyButton").removeClass("btn-secondary").addClass(originalClass);
            }, 800);
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
