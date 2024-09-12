<%@ page import="bean.Import" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="viewport" content="width=device-width, initial-scale=1, minimum-scale=1, maximum-scale=1">
    <title>Quản lý Nhập hàng</title>
    <style>
        /* Tùy chỉnh CSS cho DataTables */
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
        thead tr th{
            font-weight: bold!important;
        }
    </style>
</head>
<body>
<div class="container my-5">
    <div class="text-center">
        <h3 class="page-title" style="color:black; font-weight: bold; margin-top: 30px; margin-bottom: 40px">Quản Lý Nhập Hàng</h3>
    </div>
    <div class="mb-5 d-flex align-items-center">
        <button class="btn btn-success" data-toggle="modal" data-target="#createModal">+ Tạo mới nhập hàng</button>
        <button class="btn btn-info ml-3" id="exportButton">=> Xuất file</button>
    </div>
    <table id="quanlyTable" class="table table-striped table-bordered" style="width:100%">
        <thead>
        <tr class="ex">
            <th>Id</th>
            <th>Id product</th>
            <th>Số lượng</th>
            <th>Tên sản phẩm</th>
            <th>Giá tiền</th>
            <th>Ngày nhập</th>
            <th>Tình Trạng</th>
            <th style="width:80px">Tính Năng</th>
        </tr>
        </thead>
        <tbody id='tableBody'>
        <%
            List<Import> importOrders = (List<Import>) request.getAttribute("importOrders");
            for (Import order : importOrders) {
        %>
        <tr>
            <td><%= order.getId() %></td>
            <td><%= order.getId_product() %></td>
            <td><%= order.getQuantity() %></td>
            <td><%= order.getProduct_name() %></td>
            <td><fmt:formatNumber value="<%= order.getPrice() %>" pattern="#,##0 VND"/></td>
            <td><fmt:formatDate value="<%= order.getDate_import() %>" pattern="yyyy-MM-dd HH:mm:ss"/></td>
            <td><%= order.getStatus() %></td>
            <td>
                <button class="btn btn-primary update-btn" data-toggle="modal" data-target="#updateModal" data-order-id="<%= order.getId() %>"><i class="fa-solid fa-pen-to-square"></i></button>
            </td>
        </tr>
        <%
            }
        %>
        </tbody>
    </table>
</div>
<!-- Tạo đơn hàng mới -->
<div class="modal fade" id="createModal" tabindex="-1" role="dialog" aria-labelledby="createModalLabel" aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <form action="/importManagement" id="createOrderForm" method="post">
                <input type="hidden" name="action" value="create">
                <div class="modal-header">
                    <h5 class="modal-title" id="createModalLabel">Tạo mới nhập hàng</h5>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <div class="modal-body">
                    <div class="form-group">
                        <label for="productId">Id sản phẩm</label>
                        <input type="text" class="form-control" id="productId" name="productId" required>
                    </div>
                    <div class="form-group">
                        <label for="quantity">Số lượng</label>
                        <input type="text" class="form-control" id="quantity" name="quantity" required>
                    </div>
                    <div class="form-group">
                        <label for="price">Giá tiền</label>
                        <input type="text" class="form-control" id="price" name="price" required>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-dismiss="modal">Đóng</button>
                    <button type="submit" class="btn btn-primary">Lưu</button>
                </div>
            </form>
        </div>
    </div>
</div>
<!-- Modal Cập nhật -->
<div class="modal fade" id="updateModal" tabindex="-1" role="dialog" aria-labelledby="updateModalLabel" aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <form action="/importManagement" id="updateOrderForm" method="post">
                <div class="modal-header">
                    <h5 class="modal-title" id="updateModalLabel">Cập nhật tình trạng</h5>
                </div>
                <div class="modal-body">
                    <input type="hidden" name="action" value="update">
                    <input type="hidden" name="orderId" id="updateOrderId">
                    <div class="form-group">
                        <label for="status">Tình trạng mới</label>
                        <input type="text" class="form-control" id="status" name="status" required>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-dismiss="modal">Đóng</button>
                    <button type="submit" class="btn btn-primary">Cập nhật</button>
                </div>
            </form>
        </div>
    </div>
</div>

<script>
    $(document).ready(function() {
        // Xử lý khi nhấn vào nút "Cập nhật", lấy ra trường id
        $(document).on('click', '.update-btn', function() {
            var orderId = $(this).data('order-id');
            $('#updateOrderId').val(orderId);
        });


        // Xử lý khi nhấn vào nút "Tạo mới"
        $('#createOrderForm').on('submit', function(e) {
            e.preventDefault();
            $.ajax({
                type: "POST",
                url: "/importManagement",
                data: $(this).serialize(),
                success: function(data) {
                    alert('Đơn hàng mới đã được tạo thành công!');
                    $('#createModal').modal('hide');
                    loadContent($('#importManagementLink'));
                },
                error: function(xhr, error) {
                    alert('Lỗi xảy ra khi tạo đơn hàng mới! Lỗi: ' + xhr.responseText);
                }
            });
        });

        // Xử lý khi nhấn vào nút "Cập nhật"
        $('#updateOrderForm').on('submit', function(e) {
            e.preventDefault();
            $.ajax({
                type: "POST",
                url: "/importManagement",
                data: $(this).serialize(),
                success: function(data) {
                    alert('Tình trạng đơn hàng đã được cập nhật!');
                    $('#updateModal').modal('hide');
                    loadContent($('#importManagementLink'));
                },
                error: function(xhr, error) {
                    alert('Lỗi xảy ra khi cập nhật tình trạng đơn hàng! Lỗi: ' + xhr.responseText);
                }
            });
        });

        // Xuất file excel
        $('#exportButton').on('click', function() {
            var tableElement = document.getElementById('quanlyTable');
            var wb = XLSX.utils.table_to_book(tableElement, {sheet: "Sheet1"});
            XLSX.writeFile(wb, 'ImportDetails.xlsx');
        });
    });

</script>
</body>
</html>