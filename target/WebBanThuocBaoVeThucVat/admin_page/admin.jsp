<%@ page import="bean.User" %>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!doctype html>
<html lang="vi">
<head>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <title>Admin vườn phố</title>
    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
    <!-- Custom -->
    <link rel="stylesheet" href="admin_page/css/custom.css">
    <link rel="icon" type="image/x-icon" href="assets/img/logo.png">
    <!-- DataTables CSS with Bootstrap -->
    <link href="https://cdn.datatables.net/1.10.24/css/dataTables.bootstrap4.min.css" rel="stylesheet">
    <!-- Font Awesome CSS -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.2/css/all.min.css">
    <!-- Google Fonts -->
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;500;600&display=swap" rel="stylesheet">
    <link href="https://fonts.googleapis.com/css2?family=Material+Icons" rel="stylesheet">
    <style>
        .nav-custom{
            margin-top:-18px;
        }
        .submenu-item{
            padding-left: 20px !important;
        }
        body.modal-open {
            padding-right: 0 !important;
        }
    </style>
    <!-- Script - custom -->
    <script src="/admin_page/js/adminJS/custom.js"></script>
</head>
<body>

<div class="wrapper">
    <!-- Sidebar -->
    <nav id="sidebar" class="bg-light">
        <div class="sidebar-header text-center">
            <a href="/HomePageController">
                <img src="./assets/img/logo.png" class="img-fluid"/>
            </a>
        </div>
        <ul class="list-unstyled components">
            <li class="active">
                <a href="#" class="dashboard" id="dashboardLink" data-url="./dashboard">
                    <i class="material-icons">dashboard</i> Trang chủ
                </a>
            </li>
            <li>
                <a href="#userSubmenu" data-toggle="collapse" aria-expanded="false" class="dropdown-toggle">
                    <i class="fas fa-users-cog"></i> Quản lý người dùng
                </a>
                <ul class="collapse list-unstyled" id="userSubmenu">
                    <li class="submenu-item">
                        <a href="#" data-url="./maUser?roleID=0&uid=1" class="load-content" id="customerManagementLink">
                            <i class="fas fa-user"></i> Quản lý khách hàng
                        </a>
                    </li>
                    <li class="submenu-item">
                        <a href="#" data-url="./maUser?roleID=1&uid=1" class="load-content" id="waiterManagementLink">
                            <i class="fas fa-user-tie"></i> Quản lý nhân viên
                        </a>
                    </li>
                    <li class="submenu-item">
                        <a href="#" data-url="./logAdmin" class="load-content" id="logManagementLink">
                            <i class="fas fa-history"></i> Lịch sử hoạt động
                        </a>
                    </li>
                </ul>
            </li>
            <li>
                <a href="#" data-url="./maProduct" class="load-content" id="productManagementLink">
                    <i class="fas fa-boxes"></i> Quản lý Sản Phẩm
                </a>
            </li>
            <li>
                <a href="#" data-url="./maCategory" class="load-content" id="categoryManagementLink">
                    <i class="fas fa-list-alt"></i> Quản lý Danh Mục
                </a>
            </li>
            <li>
                <a href="#" id="orderManagementLink" data-url="./orderManagement" class="load-content">
                    <i class="fas fa-clipboard-list"></i> Quản lý Đơn Hàng
                </a>
            </li>
            <li>
                <a href="#" id="importManagementLink" data-url="./importManagement" class="load-content">
                    <i class="fas fa-truck-loading"></i> Quản lý Nhập Hàng
                </a>
            </li>
            <li>
                <a href="#" data-url="./maDiscount" class="load-content">
                    <i class="fas fa-tags"></i> Quản lý Mã Giảm Giá
                </a>
            </li>
        </ul>
    </nav>
    <!-- Page Content -->
    <div id="content">
        <!-- Top Navbar -->
        <nav class="navbar navbar-expand-lg navbar-light bg-light">
            <div class="container-fluid">
                <button type="button" id="sidebarCollapse" class="btn btn-info">
                    <i class="fas fa-bars"></i>
                </button>
                <div class="collapse navbar-collapse">
                    <ul class="nav navbar-nav ml-auto">
                        <li class="nav-item dropdown">
                            <a class="nav-link dropdown-toggle nav-custom" href="#" id="navbarDropdown" role="button" data-toggle="dropdown">
                                <i class="material-icons">person</i>
                            </a>
                            <div class="dropdown-menu dropdown-menu-right">
                                <a class="dropdown-item" href="#">
                                    <i class="material-icons">person_outline</i> Profile
                                </a>
                                <a class="dropdown-item" href="#">
                                    <i class="material-icons">settings</i> Settings
                                </a>
                                <%
                                    User auth = (User) session.getAttribute("admin");
                                    if (auth != null) {
                                %>
                                <a class="dropdown-item" href="logout">
                                    <i class="material-icons">logout</i> Đăng xuất
                                </a>
                                <% } %>
                            </div>
                        </li>
                    </ul>
                </div>
            </div>
        </nav>
        <!-- Main Content -->
        <div class="container-fluid" id="mainContent">
        </div>
    </div>
</div>

<!-- Optional JavaScript -->

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
<%--    Excel--%>
<script src="https://cdnjs.cloudflare.com/ajax/libs/xlsx/0.17.0/xlsx.full.min.js"></script>
<%
    String redirectPage = (String) request.getAttribute("page");
    if (redirectPage != null && redirectPage.equals("./maProduct")) {
%>
<script type="text/javascript">
    $(document).ready(function () {
        loadContent($('#productManagementLink'));
    });
</script>
<%
    }
%>
<script type="text/javascript">
    $(document).ready(function () {
        // Toggle sidebar
        $('#sidebarCollapse').on('click', function () {
            $('#sidebar').toggleClass('active');
            $('#content').toggleClass('active');
        });
        // Handle click events for dynamic content loading
        $('.load-content').on('click', function (e) {
            e.preventDefault();
            loadContent(this);
        });
        // Load dashboard content
        $('#dashboardLink').on('click', function () {
            loadContent(this);
        });
        // Initially load dashboard content
        $('#mainContent').load('./dashboard');
    });

</script>
</body>
</html>
