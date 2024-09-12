<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="java.util.List" %>
<%@ page import="com.google.gson.Gson" %>
<%@ page import="bean.Products" %><%--
  Created by IntelliJ IDEA.
  User: uyent
  Date: 7/5/2024
  Time: 10:50 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <style>
        .card-custom {
            margin-bottom: 20px;
        }
        .chart-container {
            margin-top: 30px;
            margin-bottom:15px;
            margin-left:70px;
        }
        .text-info-custom {
            color: #17a2b8;
        }
        .card-icon {
            font-size: 40px;
        }
        .card-title {
            font-size: 22px;
            font-weight: bold;
        }
        .card-text {
            font-size: 18px;
            font-weight: bold;
        }
    </style>
</head>
<body>
<%
    int numUser = (request.getAttribute("numUser") != null) ? (int) request.getAttribute("numUser") : 0;
    int numPro = (request.getAttribute("numPro") != null) ? (int) request.getAttribute("numPro") : 0;
    int numOrder = (request.getAttribute("numOrder") != null) ? (int) request.getAttribute("numOrder") : 0;
    int revenueTotal = (request.getAttribute("revenueTotal") != null) ? (int) request.getAttribute("revenueTotal") : 0;
    int lowStockCount = (request.getAttribute("lowStockCount") != null) ? (int) request.getAttribute("lowStockCount") : 0;
    int unSoldProduct = (request.getAttribute("unSoldProduct") != null) ? (int) request.getAttribute("unSoldProduct") : 0;
    List<Integer> revenueData = (List<Integer>) request.getAttribute("revenueData");
    List<Integer> orderData = (List<Integer>) request.getAttribute("orderData");
    List<Products> listUnSold = (List<Products>) request.getAttribute("listUnSold");
    List<Products> listSoldOut = (List<Products>) request.getAttribute("listSoldOut");

%>
<div>
    <div class="text-center">
        <h3 class="page-title" style="color:black; font-weight: bold; margin-top: 30px">Trang Chủ</h3>
    </div>
    <div class="row chart-container">
        <div class="col-lg-6">
            <div class="card">
                <div class="card-title">
                    Biểu đồ doanh thu
                </div>
                <div class="card-body">
                    <canvas id="revenueChart"></canvas>
                </div>
            </div>
        </div>
        <div class="col-lg-6">
            <div class="card">
                <div class="card-title">
                    Biểu đồ đơn hàng
                </div>
                <div class="card-body">
                    <canvas id="orderChart"></canvas>
                </div>
            </div>
        </div>
    </div>
    <div class="row">
        <div class="col-lg-4 col-md-6 col-sm-6">
            <div class="card text-center card-custom">
                <div class="card-header bg-warning">
                    <i class="fas fa-shopping-cart card-icon"></i>
                </div>
                <div class="card-body">
                    <p class="card-title"><strong>Đơn hàng</strong></p>
                    <h3 class="card-text"><%=numOrder%></h3>
                </div>
                <div class="card-footer" id="orderManagement">
                    <a href="#" class="text-info-custom">Xem đơn hàng</a>
                </div>
            </div>
        </div>
        <div class="col-lg-4 col-md-6 col-sm-6">
            <div class="card text-center card-custom">
                <div class="card-header bg-danger">
                    <i class="fas fa-box-open card-icon"></i>
                </div>
                <div class="card-body">
                    <p class="card-title"><strong>Sản phẩm</strong></p>
                    <h3 class="card-text"><%=numPro%></h3>
                </div>
                <div class="card-footer" id="productManagement">
                    <a href="#" class="text-info-custom">Xem Sản Phẩm</a>
                </div>
            </div>
        </div>
        <div class="col-lg-4 col-md-6 col-sm-6">
            <div class="card text-center card-custom">
                <div class="card-header bg-success">
                    <i class="fas fa-dollar-sign card-icon"></i>
                </div>
                <div class="card-body">
                    <p class="card-title"><strong>Doanh thu</strong></p>
                    <h3 class="card-text"><fmt:formatNumber value="<%=revenueTotal%>" pattern="#,##0 VND"/></h3>
                </div>
                <div class="card-footer">
                    <a href="#" class="text-info-custom" data-toggle="modal" data-target="#productSold">Sản Phẩm Bán Chạy</a>
                </div>
            </div>
        </div>
    </div>
    <div class="row">
        <div class="col-lg-4 col-md-6 col-sm-6">
            <div class="card text-center card-custom">
                <div class="card-header bg-info">
                    <i class="fas fa-users card-icon"></i>
                </div>
                <div class="card-body">
                    <p class="card-title"><strong>Khách hàng</strong></p>
                    <h3 class="card-text"><%=numUser%></h3>
                </div>
                <div class="card-footer" id="userManagement">
                    <a href="#" class="text-info-custom">Quản lý khách hàng</a>
                </div>
            </div>
        </div>
        <div class="col-lg-4 col-md-6 col-sm-6">
            <div class="card text-center card-custom">
                <div class="card-header bg-danger">
                    <i class="fas fa-exclamation-triangle card-icon"></i>
                </div>
                <div class="card-body">
                    <p class="card-title"><strong>Sắp hết hàng</strong></p>
                    <h5 class="card-text"><%=lowStockCount%></h5>
                </div>
                <div class="card-footer" id="importWarning">
                    <a href="#" class="text-info-custom">Cần nhập hàng</a>
                </div>
            </div>
        </div>
        <div class="col-lg-4 col-md-6 col-sm-6">
            <div class="card text-center card-custom">
                <div class="card-header bg-primary">
                    <i class="fas fa-box-open card-icon"></i>
                </div>
                <div class="card-body">
                    <p class="card-title"><strong>Không bán được</strong></p>
                    <h3 class="card-text" id="unsoldProductsCount"><%=unSoldProduct%></h3>
                </div>
                <div class="card-footer">
                    <a href="#" class="text-info-custom" id="unsoldProductsLink" data-toggle="modal" data-target="#productDetailModal">Chi tiết sản phẩm</a>
                </div>
            </div>
        </div>
    </div>
    <!-- Modal Chi tiết sản phẩm, những sản phẩm mà 3 tháng chưa được mua-->
    <div class="modal fade " id="productDetailModal" tabindex="-1" role="dialog" aria-labelledby="productDetailModalLabel" aria-hidden="true">
        <div class="modal-dialog " role="document">
            <div class="modal-content" style="width:max-content;left: -110px">
                <div class="modal-header">
                    <h5 class="modal-title" id="productDetailModalLabel">Sản phẩm không bán được trong 3 tháng</h5>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    </button>
                </div>
                <div class="modal-body">
                    <table class="table table-bordered table-hover">
                        <thead>
                        <tr>
                            <th style="font-weight: bold">ID</th>
                            <th style="font-weight: bold">Ảnh</th>
                            <th style="font-weight: bold">Tên Sản Phẩm</th>
                            <th style="font-weight: bold">Tồn Kho</th>
                            <th style="font-weight: bold">Giá</th>
                        </tr>
                        </thead>
                        <tbody id="productDetailsContent">
                        <%for (Products a : listUnSold) {%>
                        <tr>
                            <th><%= a.getId()%></th>
                            <th><img src="<%= a.getImage() %>" alt="<%=a.getProduct_name()%>" style="width: 110px;height: 110px"></th>
                            <th><%= a.getProduct_name()%></th>
                            <th><%= a.getInventory_quantity() %></th>
                            <td style="font-size:14px"><fmt:formatNumber value="<%= a.getPrice() %>" pattern="#,##0 VND"/></td>
                        </tr>
                        <%}%>
                        </tbody>
                    </table>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-dismiss="modal">Đóng</button>
                </div>
            </div>
        </div>
    </div>
    <!-- Modal top 3 sản phẩm bán chạy-->
    <div class="modal fade " id="productSold" tabindex="-1" role="dialog" aria-labelledby="productSoldModalLabel" aria-hidden="true">
        <div class="modal-dialog " role="document">
            <div class="modal-content" style="width:max-content;left: -110px">
                <div class="modal-header">
                    <h5 class="modal-title" id="productSoldModalLabel">Top 3 sản phẩm bán chạy</h5>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    </button>
                </div>
                <div class="modal-body">

                    <table class="table table-bordered table-hover">
                        <thead>
                        <tr>
                            <th style="font-weight: bold">ID</th>
                            <th style="font-weight: bold">Ảnh</th>
                            <th style="font-weight: bold">Tên Sản Phẩm</th>
                            <th style="font-weight: bold">Số lượng bán được</th>
                            <th style="font-weight: bold">Giá</th>
                        </tr>
                        </thead>
                        <tbody id="productSoldContent">
                        <%for (Products a : listSoldOut) {%>
                        <tr>
                            <th><%= a.getId()%></th>
                            <th><img src="<%= a.getImage() %>" alt="<%=a.getProduct_name()%>" style="width: 110px;height: 110px"></th>
                            <th><%= a.getProduct_name()%></th>
                            <th><%= a.getTotal_sold() %></th>
                            <td style="font-size:14px"><fmt:formatNumber value="<%= a.getPrice() %>" pattern="#,##0 VND"/></td>
                        </tr>
                        <%}%>
                        </tbody>
                    </table>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-dismiss="modal">Đóng</button>
                </div>
            </div>
        </div>
    </div>
</div>
<script>
<%--    Gắn sự kiện nhập hàng--%>
    document.getElementById("importWarning").addEventListener("click", function() {
        loadContent($('#importManagementLink'));
    });
//     gắn sự kiện khi click vào quản lý user
document.getElementById("userManagement").addEventListener("click", function() {
    loadContent($('#customerManagementLink'));
});
//orderManagement
document.getElementById("orderManagement").addEventListener("click", function() {
    loadContent($('#orderManagementLink'));
});
// vào trang quản lý sản phẩm
document.getElementById("productManagement").addEventListener("click", function() {
    loadContent($('#productManagementLink'));
});
    var revenueData = <%= new Gson().toJson(revenueData) %>;
    var orderData = <%= new Gson().toJson(orderData) %>;

    var ctx = document.getElementById('revenueChart').getContext('2d');
    var revenueChart = new Chart(ctx, {
        type: 'line',
        data: {
            labels: ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec'],
            datasets: [{
                label: 'Doanh thu',
                data: revenueData,
                backgroundColor: 'rgba(75, 192, 192, 0.2)',
                borderColor: 'rgba(75, 192, 192, 1)',
                borderWidth: 1
            }]
        },
        options: {
            scales: {
                y: {
                    beginAtZero: true
                }
            }
        }
    });

    var ctx2 = document.getElementById('orderChart').getContext('2d');
    var orderChart = new Chart(ctx2, {
        type: 'bar',
        data: {
            labels: ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec'],
            datasets: [{
                label: 'Đơn hàng',
                data: orderData,
                backgroundColor: 'rgba(153, 102, 255, 0.2)',
                borderColor: 'rgba(153, 102, 255, 1)',
                borderWidth: 1
            }]
        },
        options: {
            scales: {
                y: {
                    beginAtZero: true
                }
            }
        }
    });
</script>
</body>
</html>
