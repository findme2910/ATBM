<%@ page import="java.util.List" %>
<%@ page import="bo.CategoryBO" %>
<%@ page import="bean.*" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql"%>
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
    <%--    <script src="assets/js/log_reg.js" defer></script>--%>
    <%
        List<Products> products = (List<Products>) request.getAttribute("products");
        User auth = (User) session.getAttribute("user");
        List<Category> category = (List<Category>) request.getAttribute("category");
        List<Products> productNew1 = (List<Products>) request.getAttribute("productsNew1");
        List<Products> productNew2 = (List<Products>) request.getAttribute("productsNew2");
        List<Products> findDiscountPro1 = (List<Products>) request.getAttribute("findDiscountPro1");
        List<Products> findDiscountPro2 = (List<Products>) request.getAttribute("findDiscountPro2");

        ShoppingCart shoppingCart = (ShoppingCart) session.getAttribute("cart");
        CategoryBO cb = new CategoryBO();

//        if(shoppingCart==null){
//            shoppingCart = new ShoppingCart();
//        }
    %>

    <%--    <link rel="stylesheet" href="css/Log_Regis.css">--%>
    <%--    <script src="js/log_reg.js" defer></script>--%>
    <style>
        .red {
            color: red;
            /*background-color: aliceblue;*/
        }
    </style>
</head>
<body>
<!-- Page Preloder -->
<div id="preloder">
    <div class="loader"></div>
</div>

<jsp:include page="layout/header.jsp"/>

<!-- Hero Section Begin -->
<!-- Hero Section End -->

<!-- Categories Section Begin -->
<section class="categories">
    <div class="container">
        <div class="section-title text-center">
            <h2>Các sản phẩm nổi bật</h2>
        </div>
        <div class="row">
            <div class="categories__slider owl-carousel">
                <!-- Các sản phẩm nổi bật  -->
                <%
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
                <div class="col-lg-3 col-md-4 col-sm-6 mix a">
                    <div class="featured__item">
                        <div class="featured__item__pic set-bg" data-setbg="<%=p.getImage()%>">
                            <ul class="featured__item__pic__hover">
                                <li><a class="d-flex align-items-center justify-content-center" href="ProductInfor?id_product=<%= p.getId() %>"><i class="fa fa-retweet"></i></a></li>
<%--                                <li><a class="d-flex align-items-center justify-content-center"  href="ShoppingCartCL?action=post&id=<%=p.getId()%>&type=0"><i class="fa fa-shopping-cart"></i></a></li>--%>
                                <li>
                                    <a class="d-flex add-to-cart align-items-center justify-content-center"
                                       href="javascript:void(0)"
                                       data-id="<%=p.getId()%>"
                                       onclick="addCart(this, '<%=p.getId()%>', '<%=remain%>')">
                                        <i class="fa fa-shopping-cart"></i>
                                    </a>
                                </li>
                            </ul>
                        </div>
                        <div class="featured__item__text">
                            <h6><a href="ProductInfor?id_product=<%= p.getId() %>"><%=p.getProduct_name()%></a></h6>
                            <h5><fmt:formatNumber value="<%= p.getPrice() %>" type="currency" pattern="###,###"/>₫</h5>
                        </div>
                    </div>
                </div>
                <% } %>
            </div>
        </div>
    </div>
</section>
<!-- Categories Section End -->

<!-- Featured Section Begin -->
<section class="pb-5">
    <div class="container">
        <div class="row">
            <div class="col-lg-12">
                <div class="section-title">
                    <h2>Các sản phẩm theo loại</h2>
                </div>
                <div class="featured__controls">
                    <ul>
                        <li class="active" data-filter="*">All</li>
                        <% for(Category list : cb.getListCategory()) {%>
                        <% String id = null; %>
                        <% if(list.getId() == 1) {%>
                        <li data-filter=".a">Thuốc trừ cỏ</li>
                        <% } %>
                        <% if(list.getId() == 2) {%>
                        <li data-filter=".b">Thuốc trừ sâu</li>
                        <% } %>
                        <% if(list.getId() == 3) {%>
                        <li data-filter=".c">Thuốc trừ bệnh</li>
                        <% } %>
                        <% if(list.getId() == 4) {%>
                        <li data-filter=".d">Thuốc trừ ốc</li>
                        <% } %>
                        <%--                        <li data-filter=".fastfood">Thuốc bảo vệ thực vật</li>--%>
                        <% } %>
                    </ul>
                </div>
            </div>
        </div>
        <div class="row featured__filter">
            <%
                for(Products p : products) {
                    int remain = p.getInventory_quantity();
                    if (shoppingCart != null) {
                        for (CartItem item : shoppingCart.getCartItemList()) {
                            if (item.getProduct().getId()==p.getId()) {
                                remain = p.getInventory_quantity() - item.getQuantity();
                            }
                        }
                    }
                    if(p.getId_category() == 1) {
            %>
            <div class="col-lg-3 col-md-4 col-sm-6 mix a">
                <div class="featured__item">
                    <div class="featured__item__pic set-bg" data-setbg="<%=p.getImage()%>">
                        <ul class="featured__item__pic__hover">
                            <li><a class="d-flex align-items-center justify-content-center" href="ProductInfor?id_product=<%= p.getId() %>"><i class="fa fa-retweet"></i></a></li>
                            <li>
                                <a class="d-flex add-to-cart align-items-center justify-content-center"
                                   href="javascript:void(0)"
                                   data-id="<%=p.getId()%>"
                                   onclick="addCart(this, '<%=p.getId()%>', '<%=remain%>')">
                                    <i class="fa fa-shopping-cart"></i>
                                </a>
                            </li>

                        </ul>
                    </div>
                    <div class="featured__item__text">
                        <h6><a href="ProductInfor?id_product=<%= p.getId() %>"><%=p.getProduct_name()%></a></h6>
                        <h5><fmt:formatNumber value="<%= p.getPrice() %>" type="currency" pattern="###,###"/>₫</h5>
                    </div>
                </div>
            </div>
            <% } %>
            <% if(p.getId_category() == 2) { %>
            <div class="col-lg-3 col-md-4 col-sm-6 mix b">
                <div class="featured__item">
                    <div class="featured__item__pic set-bg" data-setbg="<%=p.getImage()%>">
                        <ul class="featured__item__pic__hover">
                            <li><a class="d-flex align-items-center justify-content-center" href="ProductInfor?id_product=<%= p.getId() %>"><i class="fa fa-retweet"></i></a></li>
<%--                            <li><a class="d-flex align-items-center justify-content-center" href="ShoppingCartCL?action=post&id=<%=p.getId()%>&type=0"><i class="fa fa-shopping-cart"></i></a></li>--%>
                            <li>
                                <a class="d-flex add-to-cart align-items-center justify-content-center"
                                   href="javascript:void(0)"
                                   data-id="<%=p.getId()%>"
                                   onclick="addCart(this, '<%=p.getId()%>', '<%=remain%>')">
                                    <i class="fa fa-shopping-cart"></i>
                                </a>
                            </li>
                        </ul>
                    </div>
                    <div class="featured__item__text">
                        <h6><a href="ProductInfor?id_product=<%= p.getId() %>"><%=p.getProduct_name()%></a></h6>
                        <%--                        <h5><%=p.getPrice()%></h5>--%>
                        <h5><fmt:formatNumber value="<%= p.getPrice() %>" type="currency" pattern="###,###"/>₫</h5>
                    </div>
                </div>
            </div>
            <% } %>
            <% if(p.getId_category() == 3) {%>
            <div class="col-lg-3 col-md-4 col-sm-6 mix c">
                <div class="featured__item">
                    <div class="featured__item__pic set-bg" data-setbg="<%=p.getImage()%>">
                        <ul class="featured__item__pic__hover">
                            <li><a class="d-flex align-items-center justify-content-center" href="ProductInfor?id_product=<%= p.getId() %>"><i class="fa fa-retweet"></i></a></li>
<%--                            <li><a class="d-flex align-items-center justify-content-center" href="ShoppingCartCL?action=post&id=<%=p.getId()%>&type=0"><i class="fa fa-shopping-cart"></i></a></li>--%>
                            <li>
                                <a class="d-flex add-to-cart align-items-center justify-content-center"
                                   href="javascript:void(0)"
                                   data-id="<%=p.getId()%>"
                                   onclick="addCart(this, '<%=p.getId()%>', '<%=remain%>')">
                                    <i class="fa fa-shopping-cart"></i>
                                </a>
                            </li>
                        </ul>
                    </div>
                    <div class="featured__item__text">
                        <h6><a href="ProductInfor?id_product=<%= p.getId() %>"><%=p.getProduct_name()%></a></h6>
                        <%--                        <h5><%=p.getPrice()%></h5>--%>
                        <h5><fmt:formatNumber value="<%= p.getPrice() %>" type="currency" pattern="###,###"/>₫</h5>
                    </div>
                </div>
            </div>
            <% } %>
            <% if(p.getId_category() == 4) {%>
            <div class="col-lg-3 col-md-4 col-sm-6 mix d">
                <div class="featured__item">
                    <div class="featured__item__pic set-bg" data-setbg="<%=p.getImage()%>">
                        <ul class="featured__item__pic__hover">
                            <li><a class="d-flex align-items-center justify-content-center" href="ProductInfor?id_product=<%= p.getId() %>"><i class="fa fa-retweet"></i></a></li>
<%--                            <li><a class="d-flex align-items-center justify-content-center" href="ShoppingCartCL?action=post&id=<%=p.getId()%>&type=0"><i class="fa fa-shopping-cart"></i></a></li>--%>
                            <li>
                                <a class="d-flex add-to-cart align-items-center justify-content-center"
                                   href="javascript:void(0)"
                                   data-id="<%=p.getId()%>"
                                   onclick="addCart(this, '<%=p.getId()%>', '<%=remain%>')">
                                    <i class="fa fa-shopping-cart"></i>
                                </a>
                            </li>
                        </ul>
                    </div>
                    <div class="featured__item__text">
                        <h6><a href="ProductInfor?id_product=<%= p.getId() %>"><%=p.getProduct_name()%></a></h6>
                        <%--                        <h5><%=p.getPrice()%></h5>--%>
                        <h5><fmt:formatNumber value="<%= p.getPrice() %>" type="currency" pattern="###,###"/>₫</h5>
                    </div>
                </div>
            </div>
            <% } }%>
        </div>
    </div>
</section>
<!-- Featured Section End -->

<!-- Banner Begin -->
<div class="banner">
    <div class="container">
        <div class="row">
            <div class="col-lg-6 col-md-6 col-sm-6">
                <div class="banner__pic">
                    <img src="assets/img/banner/banner-1.jpg" alt="">
                </div>
            </div>
            <div class="col-lg-6 col-md-6 col-sm-6">
                <div class="banner__pic">
                    <img src="assets/img/banner/banner-2.jpg" alt="">
                </div>
            </div>
        </div>
    </div>
</div>
<!-- Banner End -->

<!-- Latest Product Section Begin -->
<section class="latest-product spad">
    <div class="container">
        <div class="row" style="justify-content: space-around">
            <div class="col-lg-4 col-md-6">
                <div class="latest-product__text">
                    <h4>Sản phẩm mới nhất</h4>
                    <div class="latest-product__slider owl-carousel">
                        <div class="latest-prdouct__slider__item">
                            <% for(Products p : productNew1) {%>
                            <a href="ProductInfor?id_product=<%= p.getId() %>" class="latest-product__item">
                                <div class="latest-product__item__pic">
                                    <img src="<%=p.getImage()%>" alt="">
                                </div>
                                <div class="latest-product__item__text">
                                    <h6><%=p.getProduct_name()%></h6>
                                    <span><fmt:formatNumber value="<%= p.getPrice() %>" type="currency" pattern="###,###"/>₫</span>
                                </div>
                            </a>
                            <% } %>
                        </div>
                        <div class="latest-prdouct__slider__item">
                            <% for(Products p : productNew2) { %>
                            <a href="ProductInfor?id_product=<%= p.getId() %>" class="latest-product__item">
                                <div class="latest-product__item__pic">
                                    <img src="<%=p.getImage()%>" alt="">
                                </div>
                                <div class="latest-product__item__text">
                                    <h6><%=p.getProduct_name()%></h6>
                                    <span><fmt:formatNumber value="<%= p.getPrice() %>" type="currency" pattern="###,###"/>₫</span>
                                </div>
                            </a>
                            <% } %>
                        </div>
                    </div>
                </div>
            </div>

            <div class="col-lg-4 col-md-6">
                <div class="latest-product__text">
                    <h4>Sản phẩm đang giảm giá</h4>
                    <div class="latest-product__slider owl-carousel">
                        <div class="latest-prdouct__slider__item">
                            <% for(Products p : findDiscountPro1) {%>
                            <a href="ProductInfor?id_product=<%= p.getId() %>" class="latest-product__item">
                                <div class="latest-product__item__pic">
                                    <img src="<%=p.getImage()%>" alt="">
                                </div>
                                <div class="latest-product__item__text">
                                    <h6><%=p.getProduct_name()%></h6>
                                    <span><fmt:formatNumber value="<%= p.getPrice() %>" type="currency" pattern="###,###"/>₫</span>
                                </div>
                            </a>
                            <% } %>
                        </div>
                        <div class="latest-prdouct__slider__item">
                            <% for(Products p : findDiscountPro2) {%>
                            <a href="ProductInfor?id_product=<%= p.getId() %>" class="latest-product__item">
                                <div class="latest-product__item__pic">
                                    <img src="<%=p.getImage()%>" alt="">
                                </div>
                                <div class="latest-product__item__text">
                                    <h6><%=p.getProduct_name()%></h6>
                                    <span><fmt:formatNumber value="<%= p.getPrice() %>" type="currency" pattern="###,###"/>₫</span>
                                </div>
                            </a>
                            <% } %>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</section>
<!-- Latest Product Section End -->

<!-- Footer Section Begin -->
<footer class="footer spad">
    <div class="container">
        <div class="row">
            <div class="col-lg-3 col-md-6 col-sm-6">
                <div class="footer__about">
                    <div class="footer__about__logo">
                        <a href="HomePageController"><img src="assets/img/logo.png" alt=""></a>
                    </div>
                    <ul>
                        <li>Địa chỉ: 171 Nguyễn Văn Khối, Phường 8, Gò Vấp, TP. HCM</li>
                        <li>Liên hệ: +84 123456789</li>
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
                    <div class="footer__widget__social d-flex">
                        <a class="d-flex align-items-center justify-content-center" href="#"><i class="fa fa-facebook"></i></a>
                        <a class="d-flex align-items-center justify-content-center" href="#"><i class="fa fa-instagram"></i></a>
                        <a class="d-flex align-items-center justify-content-center" href="#"><i class="fa fa-twitter"></i></a>
                        <a class="d-flex align-items-center justify-content-center" href="#"><i class="fa fa-pinterest"></i></a>
                    </div>
                </div>
            </div>
        </div>
    </div>
</footer>
<!-- Js Plugins -->
<script src="assets/js/jquery-3.3.1.min.js"></script>
<script src="assets/js/bootstrap.min.js"></script>
<script src="assets/js/jquery.nice-select.min.js"></script>
<script src="assets/js/jquery-ui.min.js"></script>
<script src="assets/js/jquery.slicknav.js"></script>
<script src="assets/js/mixitup.min.js"></script>
<script src="assets/js/owl.carousel.min.js"></script>
<script src="assets/js/main.js"></script>
<script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
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