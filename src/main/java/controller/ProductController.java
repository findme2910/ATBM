package controller;
//cmt là origin

import Service.ProductsService;
import bean.Pagination;
//
//import Service.IProductService;
//import Service.ProductsService;

import bean.Products;
import bean.User;
import bean.WishlistItem;
import dao.IProductDAO;
import dao.ProductDAO;
import dao.WishlistDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "ProductController", value = "/ProductController")
public class ProductController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private static final int PRODUCTS_PER_PAGE = 3;
    private static final int VISIBLE_PAGES = 5;
    private int totalPageHome;
    IProductDAO dao;
//
//    private static final int VISIBLE_PAGES = 5;
//    private ProductDAO dao = new ProductDAO();
//    private int totalPageHome;


    @Override
    public void init() throws ServletException {
        super.init();

        this.dao = new ProductDAO();
        this.totalPageHome = ProductsService.getInstance().getTotalPages();
//=======
//        totalPageHome = this.dao.getTotalPages();
//>>>>>>> origin/main
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        response.setCharacterEncoding("UTF-8");
        HttpSession session = request.getSession(true);

        String page = request.getParameter("currentPage");
        String order = request.getParameter("order");
        String name = request.getParameter("search");
        String action = request.getParameter("action");
        String idCate = request.getParameter("id_category");
        List<Products> list = new ArrayList<>();
        User user = (User) session.getAttribute("user");
        if(user != null){
            List<WishlistItem> wishlistItemList = WishlistDAO.getInstance().getWishlistByUser(user.getId());
            request.setAttribute("wishlistItemList", wishlistItemList);
        }
        String url;
        if(action != null && !action.equals("")) {
            if (action.equalsIgnoreCase("By Name")) {
                list = ProductsService.getInstance().searchByName(name);
           }
            session.setAttribute("action", action);
            session.setAttribute("name", name);
            session.setAttribute("words", name);
            url = "cuahang.jsp?page=search";
        } else {
            if (idCate == null) {
                idCate = "";
                list = ProductsService.getInstance().searchByName(idCate);
            } else {
                int cateId = Integer.parseInt(idCate);
                list = ProductsService.getInstance().findByCategory(cateId, "");
            }
            session.setAttribute("idCate", idCate);

            url = "cuahang.jsp";
        }
        if(order != null) {
            int orderValue = Integer.parseInt(order);
            if(orderValue == 2){
                dao.sortByPrice(list, true);
            } else if(orderValue == 3){
                dao.sortByPrice(list, false);
            }
            session.setAttribute("order", order);
        }

        int totalPageSearch = (int) Math.ceil((double) list.size() / PRODUCTS_PER_PAGE);
        Pagination pagination = new Pagination(PRODUCTS_PER_PAGE, list);
        int currentPage = 1;
        if (page != null) {
            currentPage = Integer.parseInt(page);
//=======
//        if (page == null || page.equals("home")) {
//            int currentPage = 1;
//            String numberPage = request.getParameter("currentPage");
//            if (numberPage != null) {
//                currentPage = Integer.parseInt(numberPage);
//            }
//            int startPage = Math.max(currentPage - VISIBLE_PAGES/2, 1);
//            int endPage = Math.min(startPage + VISIBLE_PAGES - 1, totalPageHome);
//            List<Products> products = this.dao.getProductsPerPage(currentPage);
//            session.setAttribute("words", "");
//            session.setAttribute("currentPageHome", currentPage);
//            session.setAttribute("startPageHome", startPage);
//            session.setAttribute("endPageHome", endPage);
//            session.setAttribute("products_per_page", this.dao.getProductsPerPageConstant());
//            session.setAttribute("ProductHome", products);
//            session.setAttribute("totalPageHome", totalPageHome);
//            request.getRequestDispatcher("cuahang.jsp?page=home").forward(request, response);
//        }
////        request.setAttribute("products", list);
//    }
//
//    @Override
//    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        request.setCharacterEncoding("UTF-8");
//        HttpSession session = request.getSession();
//        String search = request.getParameter("search");
//
//        int id = 0;
//        Object idCateObj = session.getAttribute("idCate");
//        if (idCateObj != null) {
//            id = Integer.parseInt(idCateObj.toString());
//>>>>>>> origin/main
        }
        int startPage = Math.max(currentPage - VISIBLE_PAGES / 2, 1);
        int endPage = Math.min(startPage + VISIBLE_PAGES - 1, totalPageSearch);
        List<Products> products = pagination.getProductPerPage(currentPage);
        session.setAttribute("currentPage", currentPage);
        session.setAttribute("startPage", startPage);
        session.setAttribute("endPage", endPage);
        session.setAttribute("products_per_page", PRODUCTS_PER_PAGE);
        session.setAttribute("Product", products);
        session.setAttribute("listProducts", list);
        session.setAttribute("totalPage", totalPageSearch);
        request.getRequestDispatcher(url).forward(request, response);
    }
}