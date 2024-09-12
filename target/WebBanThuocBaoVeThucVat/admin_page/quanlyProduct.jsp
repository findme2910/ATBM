<%@ page import="java.util.List" %>
<%@ page import="bean.Products" %>
<%@ page import="Service.ProductsService" %>
<%@ page import="java.util.ArrayList" %>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!doctype html>
<html lang="en">
<head>
    <!-- Required meta tags -->
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="viewport" content="width=device-width, initial-scale=1, minimum-scale=1, maximum-scale=1">
    <title>Quản lý đơn hàng</title>
    <style>
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
        .btn {
            margin-right: 5px;
        }
        .low-inventory {
            background-color: #ffcccc !important;
        }
    </style>
</head>
<%List<Products> allProducts = (List<Products>) request.getAttribute("allProducts");%>

<body>

<div class="container my-5">
    <div class="text-center">
        <h3 class="page-title" style="color:black; font-weight: bold; margin-top: 30px; margin-bottom: 40px">Quản Lý Sản Phẩm</h3>
    </div>
    <div class="mb-5 d-flex align-items-center">
        <a href="./insertPro" class="btn btn-success">+ Thêm sản phẩm mới</a>
        <button class="btn btn-info ml-3" id="exportButton">=> Xuất file</button>
    </div>
    <table id="quanlyTable" class="table table-striped table-bordered" style="width:100%">
        <thead>
        <tr>
            <th style="width: 20px;font-weight: bold">ID</th>
            <th style="width: 200px;font-weight: bold">Tên</th>
            <th style="width:175px;font-weight: bold">Loại sản phẩm</th>
            <th style="font-weight: bold">Ảnh</th>
            <th style="font-weight: bold">Giá</th>
            <th style="font-weight: bold">Tồn Kho</th>
            <th style="width:100px;font-weight: bold">Trạng Thái</th>
            <th style="width: 150px;font-weight: bold">Tính Năng</th>
        </tr>
        </thead>
        <tbody>
        <%for (Products a : allProducts) {%>
        <tr class="<%= a.getInventory_quantity() <= 10 ? "low-inventory" : "" %>">
            <th><%= a.getId()%></th>
            <th><%= a.getProduct_name() %></th>
            <th><%= a.cateOfProduct() %></th>
            <th><img src="<%= a.getImage() %>" alt="" style="width: 110px;height: 110px"></th>
            <th><%= a.formatPrice() %></th>
            <th><%= a.getInventory_quantity() %></th>
            <th><%= a.getStatus() == 1 ? "Hoạt động" : "Vô Hiệu Hóa" %></th>
            <th>
                <a href="./editPro?proID=<%= a.getId() %>" class="btn btn-primary">
                    <i class="fa-solid fa-pen-to-square"></i>
                </a>
                <button class="btn <%= a.getStatus() == 1 ? "btn-warning" : "btn-success" %>" data-toggle="modal" data-target="#toggleDisableModal<%=a.getId()%>">
                    <i class="fas <%= a.getStatus() == 1 ? "fa-ban" : "fa-check" %>"></i>
                </button>
            </th>
        </tr>

<%--    Vô hiệu hóa sản phẩm    --%>
        <div class="modal fade" id="toggleDisableModal<%=a.getId()%>" tabindex="-1" role="dialog" aria-labelledby="disableModalLabel" aria-hidden="true">
            <div class="modal-dialog" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title"><%= a.getStatus() == 1 ? "Xác nhận vô hiệu hóa" : "Xác nhận kích hoạt lại" %></h5>
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                    <div class="modal-body">
                        <p>Bạn có chắc chắn muốn <%= a.getStatus() == 1 ? "vô hiệu hóa" : "kích hoạt lại" %> sản phẩm <%=a.getProduct_name()%>?</p>
                        <p class="text-warning"><small>Bấm "Hủy" để dừng lại</small></p>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-dismiss="modal">Hủy</button>
                        <button type="button" class="btn <%= a.getStatus() == 1 ? "btn-danger" : "btn-success" %>" onclick="toggleDisableProduct(<%=a.getId()%>, <%=a.getStatus()%>)"><%= a.getStatus() == 1 ? "Vô hiệu hóa" : "Kích hoạt lại" %></button>
                    </div>
                </div>
            </div>
        </div>
        <%}%>
        </tbody>
    </table>
</div>


<script type="text/javascript">
    // Xuất file excel
    document.getElementById('exportButton').addEventListener('click', function() {
        var table = document.getElementById('quanlyTable');
        var wb = XLSX.utils.table_to_book(table, {sheet: "Sheet1"});
        XLSX.writeFile(wb, 'Product.xlsx');
    });


    // function deleteUser(proID, page) {
    //     var form = document.createElement("form");
    //     form.setAttribute("method", "post");
    //     form.setAttribute("action", "./deletePro");
    //
    //     var inputProID = document.createElement("input");
    //     inputProID.setAttribute("type", "hidden");
    //     inputProID.setAttribute("name", "proID");
    //     inputProID.setAttribute("value", proID);
    //
    //     var inputPage = document.createElement("input");
    //     inputPage.setAttribute("type", "hidden");
    //     inputPage.setAttribute("name", "page");
    //     inputPage.setAttribute("value", page);
    //
    //     form.appendChild(inputProID);
    //     form.appendChild(inputPage);
    //     document.body.appendChild(form);
    //
    //     form.submit();
    // }
    //logic disable product
    window.toggleDisableProduct = function(productID, currentState) {
        var action = currentState == 1 ? 'disableProduct' : 'cancelDisableProduct';
        $.ajax({
            url: '/' + action,
            type: "POST",
            data: { 'productID': productID },
            success: function(data) {
                alert(currentState == 1 ? 'Sản Phẩm đã được vô hiệu hóa!' : 'Sản Phẩm đã được kích hoạt lại!');
                loadContent($('#productManagementLink'));
            },
            error: function(xhr, error) {
                alert('Lỗi xảy ra khi ' + (currentState == 1 ? 'vô hiệu hóa' : 'kích hoạt lại') + ' Sản Phẩm! Lỗi: ' + xhr.responseText);
            }
        });
    };
</script>
</body>
</html>
