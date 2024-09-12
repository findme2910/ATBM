<%@ page import="java.util.List" %>
<%@ page import="bean.OrderDetailTable" %>
<%@ page import="bean.Util" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <meta charset="UTF-8">
  <title>Đánh Giá Sản Phẩm</title>
  <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
  <style>
    .star-rating {
      display: flex;
      flex-direction: row-reverse;
      justify-content: flex-end;
      margin-bottom: 10px;
    }

    .star-rating input {
      display: none;
    }

    .star-rating label {
      position: relative;
      width: 1em;
      color: #FFF;
      cursor: pointer;
      font-size: 2em; /* Adjust as needed */
      margin-bottom: 0;
    }

    .star-rating label::before {
      content: "★";
      position: absolute;
      opacity: 1;
      color: black; /* Star color */
      border: none; /* Remove border when hovered */
    }

    .star-rating label:hover:before,
    .star-rating label:hover ~ label:before {
      color: #FFD700; /* Yellow star color on hover */
      border: none; /* Remove border on hover */
    }

    .star-rating input:checked ~ label:before {
      color: #FFD700; /* Yellow star color when selected */
      border: none; /* Remove border when selected */
    }

    .star-rating input:checked + label:hover:before,
    .star-rating input:checked + label:hover ~ label:before,
    .star-rating input:checked ~ label:hover:before,
    .star-rating input:checked ~ label:hover ~ label:before {
      opacity: 1; /* Ensure opacity is 1 on hover when selected */
    }

    .review-item {
      border: 1px solid #ddd;
      padding: 15px;
      margin-bottom: 15px;
      border-radius: 10px; /* Rounded corners */
      display: flex;
      flex-wrap: wrap;
      background-color: #f9f9f9;
    }

    .review-item img {
      max-width: 200px;
      max-height: 200px;
      margin-right: 15px;
      border-radius: 10px; /* Rounded corners */
    }

    .review-item .details {
      flex: 1;
      display: flex;
      flex-direction: column;
      justify-content: center;
    }

    .review-item .details p {
      margin: 0;
    }

    .review-item form {
      margin-top: 10px;
      width: 100%;
    }

    .btn-primary {
      background-color: #007bff;
      border: none;
      border-radius: 7px;
    }

    .btn-primary:hover {
      background-color: #0056b3;
    }
  </style>
</head>
<body>
<div class="container mt-5">
  <h2 class="mb-4">Đánh Giá Sản Phẩm</h2>
  <% List<OrderDetailTable> orderDetails = (List<OrderDetailTable>) request.getAttribute("orderDetails");
    if (orderDetails != null) {
      for (OrderDetailTable detail : orderDetails) {
  %>
  <div class="review-item">
    <img src="<%= detail.getImg() %>" alt="<%= detail.getProduct_name() %>">
    <div class="details">
      <h4><%= detail.getProduct_name() %></h4>
      <p>Số Lượng: x<%=detail.getQuantity()%></p>
      <p>Ngày mua: <%=Util.formatTimestampToString(detail.getCreate_at())%></p>
      <p>Tổng Giá: <%=Util.formatCurrency(detail.getPriceDetails())%></p>
      <form action="ProductInfor" method="post" class="rating-form-<%= detail.getId_product() %>">
        <input type="hidden" name="productId" value="<%= detail.getId_product() %>">
        <input type="hidden" name="orderDetailID" value="<%= detail.getId() %>">
        <div class="star-rating rating-<%= detail.getId_product() %>-<%= detail.getCreate_at().getTime() %>">
          <input type="radio" id="star5-<%= detail.getId_product() %>-<%= detail.getCreate_at().getTime() %>" name="rating" value="5"><label for="star5-<%= detail.getId_product() %>-<%= detail.getCreate_at().getTime() %>">★</label>
          <input type="radio" id="star4-<%= detail.getId_product() %>-<%= detail.getCreate_at().getTime() %>" name="rating" value="4"><label for="star4-<%= detail.getId_product() %>-<%= detail.getCreate_at().getTime() %>">★</label>
          <input type="radio" id="star3-<%= detail.getId_product() %>-<%= detail.getCreate_at().getTime() %>" name="rating" value="3"><label for="star3-<%= detail.getId_product() %>-<%= detail.getCreate_at().getTime() %>">★</label>
          <input type="radio" id="star2-<%= detail.getId_product() %>-<%= detail.getCreate_at().getTime() %>" name="rating" value="2"><label for="star2-<%= detail.getId_product() %>-<%= detail.getCreate_at().getTime() %>">★</label>
          <input type="radio" id="star1-<%= detail.getId_product() %>-<%= detail.getCreate_at().getTime() %>" name="rating" value="1"><label for="star1-<%= detail.getId_product() %>-<%= detail.getCreate_at().getTime() %>">★</label>
        </div>
        <textarea name="content" class="form-control" placeholder="Nhập đánh giá của bạn" required></textarea>
        <button type="submit" class="btn btn-primary mt-2">Gửi Đánh Giá</button>
      </form>
    </div>
  </div>
  <% } } else { %>
  <p>Không có sản phẩm nào để đánh giá.</p>
  <% } %>
</div>
<script src="https://code.jquery.com/jquery-3.3.1.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
<script>
  $(document).ready(function() {
    $('form').on('submit', function(e) {
      var form = $(this);
      var ratingChecked = form.find('input[name="rating"]:checked').length > 0;
      if (!ratingChecked) {
        e.preventDefault();
        alert('Vui lòng chọn số sao trước khi gửi biểu mẫu');
      }
    });
  });
</script>
</body>
</html>
