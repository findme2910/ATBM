`<%--
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

        .button {

            --border_radius: 9999px;
            --transtion: 0.3s ease-in-out;
            --offset: 2px;

            cursor: pointer;
            position: relative;

            display: flex;
            align-items: center;
            gap: 0.5rem;
            font-size: 19px;
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


        radial-gradient(at 100 % 100 %, hsla(266, 36 %, 60 %, 1) 0 px, transparent 50 %),
        radial-gradient(at 22 % 91 %, hsla(266, 36 %, 60 %, 1) 0 px, transparent 50 %);
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

        #runTool {
            margin: auto;
        }
    </style>
</head>
<body>
<jsp:include page="layout/header.jsp"/>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>

<!-- jQuery -->
<script src="https://code.jquery.com/jquery-3.3.1.min.js"></script>
<main>
    <div class="container mt-5">
        <div class="d-flex justify-content-between align-items-center mb-4">
            <h2 class="mb-0">Lịch sử mua hàng</h2>
            <button id="review" class="btn-review">Đánh Giá Sản Phẩm</button>
        </div>
        <div class="navbar" style="margin-bottom:25px">
            <ul>
                <li><a class="active" href="#" data-status="5">Tất cả</a></li>
                <li><a href="#" data-status="6">Chưa ký</a></li>
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
                <td><%= order.getId() %>
                </td>
                <td><%= order.getUsername() %>
                </td>
                <td><%= order.getAddress() %>
                </td>
                <td><%= order.getPhone_number() %>
                </td>
                <td><fmt:formatNumber value="<%= order.getTotal_price() %>" pattern="#,##0 VND"/></td>
                <td><%=Util.formatTimestampToString(order.getCreateAt())%>
                </td>
                <td><%= order.getPayment_status() %>
                </td>
                <td><%= Utility.getOrderStatus(order.getOrder_status()) %>
                </td>
                <td>
                    <button class="btn btn-view view" data-toggle="modal" data-target="#orderDetailModal"
                            data-id="<%= order.getId() %>">
                        <i class="fas fa-eye" data-toggle="tooltip" title="Xem chi tiết"></i>
                    </button>
                    <button class="btn btn-danger cancel-btn" data-toggle="modal" data-target="#cancelOrderModal"
                            data-order-id="<%= order.getId() %>">
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
    <div class="modal fade " id="orderDetailModal" tabindex="-1" role="dialog" aria-labelledby="orderDetailModalLabel"
         aria-hidden="true">
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

<%--                    <button id="btnSign" class="btn btn-custom sign-btn" data-toggle="modal"--%>
<%--                            data-target="#signOrderModal"--%>
<%--                            data-order-id="9">--%>
<%--                        Ký đơn hàng--%>
<%--                    </button>--%>


                    <button type="button" class="btn btn-secondary" data-dismiss="modal">Đóng</button>
                </div>
            </div>
        </div>
    </div>

    <%--    Modal ký đơn hàng--%>
    <div class="modal fade " id="signOrderModal" tabindex="-1" role="dialog" aria-labelledby="orderDetailModalLabel"
         aria-hidden="true">
        <div class="modal-dialog" style="margin-left: 500px;" role="document">
            <div class="modal-content" style="width:200% !important;">
                <div class="modal-header">
                    <h5 class="modal-title" id="signOrderModalLabel">Kí đơn hàng</h5>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    </button>
                </div>
                <div class="modal-body">
                    <%-- Nội dung chi tiết đơn hàng sẽ được cập nhật tại đây --%>
                        <div style="margin-top: 10%" class="container w-100 p-4 border rounded shadow">
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
                                    <textarea id="signalInput" name="signalKey" placeholder="Nhập chữ ký ở đây"
                                              style="width: 160%; height: 100px;font-size: 18px"></textarea>
                                </div>
                            </div>

                            <!-- Lỗi và Nút Xác Nhận -->
                            <div class="text-center mt-4" style="margin-left:42%;">
                                <span id="errorSign" class="text-danger d-block mb-2"></span>
                                <button id="signedOrder" class="btn-success button">Xác nhận</button>
                            </div>
                        </div>
                </div>
                <div class="modal-footer">
                    <%--                    <button class="btn btn-custom" id="cofirmSignOrder">Xác thực</button>--%>
                    <button type="button" class="btn btn-secondary" data-dismiss="modal">Đóng</button>
                </div>
            </div>
        </div>
    </div>
    <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
    <script>
        $(document).ready(function () {
            $("#btnSign").click(function () {
                var idO = orderIdParam;
                $.ajax({
                    url: 'SignOrder',
                    type: 'POST',
                    data: {
                        action: 'hash',
                        orderId: idO
                    },
                    success: function (data) {
                        // console.log(data);
                        // $('#signalInput').val(data);
                        $('#signOrderModal').modal('show');
                    }
                });
            });
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
            $('#signedOrder').click(function () {
                var signedOrder = $('#signalInput').val();
                if (signedOrder === "") {
                    Swal.fire({
                        titleText: "Chưa nhập chữ ký",
                        icon: "error"
                    });
                } else {
                    $.ajax({
                        url: 'SignOrder',
                        type: 'POST',
                        data: {
                            action: 'sign',
                            signedOrder: signedOrder

                        },
                        success: function (data) {
                            window.location.href = "HomePageController";
                        }, error: function (data) {
                            String
                            var errorText = data.responseText;
                            Swal.fire({
                                titleText: errorText,
                                icon: "error"
                            });
                        }
                    });
                }
            });
        })
        $(document).ready(function () {
            $('.btn-view').on('click', function () {
                const orderId = $(this).data('id');
                const orderStatus = $(this).closest('tr').find('td:nth-child(8)').text().trim();

                const invalidStatuses = ["Đã Giao", "Đã Hủy", "Chờ Xét Duyệt", "Đang Đóng Gói", "Đang Vận Chuyển"];
                console.log("Order Status:", orderStatus);
                if (invalidStatuses.includes(orderStatus)) {
                    $('#btnSign').hide();
                } else {
                    $('#btnSign').show();
                }

                orderIdParam = orderId;
            });
        });

        let fileData;

        function handleFile(event) {
            const file = event.target.files[0];
            if (file) {
                const reader = new FileReader();

                reader.onload = function (e) {
                    fileData = e.target.result.trim();
                    console.log("File data: ", fileData);
                };

                reader.readAsText(file);
            } else {
                console.error("Không có file nào được chọn.");
            }
        }

        let orderIdParam;

        $('#cofirmSignOrder').on('click', function () {
            let privateKey;
            const privateKeyTextArea = $('#privateKeyTextArea').val().trim();

            if (!privateKeyTextArea && !fileData) {
                privateKey = null;
            } else if (privateKeyTextArea) {
                privateKey = privateKeyTextArea;
            } else if (fileData) {
                privateKey = fileData;
            }

            console.log("Private Key: ", privateKey);

            $.ajax({
                type: 'POST',
                url: 'SignOrder',
                data: {action: 'sign', orderId: orderIdParam, privateKey: privateKey},
                success: function (response) {
                    Swal.fire({
                        titleText: response,
                        icon: "success"
                    });
                    location.reload();
                    $('#signOrderModal').modal('hide');
                    $('#orderDetailModal').modal('hide');

                },
                error: function (error) {
                    const errorMessage = error.responseText;
                    Swal.fire({
                        titleText: errorMessage,
                        icon: "error"
                    });
                }
            });
        });
    </script>
    <%-- Modal Hủy đơn hàng --%>
    <div class="modal fade" id="cancelOrderModal" tabindex="-1" role="dialog" aria-labelledby="cancelOrderModalLabel"
         aria-hidden="true">
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
    <div class="modal fade" id="errorModal" tabindex="-1" role="dialog" aria-labelledby="errorModalLabel"
         aria-hidden="true" style="top:150px">
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
<!-- popper -->
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js"></script>
<!-- Bootstrap JS -->
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
<!-- DataTables JS with Bootstrap -->
<script src="https://cdn.datatables.net/1.10.24/js/jquery.dataTables.min.js"></script>
<script src="https://cdn.datatables.net/1.10.24/js/dataTables.bootstrap4.min.js"></script>
</body>
<script>
    $(document).ready(function () {
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

        $('.navbar ul li a').on('click', function (e) {
            e.preventDefault();
            var status = $(this).data('status');
            $('.navbar ul li a').removeClass('active');
            $(this).addClass('active');
            $.ajax({
                type: 'GET',
                url: 'OrderHistoryCL',
                data: {action: 'filter', status: status},
                success: function (response) {
                    var orderDetailsHtml = '';
                    response.forEach(function (order) {
                        orderDetailsHtml += '<tr>';
                        orderDetailsHtml += '<td>' + order.id + '</td>';
                        orderDetailsHtml += '<td>' + order.username + '</td>';
                        orderDetailsHtml += '<td>' + order.address + '</td>';
                        orderDetailsHtml += '<td>' + order.phone_number + '</td>';
                        orderDetailsHtml += '<td>' + parseInt(order.total_price).toLocaleString('vi-VN', {
                            style: 'currency',
                            currency: 'VND'
                        }) + '</td>';
                        orderDetailsHtml += '<td>' + order.createAt + '</td>';
                        orderDetailsHtml += '<td>' + order.payment_status + '</td>';
                        orderDetailsHtml += '<td>' + order.orderStatusText + '</td>';
                        orderDetailsHtml += '<td>';
                        orderDetailsHtml += '<button class="btn btn-view view" data-toggle="modal" data-target="#orderDetailModal" data-id="' + order.id + '">';
                        orderDetailsHtml += '<i class="fas fa-eye" data-toggle="tooltip" title="Xem chi tiết"></i>';
                        orderDetailsHtml += '</button>';
                        orderDetailsHtml += '<button class="btn btn-danger cancel-btn" data-toggle="modal"  data-target="#cancelOrderModal" data-order-id="' + order.id + '">';
                        orderDetailsHtml += '<i class="fa-solid fa-ban"></i>';
                        orderDetailsHtml += '</button>';

                        orderDetailsHtml += '</td>';
                        orderDetailsHtml += '</tr>';
                    });
                    table.clear().draw();
                    table.rows.add($(orderDetailsHtml)).draw();
                },
                error: function () {
                    alert('Có lỗi xảy ra khi lọc đơn hàng');
                }
            });
        });
        // Hiển thị chi tiết đơn hàng trong modal
        $(document).on('click', '.view', function () {

            var orderId = $(this).data('id');
            orderIdParam = orderId;
            $.ajax({
                type: 'GET',
                url: 'OrderHistoryCL',
                data: {action: 'view', orderId: orderId},
                success: function (response) {
                    var orderDetailsHtml = '';
                    response.forEach(function (detail) {
                        orderDetailsHtml += '<tr>';
                        orderDetailsHtml += '<td>' + detail.id + '</td>';
                        orderDetailsHtml += '<td>' + detail.product_name + '</td>';
                        orderDetailsHtml += '<td><img src="' + detail.img + '" alt="' + detail.product_name + '" style="width: 50px; height: 50px;"></td>';
                        orderDetailsHtml += '<td>' + detail.quantity + '</td>';
                        orderDetailsHtml += '<td>' + parseInt(detail.priceDetails).toLocaleString('vi-VN', {
                            style: 'currency',
                            currency: 'VND'
                        }) + '</td>';
                        orderDetailsHtml += '</tr>';
                    });
                    $('#orderDetailsContent').html(orderDetailsHtml);
                    $('#orderDetailModal').modal('show');
                },
                error: function () {
                    alert('Có lỗi xảy ra khi lấy chi tiết đơn hàng');
                }
            });
        });

        // Hiển thị modal hủy đơn hàng với đúng orderId
        $(document).on('click', '.cancel-btn', function () {
            var orderId = $(this).data('order-id');
            $('#confirmCancelOrder').data('order-id', orderId);
        });

        // Xử lý việc hủy đơn hàng
        $('#confirmCancelOrder').on('click', function () {
            var orderId = $(this).data('order-id');
            $.ajax({
                type: 'POST',
                url: 'OrderHistoryCL',
                data: {action: 'cancelOrder', orderId: orderId},
                success: function (response) {
                    if (response === 'Success') {
                        alert('Đơn hàng đã được hủy thành công');
                        location.reload();
                    } else {
                        $('#errorMessage').text(response);
                        $('#cancelOrderModal').modal('hide');
                        $('#errorModal').modal('show');
                    }
                },
                error: function () {
                    alert('Có lỗi xảy ra khi hủy đơn hàng');
                }
            });
        });
        // Xử lý sự kiện click vào nút "Đánh Giá Sản Phẩm"
        $('#review').on('click', function (e) {
            e.preventDefault();
            $.ajax({
                url: 'OrderHistoryCL',
                method: 'GET',
                data: {action: 'review'},
                success: function (response) {
                    $('#orderDetailsTable_wrapper').html(response);
                },
                error: function () {
                    alert('Có lỗi xảy ra khi tải trang đánh giá');
                }
            });
        });
    });

</script>
</html>
`