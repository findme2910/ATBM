<%@ page import="java.util.List" %>
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
            margin-top: 20px;
        }
    </style>
</head>
<body>
<%--<jsp:include page="layout/header.jsp"/>--%>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>

<div class="container mt-lg-5">

    <div class="text-center">
        <button type="button" class="btn btn-custom" id="btnGenKey">Tạo khóa</button>
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
</div>
<script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
<script>
    $(document).ready(function () {
        $('#btnGenKey').click(function () {

            $.ajax({
                url: 'SignOrder',
                type: 'GET',
                data: {action:'genkey'},
                success: function (data) {
                    $('#publicKey').val(data.publicKey);
                    $('#privatekey').val(data.privateKey);
                },
                error: function (e) {
                    const text = e.responseText;
                    Swal.fire({
                        titleText: text,
                        icon: "error"
                    });
                }
            });
        });
    });


</script>

<!-- jQuery -->
<script src="https://code.jquery.com/jquery-3.3.1.min.js"></script>

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