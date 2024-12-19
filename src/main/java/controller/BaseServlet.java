package controller;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

public abstract class BaseServlet extends HttpServlet {
    protected HttpServletRequest request;
    protected HttpServletResponse response;

    protected void setRequestResponse(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
        this.request = request;
        this.response = response;
        setEncoding();
    }

    private void setEncoding() throws UnsupportedEncodingException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
    }

    protected void forward(String path) throws ServletException, IOException {
        request.getRequestDispatcher(path).forward(request, response);
    }
}
