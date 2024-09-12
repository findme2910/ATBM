<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql"%>
<%@ page import="bean.Product" %>
<%@ page import="bean.ShoppingCart" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="java.util.Map" %>
<%@ page import="bean.CartItem" %>
<%@ page import="controller.ShoppingCartCL" %>
<%@ page import="bean.User" %>
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
    <link rel="stylesheet" href="assets/css/Log_Regis.css">
    <script src="assets/js/log_reg.js" defer></script>
    <style>
        .btn_chosePay {
            display: flex;
            justify-content: center;
            margin-bottom: 20px;
        }
        .btn_chosePay .btn {
            background-color: #0069d9;
            flex: 1;
            margin: 0 10px;
        }
        .payBeforeReceive {
            display: none;
        }
        .active {
            background-color: #28a745 !important;
            color: white !important;
        }

    </style>
</head>

<body>
<%
    ShoppingCart shoppingCart = (ShoppingCart) session.getAttribute("cart");
    List<CartItem> cartItems = shoppingCart.getCartItemList();
    User auth = (User) session.getAttribute("user");
%>

<jsp:include page="layout/header.jsp"/>
<%----%>
<!-- Breadcrumb Section Begin -->
<section class="breadcrumb-section set-bg" data-setbg="assets/img/breadcrumb.jpg">

    <div class="container">
        <div class="row">
            <div class="col-lg-12 text-center">
                <div class="breadcrumb__text">
                    <h2>Thủ tục thanh toán</h2>
                    <div class="breadcrumb__option">
                        <a href="HomePageController">Trang chủ</a>
                        <span>Thủ tục thanh toán</span>
                    </div>
                </div>
            </div>
        </div>
    </div>
</section>
<!-- Breadcrumb Section End -->

<!-- Checkout Section Begin -->
<section class="checkout spad">

    <div class="container">
        <div class="row">
            <div class="col-lg-12">
                <h6><span class="icon_tag_alt"></span> Có mã giảm giá? <a href="#">Bấm vào đây</a> để áp dụng mã giảm giá
                </h6>
            </div>
        </div>
        <div class="checkout__form">
            <h4>Thông tin thanh toán</h4>
<%--            <div class="btn_chosePay">--%>
<%--                <button type="button" class="btn btn-primary active" id="btn-Payafter">Thanh toán sau khi nhận hàng</button>--%>
<%--                <button type="button" class="btn btn-success" id="btn-Paybefore">Thanh toán online</button>--%>
<%--            </div>--%>
            <form action="ThanhToanCL" method="post" class="payAfterReceive">
                <div class="row">
                    <div class="col-lg-6 col-md-6">
                        <div class="row">
<%--                            <div class="col-lg-6">--%>
<%--                                <div class="checkout__input">--%>
<%--                                    <p>Tên<span>*</span></p>--%>
<%--                                    <input type="text" name = "firstname" value="<%=request.getAttribute("firstname")%>">--%>
<%--                                </div>--%>
<%--                            </div>--%>
                            <div class="col-lg-12">
                                <div class="checkout__input">
                                    <p>Họ Tên<span>*</span></p>
                                    <input id="username" type="text" name ="username" required value="<%=request.getAttribute("username")%>">
                                </div>
                            </div>
                        </div>
                        <div class="checkout__input">
                            <p>Tỉnh / Thành phố<span>*</span></p>

                                <select id="tinh" name="tinh" class="form-control" title="Chọn Tỉnh Thành">
                                    <option value="0">Tỉnh Thành</option>
                                </select>
                        </div>
                        <div class="checkout__input">
                            <p>Quận/ Huyện<span>*</span></p>
                            <select class="css_select form-control" id="quan"  name="quan" title="Chọn Quận Huyện">
                                <option value="0">Quận Huyện</option>
                            </select>
                        </div>
                        <div class="checkout__input">
                            <p>Phường, xã<span>*</span></p>
                            <select class="css_select form-control" id="phuong" name="phuong" title="Chọn Phường Xã">
                                <option value="0">Phường Xã</option>
                            </select>
                        </div>
                        <div class="checkout__input">

                            <p>Số nhà<span>*</span></p>
                            <input type="text" id="homeNumber" name ="homeNumber" required placeholder="Số nhà" class="checkout__input__add">


                        </div>
                        <div class="row">
                            <div class="col-lg-12">
                                <div class="checkout__input">
                                    <p>Số điện thoại<span>*</span></p>
                                    <input id="phoneNumber" type="tel" pattern="^\+?[0-9]{1,3}?[-. ]?\(?[0-9]{3}\)?[-. ]?[0-9]{3}[-. ]?[0-9]{4}$" required name="phone" value="<%=request.getAttribute("phone")%>">
                                </div>
                            </div>
                        </div>
                        <div class="checkout__input__checkbox">
                        </div>
                    </div>
                    <div class="col-lg-6 col-md-6">
                        <div class="checkout__order">
                            <h4>Đơn hàng của bạn</h4>
                            <div class="checkout__order__products d-flex align-items-center justify-content-between"><span>Ảnh</span><span>Tên sản phẩm</span><span>Số lượng</span><span>Tổng tiền</span></div>
                            <ul>
                                <%
                                        if(cartItems!=null){
                                            for (CartItem i :  cartItems) {
                                %>
                                    <li class="d-flex align-items-center justify-content-between my-4">
                                        <img src="<%=i.getProduct().getImage()%>" alt="" width="40" height="40"/>
                                       <span class="w-25 text-truncate d-block"> <%=i.getProduct().getProduct_name()%></span>
                                       <span> <%=i.getQuantity()%></span>
                                        <span>
                                            <%=i.getTotalPrice()%>
                                        </span>
                                    </li>
                                <%
                                       }
                                   } else {
                                %>
                                    <li>Trống</li>
                                <%
                                    }
                                %>
                            </ul>
                            <!-- <div class="checkout__order__subtotal">Thuế <span>0₫</span></div> -->
                            <div class="checkout__order__total">Tổng cộng: <%=shoppingCart.getTotalPrice()%></div>
<%--                            <div class="checkout__input__checkbox">--%>
<%--                                <label for="acc-or">--%>
<%--                                    Tạo tài khoản mới?--%>
<%--                                    <input type="checkbox" id="acc-or">--%>
<%--                                    <span class="checkmark"></span>--%>
<%--                                </label>--%>
<%--                            </div>--%>

<%--                            <div class="checkout__input__checkbox">--%>
<%--                                <label for="payment">--%>
<%--                                    Chấp nhận thanh toán--%>
<%--                                    <input type="checkbox" id="payment">--%>
<%--                                    <span class="checkmark"></span>--%>
<%--                                </label>--%>
<%--                            </div>--%>
<%--                            <div class="checkout__input__checkbox">--%>
<%--                                <label for="paypal">--%>
<%--                                    Paypal--%>
<%--                                    <input type="checkbox" id="paypal">--%>
<%--                                    <span class="checkmark"></span>--%>
<%--                                </label>--%>
<%--                            </div>--%>
                            <input id="hidden" type="hidden" name="action" value="order">
                            <button type="submit" class="site-btn">Đặt hàng</button>
                            <button type="button" class="site-btn" id="payButton">Thanh toán ngay</button>
                        </div>
                    </div>
                </div>
            </form>
        </div>
    </div>
</section>
<!-- Checkout Section End -->

<!-- Footer Section Begin -->
<jsp:include page="layout/footer.jsp"/>
<!-- Footer Section End -->


<!-- Js Plugins -->
<script src="assets/js/bootstrap.min.js"></script>
<script src="assets/js/jquery.nice-select.min.js"></script>
<script src="assets/js/jquery-ui.min.js"></script>
<script src="assets/js/jquery.slicknav.js"></script>
<script src="assets/js/mixitup.min.js"></script>
<script src="assets/js/owl.carousel.min.js"></script>
<script src="assets/js/main.js"></script>

<script>
    document.getElementById("payButton").addEventListener("click",function () {
        const username = document.getElementById("username").value;
        const amount = Math.round(<%= shoppingCart.getTotalPrice() %>);
        const tinhText = document.getElementById('tinh').options[document.getElementById('tinh').selectedIndex].text;
        const quanText= document.getElementById('quan').options[document.getElementById('quan').selectedIndex].text;
        const phuongText = document.getElementById('phuong').options[document.getElementById('phuong').selectedIndex].text;
        const homeNumber = document.getElementById("homeNumber").value;
        const phoneNumber = document.getElementById("phoneNumber").value;

        console.log(username+"/"+amount+"/"+tinhText+"/"+quanText+"/"+phuongText+"/"+homeNumber+"/"+phoneNumber);

        const data = {
            tinhText:tinhText,
            quanText: quanText,
            phuongText: phuongText,
            vnp_OrderInfo: "Thanh toan don hang",
            ordertype: "Sample order type",
            amount: amount,
            bankcode: "NCB",
            language: "vn",
            txt_billing_mobile: "0123456789",
            txt_billing_email: "example@example.com",
            txt_billing_fullname: "John Doe",
            txt_inv_addr1: "123 Sample Street",
            txt_bill_city: "Hanoi",
            txt_bill_country: "Vietnam",
            txt_bill_state: "HN",
            txt_inv_mobile: "0123456789",
            txt_inv_email: "example@example.com",
            txt_inv_customer: "John Doe",
            txt_inv_addr1: "123 Sample Street",
            txt_inv_company: "Sample Company",
            txt_inv_taxcode: "123456789",
            cbo_inv_type: "I",
        };
        const formBody = Object.keys(data).map(key => encodeURIComponent(key) + '=' + encodeURIComponent(data[key])).join('&');
        const xhr = new XMLHttpRequest();
        xhr.open("POST", "http://localhost:8081/payByVN", true);
        xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
        xhr.onreadystatechange = function() {
            if (xhr.readyState === 4 && xhr.status === 200) {
                const response = JSON.parse(xhr.responseText);
                if (response.code === "00") {
                    // Redirect to payment URL
                    window.location.href = response.data;
                } else {
                    alert("Error: " + response.message);
                }
            }
        };
        xhr.send(formBody);
    })
</script>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
<script src="https://esgoo.net/scripts/jquery.js"></script>
<script>
    $(document).ready(function () {
        // Lấy tỉnh thành
        $.getJSON('https://cors-anywhere.herokuapp.com/https://esgoo.net/api-tinhthanh/1/0.htm', function (data_tinh) {

            if (data_tinh.error == 0) {
                $.each(data_tinh.data, function (key_tinh, val_tinh) {
                    $("#tinh").append('<option value="' + val_tinh.id + '" data-full-name="' + val_tinh.full_name + '">' + val_tinh.full_name + '</option>');
                    console.log(val_tinh.full_name)
                });
                console.log($("#tinh").value)
            }
        });


        $("#tinh").change(function (e) {
            var idtinh = $(this).val(); // lấy ID của tỉnh
            var fullNameTinh = $("#tinh option:selected").data('full-name'); // lấy full name của tỉnh đã chọn
            // Lấy quận huyện
            $.getJSON('https://cors-anywhere.herokuapp.com/https://esgoo.net/api-tinhthanh/2/' + idtinh + '.htm', function (data_quan) {
                if (data_quan.error == 0) {
                    $("#quan").empty().append('<option value="0">--Chọn Quận Huyện--</option>');
                    $("#phuong").empty().append('<option value="0">--Chọn Phường/ Xã/ Thị trấn--</option>');
                    $.each(data_quan.data, function (key_quan, val_quan) {
                        $("#quan").append('<option value="' + val_quan.id + '" data-full-name="' + val_quan.full_name + '">' + val_quan.full_name + '</option>');
                    });
                }
            });
        });

        $("#quan").change(function (e) {
            var idquan = $(this).val(); // lấy ID của quận/huyện
            var fullNameQuan = $("#quan option:selected").data('full-name'); // lấy full name của quận/huyện đã chọn
            // Lấy phường xã
            $.getJSON('https://cors-anywhere.herokuapp.com/https://esgoo.net/api-tinhthanh/3/' + idquan + '.htm', function (data_phuong) {
                if (data_phuong.error == 0) {
                    $("#phuong").empty().append('<option value="0">--Chọn Phường/ Xã/ Thị trấn--</option>');
                    $.each(data_phuong.data, function (key_phuong, val_phuong) {
                        $("#phuong").append('<option value="' + val_phuong.id + '" data-full-name="' + val_phuong.full_name + '">' + val_phuong.full_name + '</option>');
                    });
                }
            });
        });
    });
</script>
</body>
</html>