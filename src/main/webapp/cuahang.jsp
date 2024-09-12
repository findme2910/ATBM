<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="bo.CategoryBO" %>
<%@ page import="bean.*" %>
<%@page language="java" contentType="text/html; UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql"%>
<!DOCTYPE html>
<html lang="zxx">

<head>
    <meta charset="UTF-8">
    <meta name="description" content="Ogani Template">
    <meta name="keywords" content="Ogani, unica, creative, html">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <link rel="icon" type="image/x-icon" href="assets/img/logo.png">
    <title>Vườn phố</title>

    <!-- Google Font -->
    <link href="https://fonts.googleapis.com/css2?family=Cairo:wght@200;300;400;600;900&display=swap" rel="stylesheet">

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
    <%--    <link rel="stylesheet" href="assets/css/Log_Regis.css">--%>
    <%--    <script src="js/log_reg.js" defer></script>--%>
    <%
        User auth = (User) session.getAttribute("user");
        List<Products> products = (List<Products>) session.getAttribute("Product");
        List<Products> list = (List<Products>) session.getAttribute("listProducts");
        CategoryBO cb = new CategoryBO();
        int totalPages = (Integer) session.getAttribute("totalPage");
        int currentPage = (Integer) session.getAttribute("currentPage");
        int startPage = (Integer) session.getAttribute("startPage");
        int endPage = (Integer) session.getAttribute("endPage");
        int products_per_page = (Integer) session.getAttribute("products_per_page");
        String action = (String) session.getAttribute("action");
        String name = (String) session.getAttribute("name");
        String idCate = (String) session.getAttribute("idCate");
        String order = (String) session.getAttribute("order");
        String page1 = request.getParameter("page");
        String pattern;
        if(page1!= null) {
            pattern = "ProductController?action=" + action + "&search=" + name + "&";
        } else {
            if(idCate== null || idCate.equals("")) {
                pattern = "ProductController?";
            } else {
                pattern = "ProductController?id_category=" + idCate + "&";
            }
        }
    %>
    <style>
        .product__pagination {
            display: flex;
            justify-content: center;
            align-items: center;
            margin: 20px 0;
            font-family: Arial, sans-serif;
        }

        .product__pagination a {
            display: flex;
            align-items: center;
            justify-content: center;
            margin: 0 5px;
            padding: 8px 16px;
            border: 1px solid #ddd;
            border-radius: 4px;
            text-decoration: none;
            color: #007bff;
            transition: background-color 0.3s, color 0.3s;
        }

        .product__pagination a:hover {
            background-color: #007bff;
            color: #fff;
        }

        .product__pagination strong {
            display: inline-block;
            margin: 0 5px;
            padding: 8px 16px;
            border: 1px solid #007bff;
            border-radius: 4px;
            background-color: #007bff;
            color: #fff;
        }

        .product__pagination span {
            margin: 0 5px;
            color: #999;
        }
        .filter__sort select {
            padding: 5px 10px;
            border: 1px solid #ccc;
            border-radius: 4px;
            background-color: #f9f9f9;
            font-size: 16px;
        }
        .filter__sort select:focus {
            border-color: #66afe9;
            outline: none;
            box-shadow: 0 0 8px rgba(102, 175, 233, 0.6);
        }
        .product__item__pic {
            position: relative;
        }

        .wishlist-badge {
            position: absolute;
            top: 3px;
            left: 3px;
            background-color: #ff6347;
            color: white;
            padding: 5px 10px;
            border-radius: 3px;
            font-size: 12px;
            font-weight: bold;
            z-index: 10;

        }
    </style>
</head>

<body>
<jsp:include page="layout/header.jsp"/>

<!-- Breadcrumb Section Begin -->
<section class="breadcrumb-section set-bg" data-setbg="assets/img/breadcrumb.jpg">
    <div class="container">
        <div class="row">
            <div class="col-lg-12 text-center">
                <div class="breadcrumb__text">
                    <h2>Cửa hàng</h2>
                    <div class="breadcrumb__option">
                        <a href="index.jsp">Trang chủ</a>
                        <span>Cửa hàng</span>
                    </div>
                </div>
            </div>
        </div>
    </div>
</section>
<!-- Breadcrumb Section End -->

<!-- Product Section Begin -->
<section class="product spad">
    <div class="container">
        <div class="row">
            <div class="col-lg-3 col-md-5">
                <div class="sidebar">
                    <div class="sidebar__item">
                        <h4>Danh mục sản phẩm</h4>
                        <ul>
                            <li><a href="ProductController?order=<%=order%>">Tất cả sản phẩm</a></li>
                            <% for(Category cate : cb.getListCategory()) {%>
                                <li><a href="ProductController?id_category=<%=cate.getId()%>&order=<%=order%>"><%=cate.getNameCategory()%></a></li>
                            <% } %>
                        </ul>
                    </div>
                </div>
            </div>
            <div class="col-lg-9 col-md-7">
                <div class="filter__item">
                    <div class="row">
                        <div class="col-lg-4 col-md-5">
                            <div class="filter__sort">
                                <select id="selectOrder">
                                    <option value="0" data-href="<%= pattern %>order=0&" <%= "0".equals(order) ? "selected" : "" %>>Thứ tự mặc định</option>
                                    <option value="1" data-href="<%= pattern %>order=1&" <%= "1".equals(order) ? "selected" : "" %>>Thứ tự theo mức độ phổ biến</option>
                                    <option value="2" data-href="<%= pattern %>order=2&" <%= "2".equals(order) ? "selected" : "" %>>Thứ tự theo giá: thấp đến cao</option>
                                    <option value="3" data-href="<%= pattern %>order=3&" <%= "3".equals(order) ? "selected" : "" %>>Thứ tự theo giá: cao xuống thấp</option>
                                </select>
                            </div>
                        </div>
                        <div class="col-lg-4 col-md-4">
                            <div class="filter__found">
                                <h6 style="font-size:18px"><span><%=list.size() %></span> sản phẩm được tìm thấy</h6>
                            </div>
                        </div>
                    </div>
                </div>

                <div class="row">
                    <%
                        ShoppingCart shoppingCart = (ShoppingCart) session.getAttribute("cart");
                        for(Products p : products) {
                            int remain = p.getInventory_quantity();
                            if (shoppingCart != null) {
                                for (CartItem item : shoppingCart.getCartItemList()) {
                                    if (item.getProduct().getId()==p.getId()) {
                                        remain = p.getInventory_quantity() - item.getQuantity();
                                    }
                                }
                            }
                    %>
                    <div class="col-lg-4 col-md-6 col-sm-6">
                        <div id="" class="product__item">
                            <div class="product__item__pic set-bg" data-setbg="<%=p.getImage()%>">
                                <div class="favourite_pro<%=p.getId()%>">
                                    <%
                                        if(auth != null){
                                            List<WishlistItem> wishlistItemList = (List<WishlistItem>) request.getAttribute("wishlistItemList");
                                            boolean isLiked = false;
                                            for(WishlistItem a : wishlistItemList){
                                                if(a.getProducts().getId() == p.getId()){
                                                    isLiked = true;
                                                    break;
                                                }
                                            }
                                            if(isLiked) {
                                    %>
                                    <div class="wishlist-badge">
                                        <span>ĐÃ THÍCH</span>
                                    </div>
                                    <%
                                            }
                                        }
                                    %>
                                </div>
                                <ul class="product__item__pic__hover">
                                    <li><a href="ProductInfor?id_product=<%= p.getId() %>"><i class="fa fa-retweet"></i></a></li>
                                    <li><a class="d-flex align-items-center justify-content-center" href="javascript:void(0)" onclick="toggleWishlist(this, '<%=p.getId()%>')"><i class="fa fa-heart "></i></a></li>
                                    <li><a href="javascript:void(0)" onclick="addCart(this, '<%=p.getId()%>', '<%=remain%>')"><i class="fa fa-shopping-cart"></i></a></li>
<%--                                    <li><a  href="ShoppingCartCL?action=post&id=<%=a.getId()%>&type=0"><i class="fa fa-shopping-cart"></i></a></li>--%>
                                </ul>
                            </div>
                            <div class="product__item__text">
                                <h6><a href="ProductInfor?id_product=<%= p.getId() %>"><%=p.getProduct_name()%></a></h6>
                                <h5><%=p.formatPrice()%>₫</h5>
                            </div>
                        </div>
                    </div>
                    <% } %>
                </div>

                <div class="product__pagination">
                    <% if(currentPage > 1) { %>
                    <a href="<%=pattern%>order=<%=order%>&currentPage=<%=currentPage - 1%>" style="width: 70px">Trước</a>
                    <% } %>
                    <% if(startPage > 2) { %>
                    <a href="<%=pattern%>order=<%=order%>&currentPage=1">1</a>
                    <span>..</span>
                    <% } %>
                    <% for (int i = startPage; i <= endPage; i++) { %>
                    <% if(i == currentPage) { %>
                    <strong><%=i%></strong>
                    <% } else { %>
                    <a href="<%=pattern%>order=<%=order%>&currentPage=<%=i%>"><%=i%></a>
                    <% } %>
                    <% } %>
                    <% if(endPage < totalPages) { %>
                    <span>..</span>
                    <a href="<%=pattern%>order=<%=order%>&currentPage=<%=totalPages%>"><%=totalPages%></a>
                    <% } %>
                    <% if(currentPage < totalPages) { %>
                    <a href="<%=pattern%>order=<%=order%>&currentPage=<%=currentPage + 1%>" style="width: 70px">Next</a>
                    <% } %>
                </div>

            </div>
        </div>
    </div>
</section>
<!-- Product Section End -->

<!-- Footer Section Begin -->
<footer class="footer spad">
    <div class="container">
        <div class="row">
            <div class="col-lg-3 col-md-6 col-sm-6">
                <div class="footer__about">
                    <div class="footer__about__logo">
                        <a href="index.jsp"><img src="assets/img/logo.png" alt=""></a>
                    </div>
                    <ul>
                        <li>Address: 171 Nguyễn Văn Khối, Phường 8, Gò Vấp, TP. HCM</li>
                        <li>Phone: +84 123456789</li>
                        <li>Email: vuonpho@gmail.com</li>
                    </ul>
                </div>
            </div>
            <div class="col-lg-4 col-md-6 col-sm-6 offset-lg-1">
                <div class="footer__widget">
                    <h6>Chính sách công ty</h6>
                    <ul>
                        <li><a href="#">Về chúng tôi</a></li>
                        <li><a href="#">Về cửa hàng chúng tôi</a></li>
                        <li><a href="#">Chính sách mua hàng</a></li>
                        <li><a href="#">Thông tin vận chuyển</a></li>
                        <li><a href="#">Điều khoản và bảo mật</a></li>
                        <li><a href="#">Địa chỉ cửa hàng</a></li>
                    </ul>
                    <ul>
                        <li><a href="#">Chúng tôi là ai</a></li>
                        <li><a href="#">Dịch vụ của chúng tôi</a></li>
                        <li><a href="#">Các Project</a></li>
                        <li><a href="#">Liên hệ</a></li>
                    </ul>
                </div>
            </div>
            <div class="col-lg-4 col-md-12">
                <div class="footer__widget">
                    <h6>Tham gia với chúng tôi</h6>
                    <p>Cập nhật thông tin mới nhất và các ưu đãi về cửa hàng thông qua email.</p>
                    <form action="#">
                        <input type="text" placeholder="Nhập địa chỉ email">
                        <button type="submit" class="site-btn">ĐĂNG KÝ</button>
                    </form>
                    <div class="footer__widget__social">
                        <a href="#"><i class="fa fa-facebook"></i></a>
                        <a href="#"><i class="fa fa-instagram"></i></a>
                        <a href="#"><i class="fa fa-twitter"></i></a>
                        <a href="#"><i class="fa fa-pinterest"></i></a>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <%--    <h1><%= productNew1.size() %></h1>--%>
</footer>

<!-- Js Plugins -->
<script src="assets/js/jquery-3.3.1.min.js"></script>
<script src="assets/js/bootstrap.min.js"></script>
<%--<script src="assets/js/jquery.nice-select.min.js"></script>--%>
<script src="assets/js/jquery-ui.min.js"></script>
<script src="assets/js/jquery.slicknav.js"></script>
<script src="assets/js/mixitup.min.js"></script>
<script src="assets/js/owl.carousel.min.js"></script>
<script src="assets/js/main.js"></script>
<script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script>
    function toggleWishlist(element, productId) {
        const selector = '.favourite_pro' + productId;
        const favourite_pro = document.querySelector(selector);
        console.log("favourite_pro", favourite_pro);
        console.log("productId", productId);
        addToWishlist(productId, favourite_pro);
    }

    function addToWishlist(productId) {
        const selector = '.favourite_pro' + productId;
        const favourite_pro = document.querySelector(selector);
        $.ajax({
            url: '/wishlistController',
            method: "POST",
            data: {
                id: productId,
                action: "add"
            },
            success: function (response) {
                if (response.status === "success") {
                    Swal.fire({
                        position: "center",
                        icon: "success",
                        title: "Thêm Sản Phẩm Vào Danh Sách Yêu Thích Thành Công!",
                        showConfirmButton: false,
                        timer: 1500
                    });
                    const newBadge = document.createElement("div");
                    newBadge.className="wishlist-badge";
                    newBadge.innerHTML="<span>ĐÃ THÍCH</span>";
                    favourite_pro.appendChild(newBadge);

                } else if (response.status === "insertFailed") {
                    Swal.fire({
                        position: "center",
                        icon: "error",
                        title: "Thêm Sản Phẩm Vào Danh Sách Yêu Thích Không Thành Công!",
                        showConfirmButton: false,
                        timer: 1500
                    });
                } else if (response.status === "isExists") {
                    Swal.fire({
                        position: "center",
                        icon: "success",
                        title: "Xóa Sản Phẩm Ra Khỏi Sẵn Danh Sách Yêu Thích!",
                        showConfirmButton: false,
                        timer: 1500
                    });
                    const newBadge=favourite_pro.querySelector(".wishlist-badge");
                    if(newBadge){
                        newBadge.remove();
                    }

                } else {
                    window.location.href = '/login';
                }
            }
        });
    }
</script>
<script>
    document.getElementById('selectOrder').addEventListener('change', function() {
        var selectedOption = this.options[this.selectedIndex];
        var href = selectedOption.getAttribute('data-href');
        window.location.href = href;
    });
</script>
<script>
    var context = "${pageContext.request.contextPath}";
    function addCart(btn, id, remain) {
        console.log(remain)
        $.ajax({
            url: 'ShoppingCartCL',
            method: "POST",
            data: {
                id: id,
                action: "add",
                type: 0,
                contain: remain
            },
            success: function (response) {
                if (response.status === "failed") {
                    window.location.href = 'login';
                } else if(response.status === "stock") {
                    alert(response.error)
                } else {
                    Swal.fire({
                        position: "center",
                        icon: "success",
                        title: "Thêm Sản Phẩm Vào Giỏ Hàng Thành Công!",
                        showConfirmButton: false,
                        timer: 1500
                    });
                    const badge = document.getElementById("badge");
                    badge.innerHTML = response.total;
                }
            }
        });
    }
</script>
</body>
</html>