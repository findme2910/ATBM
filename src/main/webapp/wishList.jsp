<%--
  Created by IntelliJ IDEA.
  User: 84828
  Date: 4/21/2024
  Time: 1:01 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page import="java.util.List" %>
<%@ page import="bean.*" %>
<%@ page import="dao.OrdersDAO" %>
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
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.2/css/all.min.css" integrity="sha512-z3gLpd7yknf1YoNbCzqRKc4qyor8gaKU1qmn+CShxbuBusANI9QpRohGBreCFkKxLhei6S9CQXFEbbKuqLg0DA==" crossorigin="anonymous" referrerpolicy="no-referrer" />
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
        .dataTables_wrapper .dataTables_paginate .paginate_button {
            padding: 0.5rem 0.75rem;
            margin-left: -1px;
            border: 1px solid #dee2e6;
            border-radius: 0.25rem;
            color: #007bff;
            background-color: #fff;
            text-decoration: none;
            cursor:pointer;
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
        input[type="search"]{
            background-color:#fff;
        }

        .modal .modal-footer{
            background-color:#fff;

        }
        table.table td:last-child{
            font-size:14px;
        }
        a{
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
            padding:0;
            width: 100%;
        }

        .navbar ul li {
            flex:1;
            border-right:1px solid white ;
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
        .btn-view  {
            color:#ff6347;
            background-color: #ffe4e1;
        }

        .btn-view:hover {
            background-color: #ffcccb;
            color:#dc3545;
        }
        body{
            padding-right: 0!important;
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
            background-color:#cc7900;
        }
    </style>
</head>
<body>
<jsp:include page="layout/header.jsp"/>
<main>
    <div class="container mt-5">
        <div class="d-flex justify-content-between align-items-center mb-4">
            <h2 class="mb-0">Danh sách sản phẩm yêu thích</h2>
            <button id="review" class="btn-review" onclick="deleteAllItemWishlist()">Xóa toàn bộ</button>
        </div>
<%--        <div class="navbar" style="margin-bottom:25px">--%>
<%--            <ul>--%>
<%--                <li><a class="active" href="#" data-status="5">Tất cả</a></li>--%>
<%--                <li><a href="#" data-status="1">Chờ Xét Duyệt</a></li>--%>
<%--                <li><a href="#" data-status="2">Đang Đóng Gói</a></li>--%>
<%--                <li><a href="#" data-status="3">Đang Vận Chuyển</a></li>--%>
<%--                <li><a href="#" data-status="4">Đã Giao</a></li>--%>
<%--                <li><a href="#" data-status="0">Đã hủy</a></li>--%>
<%--            </ul>--%>
<%--        </div>--%>
        <table id="orderDetailsTable" class="table table-striped table-bordered" style="width:100%">
            <thead>
            <tr>
                <th style="font-weight: bold">Tên Sản Phẩm</th>
                <th style="font-weight: bold">Ảnh</th>
                <th style="font-weight: bold">Giá</th>
                <th style="width:100px;font-weight: bold">Tính Năng</th>
            </tr>
            </thead>
            <tbody>
            <%
                List<WishlistItem> wishlistItemList = (List<WishlistItem>) request.getAttribute("wishlistItemList");
                for (WishlistItem order : wishlistItemList) {
            %>
            <tr id="product-<%= order.getProducts().getId() %>">
                <td><%= order.getProducts().getProduct_name()%></td>
                <td><img src="<%= order.getProducts().getImage()%>" alt="" style="width: 110px;height: 110px"></td>
                <td><%= order.getProducts().getPrice()%></td>
                <td style=" display: flex;justify-content: center;align-items: center; border: none">
                    <button class="btn btn-view view" data-toggle="modal" style="margin: 0 5px;" onclick="redirectToProductInfo(<%= order.getProducts().getId() %>)">
                        <i class="fas fa-eye" data-toggle="tooltip" title="Xem chi tiết"></i>
                    </button>
                    <button class="btn btn-danger cancel-btn" data-toggle="modal" style="margin: 0 5px;" onclick="deleteItemWishlist(<%=order.getProducts().getId()%>)">
                        <i class="fa-solid fa-trash"></i>
                    </button>
                </td>
            </tr>
            <%
                }
            %>
            </tbody>
        </table>
    </div>


    <%-- Modal Thông Báo Lỗi --%>
    <div class="modal fade" id="errorModal" tabindex="-1" role="dialog" aria-labelledby="errorModalLabel" aria-hidden="true" style="top:150px">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="errorModalLabel">Thông Báo Lỗi</h5>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <div class="modal-body" id="errorMessage">
                    <%-- Nội dung thông báo lỗi sẽ được thêm vào đây qua AJAX --%>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-dismiss="modal">Đóng</button>
                </div>
            </div>
        </div>
    </div>
</main>
<jsp:include page="layout/footer.jsp"/>
<!-- jQuery -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script src="https://code.jquery.com/jquery-3.3.1.min.js"></script>
<!-- popper -->
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js"></script>
<!-- Bootstrap JS -->
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
<!-- DataTables JS with Bootstrap -->
<script src="https://cdn.datatables.net/1.10.24/js/jquery.dataTables.min.js"></script>
<script src="https://cdn.datatables.net/1.10.24/js/dataTables.bootstrap4.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
<script>
    function deleteItemWishlist(productId) {
        $.ajax({
            url: '/wishlistController',
            method: "POST",
            data: {
                id: productId,  // Đảm bảo tham số này khớp với servlet
                action: "remove",
            },
            success: function (response) {
                if (response.status === "success") {
                    Swal.fire({
                        position: "center",
                        icon: "success",
                        title: "Xóa Sản Phẩm Khỏi Danh Sách Yêu Thích Thành Công!",
                        showConfirmButton: false,
                        timer: 1500
                    }).then(() => {
                        // Xóa hàng chứa sản phẩm khỏi bảng
                        $(`#product-${productId}`).remove();
                    });
                } else if (response.status === "removeFailed") {
                    Swal.fire({
                        position: "center",
                        icon: "error",
                        title: "Xóa Sản Phẩm Khỏi Danh Sách Yêu Thích Không Thành Công!",
                        showConfirmButton: false,
                        timer: 1500
                    });
                } else {
                    window.location.href = '/login';
                }
            }
        });
    }
    function deleteAllItemWishlist() {
        $.ajax({
            url: '/wishlistController',
            method: "POST",
            data: {
                action: "removeAll",
            },
            success: function (response) {
                if (response.status === "success") {
                    Swal.fire({
                        position: "center",
                        icon: "success",
                        title: "Xóa Toàn Bộ Sản Phẩm Khỏi Danh Sách Yêu Thích Thành Công!",
                        showConfirmButton: false,
                        timer: 1500
                    }).then(() => {
                        $('#orderDetailsTable tbody').empty();
                        $('#orderDetailsTable tbody').append('<tr><td colspan="4" class="text-center">Danh sách sản phẩm yêu thích hiện đang trống.</td></tr>');
                    });
                } else if (response.status === "removeFailed") {
                    Swal.fire({
                        position: "center",
                        icon: "error",
                        title: "Xóa Toàn Bộ Sản Phẩm Khỏi Danh Sách Yêu Thích Không Thành Công!",
                        showConfirmButton: false,
                        timer: 1500
                    });
                } else {
                    window.location.href = '/login';
                }
            }
        });
    }
    function redirectToProductInfo(productId) {
        window.location.href = 'ProductInfor?id_product=' + productId;
    }

</script>
</body>
</html>
