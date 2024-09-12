package controller;

import Service.DiscountService;
import Service.IProductService;
import Service.ProductService;
import bean.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "ShoppingCartCL", value = "/ShoppingCartCL")
public class ShoppingCartCL extends HttpServlet {
    IProductService productService = new ProductService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        HttpSession session = request.getSession(true);
        User user = (User) session.getAttribute("user");
        if (user == null) getServletContext().getRequestDispatcher("/login-register/login.jsp").forward(request, response);
        else {
            String ip = request.getHeader("X-FORWARDED-FOR");
            if (ip == null) ip = request.getRemoteAddr();

            Discount discount = (Discount) session.getAttribute("discount");
            ShoppingCart shoppingCart = (ShoppingCart) session.getAttribute("cart");
            if (!shoppingCart.getCartItemList().isEmpty()) {
                double re = 0.0;
                for(CartItem i : shoppingCart.getCartItemList()) {
                    Products product = productService.findById(i.getProduct().getId());
                    re += product.getPrice() * i.getQuantity();
                }
                if (discount != null) {
                    double result = re - re * discount.getSalePercent();
                    session.setAttribute("result", result);
                    session.setAttribute("retain", (re * discount.getSalePercent()));
                } else {
                    session.setAttribute("result", re);
                }
            }
            request.getRequestDispatcher("gio-hang.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();

        HttpSession session = request.getSession(true);
        User user = (User) session.getAttribute("user");
        if (user == null) {
            out.write("{\"status\": \"failed\"}");
            out.close();
            return;
        }

        ShoppingCart shoppingCart = (ShoppingCart) session.getAttribute("cart");

        Products product;
        CartItem cartItem = null;
        int id, quantity = 0;
        double retain, re = 0.0;

        Discount discount = (Discount) session.getAttribute("discount");
        if (discount == null) {
            discount = new Discount();
            discount.setSalePercent(0.0);
        }

        String ip = request.getHeader("X-FORWARDED-FOR");
        if (ip == null) ip = request.getRemoteAddr();

        String action = request.getParameter("action");
        switch (action) {
            case "check":
                String code = request.getParameter("discount");
                re = 0.0;
                for(CartItem i : shoppingCart.getCartItemList()) {
                    product = productService.findById(i.getProduct().getId());
                    re += i.getQuantity() * product.getPrice();
                }
                if (code != null && !code.isEmpty()) {
                    discount = DiscountService.getInstance().getCouponByCode(code);
                    if (discount == null) {
                        session.removeAttribute("discount");
                        session.removeAttribute("retain");
                        out.write("{ \"state\": \"notfound\", \"error\": \"Mã giảm giá không tồn tại!\", \"rect\": \""+0.0+"\", \"result\": \""+re+"\" }");
                        out.close();
                        return;
                    }
                } else {
                    out.write("{ \"state\": \"notempty\", \"error\": \"\", \"rect\": \""+ 0.0 +"\", \"result\": \""+re+"\" }");
                    session.removeAttribute("discount");
                    session.removeAttribute("retain");
                    out.close();
                    return;
                }
                session.setAttribute("discount", discount);
                retain = re - discount.getSalePercent() * re;
                session.setAttribute("result", retain);
                session.setAttribute("retain", re * discount.getSalePercent());
                out.write("{\"result\": \""+retain+"\", \"rect\": \"" + re * discount.getSalePercent() +"\"}");
                out.flush();
                out.close();
                break;
            case "delete":
                id = Integer.parseInt(request.getParameter("id"));
                shoppingCart.remove(id);
                session.setAttribute("cart", shoppingCart);
                re = 0.0;
                for(CartItem i : shoppingCart.getCartItemList()) {
                    product = productService.findById(i.getProduct().getId());
                    re += i.getQuantity() * product.getPrice();
                }
                retain = re - discount.getSalePercent() * re;

                if (re==0) {
                    out.write("{ \"state\": \"zero\", \"total\": \""+shoppingCart.getCartItemList().size()+"\", \"items\": \""+shoppingCart.getCartItemList()+"\" , \"result\": \""+retain+"\" }");
                } else {
                    out.write("{ \"total\": \""+shoppingCart.getCartItemList().size()+"\", \"items\": \""+shoppingCart.getCartItemList()+"\" , \"result\": \""+retain+"\", \"rect\": \"" + re * discount.getSalePercent() +"\"}");
                }
                session.setAttribute("totalItems", shoppingCart.getCartItemList().size());
                session.setAttribute("result", retain);
                session.setAttribute("retain", re * discount.getSalePercent());
                out.flush();
                out.close();
                break;
            case "put":
                id = Integer.parseInt(request.getParameter("id"));
                quantity = Integer.parseInt(request.getParameter("quantity"));
                product = productService.findById(id);

                if (quantity > 0) {
                    shoppingCart.update(product, quantity);
                } else if (quantity == 0) {
                    shoppingCart.remove(product.getId());
                }
                session.setAttribute("cart", shoppingCart);
                System.out.println(shoppingCart.getCartItemList());
                re = 0.0;
                for(CartItem i : shoppingCart.getCartItemList()) {
                    Products p = productService.findById(i.getProduct().getId());
                    re += p.getPrice() * i.getQuantity();
                }
                System.out.println(re);
                retain = re - (discount.getSalePercent() * re);
                if (re==0) {
                    out.write("{ \"state\": \"zero\", \"total\": \""+shoppingCart.getCartItemList().size()+"\", \"items\": \""+shoppingCart.getCartItemList()+"\" , \"result\": \""+retain+"\" }");
                } else {
                    out.write("{ \"total\": \""+shoppingCart.getCartItemList().size()+
                            "\", \"items\": \""+shoppingCart.getCartItemList()+"\" , \"result\": \""+retain+"\", " +
                            "\"rect\": \"" + re * discount.getSalePercent() +"\"}");
                }
                session.setAttribute("totalItems", shoppingCart.getCartItemList().size());
                session.setAttribute("result", retain);
                session.setAttribute("retain", re * discount.getSalePercent());
                out.flush();
                out.close();
                break;
            case "add":
                id = Integer.parseInt(request.getParameter("id"));
                int type = Integer.parseInt(request.getParameter("type"));
                String input = request.getParameter("quantity");

                product = productService.findById(id);
                if (type == 0) {
                    cartItem = new CartItem(product, 1);
                } else if (type == 1) {
                    if (input == null || input.isEmpty()) {
                        out.write("{\"status\": \"empty\", \"error\": \"The input do not empty!\"}");
                        out.close();
                        return;
                    }
                    quantity = Integer.parseInt(input);
                    if (quantity==0) {
                        out.write("{\"status\": \"bigger\", \"error\": \"Bạn chỉ được phép thêm số lượng sản phẩm khác 0!\"}");
                        out.close();
                        return;
                    }
                    cartItem = new CartItem(product, quantity);
                }
                int remain = product.getInventory_quantity();
                int count = 0;
                if (shoppingCart.getCartItemList().isEmpty()) {
                    remain = product.getInventory_quantity()- quantity;
                } else {
                    for (CartItem item : shoppingCart.getCartItemList()) {
                        if (item.getProduct().getId() == product.getId()) {
                            remain = product.getInventory_quantity() - item.getQuantity() - quantity;
                            count++;
                            break;
                        }
                    }
                }
                if (count == 0) remain = product.getInventory_quantity() - quantity;
                int contain = Integer.parseInt(request.getParameter("contain"));
                if (remain < 0) {
                    if (contain > 0) {
                        out.write("{\"status\": \"out\", \"error\": \"Số lượng thêm không được lớn hơn số lượng còn lại!\"}");
                    } else {
                        out.write("{\"status\": \"stock\", \"error\": \"Bạn đã thêm số lượng sản phẩm tối đa vào giỏ!\"}");
                    }
                    out.close();
                    return;
                }
                shoppingCart.add(cartItem);
                session.setAttribute("cart", shoppingCart);
                re = 0.0;
                for(CartItem i: shoppingCart.getCartItemList()) {
                    product = productService.findById(id);
                    re += i.getQuantity()*product.getPrice();
                }
                retain = re - discount.getSalePercent() * re;
                out.write("{ \"total\": \""+shoppingCart.getCartItemList().size()+"\", \"items\": \""+shoppingCart.getCartItemList()+"\" , \"prefix\": \""+remain+"\", \"rect\": \""+ re * discount.getSalePercent()+ "\"}");
                session.setAttribute("totalItems", shoppingCart.getCartItemList().size());
                session.setAttribute("result", retain);
                session.setAttribute("retain", re * discount.getSalePercent());
                out.flush();
                out.close();
                break;
        }
    }
}