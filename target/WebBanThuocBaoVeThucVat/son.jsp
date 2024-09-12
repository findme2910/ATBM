<%--
  Created by IntelliJ IDEA.
  User: 84828
  Date: 7/12/2024
  Time: 5:02 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 0;
            background-color: #f5f5f5;
        }

        .container {
            width: 85%;
            margin: auto;
            overflow: hidden;
        }

        .navbar {
            background-color: #fff;
            border-bottom: 1px solid #ccc;
        }

        .navbar ul {
            list-style: none;
            margin: 0;
            display: flex;
            justify-content: center;
            align-items: center;
            padding:0;
        }

        .navbar ul li {
            flex:1;
        }

        .navbar ul li a {
            text-decoration: none;
            color: #000;
            padding: 10px;
            display: block;
            width: 100%;
            text-align: center;

        }
.active{
    border-bottom: 2px solid #f60;
}
        .navbar input[type="text"] {
            margin: 10px 0;
            width: 100%;
            padding: 10px;
            border: none;
            background: #eee;
        }

        .order-details {
            background-color: #fff;
            padding: 20px;
            margin-top: 20px;
            border: 1px solid #ccc;
        }

        .shop {
            display: flex;
            align-items: center;
            justify-content: space-between;
            margin-bottom: 10px;
        }

        .shop .favorite {
            color: white;
            margin-right: 10px;
            background-color: #f60;
            padding: 5px;
        }

        .shop .shop-name {
            font-weight: bold;
            margin-right: 10px;
        }

        .shop .chat-btn, .shop .view-shop-btn {
            background-color: #f60;
            color: #fff;
            border: none;
            padding: 5px 10px;
            margin-right: 10px;
            cursor: pointer;
        }

        .product {
            display: flex;
            align-items: center;
            padding: 10px 0;
            border-top: 1px solid #eee;
            border-bottom: 1px solid #eee;
            gap:15px
        }

        .product img {
            width: 100px;
            height: auto;
        }

        .product-info {
            flex: 1;
            display: flex;
            flex-direction: column;
            gap:10px
        }

        .product-info p {
            margin: 0;
        }
        .product-price{
            display: flex;
            align-items: center;
            gap:10px
        }
        .product-price div:first-child{
            color: #999;
            font-size: 1.2em;
            text-decoration: line-through;
            font-weight: bold;
        }

        .product-price div:last-child{
            color: #f60;
            font-size: 1.2em;
            font-weight: bold;
        }
        .total-price{
            justify-content: flex-end;
            font-size: 1.2em;
            font-weight: 600;
            margin: 15px 0;
            display: flex;
            align-items: center;
            gap:5px
        }
        .total-price div{
            color: #f60;
        }
        .order-status {
            display: flex;
            align-items: center;
            margin-top: 20px;
        }

        .order-status .delivery-status {
            color: #0a0;
            margin-right: 10px;
        }

        .order-status-label {
            color: #f00;
            font-weight: bold;
        }

        .order-actions {
            display: flex;
            align-items: center;
            justify-content: flex-end;
            gap:15px
        }

        .order-actions button{
            font-size: 15px;
      width:160px;
            height: 38px;
            display: flex;
            align-items: center;
            justify-content:center;
            }
        .buy-again-btn{
    background-color: #f60;
            color:white;
            border:none;

        }
.contact-seller-btn{
background-color: transparent;
    color: #999;
    border: 1px solid #999;
}
    </style>
</head>
<body>
<div class="container">
    <div class="navbar">
        <ul>
            <li><a class="active" href="#">Tất cả</a></li>
            <li><a href="#">Chờ thanh toán</a></li>
            <li><a href="#">Vận chuyển</a></li>
            <li><a href="#">Chờ giao hàng</a></li>
            <li><a href="#">Hoàn thành</a></li>
            <li><a href="#">Đã hủy</a></li>
            <li><a href="#">Trả hàng/Hoàn tiền</a></li>
        </ul>
        <input type="text" placeholder="Bạn có thể tìm kiếm theo tên Shop, ID đơn hàng hoặc Tên Sản phẩm">
    </div>
    <div class="order-details">
        <div class="shop">
            <div>
                <span class="favorite">Yêu thích</span>
                <span class="shop-name">mybeautyhouse</span>
                <button class="chat-btn">Chat</button>
                <button class="view-shop-btn">Xem Shop</button>
            </div>
            <div class="order-status">
                <span class="delivery-status">Giao hàng thành công</span>
                <span class="order-status-label">HOÀN THÀNH</span>
            </div>
        </div>
        <div class="product">
            <img src="product-image.jpg" alt="Product Image">
            <div class="product-info">
                <p>[Mã SKAMFMW035 giảm 8% đơn 500K] Sữa Rửa Mặt Simple Gel Kind To Skin Refreshing Facial Wash Gel 150Ml</p>
                <span class="price">x1</span>
            </div>
            <div class="product-price">
                <div>₫89.000</div>
                <div>₫89.000</div>
            </div>
        </div>
        <div class="total-price">Thành tiền:<div> ₫0</div></div>
        <div class="order-actions">
            <button class="buy-again-btn">Mua Lại</button>
            <button class="contact-seller-btn">Liên Hệ Người Bán</button>
        </div>
    </div>
</div>
</body>
</html>
