package controller.Admin;

import bean.Import;
import dao.LogDao;
import dao.admin.ImportDao;
import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet(name = "ImportManagement", value = "/importManagement")
public class ImportController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html; charset=UTF-8");
        List<Import> importOrders = ImportDao.getList(); // lấy ra danh sách import
        req.setAttribute("importOrders", importOrders);
        req.getRequestDispatcher("admin_page/quanlynhaphang.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json; charset=UTF-8");
        String action = req.getParameter("action");
        String ipAddress = req.getHeader("X-FORWARDED-FOR");
        if (ipAddress == null) {
            ipAddress = req.getRemoteAddr();
        }

        Map<String, String> result = new HashMap<>();
        if (action != null) {
          try{  switch (action) {
                case "create":
                    Import newOrder = new Import();
                    newOrder.setId_product(Integer.parseInt(req.getParameter("productId")));
                    newOrder.setQuantity(Integer.parseInt(req.getParameter("quantity")));
                    newOrder.setPrice(Double.parseDouble(req.getParameter("price")));
                    boolean insertSuccess = ImportDao.insertImport(newOrder);
                    LogDao.getInstance().insertModel(newOrder,ipAddress,1,"");
                    result.put("status", insertSuccess ? "success" : "error");
                    break;
                case "delete":
                    int orderIdToDelete = Integer.parseInt(req.getParameter("orderId"));
                    boolean deleteSuccess = ImportDao.deleteImport(orderIdToDelete);
                    result.put("status", deleteSuccess ? "success" : "error");
                    break;
                case "update":
                    int orderIdToUpdate = Integer.parseInt(req.getParameter("orderId"));
                    Import oldIpo = ImportDao.getImportById(orderIdToUpdate);
                    String status = req.getParameter("status");
                    boolean updateSuccess = ImportDao.updateImport(orderIdToUpdate, status);
                    Import newIpo = ImportDao.getImportById(orderIdToUpdate);
                    LogDao.getInstance().updateModel(oldIpo,newIpo,ipAddress,1,"");
                    result.put("status", updateSuccess ? "success" : "error");
                    break;
            }}
             catch (Exception e) {
                e.printStackTrace();
                result.put("status", "error");
                result.put("message", e.getMessage());}
        } else {
            result.put("message", "Action is null");
        }

        Gson gson = new Gson();
        String jsonResponse = gson.toJson(result);
        resp.getWriter().write(jsonResponse);
    }
}
