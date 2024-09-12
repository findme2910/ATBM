package controller;

// ... your code ...


import Service.IProductService;
import Service.ProductService;

import bean.*;

import bean.Comment;
import bean.ProductReview;
import bean.Products;
import bean.User;

import dao.CommentDAO;
import dao.OrdersDAO;
import dao.ProductReviewDao;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet(urlPatterns = "/ProductInfor")
public class ProductInfor extends HttpServlet {
    IProductService pro = new ProductService();


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        HttpSession session = request.getSession(true);

        String id = request.getParameter("id_product");
        if (id != null && !id.isEmpty()) {
            try {
                String ip = request.getHeader("X-FORWARDED-FOR");
                if (ip == null) ip = request.getRemoteAddr();
                Products prod = pro.findById(Integer.parseInt(id));
                List<Products> relatedProducts = pro.getRelatedProducts(prod.getId_category(),prod.getId());
                ShoppingCart cart = (ShoppingCart) session.getAttribute("cart");
                User user = (User) session.getAttribute("auth");
                int remain = prod.getInventory_quantity();
                if (cart != null && !cart.getCartItemList().isEmpty() && user != null) {
                    for (CartItem item : cart.getCartItemList()) {
                        if (item.getProduct().getId() == prod.getId()) {
                            remain = prod.getInventory_quantity() - item.getQuantity();
                            break;
                        }
                    }
                }
                request.setAttribute("relatedProducts", relatedProducts);
                request.setAttribute("proID", prod);
                List<ProductReview> productReviews;
                productReviews = ProductReviewDao.getListReview(Integer.parseInt(id));
                double averageRating = averageRating(productReviews);
                request.setAttribute("productReviews", productReviews);
                request.setAttribute("averageRating", averageRating);
                if (remain > 0) {
                    request.setAttribute("remain", remain);
                } else {
                    request.setAttribute("error", "Bạn đã thêm số lượng sản phẩm tối đa vào giỏ!");
                }
                request.getRequestDispatcher("thong-tin-don-hang.jsp").forward(request,response);
            } catch (Exception e) {
                out.println(e.getLocalizedMessage());
            }
        } else {
            out.println("Product ID is missing");
        }
        out.flush();
        out.close();

    }

    public double averageRating(List<ProductReview>productReviews){
        double sum = 0;
        for (ProductReview review : productReviews){
            sum +=review.getRating();
        }
        return sum/ productReviews.size();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        String commentText = request.getParameter("content");
        int rating = Integer.parseInt(request.getParameter("rating"));
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        String id_product = request.getParameter("productId");
        System.out.println(id_product);
        int productId = Integer.parseInt(id_product);
        int orderDetailID = Integer.parseInt(request.getParameter("orderDetailID"));
        System.out.println(orderDetailID);
        if (user != null) {
            ProductReview review = new ProductReview();
            review.setUser_name(user.getUsername());
            review.setContent(commentText);
            review.setRating(rating);
            review.setId_user(user.getId());
            review.setId_product(productId);

            ProductReviewDao dao = new ProductReviewDao();
            dao.insertProductReview(review);
            OrdersDAO ordersDao = new OrdersDAO();
            ordersDao.updateReviewStatus(orderDetailID);
            response.sendRedirect(request.getContextPath() + "/ProductInfor?id_product=" + productId);
        } else {
            response.sendRedirect(request.getContextPath() + "/login");
        }
    }

}
