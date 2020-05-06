package servlets;

import model.User;
import service.UserService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet(urlPatterns = "/forward", name = "ForwardServlet")

public class ForwardServlet extends HttpServlet {

    private UserService service = UserService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {


    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        User user = service.getUserById(new Long(req.getParameter("id")));
        req.setAttribute("user", user);//записываю атрибут на страницу перехода updateUser.jsp
        RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/updateUser.jsp");
        dispatcher.forward(req, resp);

    }
}