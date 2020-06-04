package servlets;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(urlPatterns = "/noaccess", name = "NoAccessServlet")
public class NoAccessServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        //req.setAttribute("nodata", "Нет доступа");
        HttpSession session = req.getSession(false);
        session.invalidate();
        RequestDispatcher dispatcher = req.getRequestDispatcher("/");
        dispatcher.forward(req, resp);

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        //req.setAttribute("nodata", "Нет доступа");
        HttpSession session = req.getSession(false);
        session.invalidate();
        RequestDispatcher dispatcher = req.getRequestDispatcher("/");
        dispatcher.forward(req, resp);

    }

}
