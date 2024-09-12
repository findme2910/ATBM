<%@ page import="dao.CommentDAO" %>
<%@ page import="bean.Comment" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.List" %>
<%@ page import="bean.Products" %>
<%@ page import="bean.ProductReview" %>
<%@page language="java" contentType="text/html; UTF-8" pageEncoding="UTF-8" %>
<%--chức năng cho người dùng đánh giá sản phẩm đang ở dạng comment--%>

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
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.6.0/css/all.min.css" integrity="sha512-Kc323vGBEqzTmouAECnVceyQqyqdsSiqLQISBL29aUW4U/M7pSPA/gEUZQqv1cwx4OnYxTxve5UMg5GT6L4JJg==" crossorigin="anonymous" referrerpolicy="no-referrer" />
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
        .qtybtn {
            display: none !important;
        }
        .product__details__tab .nav-tabs:after {
            width: 320px;

        }
        .product__details__tab .nav-tabs:before {
            width: 320px;

        }

    </style>
</head>
<%
    List<ProductReview> productReviews = (List<ProductReview>) request.getAttribute("productReviews");
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
                        <img class="product__details__pic__item--large"
                             src="<%= proID.getImage() %>" alt="">
                    </div>
                </div>
            </div>
            <div class="col-lg-6 col-md-6">
                <div class="product__details__text">
                    <h3><%= proID.getProduct_name() %></h3>
                    <div class="product__details__price" style="margin-bottom: 0"><%= proID.formatPrice() %>₫</div>

                    <div class="quantity-container">
                        <span class="quantity-label">Số Lượng</span>
                        <div class="product__details__quantity">
                            <div class="quantity">
                                <div class="pro-qty" style="
    display: flex;
    align-items: center;
    justify-content: center;
    border-radius: 999px;
    margin-top: 30px;
">

                                    <input style="border: none" type="number" id="quantity" class="input-number" value="1" min="1" max="<%=request.getAttribute("remain")%>" />


                                </div>    <span style="color: red; margin: 10px" id="error"></span>

                            </div>
                        </div>
                        <span class="available-quantity"><%= proID.getInventory_quantity() %> sản phẩm có sẵn</span>
                    </div>
                    <div class="d-flex align-items-center">
                        <a class="add-to-cart" id="add_cart"
                           href="javascript:void(0)"
                           data-id="<%=proID.getId()%>"
                           onclick="addCart(this, '<%=proID.getId()%>')" style="color:#7fad39;background-color:#fff; border: 2px solid #7fad39;">
                            <i class="fa fa-shopping-cart"></i> Thêm Vào Giỏ Hàng
                        </a>

                    </div>
                  <div class="mt-3" style="font-size: 22px; display:flex; flex-direction: column;height:170px;justify-content: space-around;margin-top:30px !important;">
                      <b>Chính sách của sản phẩm:</b>
                      <p class="mt-2 mb-0" style="font-size: 20px;"> <i class="fa-solid fa-circle-check" style="color: green;font-size: 20px"></i> Bảo vệ người mua hàng</p>
                      <p class="mb-0" style="font-size: 20px;"> <i class="fa-solid fa-circle-check" style="color: green;font-size: 20px"></i> Hoàn 100% hoá đơn nếu khách hàng không nhận được hàng</p>
                      <p class="mb-0" style="font-size: 20px;"> <i class="fa-solid fa-circle-check" style="color: green;font-size: 20px"></i> Trả lại sản phẩm nếu như không giống với mô tả</p>
                  </div>
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
                                    aria-selected="false" id="commentPreview">Đánh giá <span>(<%=productReviews.size() %>)</span></a>
                            </li>
                        <li class="nav-item">
                            <a class="nav-link" data-toggle="tab" href="#tabs-2" role="tab" aria-selected="false">
                                Bình luận bằng Facebook
                            </a>
                        </li>
                    </ul>
                    <div class="tab-content">
                        <div class="tab-pane active" id="tabs-1" role="tabpanel">
                            <div class="product__details__tab__desc">
                                <h6>Mô tả sản phẩm</h6>
                                <p><%= proID.formatDescription(proID.getDes()) %></p>
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
                                        <h2 class="mb-4" style="font-size:25px">Đánh Giá Sản Phẩm</h2>
                                        <div class="d-flex align-items-center mb-4">
                                            <div class="mr-3">
                                                <span class="review-rating" style="font-size:20px;color:black"><%= String.format("%.1f", (Double) request.getAttribute("averageRating")) %> trên 5</span>
                                            </div>
                                            <div style="font-size:20px">
                                                <span class="text-warning">★★★★★</span>
                                            </div>
                                        </div>
                                        <div class="filter-buttons mb-4">
                                            <button class="btn btn-outline-secondary p-2 active" onclick="loadComments('all', this)">Tất Cả</button>
                                            <button class="btn btn-outline-secondary p-2" onclick="loadComments(5, this)">5 Sao</button>
                                            <button class="btn btn-outline-secondary p-2" onclick="loadComments(4, this)">4 Sao</button>
                                            <button class="btn btn-outline-secondary p-2" onclick="loadComments(3, this)">3 Sao</button>
                                            <button class="btn btn-outline-secondary p-2" onclick="loadComments(2, this)">2 Sao</button>
                                            <button class="btn btn-outline-secondary p-2" onclick="loadComments(1, this)">1 Sao</button>
                                        </div>
                                        <div id="commentsSection">
                                            <!-- Comments will be loaded here -->
                                        </div>
<%--                                        <div class="review">--%>
<%--                                            <div class="d-flex align-items-center">--%>
<%--                                                <img src="https://via.placeholder.com/50" class="rounded-circle mr-3" alt="User Image">--%>
<%--                                                <div>--%>
<%--                                                    <span class="review-user">h****n</span>--%>
<%--                                                    <span class="text-muted">2024-07-04 16:38 | Phân loại hàng: màu nữ</span>--%>
<%--                                                </div>--%>
<%--                                            </div>--%>
<%--                                            <div class="review-content mt-3">--%>
<%--                                                <p>Chất liệu: nhựa<br>Chất lượng sản phẩm: tốt<br>Đúng với mô tả: đúng mô tả của shop</p>--%>
<%--                                                <p>Giao hàng nhanh chóng, đóng gói cẩn thận, rất đáng mua bạn.</p>--%>
<%--                                            </div>--%>
<%--                                        </div>--%>
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
            <%
                List<Products> relatedProducts = (List<Products>) request.getAttribute("relatedProducts");
                if (relatedProducts != null) {
                    for (Products product : relatedProducts) {
            %>
            <div class="col-lg-3 col-md-4 col-sm-6">
                <div class="product__item">
                    <div class="product__item__pic set-bg" data-setbg="<%= product.getImage() %>">
                        <ul class="product__item__pic__hover">
                            <li><a href="ProductInfor?id_product=<%= product.getId() %>"><i class="fa fa-retweet"></i></a></li>
                            <li><a href="javascript:void(0)" onclick="toggleWishlist(this, '<%=product.getId()%>')"><i class="fa fa-shopping-cart"></i></a></li>
                        </ul>
                    </div>
                    <div class="product__item__text">
                        <h6><a href="product-details.jsp?id=<%= product.getId() %>"><%= product.getProduct_name() %></a></h6>
                        <h5><%= product.formatPrice() %>₫</h5>
                    </div>
                </div>
            </div>
            <%
                    }
                }
            %>
        </div>
    </div>
</section>
<!-- Related Product Section End -->

<!-- Footer Section Begin -->
<jsp:include page="layout/footer.jsp"/>
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

    document.addEventListener('DOMContentLoaded', function() {
        loadComments('all', document.querySelector('.filter-buttons .btn.active'));
    });


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
    //script loadcomments
    function loadComments(rating, element) {
        var productId = '<%=proID.getId()%>';
        var url = "LoadComments?productId=" + productId + "&rating=" + rating;
        $.ajax({
            url: url,
            method: "GET",
            success: function (response) {
                $("#commentsSection").html(response);
                setActiveButton(element);
            }
        });
    }

    function setActiveButton(element) {
        var buttons = document.querySelectorAll('.filter-buttons .btn');
        buttons.forEach(function(button) {
            button.classList.remove('active');
        });
        element.classList.add('active');
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
<script>
    function toggleWishlist(element, productId) {
        var icon = element.querySelector('i');
        console.log("icon", icon);
        console.log("productId", productId);
        addToWishlist(productId);
    }

    function addToWishlist(productId) {
        $.ajax({
            url: '/wishlistController',
            method: "POST",
            data: {
                id: productId,  // Đảm bảo tham số này khớp với servlet
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
                }
                else if (response.status === "insertFailed") {
                    Swal.fire({
                        position: "center",
                        icon: "error",
                        title: "Thêm Sản Phẩm Vào Danh Sách Yêu Thích Không Thành Công!",
                        showConfirmButton: false,
                        timer: 1500
                    });
                } else if(response.status === "isExists"){
                    Swal.fire({
                        position: "center",
                        icon: "success",
                        title: "Xóa Sản Phẩm Ra Khỏi Sẵn Danh Sách Yêu Thích !",
                        showConfirmButton: false,
                        timer: 1500
                    });
                } else {
                    window.location.href = '/login';
                }
            }
        });
    }
</script>
</body>

</html>