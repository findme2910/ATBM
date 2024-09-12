<%@ page import="bean.User" %>
<%@ page import="bean.Category" %>
<%@ page import="dao.CategoryDAO" %>
<%@ page import="java.util.List" %>
<%@ page import="bean.ShoppingCart" %>
<%@ page import="bean.Product" %>
<%@ page import="bo.CategoryBO" %>
<%@ page import="java.util.ArrayList" %>
<%@page language="java" contentType="text/html; UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="zxx">
<head>
    <%
        CategoryBO cb = new CategoryBO();
        ShoppingCart shoppingCart = (ShoppingCart) session.getAttribute("cart");
        if(shoppingCart==null){
            shoppingCart = new ShoppingCart();
        }
    %>


</head>
<body>
<!-- Page Preloder -->
<%--<div id="preloder">--%>
<%--    <div class="loader"></div>--%>
<%--</div>--%>

<!-- Humberger Begin -->
<div class="humberger__menu__overlay"></div>
<div class="humberger__menu__wrapper">
    <div class="humberger__menu__logo">
        <a href="HomePageController"><img src="assets/img/logo.png" alt=""></a>
    </div>
    <div class="humberger__menu__cart">
        <ul>

            <li><a href="#"><i class="fa fa-shopping-bag"></i> <span>3</span></a></li>
        </ul>
        <!-- <div class="header__cart__price">Số dư tài khoản: <span>200.000₫</span></div> -->
    </div>
    <div class="humberger__menu__widget">
        <!-- <div class="header__top__right__language">
            <img src="assets/img/language.png" alt="">
            <div>English</div>
            <span class="arrow_carrot-down"></span>
            <ul>
                <li><a href="#">Spanis</a></li>
                <li><a href="#">English</a></li>
            </ul>
        </div> -->
        <div class="header__top__right__auth">
            <a href="login"><i class="fa fa-user"></i> Tài khoản</a>
        </div>
    </div>
    <nav class="humberger__menu__nav mobile-menu">
        <ul>
            <li><a href="HomePageController">Trang chủ</a></li>
            <li class="active"><a href="ProductController">Cửa hàng</a></li>
            <li><a href="blog-details.jsp">Các bài viết</a>
            </li>
            <li><a href="blog.jsp">Blog</a></li>
            <li><a href="lien-he.jsp">Liên hệ</a></li>
        </ul>
    </nav>
    <div id="mobile-menu-wrap"></div>
    <div class="header__top__right__social">
        <a href="#"><i class="fa fa-facebook"></i></a>
        <a href="#"><i class="fa fa-twitter"></i></a>
        <a href="#"><i class="fa fa-linkedin"></i></a>
        <a href="#"><i class="fa fa-pinterest-p"></i></a>
    </div>
    <div class="humberger__menu__contact">
        <ul>
            <li><i class="fa fa-envelope"></i> vuonpho@gmail.com</li>
            <li>Miễn phí giao hàng cho đơn đặt hàng trị giá trên 500.000đ</li>
        </ul>
    </div>
</div>
<!-- Humberger End -->

<!-- Header Section Begin -->
<header class="header">
    <div class="header__top">
        <div class="container">
            <div class="row">
                <div class="col-lg-6">
                    <div class="header__top__left">
                        <ul class="d-flex align-items-center">
                            <li class="d-flex align-items-center"><i class="fa fa-envelope"></i> vuonpho@gmail.com</li>
                            <li class="d-flex align-items-center">Miễn phí giao hàng cho đơn đặt hàng trị giá trên 500.000đ</li>
                        </ul>
                    </div>
                </div>
                <div class="col-lg-6">
                    <div class="header__top__right">
                        <div class="header__top__right__social">
                            <a href="#"><i class="fa fa-facebook"></i></a>
                            <a href="#"><i class="fa fa-twitter"></i></a>
                            <a href="#"><i class="fa fa-linkedin"></i></a>
                            <a href="#"><i class="fa fa-pinterest-p"></i></a>
                        </div>
                        <!-- <div class="header__top__right__language">
                            <img src="assets/img/language.png" alt="">
                            <div>English</div>
                            <span class="arrow_carrot-down"></span>
                            <ul>
                                <li><a href="#">Spanis</a></li>
                                <li><a href="#">English</a></li>
                            </ul>
                        </div> -->
                        <div class="header__top__right__auth">
                            <%User auth = (User) session.getAttribute("user");%>
                            <% if(auth != null){ %>
                            <div class="openBtn">
                                <div class="header__top__right__social">
                                    <a class="#" href="logout"><i class="fa fa-user"></i> Đăng xuất </a>
                                </div>
                                <a class="#" href="user-profile.jsp"> Xin chào <%= auth.getUsername() %></a>
                            </div>
                            <% }else { %>
                            <div class="openBtn">
                                <a class="#" href="login"><i class="fa fa-user"></i> Tài khoản</a>
                            </div>
                            <% } %>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div class="container">
        <div class="row d-flex align-items-center">
            <div class="col-lg-2">
                <div class="header__logo">
                    <a href="HomePageController"><img src="assets/img/logo.png" alt=""></a>
                </div>
            </div>
            <div class="col-lg-6">
                <nav class="header__menu">
                    <ul>
                        <li><a href="HomePageController">Trang chủ</a></li>
                        <li><a href="StoreProductHome">Cửa hàng</a></li>
                        <li><a href="blog-details.jsp">Các bài viết</a></li>
                        <li><a href="lien-he.jsp">Liên hệ</a></li>
                    </ul>
                </nav>
            </div>
            <div class="col-lg-2"> <a href="OrderHistoryCL" class="btn btn-primary d-block">Lịch sử mua hàng</a></div>
            <div class="col-lg-2">
                <div class="header__cart">
                    <a href="ShoppingCartCL">
                        <ul>
                            <span class="cart-word" style="font-weight: bold;">Giỏ hàng</span>
                            <li><i class="fa-solid fa-cart-shopping"></i> <span id="badge" class="cart-count"></span></li>
                        </ul>
                        <!--có thể xóa-->


                    </a>
                </div>
            </div>
        </div>
        <div class="humberger__open">
            <i class="fa fa-bars"></i>
        </div>
    </div>

</header>
<!-- Header Section End -->

<!-- Hero Section Begin -->
<section class="hero hero-normal">
    <div class="container">
        <div class="row">
            <div class="col-lg-3">
                <div class="hero__categories">
                    <div class="hero__categories__all">
                        <i class="fa fa-bars"></i>
                        <span>Danh mục sản phẩm</span>
                    </div>
                    <ul>
                        <li><a href="ProductController">Tất cả sản phẩm</a></li>
                        <% for(Category cate : cb.getListCategory()) {%>
                        <li><a href="ProductController?id_category=<%=cate.getId()%>"><%= cate.getNameCategory() %></a></li>
                        <% } %>
                    </ul>
                </div>
            </div>
            <%
                String words = session.getAttribute("words")!= null? (String)session.getAttribute("words") : "";
            %>
            <div class="col-lg-9">
                <div class="hero__search">
<%--                    <div class="hero__search__form">--%>
<%--                        <form action="ProductController" method="post">--%>
<%--                            <input type="text" name="search" placeholder="Bạn cần tìm thứ gì?" value="${sessionScope.words == null ? '' : sessionScope.words}">--%>
<%--                            <button type="submit" class="site-btn"><i class="fa-solid fa-magnifying-glass"></i></button>--%>
<%--                        </form>--%>
<%--                    </div>--%>
    <div class="hero__search__form">
        <form style="display: flex; align-items: center; width: 100%; gap: 10px" action="ProductController" method="GET">

            <input type="hidden" id="searchTypeValue" name="action" value="" />

            <input id="search-panel" style="flex: 1; border: none; padding-left: 12px; margin-right: 10px; outline: none; border-right: 1px solid #ccc"
                   type="text" name="search" placeholder="Bạn cần tìm thứ gì?" value="<%=words%>">

            <!-- Search type dropdown -->
            <div class="bc" style="position: relative; margin-right: 120px; cursor: pointer;">
                <span id="searchType" onclick="toggleSearchTypeDropdown()">Theo loại</span>
                <div id="dropdownMenu" class="de" style="position: absolute; display: block; z-index: 1000; top: 100%; left: 0; right: 0; background: #fff; box-shadow: rgba(149, 157, 165, 0.2) 0px 8px 24px;">
                    <p style="width: 100%; text-align: center; cursor: pointer;" class="a" onclick="setSearchType('name')">By Name</p>
                    <p style="width: 100%; text-align: center; cursor: pointer;" class="a" onclick="setSearchType('description')">By Description</p>
                    <p style="width: 100%; text-align: center; cursor: pointer;" class="a" onclick="setSearchType('price')">By Price</p>
                </div>
            </div>
            <input type="hidden" name="order" value="<%=session.getAttribute("order")%>">
            <!-- Submit button -->
            <button type="submit" class="site-btn"><i class="fa fa-search"></i></button>
        </form>
    </div>

    <div class="hero__search__phone">
                        <div class="hero__search__phone__icon">
                            <i class="fa fa-phone"></i>
                        </div>
                        <div class="hero__search__phone__text">
                            <h5>+84 123456789</h5>
                            <span>Hỗ trợ 24/7</span>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</section>
<!-- Hero Section End -->
<%
    Integer totalItems = (Integer) session.getAttribute("total");
    ShoppingCart cart = (ShoppingCart) session.getAttribute("cart");
    if (cart != null) {
        if (cart.getCartItemList().isEmpty()) totalItems = 0;
    } else {
        session.setAttribute("cart", new ArrayList<>());
        if(totalItems == null) totalItems = 0;
    }
%>
<script>
    const badge = document.getElementById('badge');
    if (badge.innerHTML === '') badge.innerHTML = '<%=totalItems%>';
</script>
</body>
<script>
    document.addEventListener('DOMContentLoaded', function () {
        // Toggle display of elements with class "de" when hovering over elements with class "bc"
        var bcElement = document.querySelector('.bc');
        var deElement = document.querySelector('.de');
        if (bcElement && deElement) {
            bcElement.addEventListener("mouseenter", function () {
                deElement.style.display = 'block';
            });
            bcElement.addEventListener("mouseleave", function () {
                deElement.style.display = 'none';
            });
        }

        // Update search type display and hidden input value when a dropdown item is clicked
        var searchTypeElement = document.getElementById('searchType');
        var hiddenInputElement = document.getElementById('searchTypeValue');
        if (searchTypeElement && hiddenInputElement) {
            document.querySelectorAll('.de p').forEach(function (element) {
                element.addEventListener('click', function () {
                    var selectedValue = this.innerText; // Get the selected text
                    searchTypeElement.innerHTML = selectedValue; // Update displayed search type
                    hiddenInputElement.value = selectedValue; // Update hidden input value
                    deElement.style.display = 'none'; // Hide the dropdown
                });
            });
        }
    });

    function toggleSearchTypeDropdown() {
        var deElement = document.querySelector('.de');
        if (deElement) {
            deElement.style.display = deElement.style.display === 'none' || deElement.style.display === '' ? 'block' : 'none';
        }
    }
</script>
</html>