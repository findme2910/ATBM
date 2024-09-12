<%@ page import="bean.Comment" %>
<%@ page import="java.util.List" %>
<%@ page import="bean.ProductReview" %>
<%@ page import="bean.Util" %>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<% List<ProductReview> productReviewList = (List<ProductReview>) request.getAttribute("productReviewList"); %>
<style>
    .review img {
        width: 40px;
        height: 40px;
        object-fit: cover;
    }
    .star-rating {
        color: #FFD700; /* Gold color for stars */
        font-size:20px;
    }
</style>
<div>
    <% for (ProductReview productReview : productReviewList) { %>
    <div class="review">
        <div class="d-flex align-items-center">
            <img src="<%=productReview.getPicture()%>" class="rounded-circle mr-3" alt="User Image">
            <div>
                <span class="review-user"><%= productReview.getUser_name() %></span>
                <span class="text-muted"><p><%= Util.formatTimestampToString(productReview.getCreate_at()) %></p></span>
            </div>
        </div>
        <div class="review-content mt-3" style="margin-top:0px !important;">
            <div class="star-rating">
                <% for (int i = 0; i < productReview.getRating(); i++) { %>
                ★
                <% } %>
                <% for (int i = productReview.getRating(); i < 5; i++) { %>
                ☆
                <% } %>
            </div>
            <p><%= productReview.getContent() %></p>
        </div>
    </div>
    <% } %>
</div>
