<%@ page import="dao.CommentDAO" %>
<%@ page import="bean.Comment" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.List" %>
<%@ page import="bean.Products" %>
<%@page language="java" contentType="text/html; UTF-8" pageEncoding="UTF-8" %>
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
    <%--    <script src="assets/js/log_reg.js" defer></script>--%>
    <style>
        .review-rating {
            font-size: 1.5rem;
            color: #FFD700; /* Gold color for stars */
        }
        .review-user {
            font-weight: bold;
        }
        .review-content {
            margin-top: 10px;
        }
        .filter-buttons button {
            margin-right: 5px;
        }
        .product__details__text {
            margin-bottom: 30px;
        }
        .product__details__price {
            color: #e53637;
            font-size: 24px;
            font-weight: 700;
        }
        .product__details__text p {
            color: #6f6f6f;
            margin-bottom: 20px;
        }
        .product-quantity {
            color: #ff0000;
            font-weight: bold;
            margin-bottom: 20px;
        }
        .product__details__quantity .quantity input {
            width: 50px;
            height: 40px;
            text-align: center;
            border: 1px solid #e5e5e5;
            border-radius: 5px;
        }
        .add-to-cart {
            background-color: #fff;
            border: 2px solid #7fad39;
            padding: 10px 20px;
            color: #7fad39;
            font-weight: bold;
            text-transform: uppercase;
            border-radius: 5px;
            margin-right: 10px;
            display: flex;
            align-items: center;
        }
        .add-to-cart:hover {
            background-color: #7fad39;
            border-color: #f8d7da;
        }
        .primary-btn {
            background-color: #7fad39;
            border: none;
            padding: 10px 20px;
            color: #fff;
            font-weight: bold;
            text-transform: uppercase;
            border-radius: 5px;
        }
        .primary-btn:hover {
            background-color: #5a9e1b;
        }
        .quantity-container {
            display: flex;
            align-items: center;
        }
        .quantity-label {
            margin-right: 10px;
            font-weight: bold;
            color: #6f6f6f;
        }
        .available-quantity {
            margin-left: 10px;
            color: #6f6f6f;
        }
        .qtybtn{
            display:none!important;
        }
    </style>
</head>
<%
    List<Comment> comments = (List<Comment>) request.getAttribute("comments");
    Products proID = (Products) request.getAttribute("proID");
%>
<body>

<jsp:include page="layout/header.jsp"/>
<!-- Breadcrumb Section Begin -->
<section class="breadcrumb-section set-bg" data-setbg="assets/img/breadcrumb.jpg">
    <div class="container">
        <div class="row">
            <div class="col-lg-12 text-center">
                <div class="breadcrumb__text">
                    <h2>Thông tin sản phẩm</h2>
                    <div class="breadcrumb__option">
                        <a href="index.jsp">Trang chủ</a>
                        <a href="index.jsp">Cửa hàng</a>
                        <span>Chi tiết sản phẩm</span>
                    </div>
                </div>
            </div>
        </div>
    </div>
</section>
<!-- Breadcrumb Section End -->

<!-- Product Details Section Begin -->
<section class="product-details spad">
    <div class="container">
        <% if(proID != null) {%>
        <div class="row">
            <div class="col-lg-6 col-md-6">
                <div class="product__details__pic">

                    <div class="product__details__pic__item">
                        <img class="product__details__pic__item--large"
                             src="<%= proID.getImage() %>" alt="">
                    </div>
                    <div class="product__details__pic__slider owl-carousel">
                        <img data-imgbigurl="assets/img/product/details/product-details-2.jpg"
                             src="assets/img/product/details/thumb-1.jpg" alt="">
                        <img data-imgbigurl="assets/img/product/details/product-details-3.jpg"
                             src="assets/img/product/details/thumb-2.jpg" alt="">
                        <img data-imgbigurl="assets/img/product/details/product-details-5.jpg"
                             src="assets/img/product/details/thumb-3.jpg" alt="">
                        <img data-imgbigurl="assets/img/product/details/product-details-4.jpg"
                             src="assets/img/product/details/thumb-4.jpg" alt="">
                    </div>
                </div>
            </div>
            <div class="col-lg-6 col-md-6">
                <div class="product__details__text">
                    <h3><%= proID.getProduct_name() %></h3>
                    <div class="product__details__price"><%= proID.formatPrice() %>₫</div>
                    <p><%= proID.getDes() %></p>
                    <div class="quantity-container">
                        <span class="quantity-label">Số Lượng</span>
                        <div class="product__details__quantity">
                            <div class="quantity">
                                <div class="pro-qty">
                                    <button id="decrease" class="btn-decrease">-</button>
                                    <input type="number" id="quantity" class="input-number" value="1" min="1" max="${requestScope.remain}" />
                                    <button id="increase" class="btn-increase">+</button>
                                    <span style="color: red; margin: 10px" id="error"></span>
                                </div>
                            </div>
                        </div>
                        <span class="available-quantity"><%= proID.getInventory_quantity() %> sản phẩm có sẵn</span>
                    </div>
                    <div class="d-flex align-items-center mt-3">
                        <a class="add-to-cart" id="add_cart"
                           href="javascript:void(0)"
                           data-id="<%=proID.getId()%>"
                           onclick="addCart(this, '<%=proID.getId()%>')" style="color:#7fad39;background-color:#fff; border: 2px solid #7fad39;">
                            <i class="fa fa-shopping-cart"></i> Thêm Vào Giỏ Hàng
                        </a>
                        <a href="javascript:void(0);" id="buyNowBtn" class="primary-btn">Mua Ngay</a>
                    </div>
                    <ul>
                        <!-- Các phần tử khác -->
                    </ul>
                </div>
            </div>
            <div class="col-lg-12">
                <div class="product__details__tab">
                    <ul class="nav nav-tabs" role="tablist">
                        <li class="nav-item">
                            <a class="nav-link active" data-toggle="tab" href="#tabs-1" role="tab"
                               aria-selected="true">Mô tả</a>
                        </li>
                            <li class="nav-item">

                                <a class="nav-link" data-toggle="tab" href="#tabs-3" role="tab"
                                    aria-selected="false">Đánh giá <span>(<%=comments.size() %>)</span></a>
                            </li>
                        <li class="nav-item">
                            <a class="nav-link" data-toggle="tab" href="#tabs-2" role="tab" aria-selected="false">
                                Binh luan
                            </a>
                        </li>
                    </ul>
                    <div class="tab-content">
                        <div class="tab-pane active" id="tabs-1" role="tabpanel">
                            <div class="product__details__tab__desc">
                                <h6>Mô tả sản phẩm</h6>
                                <p>
                                    Camilo 150SC là thuốc trừ bệnh 2 hoạt chất Azoxystrobin và Hexaconazole có tác động
                                    tiếp xúc, lưu dẫn, thấm sâu nhanh, chuyển vị mạnh trong cây, rất an toàn cho môi
                                    trường và con người.
                                </p>

                                <p>Thành phần:</p>
                                <ul>
                                    <li>Azoxystrobin: 50g/lít</li>
                                    <li>Hexaconazole: 100g/lít</li>
                                    <li>Phụ gia</li>
                                </ul>

                                <p>Quy cách: Chai 100 ml</p>

                                <p>Công dụng:</p>
                                <ul>
                                    <li>CAMILO 150SC – là thuốc chuyên trừ bệnh đốm vằn, vàng lá chín sớm trên lúa.</li>
                                    <li>CAMILO 150SC – có tác động tiếp xúc, lưu dẫn, thấm sâu nhanh, chuyển vị mạnh
                                        trong cây nên không sợ mưa rửa trôi sau vài giờ phun thuốc.
                                    </li>
                                    <li>CAMILO 150SC – làm khô nhanh vết bệnh chỉ sau 01 ngày phun thuốc. Nấm bệnh rất
                                        khó hình thành tính kháng nhờ có cơ chế tác động kép Vừa ngăn cản sự tạo vách tế
                                        bào nấm bệnh, vừa chặn đứng quá trình tạo năng lượng của tế bào nấm.
                                    </li>
                                    <li>CAMILO 150SC – giúp cây lúa sạch lá ủ (lá chân), nuôi dưỡng lá đòng xanh lâu,
                                        bông lúa vàng sáng, chắc tới cậy.
                                    </li>
                                    <li>CAMILO 150SC – rất an toàn và thân thiện với môi trường.</li>
                                </ul>
                            </div>
                        </div>
                        <div class="tab-pane" id="tabs-2" role="tabpanel">
                            <div class="fb-comments"
                                 style="border: 1px solid #ccc; margin-top: 12px"
                                 data-href="thong-tin-don-hang.jsp?productID=<%=request.getParameter("id_product")%>" data-width="1100"
                                 data-numposts="5">
                            </div>
                        </div>

                        <div class="tab-pane" id="tabs-3" role="tabpanel">
                            <div class="product__details__tab__desc">
                                <div class="row">
                                    <div class="col-md-12">
                                        <h2 class="mb-4">Đánh Giá Sản Phẩm</h2>
                                        <div class="d-flex align-items-center mb-4">
                                            <div class="mr-3">
                                                <span class="review-rating">4.8</span> trên 5
                                            </div>
                                            <div>
                                                <span class="text-warning">★★★★★</span>
                                                <span>(120 đánh giá)</span>
                                            </div>
                                        </div>
                                        <div class="filter-buttons mb-4">
                                            <button class="btn btn-outline-secondary p-2">Tất Cả</button>
                                            <button class="btn btn-outline-secondary p-2">5 Sao (112)</button>
                                            <button class="btn btn-outline-secondary p-2">4 Sao (10)</button>
                                            <button class="btn btn-outline-secondary p-2">3 Sao (5)</button>
                                            <button class="btn btn-outline-secondary p-2">2 Sao (0)</button>
                                            <button class="btn btn-outline-secondary p-2">1 Sao (3)</button>
                                            <button class="btn btn-outline-secondary p-2">Có Bình Luận (73)</button>
                                            <button class="btn btn-outline-secondary p-2">Có Hình Ảnh / Video (41)</button>
                                        </div>
                                        <div class="review">
                                            <div class="d-flex align-items-center">
                                                <img src="https://via.placeholder.com/50" class="rounded-circle mr-3" alt="User Image">
                                                <div>
                                                    <span class="review-user">h****n</span>
                                                    <span class="text-muted">2024-07-04 16:38 | Phân loại hàng: màu nữ</span>
                                                </div>
                                            </div>
                                            <div class="review-content mt-3">
                                                <p>Chất liệu: nhựa<br>Chất lượng sản phẩm: tốt<br>Đúng với mô tả: đúng mô tả của shop</p>
                                                <p>Giao hàng nhanh chóng, đóng gói cẩn thận, rất đáng mua bạn.</p>
                                            </div>
                                        </div>
                                        <hr>
                                        <!-- Repeat the .review block for more reviews -->
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <% } %>
    </div>
</section>
<!-- Product Details Section End -->

<!-- Related Product Section Begin -->
<section class="related-product">
    <div class="container">
        <div class="row">
            <div class="col-lg-12">
                <div class="section-title related__product__title">
                    <h2>Sản phẩm liên quan</h2>
                </div>
            </div>
        </div>
        <div class="row">
            <div class="col-lg-3 col-md-4 col-sm-6">
                <div class="product__item">
                    <div class="product__item__pic set-bg" data-setbg="assets/img/product/product-1.jpg">
                        <ul class="product__item__pic__hover">

                            <li><a href="#"><i class="fa fa-retweet"></i></a></li>
                            <li><a href="#"><i class="fa fa-shopping-cart"></i></a></li>
                        </ul>
                    </div>
                    <div class="product__item__text">
                        <h6><a href="#">Giống cây bơ</a></h6>
                        <h5>45.000₫</h5>
                    </div>
                </div>
            </div>
            <div class="col-lg-3 col-md-4 col-sm-6">
                <div class="product__item">
                    <div class="product__item__pic set-bg" data-setbg="assets/img/product/product-2.jpg">
                        <ul class="product__item__pic__hover">

                            <li><a href="#"><i class="fa fa-retweet"></i></a></li>
                            <li><a href="#"><i class="fa fa-shopping-cart"></i></a></li>
                        </ul>
                    </div>
                    <div class="product__item__text">
                        <h6><a href="#">Giống cây bưởi</a></h6>
                        <h5>45.000₫</h5>
                    </div>
                </div>
            </div>
            <div class="col-lg-3 col-md-4 col-sm-6">
                <div class="product__item">
                    <div class="product__item__pic set-bg" data-setbg="assets/img/product/product-3.jpg">
                        <ul class="product__item__pic__hover">


                            <li><a href="#"><i class="fa fa-retweet"></i></a></li>
                            <li><a href="#"><i class="fa fa-shopping-cart"></i></a></li>
                        </ul>
                    </div>
                    <div class="product__item__text">
                        <h6><a href="#">Giống cây mít</a></h6>
                        <h5>45.000₫</h5>
                    </div>
                </div>
            </div>
            <div class="col-lg-3 col-md-4 col-sm-6">
                <div class="product__item">
                    <div class="product__item__pic set-bg" data-setbg="assets/img/product/product-7.jpg">
                        <ul class="product__item__pic__hover">

                            <li><a href="#"><i class="fa fa-retweet"></i></a></li>
                            <li><a href="#"><i class="fa fa-shopping-cart"></i></a></li>
                        </ul>
                    </div>
                    <div class="product__item__text">
                        <h6><a href="#">Thuốc trừ sâu</a></h6>
                        <h5>45.000₫</h5>
                    </div>
                </div>
            </div>
        </div>

    </div>
</section>
<!-- Related Product Section End -->

<!-- Footer Section Begin -->
<jsp:include page="layout/footer.jsp"/>
<!-- Footer Section End -->
<%--<div class="blur-bg-overlay"></div>--%>
<%--<div class="form-popup">--%>
<%--    <span class="close-btn material-symbols-rounded"><svg xmlns="http://www.w3.org/2000/svg" height="1em"--%>
<%--                                                          viewBox="0 0 384 512"><path--%>
<%--            d="M376.6 84.5c11.3-13.6 9.5-33.8-4.1-45.1s-33.8-9.5-45.1 4.1L192 206 56.6 43.5C45.3 29.9 25.1 28.1 11.5 39.4S-3.9 70.9 7.4 84.5L150.3 256 7.4 427.5c-11.3 13.6-9.5 33.8 4.1 45.1s33.8 9.5 45.1-4.1L192 306 327.4 468.5c11.3 13.6 31.5 15.4 45.1 4.1s15.4-31.5 4.1-45.1L233.7 256 376.6 84.5z"/></svg></span>--%>
<%--    <div class="form-box login">--%>
<%--        <div class="form-details">--%>
<%--            <h2>Chào mừng quý khách quay lại</h2>--%>
<%--            <p>Hãy điền địa chỉ email và mật khẩu vào các ô bên phải</p>--%>
<%--        </div>--%>
<%--        <div class="form-content">--%>
<%--            <h2>ĐĂNG NHẬP</h2>--%>
<%--            <form action="#">--%>
<%--                <div class="input-field">--%>
<%--                    <input type="text" name="name" required>--%>
<%--                    <label>Email</label>--%>
<%--                </div>--%>
<%--                <div class="input-field">--%>
<%--                    <input type="password" name="password" required>--%>
<%--                    <label>Password</label>--%>
<%--                </div>--%>
<%--                <a href="#" class="forgot-pw">Quên mật khẩu?</a>--%>
<%--                <button type="submit">Login</button>--%>
<%--            </form>--%>
<%--            <div class="bottom-link">--%>
<%--                Chưa có tài khoản--%>
<%--                <a href="#" id="signup-link">Đăng ký</a>--%>
<%--            </div>--%>
<%--        </div>--%>
<%--    </div>--%>
<%--    <!--ĐĂNG KÝ-->--%>
<%--    <div class="form-box signup">--%>
<%--        <div class="form-details">--%>
<%--            <h2>Tạo tài khoản</h2>--%>
<%--            <p>Điền thông tin theo yêu cầu bên phải</p>--%>
<%--        </div>--%>
<%--        <div class="form-content">--%>
<%--            <h2>ĐĂNG KÝ</h2>--%>
<%--            <form action="#">--%>
<%--                <div class="input-field">--%>
<%--                    <input type="text" name="name" required>--%>
<%--                    <label>Nhập email</label>--%>
<%--                </div>--%>
<%--                <div class="input-field">--%>
<%--                    <input type="password" name="password" required>--%>
<%--                    <label>Tạo Password</label>--%>
<%--                </div>--%>
<%--                <div class="policy-text">--%>
<%--                    <input type="checkbox" id="policy">--%>
<%--                    <label for="policy">--%>
<%--                        Tôi đồng ý với--%>
<%--                        <a href="">Điều khoản</a>--%>
<%--                    </label>--%>

<%--                </div>--%>
<%--                <button type="submit">Register</button>--%>
<%--            </form>--%>
<%--            <div class="bottom-link">--%>
<%--                Đã có sẵn tài khoản?--%>
<%--                <a href="#" id="login-link">Login</a>--%>
<%--            </div>--%>
<%--        </div>--%>
<%--    </div>--%>
<%--</div>--%>
<!-- Back top end-->

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
<div id="fb-root"></div>
<script async defer crossorigin="anonymous"
        src="https://connect.facebook.net/en_US/sdk.js#xfbml=1&version=v20.0&appId=1072330460766904" nonce="9nNhsvob"></script>
<script>
    let quantityInput = document.getElementById("quantity");
    let btnAddCart = document.getElementById("add_cart");
    let increase = document.getElementById("increase");
    let decrease = document.getElementById("decrease");
    function addCart(btn, id) {
        var quantity = $('#quantity').val();
        const input = document.getElementById('quantity');
        const max = parseInt(input.max, 10);
        $.ajax({
            url: "ShoppingCartCL",
            method: "POST",
            data: {
                id: id,
                action: "add",
                type: 1,
                quantity: quantity,
                contain: max
            },
            success: function (response) {
                if (response.status === "failed") {
                    window.location.href = 'login';
                } else if (response.status === "empty" || response.status === "out" || response.status === "bigger") {
                    console.log(response.error);
                    $('#error').html(response.error);
                } else if (response.status === "stock") {
                    $('#error').html(response.error);
                    quantityInput.style.display = "none";
                    btnAddCart.style.display = "none";
                    increase.style.display = "none";
                    decrease.style.display = "none";
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
                    $('#quantity').attr('max', response.prefix);
                    $('#error').html("");
                }
            }
        });
    }
</script>
<script>
    document.getElementById('buyNowBtn').addEventListener('click', function() {
        // Lấy giá trị đã nhập từ input
        var quantity = document.getElementById('quantity').value;
        // Tạo URL mới với giá trị đã nhập từ input
        var url = "ShoppingCartCL?action=post&id=<%=proID.getId()%>&type=1&quantity=" + encodeURIComponent(quantity);
        // Chuyển hướng đến URL mới
        window.location.href = url;
    });
    document.addEventListener('DOMContentLoaded', function() {
        const input = document.getElementById('quantity');
        const increaseBtn = document.getElementById('increase');
        const decreaseBtn = document.getElementById('decrease');

        // Kiểm tra và giới hạn giá trị nhập vào input
        input.addEventListener('input', function() {
            const min = parseInt(input.min, 10);
            const max = parseInt(input.max, 10);
            let value = parseInt(input.value, 10);

            if (value > max) {
                input.value = max;
            } else if (value < min) {
                input.value = min;
            }
        });

        // Tăng số lượng
        increaseBtn.addEventListener('click', function() {
            const max = parseInt(input.max, 10);
            let value = parseInt(input.value, 10);
            if (value < max) {
                input.value = value + 1;
            }
        });

        // Giảm số lượng
        decreaseBtn.addEventListener('click', function() {
            const min = parseInt(input.min, 10);
            let value = parseInt(input.value, 10);
            if (value > min) {
                input.value = value - 1;
            }
        });
    });
</script>
<%--<script>--%>
<%--    document.getElementById('addToCartBtn').addEventListener('click', function() {--%>
<%--        // Lấy giá trị đã nhập từ input--%>
<%--        var quantity = document.getElementById('quantityInput').value;--%>

<%--        // Tạo URL mới với giá trị đã nhập từ input--%>
<%--        var url = "ShoppingCartCL?action=post&id=<%=proID.getId()%>&quantity=" + encodeURIComponent(quantity);--%>

<%--        // Chuyển hướng đến URL mới--%>
<%--        window.location.href = url;--%>
<%--    });--%>
<%--</script>--%>


</body>

</html>