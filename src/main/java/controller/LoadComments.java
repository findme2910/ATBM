package controller;

import bean.ProductReview;
import dao.ProductReviewDao;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(urlPatterns = "/LoadComments")
public class LoadComments extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String productId = request.getParameter("productId");
        String rating = request.getParameter("rating");
        int id = Integer.parseInt(productId);
        ProductReviewDao dao = new ProductReviewDao();
        List<ProductReview> productReviewList;
        if (rating == null || rating.equals("all")) {
            productReviewList = dao.getListReview(id);
        } else {
            int ratingValue = Integer.parseInt(rating);
            productReviewList = dao.getListReviewByRating(id, ratingValue);
        }
        request.setAttribute("productReviewList", productReviewList);
        request.getRequestDispatcher("productReviews.jsp").forward(request, response);
    }
}

