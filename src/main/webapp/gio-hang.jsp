<%@ page import="bean.ShoppingCart" %>
<%@ page import="bean.CartItem" %>
<%@ page import="java.util.List" %>
<%@ page import="java.text.NumberFormat" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="bean.Products" %>
<%@ page import="Service.IProductService" %>
<%@ page import="Service.ProductService" %>
<%@ page import="bean.Discount" %>
<%@ page import="utils.Utils" %>
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
<%--    <link href="https://fonts.googleapis.com/css2?family=Cairo:wght@200;300;400;600;900&display=swap" rel="stylesheet">--%>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
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
    <link rel="stylesheet" href="assets/css/Log_Regis.css">
    <script src="js/log_reg.js" defer></script>
</head>
<body>
<%--<%--%>
<%--    ShoppingCart shoppingCart = (ShoppingCart) session.getAttribute("cart");--%>
<%--    if (shoppingCart == null) {--%>
<%--        // Nếu giỏ hàng chưa tồn tại, tạo mới và đặt vào session--%>
<%--        shoppingCart = new ShoppingCart();--%>
<%--        session.setAttribute("cart", shoppingCart);--%>
<%--    }--%>

<%--    List<CartItem> cartItems = shoppingCart.getCartItemList();--%>
<%--    if (cartItems == null) {--%>
<%--        cartItems = new ArrayList<>(); // Tạo danh sách sản phẩm nếu chưa có--%>
<%--    }--%>

<%--    NumberFormat numberFormat = NumberFormat.getCurrencyInstance();--%>
<%--    String e = (String) request.getAttribute("error");--%>
<%--    if (e == null) {--%>
<%--        e = ""; // Đặt giá trị mặc định là chuỗi trống nếu e là null--%>
<%--    }--%>
<%--%>--%>
<jsp:include page="layout/header.jsp"/>
<!-- Breadcrumb Section Begin -->
<section class="breadcrumb-section set-bg" data-setbg="assets/img/breadcrumb.jpg">
    <div class="container">
        <div class="row">
            <div class="col-lg-12 text-center">
                <div class="breadcrumb__text">
                    <h2>Giỏ hàng</h2>
                    <div class="breadcrumb__option">
                        <a href="index.jsp">Trang chủ</a>
                        <span>Giỏ hàng</span>
                    </div>
                </div>
            </div>
        </div>
    </div>
</section>
<!-- Breadcrumb Section End -->

<!-- Shoping Cart Section Begin -->
<section class="shoping-cart spad">
    <%
        ShoppingCart shoppingCart = (ShoppingCart) session.getAttribute("cart");
        if (shoppingCart != null) {
            if (shoppingCart.getCartItemList().isEmpty()) {
    %>
    <h1 style="text-align: center">Vui lòng mua sắm</h1>
    <%
    } else {
        double result = (double) session.getAttribute("result");
        if(result==0.0) {
    %>
    <h1 id="please" style="text-align: center"></h1>
    <%
        }
    %>
    <h1 id="please" style="text-align: center"></h1>
    <div class="container" id="container">
        <div class="row">
            <div class="col-lg-12">
                <div class="shoping__cart__table">
                    <table>
                        <thead>
                        <tr>
                            <th class="shoping__product">Sản phẩm</th>
                            <th>Giá</th>
                            <th>Tổng cộng</th>
                            <th>Số lượng</th>
                            <th></th>
                        </tr>
                        </thead>
                        <tbody>
                        <%
                            String ip = request.getHeader("X-FORWARDED-FOR");
                            if (ip == null) ip = request.getRemoteAddr();
                            IProductService productService = new ProductService();

                            for (CartItem item : shoppingCart.getCartItemList()) {
                                Products product = productService.findById(item.getProduct().getId());
                                int total = item.getQuantity()*product.getPrice();
                        %>
                        <tr id="tr<%=item.getProduct().getId()%>">
                            <td class="shoping__cart__item">
                                <img class="product-image" src="<%=product.getImage()%>" alt="Vegetable's Package">
                                <h5><%=product.getProduct_name()%></h5>
                            </td>
                            <td class="shoping__cart__price" id="pr<%=product.getId()%>">
                                <%= Utils.formatCurrency(product.getPrice())%> VND
                            </td>
                            <td class="shoping__cart__total" id="t<%=product.getId()%>">
                                <%=Utils.formatCurrency(total)%> VND
                            </td>
                            <td style="margin-top: 52px;padding: 0;" class="shoping__cart__quantity pro-qty">
                                <button style=" width: 32px;
    border: 1px solid;" id="decrease" class="btn-decrease">-</button>
                                <input style="height: 50px;" type="number" id="p<%=product.getId()%>" class="input-number"
                                       value="<%=item.getQuantity()%>" min="0" max="<%=product.getInventory_quantity()%>" />
                                <button style=" width: 32px;
    border: 1px solid;" id="increase" class="btn-increase">+</button>
                            </td>
                            <td class="shoping__cart__item__close">
<%--                                <form action="ShoppingCartCL" method="get">--%>
<%--                                    <input type="hidden" name="action" value="delete">--%>
<%--                                    <input type="hidden" name="id" value="<%= cartItem.getProduct().getId() %>">--%>
<%--                                    <button type="submit" class="icon_close"></button>--%>
<%--                                </form>--%>
                                <button class="btn btn-success" onClick="changeStatus(<%=product.getId()%>, 0, 'delete')">Xoá</button>
                            </td>
                            <%
                                        }
                                    }
                                }
                            %>
                        </tr>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
        <div class="row">
            <div class="col-lg-12">
                <div class="shoping__cart__btns">
                    <a href="ProductController" class="primary-btn cart-btn">TIẾP TỤC MUA SẮM</a>
                    <%--                    <p class="text-danger"><%=e%></p>--%>
                    <%--                    <button id="button2" type="submit" class="primary-btn cart-btn cart-btn-right">--%>
                    <%--                        <span class="icon_loading"></span>--%>
                    <%--                        Cập nhật giỏ hàng--%>
                    <%--                    </button>--%>
                </div>
            </div>
            <div class="col-lg-6">
                <div class="shoping__continue">
                    <div class="shoping__discount">
                        <h5>Mã giảm giá</h5>
                        <span id="errorDiscount" style="color: red;"></span>
                        <form action="#">
                            <input type="text" placeholder="Điền mã của bạn vào" id="discount" value="<%=session.getAttribute("discount")==null?"":((Discount)session.getAttribute("discount")).getCode()%>">
                            <button type="submit" class="site-btn" id="btnDiscount">Áp dụng mã</button>
                        </form>
                    </div>
                </div>
            </div>
            <div class="col-lg-6">
                <form action="ThanhToanCL" method="get" class="shoping__checkout">
                    <h5>Số tiền cần thanh toán</h5>
                    <ul>
                        <li>Giảm: <span  id="retain"><%=session.getAttribute("retain")==null?0.0:Utils.formatCurrency((double)session.getAttribute("retain"))%>VND</span></li>
                        <li >Tổng: <span id="result"><%=session.getAttribute("result")==null?0.0:Utils.formatCurrency((double)session.getAttribute("result"))%>VND</span></li>
                    </ul>
                    <input type="hidden" name="action" value="checkout">
                    <%--                    <a href="thanh-toan.jsp" class="primary-btn">TIẾN HÀNH THANH TOÁN</a>--%>
                    <%if(!shoppingCart.getCartItemList().isEmpty()){ %>
                    <input class="btn btn-success" type="submit" value="TIẾN HÀNH THANH TOÁN">
                    <%}%>
                </form>
            </div>
        </div>
    </div>
</section>
<!-- Shoping Cart Section End -->

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
                    <form>
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
        <div class="row">
            <div class="col-lg-12">
                <div class="footer__copyright">

                    <div class="footer__copyright__payment"><img src="assets/img/payment-item.png" alt=""></div>
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
<script src="assets/js/mixitup.min.js"><d/script>
<script src="assets/js/owl.carousel.min.js"></script>
<script src="assets/js/main.js"></script>
<script>
    var context = "${pageContext.request.contextPath}";
    $(document).ready(function() {
        $('#btnDiscount').click(function (event) {
            event.preventDefault();
            var discountName = $('#discount').val();
            console.log(discountName)
            $.ajax({
                type: 'POST',
                data: {
                    discount: discountName,
                    action: "check"
                },
                url: 'ShoppingCartCL',
                success: function (response) {
                    const retain = document.getElementById("retain");
                    const result = document.getElementById("result");
                    if (response.state === "notfound" || response.state === "notempty") {
                        $('#errorDiscount').html(response.error);
                    } else {
                        Swal.fire({
                            position: "center",
                            icon: "success",
                            title: "Thêm Mã Giảm giá Thành Công!",
                            showConfirmButton: false,
                            timer: 1500
                        });
                        $('#errorDiscount').html("");
                    }
                    result.innerHTML = new Intl.NumberFormat('vi-VN', { style: 'decimal', maximumFractionDigits: 0 }).format(response.result) + ' VND';
                    retain.innerHTML = new Intl.NumberFormat('vi-VN', { style: 'decimal', maximumFractionDigits: 0 }).format(response.rect) + ' VND';
                }
            });
        });
    });
</script>
<script>
    document.addEventListener('DOMContentLoaded', function() {
        document.querySelectorAll('.pro-qty').forEach(function (proQty){
            const input = proQty.querySelector('.input-number');
            const btnIncrease = proQty.querySelector('.btn-increase');
            const btnDecrease = proQty.querySelector('.btn-decrease');

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

            btnIncrease.addEventListener('click', function() {
                const max = parseInt(input.max, 10);
                let value = parseInt(input.value, 10);
                if (value < max) {
                    input.value = value + 1;
                    changeStatus(input.id.slice(1), value + 1, 'put');
                }
            });

            btnDecrease.addEventListener('click', function() {
                const min = parseInt(input.min, 10);
                let value = parseInt(input.value, 10);
                if (value > min) {
                    input.value = value - 1;
                    changeStatus(input.id.slice(1), value - 1, 'put');
                }
            });
        })

    });


    function changeStatus(pid, quantity, action) {
        // Lấy giá tiền từng sản phẩm và loại bỏ các ký tự không phải số (VND và dấu phẩy)
        const priceText = document.querySelector('#pr' + pid).textContent.replace(/[^0-9]/g, '');
        const price = parseInt(priceText, 10);
        // Tính toán tổng tiền mới
        const total = price * quantity;
        // Cập nhật DOM cho tổng tiền của sản phẩm
        document.querySelector('#t' + pid).textContent = total.toLocaleString('vi-VN') + ' VND';
        // Cập nhật số lượng trong DOM, nếu số lượng là 0, xóa hàng
        if (quantity === 0) {
            document.querySelector("#tr" + pid).remove();
        } else {
            document.querySelector('#p' + pid).textContent = quantity;
        }
        console.log(pid, quantity, action)
        $.ajax({
            url: "ShoppingCartCL",
            type: 'POST',
            data: {
                id: pid,
                quantity: quantity,
                action: action
            },
            success: function(response) {
                console.log(response)
                Swal.fire({
                    position: "center",
                    icon: "success",
                    title: "Thay Đổi Giỏ Hàng Thành Công!",
                    showConfirmButton: false,
                    timer: 1500
                });
                const badge = document.getElementById("badge");
                const result = document.getElementById("result");
                const please = document.getElementById("please");
                const container = document.getElementById("container");
                const retain = document.getElementById("retain");
                badge.innerHTML = response.total;
                if (response.state === "zero") {
                    please.style.display = "block";
                    please.innerHTML = "Vui lòng mua sắm";
                    container.style.display = "none";
                } else {

                    result.innerHTML = new Intl.NumberFormat('vi-VN', { style: 'decimal', maximumFractionDigits: 0 }).format(response.result) + ' VND';
                    retain.innerHTML = new Intl.NumberFormat('vi-VN', { style: 'decimal', maximumFractionDigits: 0 }).format(response.rect) + ' VND';
                    please.style.display = "none";
                }
            }
        });
    }
</script>
</body>
</html>