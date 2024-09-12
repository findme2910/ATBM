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
    </style>
</head>
<body>
<jsp:include page="layout/header.jsp"/>
<main>
    <div class="container mt-5">
        <h2 class="mb-4">Lịch sử mua hàng</h2>
        <div class="navbar" style="margin-bottom:25px">
            <ul>
                <li><a class="active" href="#" data-status="5">Tất cả</a></li>
                <li><a href="#" data-status="1">Chờ Xét Duyệt</a></li>
                <li><a href="#" data-status="2">Đang Đóng Gói</a></li>
                <li><a href="#" data-status="3">Đang Vận Chuyển</a></li>
                <li><a href="#" data-status="4">Đã Giao</a></li>
                <li><a href="#" data-status="0">Đã hủy</a></li>
            </ul>
        </div>
        <table id="orderDetailsTable" class="table table-striped table-bordered" style="width:100%">
            <thead>
            <tr>
                <th style="font-weight: bold">Id</th>
                <th style="font-weight: bold">Tên Người Mua</th>
                <th style="font-weight: bold">Địa Chỉ</th>
                <th style="font-weight: bold">Số Điện Thoại</th>
                <th style="font-weight: bold">Tổng tiền</th>
                <th style="font-weight: bold">Ngày Tạo</th>
                <th style="font-weight: bold">Thanh Toán</th>
                <th style="font-weight: bold">Tình Trạng Đơn Hàng</th>
                <th style="width:100px;font-weight: bold">Tính Năng</th>
            </tr>
            </thead>
            <tbody>
            <%
                List<OrderTable> listOrderTables = (List<OrderTable>) request.getAttribute("listOrder");
                for (OrderTable order : listOrderTables) {
            %>
            <tr>
                <td><%= order.getId() %></td>
                <td><%= order.getUsername() %></td>
                <td><%= order.getAddress() %></td>
                <td><%= order.getPhone_number() %></td>
                <td><fmt:formatNumber value="<%= order.getTotal_price() %>" pattern="#,##0 VND"/></td>
                <td><fmt:formatDate value="<%= order.getCreateAt() %>" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                <td><%= order.getPayment_status() %></td>
                <td><%= Utility.getOrderStatus(order.getOrder_status()) %></td>
                <td>
                    <button class="btn btn-view view" data-toggle="modal" data-target="#orderDetailModal" data-id="<%= order.getId() %>">
                        <i class="fas fa-eye" data-toggle="tooltip" title="Xem chi tiết"></i>
                    </button>
                    <button class="btn btn-danger cancel-btn" data-toggle="modal" data-target="#cancelOrderModal" data-order-id="<%= order.getId() %>">
                        <i class="fa-solid fa-ban"></i>
                    </button>
                </td>
            </tr>
            <%
                }
            %>
            </tbody>
        </table>
    </div>
    <!-- Modal Chi tiết đơn hàng -->
    <div class="modal fade " id="orderDetailModal" tabindex="-1" role="dialog" aria-labelledby="orderDetailModalLabel" aria-hidden="true">
        <div class="modal-dialog " role="document">
            <div class="modal-content" style="width:max-content;margin-top:100px">
                <div class="modal-header">
                    <h5 class="modal-title" id="orderDetailModalLabel">Chi tiết đơn hàng</h5>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    </button>
                </div>
                <div class="modal-body">
                    <%-- Nội dung chi tiết đơn hàng sẽ được cập nhật tại đây --%>
                    <table class="table table-bordered table-hover">
                        <thead>
                        <tr>
                            <th style="font-weight: bold">ID Chi tiết</th>
                            <th style="font-weight: bold">Tên sản phẩm</th>
                            <th style="font-weight: bold">Ảnh</th>
                            <th style="font-weight: bold">Số lượng</th>
                            <th style="font-weight: bold">Giá</th>
                        </tr>
                        </thead>
                        <tbody id="orderDetailsContent">
                        <%-- Nội dung sẽ được thêm vào đây qua AJAX --%>
                        </tbody>
                    </table>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-dismiss="modal">Đóng</button>
                </div>
            </div>
        </div>
    </div>
    <%-- Modal Hủy đơn hàng --%>
    <div class="modal fade" id="cancelOrderModal" tabindex="-1" role="dialog" aria-labelledby="cancelOrderModalLabel" aria-hidden="true">
        <div class="modal-dialog" role="document">
            <div class="modal-content" style="margin-top:150px">
                <div class="modal-header">
                    <h5 class="modal-title" id="cancelOrderModalLabel">Hủy đơn hàng</h5>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <div class="modal-body">
                    Bạn có chắc chắn muốn hủy đơn hàng này không?
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-dismiss="modal">Đóng</button>
                    <button type="button" class="btn btn-danger" id="confirmCancelOrder">Hủy đơn hàng</button>
                </div>
            </div>
        </div>
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
<script>
    $(document).ready(function() {
        var table = $('#orderDetailsTable').DataTable({
            language: {
                sProcessing: "Đang xử lý...",
                sLengthMenu: "Xem _MENU_ mục",
                sZeroRecords: "Không có đơn hàng",
                sInfo: "Đang xem _START_ đến _END_ trong tổng số _TOTAL_ mục",
                sInfoEmpty: "Đang xem 0 đến 0 trong tổng số 0 mục",
                sInfoFiltered: "(được lọc từ _MAX_ mục)",
                sSearch: "Tìm Kiếm:",
                oPaginate: {
                    sFirst: "Đầu",
                    sPrevious: "Trước",
                    sNext: "Tiếp",
                    sLast: "Cuối"
                }
            }
        });

        $('.navbar ul li a').on('click', function(e) {
            e.preventDefault();
            var status = $(this).data('status');
            $('.navbar ul li a').removeClass('active');
            $(this).addClass('active');
            $.ajax({
                type: 'GET',
                url: 'OrderHistoryCL',
                data: { action: 'filter', status: status },
                success: function(response) {
                    var orderDetailsHtml = '';
                    response.forEach(function(order) {
                        orderDetailsHtml += '<tr>';
                        orderDetailsHtml += '<td>' + order.id + '</td>';
                        orderDetailsHtml += '<td>' + order.username + '</td>';
                        orderDetailsHtml += '<td>' + order.address + '</td>';
                        orderDetailsHtml += '<td>' + order.phone_number + '</td>';
                        orderDetailsHtml += '<td>' + parseInt(order.total_price).toLocaleString('vi-VN', { style: 'currency', currency: 'VND' }) + '</td>';
                        orderDetailsHtml += '<td>' + order.createAt + '</td>';
                        orderDetailsHtml += '<td>' + order.payment_status + '</td>';
                        orderDetailsHtml += '<td>' + order.orderStatusText  + '</td>';
                        orderDetailsHtml += '<td>';
                        orderDetailsHtml += '<button class="btn btn-view view" data-toggle="modal" data-target="#orderDetailModal" data-id="' + order.id + '">';
                        orderDetailsHtml += '<i class="fas fa-eye" data-toggle="tooltip" title="Xem chi tiết"></i>';
                        orderDetailsHtml += '</button>';
                        orderDetailsHtml += '<button class="btn btn-danger cancel-btn" data-toggle="modal" data-target="#cancelOrderModal" data-order-id="' + order.id + '">';
                        orderDetailsHtml += '<i class="fa-solid fa-ban"></i>';
                        orderDetailsHtml += '</button>';
                        orderDetailsHtml += '</td>';
                        orderDetailsHtml += '</tr>';
                    });
                    table.clear().draw();
                    table.rows.add($(orderDetailsHtml)).draw();
                },
                error: function() {
                    alert('Có lỗi xảy ra khi lọc đơn hàng');
                }
            });
        });
        // Hiển thị chi tiết đơn hàng trong modal
        $(document).on('click', '.view', function() {
            var orderId = $(this).data('id');
            $.ajax({
                type: 'GET',
                url: 'OrderHistoryCL',
                data: { action: 'view', orderId: orderId },
                success: function(response) {
                    var orderDetailsHtml = '';
                    response.forEach(function(detail) {
                        orderDetailsHtml += '<tr>';
                        orderDetailsHtml += '<td>' + detail.id + '</td>';
                        orderDetailsHtml += '<td>' + detail.product_name + '</td>';
                        orderDetailsHtml += '<td><img src="' + detail.img + '" alt="' + detail.product_name + '" style="width: 50px; height: 50px;"></td>';
                        orderDetailsHtml += '<td>' + detail.quantity + '</td>';
                        orderDetailsHtml += '<td>' + parseInt(detail.priceDetails).toLocaleString('vi-VN', { style: 'currency', currency: 'VND' }) + '</td>';
                        orderDetailsHtml += '</tr>';
                    });
                    $('#orderDetailsContent').html(orderDetailsHtml);
                    $('#orderDetailModal').modal('show');
                },
                error: function() {
                    alert('Có lỗi xảy ra khi lấy chi tiết đơn hàng');
                }
            });
        });

        // Hiển thị modal hủy đơn hàng với đúng orderId
        $(document).on('click', '.cancel-btn', function() {
            var orderId = $(this).data('order-id');
            $('#confirmCancelOrder').data('order-id', orderId);
        });

        // Xử lý việc hủy đơn hàng
        $('#confirmCancelOrder').on('click', function() {
            var orderId = $(this).data('order-id');
            $.ajax({
                type: 'POST',
                url: 'OrderHistoryCL',
                data: { action: 'cancelOrder', orderId: orderId },
                success: function(response) {
                    if (response === 'Success') {
                        alert('Đơn hàng đã được hủy thành công');
                        location.reload();
                    } else {
                        $('#errorMessage').text(response);
                        $('#cancelOrderModal').modal('hide');
                        $('#errorModal').modal('show');
                    }
                },
                error: function() {
                    alert('Có lỗi xảy ra khi hủy đơn hàng');
                }
            });
        });
    });

</script>
</body>
</html>
