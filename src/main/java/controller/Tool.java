package controller;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "Tool", value = "/Tool")
public class Tool extends BaseServlet {

    @Override
    public void init() throws ServletException {
        super.init();

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        setRequestResponse(request, response);
        new Thread(() -> {
            try {
                toolDS.Main.main(new String[]{});
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();

        response.setContentType("text/plain");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write("Chạy tool thành công");
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        setRequestResponse(request, response);
        doGet(request, response);
    }


}